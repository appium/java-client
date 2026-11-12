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

package io.appium.java_client.remote;

import lombok.AccessLevel;
import lombok.Getter;
import org.jspecify.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.stream.Stream;

import static io.appium.java_client.internal.CapabilityHelpers.APPIUM_PREFIX;

/** The direct connect settings that the server returns in the new session response. */
public class DirectConnect {
    private static final String DIRECT_CONNECT_PROTOCOL = "directConnectProtocol";
    private static final String DIRECT_CONNECT_PATH = "directConnectPath";
    private static final String DIRECT_CONNECT_HOST = "directConnectHost";
    private static final String DIRECT_CONNECT_PORT = "directConnectPort";

    @Getter(AccessLevel.PUBLIC) private final String protocol;

    @Getter(AccessLevel.PUBLIC) private final String path;

    @Getter(AccessLevel.PUBLIC) private final String host;

    @Getter(AccessLevel.PUBLIC) private final String port;

    /**
     * Create a DirectConnect instance.
     * @param responseValue is the response body
     */
    public DirectConnect(Map<String, ?> responseValue) {
        this.protocol = this.getDirectConnectValue(responseValue, DIRECT_CONNECT_PROTOCOL);
        this.path = this.getDirectConnectValue(responseValue, DIRECT_CONNECT_PATH);
        this.host = this.getDirectConnectValue(responseValue, DIRECT_CONNECT_HOST);
        this.port = this.getDirectConnectValue(responseValue, DIRECT_CONNECT_PORT);
    }

    @Nullable
    private String getDirectConnectValue(Map<String, ?> responseValue, String key) {
        Object directConnectPath = responseValue.get(APPIUM_PREFIX + key);
        if (directConnectPath != null) {
            return String.valueOf(directConnectPath);
        }
        directConnectPath = responseValue.get(key);
        return directConnectPath == null ? null : String.valueOf(directConnectPath);
    }

    /**
     * Returns true if the {@link DirectConnect} instance member has valid values.
     * @return true if protocol, host and port are non-blank and path is non-null
     */
    public boolean isValid() {
        return this.path != null
                && Stream.of(this.protocol, this.host, this.port)
                .allMatch(value -> value != null && !value.isBlank());
    }

    /**
     * Returns a URL instance built with members in the DirectConnect instance.
     * @return A URL object
     * @throws MalformedURLException if the built url was invalid
     */
    public URL getUrl() throws MalformedURLException {
        String formattedHost = formatHost(this.host);
        String normalizedPath = normalizePath(this.path);
        String newUrlCandidate = String.format("%s://%s:%s%s",
                this.protocol, formattedHost, this.port, normalizedPath);
        if (!isValid()) {
            throw createMalformedUrlException(newUrlCandidate);
        }

        try {
            return new URL(newUrlCandidate);
        } catch (MalformedURLException e) {
            throw createMalformedUrlException(newUrlCandidate);
        }
    }

    private static String formatHost(@Nullable String host) {
        if (host != null && !host.startsWith("[") && host.indexOf(':') != host.lastIndexOf(':')) {
            return "[" + host + "]";
        }
        return String.valueOf(host);
    }

    private static String normalizePath(@Nullable String path) {
        if (path == null) {
            return "null";
        }
        if (path.isEmpty() || path.startsWith("/")) {
            return path;
        }
        return "/" + path;
    }

    private static MalformedURLException createMalformedUrlException(String candidate) {
        return new MalformedURLException(
                String.format("The remote server returned an invalid value to build the direct connect URL: %s",
                        candidate)
        );
    }
}
