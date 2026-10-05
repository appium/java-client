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

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import io.appium.java_client.http.ClientConfig;
import io.appium.java_client.http.Contents;
import io.appium.java_client.http.Filter;
import io.appium.java_client.http.HttpMethod;
import io.appium.java_client.http.HttpRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Credentials;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UsernameAndPassword;

import javax.net.ssl.SSLContext;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class JdkHttpClientTest {
    private static final class Config implements ClientConfig {
        private final URI baseUri;
        private Duration readTimeout = Duration.ofSeconds(10);
        private Filter filter = handler -> handler;
        private Credentials credentials;

        Config(URI baseUri) {
            this.baseUri = baseUri;
        }

        @Override
        public URI baseUri() {
            return baseUri;
        }

        @Override
        public Duration connectionTimeout() {
            return Duration.ofSeconds(5);
        }

        @Override
        public Duration readTimeout() {
            return readTimeout;
        }

        @Override
        public Duration wsTimeout() {
            return Duration.ofSeconds(5);
        }

        @Override
        public Filter filter() {
            return filter;
        }

        @Override
        public Proxy proxy() {
            return null;
        }

        @Override
        public Credentials credentials() {
            return credentials;
        }

        @Override
        public SSLContext sslContext() {
            return null;
        }

        @Override
        public String version() {
            return "HTTP_1_1";
        }
    }

    private HttpServer server;
    private final List<Map<String, String>> seenHeaders = new CopyOnWriteArrayList<>();
    private final Map<String, String> seenBodies = new ConcurrentHashMap<>();
    private final List<JdkHttpClient> clients = new ArrayList<>();

    @BeforeEach
    void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", this::handle);
        server.start();
    }

    @AfterEach
    void stopServer() {
        clients.forEach(JdkHttpClient::close);
        server.stop(0);
    }

    private String baseUrl(String path) {
        return "http://127.0.0.1:" + server.getAddress().getPort() + path;
    }

    private JdkHttpClient newClient(Config config) {
        var client = new JdkHttpClient(config);
        clients.add(client);
        return client;
    }

    private void handle(HttpExchange exchange) throws IOException {
        final String path = exchange.getRequestURI().getPath();
        Map<String, String> headers = new ConcurrentHashMap<>();
        exchange.getRequestHeaders().forEach((k, v) -> headers.put(k.toLowerCase(), String.join(",", v)));
        headers.put("x-method", exchange.getRequestMethod());
        headers.put("x-path", exchange.getRequestURI().toString());
        headers.put("x-protocol", exchange.getProtocol());
        seenHeaders.add(headers);
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        seenBodies.put(exchange.getRequestMethod() + " " + path, body);
        switch (path) {
            case "/redirect":
                exchange.getResponseHeaders().add("Location", "/target");
                exchange.sendResponseHeaders(exchange.getRequestMethod().equals("POST") ? 303 : 302, -1);
                break;
            case "/redirect-307":
                exchange.getResponseHeaders().add("Location", "/target");
                exchange.sendResponseHeaders(307, -1);
                break;
            case "/loop":
                exchange.getResponseHeaders().add("Location", "/loop");
                exchange.sendResponseHeaders(302, -1);
                break;
            case "/slow":
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                respond(exchange, 200, "late");
                break;
            case "/auth":
                String expected = "Basic "
                        + Base64.getEncoder().encodeToString("user:pass".getBytes(StandardCharsets.UTF_8));
                if (expected.equals(exchange.getRequestHeaders().getFirst("Authorization"))) {
                    respond(exchange, 200, "authorized");
                } else {
                    exchange.getResponseHeaders().add("WWW-Authenticate", "Basic realm=\"test\"");
                    exchange.sendResponseHeaders(401, -1);
                }
                break;
            case "/empty":
                exchange.sendResponseHeaders(204, -1);
                break;
            case "/binary":
                exchange.getResponseHeaders().add("Content-Type", "application/octet-stream");
                respond(exchange, 200, "bytes");
                break;
            default:
                respond(exchange, 200, "ok:" + exchange.getRequestMethod());
                break;
        }
        exchange.close();
    }

    private static void respond(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(bytes);
        }
    }

    @Test
    void sendsGetPostPutAndDeleteRequests() {
        var client = newClient(new Config(URI.create(baseUrl("/wd/hub"))));
        for (var method : List.of(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)) {
            var request = new HttpRequest(method, "/session/1/echo");
            if (method == HttpMethod.POST || method == HttpMethod.PUT) {
                request.setContent(Contents.utf8String("{\"a\":1}"));
            }

            var response = client.execute(request);

            assertEquals(200, response.getStatus());
            assertEquals("ok:" + method, response.contentAsString());
        }
        assertEquals("{\"a\":1}", seenBodies.get("POST /wd/hub/session/1/echo"));
        assertEquals("{\"a\":1}", seenBodies.get("PUT /wd/hub/session/1/echo"));
    }

    @Test
    void resolvesRelativeUrisAgainstTheBaseUriAndAppendsQueryParameters() {
        var client = newClient(new Config(URI.create(baseUrl("/wd/hub/"))));

        client.execute(new HttpRequest(HttpMethod.GET, "/status").addQueryParameter("a b", "c&d"));

        assertEquals("/wd/hub/status?a+b=c%26d", seenHeaders.get(0).get("x-path"));
    }

    @Test
    void usesHttp11WithoutUpgradeAndAnExplicitContentLength() {
        var client = newClient(new Config(URI.create(baseUrl(""))));

        client.execute(new HttpRequest(HttpMethod.POST, "/echo").setContent(Contents.utf8String("body")));

        var headers = seenHeaders.get(0);
        assertEquals("HTTP/1.1", headers.get("x-protocol"));
        assertFalse(headers.containsKey("upgrade"));
        assertFalse(headers.containsKey("http2-settings"));
        assertEquals("4", headers.get("content-length"));
        assertFalse(headers.containsKey("transfer-encoding"));
    }

    @Test
    void passesCustomHeadersAndAppliesFilters() {
        var config = new Config(URI.create(baseUrl("")));
        config.filter = next -> req -> {
            req.setHeader("X-Filtered", "yes");
            return next.execute(req);
        };
        var client = newClient(config);

        client.execute(new HttpRequest(HttpMethod.GET, "/echo").setHeader("X-Custom", "1"));

        assertEquals("yes", seenHeaders.get(0).get("x-filtered"));
        assertEquals("1", seenHeaders.get(0).get("x-custom"));
    }

    @Test
    void followsRedirectsAndTurnsPostIntoGetOnSeeOther() {
        var client = newClient(new Config(URI.create(baseUrl(""))));

        var get = client.execute(new HttpRequest(HttpMethod.GET, "/redirect"));
        var post = client.execute(new HttpRequest(HttpMethod.POST, "/redirect")
                .setContent(Contents.utf8String("x")));

        assertEquals("ok:GET", get.contentAsString());
        assertEquals("ok:GET", post.contentAsString());
    }

    @Test
    void keepsThePostMethodOnTemporaryRedirect() {
        var client = newClient(new Config(URI.create(baseUrl(""))));

        var response = client.execute(new HttpRequest(HttpMethod.POST, "/redirect-307")
                .setContent(Contents.utf8String("x")));

        assertEquals("ok:POST", response.contentAsString());
    }

    @Test
    void givesUpOnRedirectLoops() {
        var client = newClient(new Config(URI.create(baseUrl(""))));

        var error = assertThrows(RuntimeException.class,
                () -> client.execute(new HttpRequest(HttpMethod.GET, "/loop")));

        assertTrue(String.valueOf(error.getCause()).contains("Too many redirects"));
    }

    @Test
    void failsWithATimeoutIfTheServerIsTooSlow() {
        var config = new Config(URI.create(baseUrl("")));
        config.readTimeout = Duration.ofMillis(300);
        var client = newClient(config);

        assertThrows(TimeoutException.class, () -> client.execute(new HttpRequest(HttpMethod.GET, "/slow")));
    }

    @Test
    void reportsRefusedConnectionsAsConnectionException() {
        int port = server.getAddress().getPort();
        server.stop(0);
        var client = newClient(new Config(URI.create("http://127.0.0.1:" + port)));

        var error = assertThrows(ConnectionException.class,
                () -> client.execute(new HttpRequest(HttpMethod.GET, "/status")));

        assertInstanceOf(java.net.ConnectException.class, error.getCause());
    }

    @Test
    void authenticatesWithUserInfoOfTheBaseUri() {
        var client = newClient(new Config(URI.create("http://user:pass@127.0.0.1:" + server.getAddress().getPort())));

        assertEquals("authorized", client.execute(new HttpRequest(HttpMethod.GET, "/auth")).contentAsString());
    }

    @Test
    void authenticatesWithCredentials() {
        var config = new Config(URI.create(baseUrl("")));
        config.credentials = new UsernameAndPassword("user", "pass");
        var client = newClient(config);

        assertEquals("authorized", client.execute(new HttpRequest(HttpMethod.GET, "/auth")).contentAsString());
    }

    @Test
    void readsEmptyAndBinaryResponses() {
        var client = newClient(new Config(URI.create(baseUrl(""))));

        var empty = client.execute(new HttpRequest(HttpMethod.GET, "/empty"));
        var binary = client.execute(new HttpRequest(HttpMethod.GET, "/binary"));

        assertEquals(204, empty.getStatus());
        assertEquals("", empty.contentAsString());
        assertEquals(5, binary.getContent().length());
        assertEquals("bytes", binary.contentAsString());
    }

    @Test
    void doesNotSendAUserAgentOnItsOwnBeyondTheJdkDefault() {
        var client = newClient(new Config(URI.create(baseUrl(""))));

        client.execute(new HttpRequest(HttpMethod.GET, "/echo"));

        assertNull(seenHeaders.get(0).get("x-appium-test"));
    }

    @Test
    void closesPooledConnectionsOnClose() throws Exception {
        assumeTrue(Runtime.version().feature() >= 21, "java.net.http.HttpClient is AutoCloseable since Java 21");
        try (var rawServer = new ServerSocket(0, 0, InetAddress.getLoopbackAddress())) {
            var connectionClosed = new CompletableFuture<Boolean>();
            var serverThread = new Thread(() -> serveKeepAliveAndAwaitClose(rawServer, connectionClosed));
            serverThread.setDaemon(true);
            serverThread.start();
            var client = newClient(new Config(URI.create("http://127.0.0.1:" + rawServer.getLocalPort())));

            var response = client.execute(new HttpRequest(HttpMethod.GET, "/echo"));
            client.close();

            assertEquals("ok", response.contentAsString());
            assertTrue(connectionClosed.get(5, TimeUnit.SECONDS));
        }
    }

    private static void serveKeepAliveAndAwaitClose(ServerSocket rawServer, CompletableFuture<Boolean> closed) {
        try (var socket = rawServer.accept()) {
            socket.setSoTimeout(5000);
            var in = socket.getInputStream();
            var received = new StringBuilder();
            while (!received.toString().endsWith("\r\n\r\n")) {
                received.append((char) in.read());
            }
            var out = socket.getOutputStream();
            var reply = "HTTP/1.1 200 OK\r\nContent-Length: 2\r\nConnection: keep-alive\r\n\r\nok";
            out.write(reply.getBytes(StandardCharsets.US_ASCII));
            out.flush();
            closed.complete(in.read() == -1);
        } catch (IOException e) {
            closed.completeExceptionally(e);
        }
    }
}
