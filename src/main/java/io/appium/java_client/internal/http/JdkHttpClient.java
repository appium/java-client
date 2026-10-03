/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.java_client.internal.http;

import io.appium.java_client.http.BinaryMessage;
import io.appium.java_client.http.ClientConfig;
import io.appium.java_client.http.CloseMessage;
import io.appium.java_client.http.ConnectionFailedException;
import io.appium.java_client.http.HttpClient;
import io.appium.java_client.http.HttpHandler;
import io.appium.java_client.http.HttpMethod;
import io.appium.java_client.http.HttpRequest;
import io.appium.java_client.http.HttpResponse;
import io.appium.java_client.http.Message;
import io.appium.java_client.http.TextMessage;
import io.appium.java_client.http.WebSocket;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.Credentials;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.Authenticator;
import java.net.ConnectException;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpTimeoutException;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * An {@link HttpClient} based on {@code java.net.http}, adapted from Selenium's
 * {@code org.openqa.selenium.remote.http.jdk.JdkHttpClient} (Apache License 2.0).
 */
public class JdkHttpClient implements HttpClient {
    private static final Logger LOG = LoggerFactory.getLogger(JdkHttpClient.class);
    private static final AtomicInteger POOL_COUNTER = new AtomicInteger(0);
    private static final int MAX_REDIRECTS = 100;

    private final JdkHttpMessages messages;
    private final HttpHandler handler;
    private final List<WebSocket> websockets = new CopyOnWriteArrayList<>();
    private final ExecutorService executorService;
    private final Duration readTimeout;
    private final Duration connectTimeout;
    private java.net.http.HttpClient client;

