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

import io.appium.java_client.http.ClientConfig;
import io.appium.java_client.http.ConnectionFailedException;
import io.appium.java_client.http.Filter;
import io.appium.java_client.http.HttpMethod;
import io.appium.java_client.http.HttpRequest;
import io.appium.java_client.http.WebSocket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Credentials;

import javax.net.ssl.SSLContext;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Base64;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JdkWebSocketTest {
    private static final String GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";

    private ServerSocket serverSocket;
    private final CompletableFuture<String> clientFrame = new CompletableFuture<>();
    private final CompletableFuture<String> handshakeHeaders = new CompletableFuture<>();
    private JdkHttpClient client;

    @BeforeEach
    void startServer() throws IOException {
        serverSocket = new ServerSocket(0, 1, InetAddress.getLoopbackAddress());
        Thread thread = new Thread(this::serve, "test-websocket-server");
        thread.setDaemon(true);
        thread.start();
    }

    @AfterEach
    void stop() throws IOException {
        if (client != null) {
            client.close();
        }
        serverSocket.close();
    }

    private void serve() {
        try (Socket socket = serverSocket.accept()) {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            String request = readHeaders(in);
            handshakeHeaders.complete(request);
            String key = request.lines().filter(l -> l.toLowerCase().startsWith("sec-websocket-key:"))
                    .map(l -> l.substring(l.indexOf(':') + 1).trim()).findFirst().orElseThrow();
            String accept = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-1")
                    .digest((key + GUID).getBytes(StandardCharsets.US_ASCII)));
            out.write(("HTTP/1.1 101 Switching Protocols\r\nUpgrade: websocket\r\nConnection: Upgrade\r\n"
                    + "Sec-WebSocket-Accept: " + accept + "\r\n\r\n").getBytes(StandardCharsets.US_ASCII));
            writeFrame(out, 0x1, "hello from server".getBytes(StandardCharsets.UTF_8));
            clientFrame.complete(readTextFrame(new DataInputStream(in)));
            writeFrame(out, 0x8, new byte[]{0x03, (byte) 0xe8});
            out.flush();
            Thread.sleep(500);
        } catch (IOException | InterruptedException | NoSuchAlgorithmException e) {
            clientFrame.completeExceptionally(e);
        }
    }

    private static String readHeaders(InputStream in) throws IOException {
        var buffer = new ByteArrayOutputStream();
        while (!buffer.toString(StandardCharsets.US_ASCII).endsWith("\r\n\r\n")) {
            int b = in.read();
            if (b < 0) {
                break;
            }
            buffer.write(b);
        }
        return buffer.toString(StandardCharsets.US_ASCII);
    }

    private static void writeFrame(OutputStream out, int opcode, byte[] payload) throws IOException {
        out.write(0x80 | opcode);
        out.write(payload.length);
        out.write(payload);
        out.flush();
    }

    private static String readTextFrame(DataInputStream in) throws IOException {
        int first = in.readUnsignedByte();
        assertEquals(0x1, first & 0x0f);
        int length = in.readUnsignedByte() & 0x7f;
        byte[] mask = new byte[4];
        in.readFully(mask);
        byte[] payload = new byte[length];
        in.readFully(payload);
        for (int i = 0; i < length; i++) {
            payload[i] ^= mask[i % 4];
        }
        return new String(payload, StandardCharsets.UTF_8);
    }

    private ClientConfig config() {
        return new ClientConfig() {
            @Override
            public URI baseUri() {
                return URI.create("http://127.0.0.1:" + serverSocket.getLocalPort());
            }

            @Override
            public Duration connectionTimeout() {
                return Duration.ofSeconds(5);
            }

            @Override
            public Duration readTimeout() {
                return Duration.ofSeconds(5);
            }

            @Override
            public Duration wsTimeout() {
                return Duration.ofSeconds(5);
            }

            @Override
            public Filter filter() {
                return handler -> handler;
            }

            @Override
            public Proxy proxy() {
                return null;
            }

            @Override
            public Credentials credentials() {
                return null;
            }

            @Override
            public SSLContext sslContext() {
                return null;
            }

            @Override
            public String version() {
                return "HTTP_1_1";
            }
        };
    }

    @Test
    void exchangesTextMessagesAndReportsClose() throws Exception {
        BlockingQueue<String> received = new LinkedBlockingQueue<>();
        CompletableFuture<Integer> closeCode = new CompletableFuture<>();
        client = new JdkHttpClient(config());

        var listener = new WebSocket.Listener() {
            @Override
            public void onText(CharSequence data) {
                received.add(data.toString());
            }

            @Override
            public void onClose(int code, String reason) {
                closeCode.complete(code);
            }
        };

        WebSocket socket = client.openSocket(
                new HttpRequest(HttpMethod.GET, "/ws/path").setHeader("X-Test", "1"), listener);
        socket.sendText("hello from client");

        assertEquals("hello from server", received.poll(5, TimeUnit.SECONDS));
        assertEquals("hello from client", clientFrame.get(5, TimeUnit.SECONDS));
        assertEquals(1000, closeCode.get(5, TimeUnit.SECONDS));
        String headers = handshakeHeaders.get(5, TimeUnit.SECONDS);
        assertTrue(headers.startsWith("GET /ws/path HTTP/1.1"), headers);
        assertTrue(headers.toLowerCase().contains("x-test: 1"), headers);
    }

    @Test
    void failsIfTheServerDoesNotAcceptTheConnection() throws IOException {
        int port = serverSocket.getLocalPort();
        serverSocket.close();
        client = new JdkHttpClient(config());

        assertThrows(ConnectionFailedException.class,
                () -> client.openSocket(new HttpRequest(HttpMethod.GET, "http://127.0.0.1:" + port + "/ws"),
                        new WebSocket.Listener() { }));
    }
}
