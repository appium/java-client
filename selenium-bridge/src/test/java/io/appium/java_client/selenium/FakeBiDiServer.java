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

package io.appium.java_client.selenium;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * A minimal WebSocket server that answers BiDi commands. It confirms session.status and session.subscribe and
 * sends one log.entryAdded event after a subscription.
 */
final class FakeBiDiServer implements AutoCloseable {
    private static final String GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    private static final Pattern ID = Pattern.compile("\"id\"\\s*:\\s*(\\d+)");
    private static final Pattern METHOD = Pattern.compile("\"method\"\\s*:\\s*\"([^\"]+)\"");

    private final ServerSocket serverSocket;
    private final AtomicInteger connections = new AtomicInteger();
    private final CountDownLatch eventSent = new CountDownLatch(1);

    FakeBiDiServer() throws IOException {
        serverSocket = new ServerSocket(0, 5, InetAddress.getLoopbackAddress());
        var thread = new Thread(this::acceptConnections, "fake-bidi-server");
        thread.setDaemon(true);
        thread.start();
    }

    int port() {
        return serverSocket.getLocalPort();
    }

    int connections() {
        return connections.get();
    }

    boolean awaitEvent(long timeout, TimeUnit unit) throws InterruptedException {
        return eventSent.await(timeout, unit);
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }

    private void acceptConnections() {
        while (!serverSocket.isClosed()) {
            try {
                var socket = serverSocket.accept();
                var thread = new Thread(() -> serve(socket), "fake-bidi-connection");
                thread.setDaemon(true);
                thread.start();
            } catch (IOException e) {
                return;
            }
        }
    }

    private void serve(Socket socket) {
        try (socket) {
            var in = socket.getInputStream();
            var out = socket.getOutputStream();
            handshake(in, out);
            connections.incrementAndGet();
            var data = new DataInputStream(in);
            while (true) {
                var opcode = data.readUnsignedByte() & 0x0f;
                var payload = readPayload(data);
                if (opcode == 0x8) {
                    writeFrame(out, 0x8, payload);
                    return;
                }
                if (opcode == 0x1) {
                    answer(out, new String(payload, StandardCharsets.UTF_8));
                }
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            // The client closed the connection
        }
    }

    private static void handshake(InputStream in, OutputStream out) throws IOException, NoSuchAlgorithmException {
        var buffer = new ByteArrayOutputStream();
        while (!buffer.toString(StandardCharsets.US_ASCII).endsWith("\r\n\r\n")) {
            var b = in.read();
            if (b < 0) {
                throw new IOException("Closed during the handshake");
            }
            buffer.write(b);
        }
        var key = buffer.toString(StandardCharsets.US_ASCII).lines()
                .filter(l -> l.toLowerCase().startsWith("sec-websocket-key:"))
                .map(l -> l.substring(l.indexOf(':') + 1).trim()).findFirst().orElseThrow();
        var accept = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-1")
                .digest((key + GUID).getBytes(StandardCharsets.US_ASCII)));
        out.write(("HTTP/1.1 101 Switching Protocols\r\nUpgrade: websocket\r\nConnection: Upgrade\r\n"
                + "Sec-WebSocket-Accept: " + accept + "\r\n\r\n").getBytes(StandardCharsets.US_ASCII));
        out.flush();
    }

    private static byte[] readPayload(DataInputStream in) throws IOException {
        var length = in.readUnsignedByte() & 0x7f;
        if (length == 126) {
            length = in.readUnsignedShort();
        }
        var mask = new byte[4];
        in.readFully(mask);
        var payload = new byte[length];
        in.readFully(payload);
        for (int i = 0; i < length; i++) {
            payload[i] ^= mask[i % 4];
        }
        return payload;
    }

    private void answer(OutputStream out, String message) throws IOException {
        var id = ID.matcher(message);
        var method = METHOD.matcher(message);
        if (!id.find() || !method.find()) {
            return;
        }
        String result;
        switch (method.group(1)) {
            case "session.status":
                result = "{\"ready\":true,\"message\":\"ready\"}";
                break;
            case "session.subscribe":
                result = "{\"subscription\":\"sub1\"}";
                break;
            default:
                result = "{}";
                break;
        }
        writeText(out, "{\"type\":\"success\",\"id\":" + id.group(1) + ",\"result\":" + result + "}");
        if ("session.subscribe".equals(method.group(1))) {
            // The client registers its listener after it has got the subscription id
            sleepQuietly(300);
            writeText(out, "{\"type\":\"event\",\"method\":\"log.entryAdded\",\"params\":{\"level\":\"info\","
                    + "\"source\":{\"realm\":\"r1\"},\"text\":\"hello\",\"timestamp\":1,\"type\":\"generic\"}}");
            eventSent.countDown();
        }
    }

    private static void sleepQuietly(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static synchronized void writeText(OutputStream out, String text) throws IOException {
        writeFrame(out, 0x1, text.getBytes(StandardCharsets.UTF_8));
    }

    private static void writeFrame(OutputStream out, int opcode, byte[] payload) throws IOException {
        out.write(0x80 | opcode);
        if (payload.length < 126) {
            out.write(payload.length);
        } else {
            out.write(126);
            out.write(payload.length >> 8);
            out.write(payload.length & 0xff);
        }
        out.write(payload);
        out.flush();
    }
}
