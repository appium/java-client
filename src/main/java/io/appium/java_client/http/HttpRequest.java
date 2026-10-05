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

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;

public class HttpRequest extends HttpMessage<HttpRequest> {
    private final HttpMethod method;
    private final String uri;
    private final Map<String, List<String>> queryParameters = new LinkedHashMap<>();

    /**
     * Creates a request.
     *
     * @param method the HTTP method
     * @param uri a path relative to the client base URI, or an absolute URI
     */
    public HttpRequest(HttpMethod method, String uri) {
        this.method = method;
        this.uri = uri;
    }

    public HttpRequest(HttpMethod method, URI uri) {
        this(method, uri.toString());
    }

    public String getUri() {
        return uri;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public HttpRequest addQueryParameter(String name, String value) {
        queryParameters.computeIfAbsent(name, n -> new ArrayList<>()).add(value);
        return this;
    }

    /**
     * The URL-encoded query string.
     *
     * @return query parameters joined with an ampersand, empty if there are none
     */
    public String getQueryString() {
        return queryParameters.entrySet().stream()
                .flatMap(e -> e.getValue().stream().map(v -> URLEncoder.encode(e.getKey(), UTF_8)
                        + "=" + URLEncoder.encode(v, UTF_8)))
                .collect(joining("&"));
    }

    @Override
    public String toString() {
        String content = super.toString();
        return content.isEmpty()
                ? String.format("(%s) %s", getMethod(), getUri())
                : String.format("(%s) %s %s", getMethod(), getUri(), content);
    }
}
