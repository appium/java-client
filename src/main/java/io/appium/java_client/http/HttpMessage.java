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

package io.appium.java_client.http;

import org.jspecify.annotations.Nullable;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;

/**
 * Headers and body shared by requests and responses. Header names are case-insensitive.
 *
 * @param <M> the concrete message type
 */
public abstract class HttpMessage<M extends HttpMessage<M>> {
    private final Map<String, List<String>> headers = new HashMap<>();
    private Contents.Supplier content = Contents.empty();

    public void forEachHeader(BiConsumer<String, String> action) {
        headers.forEach((name, values) -> values.forEach(value -> action.accept(name, value)));
    }

    public Iterable<String> getHeaderNames() {
        return Collections.unmodifiableCollection(headers.keySet());
    }

    public Iterable<String> getHeaders(String name) {
        return Collections.unmodifiableCollection(headers.getOrDefault(name.toLowerCase(Locale.ENGLISH), emptyList()));
    }

    @Nullable
    public String getHeader(HttpHeader name) {
        return getHeader(name.getName());
    }

    @Nullable
    public String getHeader(String name) {
        List<String> values = headers.getOrDefault(name.toLowerCase(Locale.ENGLISH), emptyList());
        return values.isEmpty() ? null : values.get(0);
    }

    public M setHeader(String name, String value) {
        String lowerCaseName = name.toLowerCase(Locale.ENGLISH);
        return removeHeader(lowerCaseName).addHeader(lowerCaseName, value);
    }

    public M addHeader(HttpHeader name, String value) {
        return addHeader(name.getName(), value);
    }

    public M addHeader(String name, String value) {
        headers.computeIfAbsent(name.toLowerCase(Locale.ENGLISH), n -> new ArrayList<>()).add(value);
        return self();
    }

    public M removeHeader(String name) {
        headers.remove(name.toLowerCase(Locale.ENGLISH));
        return self();
    }

    /**
     * The declared content length.
     *
     * @return the Content-Length header value or -1 if it is not set
     */
    public Long getContentLength() {
        String value = getHeader(HttpHeader.ContentLength);
        return value == null ? -1L : Long.parseLong(value);
    }

    @Nullable
    public String getContentType() {
        return getHeader(HttpHeader.ContentType);
    }

    /**
     * The charset declared by the Content-Type header.
     *
     * @return the declared charset or UTF-8
     */
    public Charset getContentEncoding() {
        try {
            String contentType = getContentType();
            if (contentType != null) {
                return Arrays.stream(contentType.split(";"))
                        .map(e -> e.trim().toLowerCase(Locale.ENGLISH))
                        .filter(e -> e.startsWith("charset="))
                        .map(e -> e.substring(e.indexOf('=') + 1))
                        .map(Charset::forName)
                        .findFirst()
                        .orElse(UTF_8);
            }
        } catch (IllegalArgumentException ignored) {
            // Unknown charset, use the default
        }
        return UTF_8;
    }

    public M setContent(Contents.Supplier supplier) {
        this.content = requireNonNull(supplier, "Supplier");
        return self();
    }

    public Contents.Supplier getContent() {
        return content;
    }

    public String contentAsString() {
        return getContent().contentAsString(getContentEncoding());
    }

    @Override
    public String toString() {
        return getContent().toString();
    }

    @SuppressWarnings("unchecked")
    private M self() {
        return (M) this;
    }
}