    JdkHttpClient(ClientConfig config) {
        Objects.requireNonNull(config, "Client config must be set");
        this.messages = new JdkHttpMessages(config);
        this.readTimeout = config.readTimeout();
        this.connectTimeout = config.connectionTimeout();
        this.handler = config.filter().andFinally(this::execute0);

        String poolName = "JdkHttpClient-" + POOL_COUNTER.getAndIncrement();
        AtomicInteger threadCounter = new AtomicInteger(0);
        this.executorService = Executors.newCachedThreadPool(r -> {
            Thread thread = new Thread(r, poolName + "-" + threadCounter.getAndIncrement());
            thread.setDaemon(true);
            return thread;
        });

        var builder = java.net.http.HttpClient.newBuilder()
                .connectTimeout(connectTimeout)
                .followRedirects(java.net.http.HttpClient.Redirect.NEVER)
                .executor(executorService);

        URI baseUri = config.baseUri();
        String info = baseUri == null ? null : baseUri.getUserInfo();
        Credentials credentials = config.credentials();
        if (info != null && !info.trim().isEmpty()) {
            builder.authenticator(new PasswordAuthenticator(info));
        } else if (credentials != null) {
            if (!(credentials instanceof UsernameAndPassword)) {
                throw new IllegalArgumentException("Credentials must be a user name and password: " + credentials);
            }
            UsernameAndPassword uap = (UsernameAndPassword) credentials;
            builder.authenticator(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(uap.username(), uap.password().toCharArray());
                }
            });
        }

        Proxy proxy = config.proxy();
        if (proxy != null) {
            builder.proxy(new HttpProxySelector(proxy));
        }
        SSLContext sslContext = config.sslContext();
        if (sslContext != null) {
            builder.sslContext(sslContext);
        }
        String version = config.version();
        if (version != null) {
            builder.version(java.net.http.HttpClient.Version.valueOf(version));
        }
        this.client = builder.build();
    }

    @Override
    public WebSocket openSocket(HttpRequest request, WebSocket.Listener listener) {
        URI uri;
        try {
            uri = getWebSocketUri(request);
        } catch (URISyntaxException e) {
            throw new ConnectionFailedException(String.format("JdkWebSocket initial request execution error (uri: %s)",
                    maskUrlCredentials(messages.getRawUri(request))), e);
        }

        var builder = client.newWebSocketBuilder();
        request.getHeaderNames().forEach(name -> builder.header(name, request.getHeader(name)));
        CompletableFuture<Integer> closed = new CompletableFuture<>();
        CompletableFuture<java.net.http.WebSocket> socketFuture = builder
                .connectTimeout(connectTimeout)
                .buildAsync(uri, new JdkListener(listener, closed));

        java.net.http.WebSocket underlyingSocket;
        try {
            underlyingSocket = socketFuture.get(readTimeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (CancellationException e) {
            throw new ConnectionFailedException("JdkWebSocket initial request canceled", e);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            throw new ConnectionFailedException(String.format("JdkWebSocket initial request execution error (uri: %s)",
                    maskUrlCredentials(uri)), cause != null ? cause : e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ConnectionFailedException(String.format("JdkWebSocket initial request interrupted (uri: %s)",
                    maskUrlCredentials(uri)), e);
        } catch (java.util.concurrent.TimeoutException e) {
            socketFuture.cancel(true);
            throw new ConnectionFailedException(String.format("JdkWebSocket initial request timeout (uri: %s)",
                    maskUrlCredentials(uri)), e);
        }

        WebSocket websocket = new JdkWebSocket(underlyingSocket, closed);
        websockets.add(websocket);
        return websocket;
    }

    private static URI getWebSocketUri(HttpRequest request, URI uri) throws URISyntaxException {
        String scheme = uri.getScheme();
        if ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) {
            return new URI("http".equalsIgnoreCase(scheme) ? "ws" : "wss", uri.getUserInfo(), uri.getHost(),
                    uri.getPort(), uri.getPath(), uri.getQuery(), uri.getFragment());
        }
        return uri;
    }

    private URI getWebSocketUri(HttpRequest request) throws URISyntaxException {
        return getWebSocketUri(request, messages.getRawUri(request));
    }

    @Override
    public CompletableFuture<HttpResponse> executeAsync(HttpRequest request) {
        CompletableFuture<HttpResponse> result = new CompletableFuture<>();
        Future<?> future = executorService.submit(() -> {
            try {
                result.complete(handler.execute(request));
            } catch (Throwable t) {
                result.completeExceptionally(t);
            }
        });
        result.whenComplete((response, throwable) -> {
            // Interrupt the request if the caller gave up on it, also to avoid JDK-8258397
            if (throwable instanceof CancellationException
                    || throwable instanceof java.util.concurrent.TimeoutException) {
                future.cancel(true);
            }
        });
        return result.orTimeout(readTimeout.toMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public HttpResponse execute(HttpRequest req) throws UncheckedIOException {
        Future<HttpResponse> async = executeAsync(req);
        try {
            return async.get();
        } catch (CancellationException e) {
            throw new WebDriverException(e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            async.cancel(true);
            throw new WebDriverException(e.getMessage(), e);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof java.util.concurrent.TimeoutException) {
                throw new TimeoutException(cause);
            } else if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else if (cause instanceof Error) {
                throw (Error) cause;
            }
            throw new WebDriverException(cause != null ? cause : e);
        }
    }

    private HttpResponse execute0(HttpRequest req) throws UncheckedIOException {
        Objects.requireNonNull(req, "Request");
        LOG.debug("Executing request: {}", req);
        long start = System.currentTimeMillis();
        URI rawUri = messages.getRawUri(req);
        HttpMethod method = req.getMethod();
        try {
            // Redirects are handled here to avoid downgrading POST requests and JDK-8304701
            for (int i = 0; i < MAX_REDIRECTS; i++) {
                if (Thread.interrupted()) {
                    throw new InterruptedException(
                            String.format("http request has been interrupted: %s", describe(req)));
                }
                var request = messages.createRequest(req, method, rawUri);
                var response = client.send(request, BodyHandlers.ofInputStream());
                switch (response.statusCode()) {
                    case 303:
                        method = HttpMethod.GET;
                        // fall through
                    case 301:
                    case 302:
                    case 307:
                    case 308:
                        URI location = rawUri.resolve(getLocationHeader(response, method, rawUri));
                        checkNotDowngrade(rawUri, location);
                        rawUri = location;
                        continue;
                    default:
                        return messages.createResponse(response);
                }
            }
            throw new ProtocolException(String.format("Too many redirects: %d (%s)", MAX_REDIRECTS + 1,
                    describe(req)));
        } catch (HttpTimeoutException e) {
            throw new TimeoutException(String.format("Timeout when executing request (%s)", describe(req)), e);
        } catch (ConnectException e) {
            throw new ConnectionException(String.format("Connection error (%s)", describe(req)),
                    maskUrlCredentials(rawUri), e);
        } catch (IOException e) {
            throw new UncheckedIOException(String.format("Failed to execute request (%s)", describe(req)), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebDriverException(
                    String.format("%s when executing request (%s)", e.getMessage(), describe(req)), e);
        } finally {
            LOG.debug("Ending request {} in {}ms", req, System.currentTimeMillis() - start);
        }
    }

    private static void checkNotDowngrade(URI from, URI to) {
        if (isDowngradeFrom("https", from, to) || isDowngradeFrom("wss", from, to)) {
            throw new SecurityException(
                    String.format("Downgrade from secure to insecure connection (%s -> %s)", from, to));
        }
    }

    private static boolean isDowngradeFrom(String protocol, URI from, URI to) {
        return protocol.equalsIgnoreCase(from.getScheme()) && !protocol.equalsIgnoreCase(to.getScheme());
    }

    private String describe(HttpRequest req) {
        return String.format("%s %s", req.getMethod(), maskUrlCredentials(messages.getRawUri(req)));
    }

    private static String maskUrlCredentials(URI u) {
        if (u.getUserInfo() == null) {
            return u.toString();
        }
        try {
            return new URI(u.getScheme(), "***", u.getHost(), u.getPort(), u.getPath(), u.getQuery(),
                    u.getFragment()).toString();
        } catch (URISyntaxException e) {
            return u.toString();
        }
    }

    private static String getLocationHeader(java.net.http.HttpResponse<InputStream> response, HttpMethod method,
                                            URI uri) throws ProtocolException {
        var location = response.headers().firstValue("location");
        if (location.isEmpty()) {
            throw new ProtocolException(String.format(
                    "HTTP response with status %d but without \"location\" header (%s %s)",
                    response.statusCode(), method, maskUrlCredentials(uri)));
        }
        return location.get();
    }

    @Override
    public void close() {
        if (this.client == null) {
            return;
        }
        for (WebSocket websocket : websockets) {
            try {
                websocket.close();
            } catch (Exception e) {
                LOG.warn("Failed to close the websocket: {}", websocket, e);
            }
        }
        this.client = null;
        executorService.shutdown();
    }

    /**
     * Creates {@link JdkHttpClient} instances.
     */
    public static class Factory implements HttpClient.Factory {
        @Override
        public HttpClient createClient(ClientConfig config) {
            return new JdkHttpClient(Objects.requireNonNull(config, "Client config must be set"));
        }
    }

    private static final class HttpProxySelector extends ProxySelector {
        private final Proxy proxy;

        HttpProxySelector(Proxy proxy) {
            this.proxy = proxy;
        }

        @Override
        public List<Proxy> select(URI uri) {
            return uri.getScheme().toLowerCase(Locale.ENGLISH).startsWith("http") ? List.of(proxy) : List.of();
        }

        @Override
        public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
            // Nothing to do
        }
    }

    private static final class JdkListener implements java.net.http.WebSocket.Listener {
        private final WebSocket.Listener listener;
        private final CompletableFuture<Integer> closed;
        private final StringBuilder text = new StringBuilder();
        private final ByteArrayOutputStream binary = new ByteArrayOutputStream();

        JdkListener(WebSocket.Listener listener, CompletableFuture<Integer> closed) {
            this.listener = listener;
            this.closed = closed;
        }

        @Nullable
        @Override
        public CompletionStage<?> onText(java.net.http.WebSocket webSocket, CharSequence data, boolean last) {
            text.append(data);
            if (last) {
                String message = text.toString();
                text.setLength(0);
                LOG.debug("Text message received: {}", message);
                listener.onText(message);
            }
            webSocket.request(1);
            return null;
        }

        @Nullable
        @Override
        public CompletionStage<?> onBinary(java.net.http.WebSocket webSocket, ByteBuffer data, boolean last) {
            byte[] chunk = new byte[data.remaining()];
            data.get(chunk);
            binary.write(chunk, 0, chunk.length);
            if (last) {
                LOG.debug("Binary message received: {} bytes", binary.size());
                listener.onBinary(binary.toByteArray());
                binary.reset();
            }
            webSocket.request(1);
            return null;
        }

        @Nullable
        @Override
        public CompletionStage<?> onClose(java.net.http.WebSocket webSocket, int statusCode, String reason) {
            LOG.debug("Websocket closed with code {}", statusCode);
            closed.complete(statusCode);
            listener.onClose(statusCode, reason);
            return null;
        }

        @Override
        public void onError(java.net.http.WebSocket webSocket, Throwable error) {
            LOG.debug("A websocket error has occurred: {}", error.getMessage(), error);
            listener.onError(error);
        }
    }

    private final class JdkWebSocket implements WebSocket {
        private final java.net.http.WebSocket socket;
        private final CompletableFuture<Integer> closed;

        JdkWebSocket(java.net.http.WebSocket socket, CompletableFuture<Integer> closed) {
            this.socket = socket;
            this.closed = closed;
        }

        @Override
        public WebSocket send(Message message) {
            Supplier<CompletableFuture<java.net.http.WebSocket>> call;
            if (message instanceof BinaryMessage) {
                BinaryMessage binaryMessage = (BinaryMessage) message;
                call = () -> socket.sendBinary(ByteBuffer.wrap(binaryMessage.data()), true);
            } else if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                call = () -> socket.sendText(textMessage.text(), true);
            } else if (message instanceof CloseMessage) {
                if (socket.isOutputClosed()) {
                    LOG.debug("Output is closed, not sending the close message");
                    return this;
                }
                CloseMessage closeMessage = (CloseMessage) message;
                // The status code is -1 if the socket is already closed, so send a normal closure then
                int statusCode = closeMessage.code() == -1 ? 1000 : closeMessage.code();
                call = () -> {
                    var future = socket.sendClose(statusCode, closeMessage.reason());
                    awaitClosed();
                    return future;
                };
            } else {
                throw new IllegalArgumentException("Unsupported message type: " + message);
            }

            synchronized (socket) {
                try {
                    call.get().get(readTimeout.toMillis(), TimeUnit.MILLISECONDS);
                } catch (CancellationException e) {
                    throw new WebDriverException(e.getMessage(), e);
                } catch (ExecutionException e) {
                    throw new WebDriverException(e.getCause() == null ? e : e.getCause());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new WebDriverException(e.getMessage());
                } catch (java.util.concurrent.TimeoutException e) {
                    throw new TimeoutException(e);
                }
            }
            return this;
        }

        private void awaitClosed() {
            try {
                closed.get(4, TimeUnit.SECONDS);
            } catch (ExecutionException e) {
                LOG.warn("Failed to wait for the websocket to close", e.getCause());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (java.util.concurrent.TimeoutException e) {
                LOG.debug("Waiting for the websocket to close timed out");
            }
        }

        @Override
        public void close() {
            send(new CloseMessage(1000, "Closing the websocket"));
        }
    }
}
