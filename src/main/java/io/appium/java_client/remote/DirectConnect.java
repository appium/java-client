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

import io.appium.java_client.internal.Strings;
import lombok.AccessLevel;
import lombok.Getter;
import org.jspecify.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
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
     * Returns true if the {@link DirectConnect} instance member has nonnull values.
     * @return true if each connection information has a nonnull value
     */
    public boolean isValid() {
        return Stream.of(this.protocol, this.path, this.host, this.port).noneMatch(Objects::isNull);
    }

    /**
     * Returns a URL instance built with members in the DirectConnect instance.
     * @return A URL object
     * @throws MalformedURLException if the built url was invalid
     */
    public URL getUrl() throws MalformedURLException {
        requireNonBlankFields();

        String expectedHost = Strings.formatHost(this.host);
        String newUrlCandidate = this.protocol + "://" + expectedHost
                + ":" + this.port + normalizePath(this.path);
        int expectedPort = parsePort(this.port, newUrlCandidate);
        try {
            URL url = new URL(newUrlCandidate);
            requireAuthorityMatches(url, expectedHost, expectedPort, newUrlCandidate);
            return url;
        } catch (MalformedURLException e) {
            throw createMalformedUrlException(newUrlCandidate);
        }
    }

    private void requireNonBlankFields() throws MalformedURLException {
        if (!isValid()
                || this.protocol.isBlank()
                || this.host.isBlank()
                || this.port.isBlank()
                || !this.path.isEmpty() && this.path.isBlank()) {
            throw createMalformedUrlException(
                    this.protocol + "://" + this.host + ":" + this.port + this.path
            );
        }
    }

    private static void requireAuthorityMatches(
            URL url,
            String expectedHost,
            int expectedPort,
            String candidate
    ) throws MalformedURLException {
        if (!expectedHost.equals(url.getHost())
                || url.getPort() != expectedPort
                || url.getUserInfo() != null) {
            throw createMalformedUrlException(candidate);
        }
    }

    private static int parsePort(String portValue, String candidate) throws MalformedURLException {
        if (!portValue.chars().allMatch(c -> c >= '0' && c <= '9')) {
            throw createMalformedUrlException(candidate);
        }
        try {
            int parsed = Integer.parseInt(portValue);
            if (parsed < 0 || parsed > 65535) {
                throw createMalformedUrlException(candidate);
            }
            return parsed;
        } catch (NumberFormatException e) {
            throw createMalformedUrlException(candidate);
        }
    }

    private static String normalizePath(String path) {
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
