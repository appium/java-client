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
import io.appium.java_client.http.Contents;
import io.appium.java_client.http.HttpMethod;
import io.appium.java_client.http.HttpRequest;
import io.appium.java_client.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Converts between Appium and {@code java.net.http} messages.
 */
class JdkHttpMessages {
    private static final List<String> IGNORED_HEADERS = List.of("content-length", "connection", "host");

    private final ClientConfig config;

    JdkHttpMessages(ClientConfig config) {
        this.config = Objects.requireNonNull(config, "Client config");
    }

    java.net.http.HttpRequest createRequest(HttpRequest req, HttpMethod method, URI rawUri) {
        String rawUrl = rawUri.toString();
        String queryString = req.getQueryString();
        if (!queryString.isEmpty()) {
            rawUrl = rawUrl + "?" + queryString;
        }

        var builder = java.net.http.HttpRequest.newBuilder().uri(URI.create(rawUrl));
        switch (method) {
            case DELETE:
                builder.DELETE();
                break;
            case GET:
                builder.GET();
                break;
            case POST:
                builder.POST(notChunkingBodyPublisher(req));
                break;
            case PUT:
                builder.PUT(notChunkingBodyPublisher(req));
                break;
            default:
                throw new IllegalArgumentException(
                        String.format("Unsupported request method %s: %s", req.getMethod(), req));
        }

        req.forEachHeader((name, value) -> {
            // Restricted headers are managed by the JDK client
            if (!IGNORED_HEADERS.contains(name.toLowerCase(Locale.ENGLISH))) {
                builder.header(name, value);
            }
        });
        builder.timeout(config.readTimeout());
        return builder.build();
    }

    private BodyPublisher notChunkingBodyPublisher(HttpRequest req) {
        Contents.Supplier content = req.getContent();
        if (content.length() > 0) {
            // The length is known, so the body is not sent chunked
            return BodyPublishers.fromPublisher(BodyPublishers.ofInputStream(content), content.length());
        }
        return BodyPublishers.noBody();
    }

    URI getRawUri(HttpRequest req) {
        String uri = req.getUri();
        if (uri.startsWith("ws://") || uri.startsWith("wss://")
                || uri.startsWith("http://") || uri.startsWith("https://")) {
            return URI.create(uri);
        }
        URI baseUri = config.baseUri();
        if (baseUri == null) {
            throw new IllegalStateException(
                    "Unable to resolve relative URI " + uri + ": base URI is not set in the client config");
        }
        String base = baseUri.toString();
        return URI.create(base.endsWith("/") ? base.substring(0, base.length() - 1) + uri : base + uri);
    }

    HttpResponse createResponse(java.net.http.HttpResponse<InputStream> response) {
        HttpResponse res = new HttpResponse().setStatus(response.statusCode());
        response.headers().map().forEach((name, values) ->
                values.stream().filter(Objects::nonNull).forEach(value -> res.addHeader(name, value)));
        res.setContent(extractContent(response));
        return res;
    }

    private Contents.Supplier extractContent(java.net.http.HttpResponse<InputStream> response) {
        boolean isBinaryStream = response.headers().firstValue("Content-Type")
                .map(JdkHttpMessages::isBinaryStream).orElse(false);
        if (isBinaryStream) {
            return Contents.fromStream(response.body(), response.headers().firstValueAsLong("Content-Length")
                    .orElse(-1));
        }
        try (InputStream in = response.body()) {
            byte[] body = in.readAllBytes();
            return body.length > 0 ? Contents.bytes(body) : Contents.empty();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static boolean isBinaryStream(String contentType) {
        return "application/octet-stream".equalsIgnoreCase(contentType);
    }
}
