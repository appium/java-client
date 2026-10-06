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

package io.appium.java_client.plugin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Imitates the storage plugin of the Appium server: the HTTP endpoints and the upload web sockets.
 */
final class FakeStorageServer implements AutoCloseable {
    private static final String GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    private static final long EVENTS_TIMEOUT_SEC = 10;

    private static final Type MAP_TYPE = new TypeToken<Map<String, Object>>() { }.getType();

    private final Gson gson = new Gson();
    private final ServerSocket serverSocket;
    private final Map<String, byte[]> items = new LinkedHashMap<>();
    private final Map<String, Upload> uploads = new ConcurrentHashMap<>();
    private final AtomicInteger uploadCounter = new AtomicInteger();
    private final String basePath;
    private final String routePrefix;
    private final String storagePrefix;
    private final AtomicInteger requestCounter = new AtomicInteger();
    private volatile boolean rejectUploads;
    private volatile int failureStatus;

    private static final class Upload {
        private final String name;
        private final String sha1;
        private final CompletableFuture<OutputStream> events = new CompletableFuture<>();

        private Upload(String name, String sha1) {
            this.name = name;
            this.sha1 = sha1;
        }
    }

    FakeStorageServer() throws IOException {
        this("", true, "/appium/storage");
    }

    /**
     * Creates a fake server with the given base path.
     *
     * @param basePath the base path of the imitated Appium server
     * @param honorBasePath whether the routes are mounted under the base path (plugin v3+) or at the root (v2)
     * @param storagePrefix the route prefix: /appium/storage, or /storage for plugins older than 1.2.0
     */
    FakeStorageServer(String basePath, boolean honorBasePath, String storagePrefix) throws IOException {
        this.basePath = basePath;
        this.storagePrefix = storagePrefix;
        this.routePrefix = (honorBasePath ? basePath : "") + storagePrefix;
        serverSocket = new ServerSocket(0, 50, InetAddress.getLoopbackAddress());
        var thread = new Thread(this::acceptConnections, "fake-storage-server");
        thread.setDaemon(true);
        thread.start();
    }

    URL url() throws IOException {
        return new URL("http://127.0.0.1:" + serverSocket.getLocalPort() + basePath);
    }

    /** Makes the server report a failure for the uploads, as if the content could not be saved. */
    void rejectUploads() {
        rejectUploads = true;
    }

    /** Makes the served routes respond with the given HTTP error status, 0 restores the normal behavior. */
    void failWith(int status) {
        failureStatus = status;
    }

    /** The number of the plain HTTP requests received so far, web socket handshakes excluded. */
    int requestCount() {
        return requestCounter.get();
    }

    synchronized byte[] content(String name) {
        return items.get(name);
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }

    private void acceptConnections() {
        while (!serverSocket.isClosed()) {
            try {
                var socket = serverSocket.accept();
                var thread = new Thread(() -> handle(socket), "fake-storage-connection");
                thread.setDaemon(true);
                thread.start();
            } catch (IOException e) {
                return;
            }
        }
    }

    private void handle(Socket socket) {
        try (socket) {
            var in = new DataInputStream(socket.getInputStream());
            var out = socket.getOutputStream();
            var head = readHead(in);
            var lines = head.split("\r\n");
            var requestLine = lines[0].split(" ");
            var headers = new LinkedHashMap<String, String>();
            for (int i = 1; i < lines.length; i++) {
                int colon = lines[i].indexOf(':');
                headers.put(lines[i].substring(0, colon).toLowerCase(), lines[i].substring(colon + 1).trim());
            }
            if ("websocket".equalsIgnoreCase(headers.get("upgrade"))) {
                handleWebSocket(requestLine[1], headers.get("sec-websocket-key"), in, out);
                return;
            }
            var body = new byte[Integer.parseInt(headers.getOrDefault("content-length", "0"))];
            in.readFully(body);
            respond(out, requestLine[0], requestLine[1], new String(body, StandardCharsets.UTF_8));
        } catch (IOException | NoSuchAlgorithmException | RuntimeException e) {
            // the client has gone away, nothing to report
        }
    }

    private void respond(OutputStream out, String method, String path, String body) throws IOException {
        var route = method + " " + path;
        var endpoint = method + " " + (path.startsWith(routePrefix + "/")
                ? path.substring(routePrefix.length()) : path);
        requestCounter.incrementAndGet();
        if (failureStatus != 0 && path.startsWith(routePrefix + "/")) {
            writeResponse(out, failureStatus,
                    "{\"value\":{\"error\":\"unknown error\",\"message\":\"boom\",\"stacktrace\":\"\"}}");
            return;
        }
        String json;
        switch (endpoint) {
            case "POST /reset":
                synchronized (this) {
                    items.clear();
                }
                json = "{\"value\":null}";
                break;
            case "GET /list":
                json = gson.toJson(Map.of("value", list()));
                break;
            case "POST /delete":
                Map<String, Object> deleteArgs = gson.fromJson(body, MAP_TYPE);
                boolean deleted;
                synchronized (this) {
                    deleted = items.remove((String) deleteArgs.get("name")) != null;
                }
                json = "{\"value\":" + deleted + "}";
                break;
            case "POST /add":
                json = gson.toJson(Map.of("value", startUpload(body)));
                break;
            default:
                writeResponse(out, 404, "{\"value\":{\"error\":\"unknown command\",\"message\":\"" + route
                        + "\",\"stacktrace\":\"\"}}");
                return;
        }
        writeResponse(out, 200, json);
    }

    private synchronized List<Map<String, Object>> list() {
        var result = new ArrayList<Map<String, Object>>();
        items.forEach((name, content) -> result.add(
                Map.of("name", name, "path", storagePrefix + "/" + name, "size", (long) content.length)));
        return result;
    }

    private Map<String, Object> startUpload(String body) {
        Map<String, Object> args = gson.fromJson(body, MAP_TYPE);
        var id = String.valueOf(uploadCounter.incrementAndGet());
        uploads.put(id, new Upload((String) args.get("name"), (String) args.get("sha1")));
        var wsPrefix = routePrefix + "/add/" + id;
        return Map.of(
                "ws", Map.of("stream", wsPrefix + "/stream", "events", wsPrefix + "/events"),
                "ttlMs", 10000L
        );
    }

    private void handleWebSocket(String path, String key, DataInputStream in, OutputStream out)
            throws IOException, NoSuchAlgorithmException {
        var accept = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-1")
                .digest((key + GUID).getBytes(StandardCharsets.US_ASCII)));
        out.write(("HTTP/1.1 101 Switching Protocols\r\nUpgrade: websocket\r\nConnection: Upgrade\r\n"
                + "Sec-WebSocket-Accept: " + accept + "\r\n\r\n").getBytes(StandardCharsets.US_ASCII));
        out.flush();
        var parts = path.substring((routePrefix + "/add/").length()).split("/");
        var upload = uploads.get(parts[0]);
        if ("events".equals(parts[1])) {
            upload.events.complete(out);
            while (readFrame(in, new ByteArrayOutputStream()) != 0x8) {
                // the events socket receives nothing but the close frame
            }
            return;
        }
        var content = new ByteArrayOutputStream();
        while (readFrame(in, content) != 0x8) {
            // collecting the chunks until the client closes the stream
        }
        try {
            var events = upload.events.get(EVENTS_TIMEOUT_SEC, TimeUnit.SECONDS);
            writeFrame(events, 0x1, uploadResult(upload, content.toByteArray()).getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new IOException(e);
        }
        writeFrame(out, 0x8, new byte[]{0x03, (byte) 0xe8});
    }

    private String uploadResult(Upload upload, byte[] content) throws NoSuchAlgorithmException {
        var actualSha1 = new StringBuilder();
        for (byte b : MessageDigest.getInstance("SHA-1").digest(content)) {
            actualSha1.append(String.format("%02x", b));
        }
        if (rejectUploads || !actualSha1.toString().equals(upload.sha1)) {
            return "{\"value\":{\"error\":\"unknown error\",\"message\":\"The upload of '" + upload.name
                    + "' has failed\",\"stacktrace\":\"\"}}";
        }
        synchronized (this) {
            items.put(upload.name, content);
        }
        return gson.toJson(Map.of("value", Map.of("success", true, "name", upload.name, "sha1", upload.sha1)));
    }

    private static String readHead(InputStream in) throws IOException {
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

    private static void writeResponse(OutputStream out, int status, String json) throws IOException {
        var bytes = json.getBytes(StandardCharsets.UTF_8);
        out.write(("HTTP/1.1 " + status + " X\r\nContent-Type: application/json; charset=utf-8\r\n"
                + "Content-Length: " + bytes.length + "\r\nConnection: close\r\n\r\n")
                .getBytes(StandardCharsets.US_ASCII));
        out.write(bytes);
        out.flush();
    }

    /** Reads a client frame, appends the payload of the binary ones to the sink and returns the opcode. */
    private static int readFrame(DataInputStream in, ByteArrayOutputStream sink) throws IOException {
        final int opcode = in.readUnsignedByte() & 0x0f;
        int lengthByte = in.readUnsignedByte();
        boolean masked = (lengthByte & 0x80) != 0;
        long length = lengthByte & 0x7f;
        if (length == 126) {
            length = in.readUnsignedShort();
        } else if (length == 127) {
            length = in.readLong();
        }
        var mask = new byte[4];
        if (masked) {
            in.readFully(mask);
        }
        var payload = new byte[(int) length];
        in.readFully(payload);
        if (opcode == 0x2 || opcode == 0x0) {
            for (int i = 0; i < payload.length; i++) {
                payload[i] ^= mask[i % 4];
            }
            sink.write(payload);
        }
        return opcode;
    }

    private static synchronized void writeFrame(OutputStream out, int opcode, byte[] payload) throws IOException {
        out.write(0x80 | opcode);
        if (payload.length < 126) {
            out.write(payload.length);
        } else {
            out.write(126);
            out.write(payload.length >> 8);
            out.write(payload.length);
        }
        out.write(payload);
        out.flush();
    }
}
