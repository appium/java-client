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

package io.appium.java_client.internal;

import io.appium.java_client.remote.SessionId;
import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriverException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helpers for working with remote session addresses.
 */
public class SessionHelpers {
    private static final Pattern SESSION = Pattern.compile("/session/([^/]+)");
    private static final Set<String> LOOPBACK_HOSTS = Set.of("localhost", "127.0.0.1", "::1", "0:0:0:0:0:0:0:1");

    private SessionHelpers() {
    }

    /** The server URL and the session identifier of a remote session. */
    @Data public static class SessionAddress {
        private final URL serverUrl;
        private final String id;

        /**
         * Creates a new session address.
         *
         * @param serverUrl the URL of the server hosting the session
         * @param id the session identifier
         */
        public SessionAddress(URL serverUrl, String id) {
            this.serverUrl = serverUrl;
            this.id = id;
        }
    }

    /**
     * Parses the address of a running remote session.
     *
     * @param address The address string containing /session/id suffix.
     * @return Parsed address object.
     * @throws InvalidArgumentException If no session identifier could be parsed.
     */
    public static SessionAddress parseSessionAddress(URL address) {
        String addressString = address.toString();
        Matcher matcher = SESSION.matcher(addressString);
        if (!matcher.find()) {
            throw new InvalidArgumentException(
                    String.format("The server URL '%s' must include /session/<id> suffix", addressString)
            );
        }
        try {
            return new SessionAddress(
                    new URL(addressString.replace(matcher.group(), "")), matcher.group(1)
            );
        } catch (MalformedURLException e) {
            throw new WebDriverException(e);
        }
    }

    /**
     * Resolves the host for a log broadcast WebSocket connection from the server URL.
     *
     * @param serverUrl the active remote server URL, or {@code null} if unknown
     * @param fallbackHost the host to return when {@code serverUrl} has no host
     * @return the resolved broadcast host
     */
    public static String resolveBroadcastHost(@Nullable URL serverUrl, String fallbackHost) {
        if (serverUrl != null && !serverUrl.getHost().isEmpty()) {
            return serverUrl.getHost();
        }
        return fallbackHost;
    }

    /**
     * Resolves the port for a log broadcast WebSocket connection from the server URL.
     *
     * @param serverUrl the active remote server URL, or {@code null} if unknown
     * @param fallbackPort the port to return when {@code serverUrl} defines neither an explicit nor default port
     * @return the resolved broadcast port
     */
    public static int resolveBroadcastPort(@Nullable URL serverUrl, int fallbackPort) {
        if (serverUrl == null) {
            return fallbackPort;
        }
        if (serverUrl.getPort() > 0) {
            return serverUrl.getPort();
        }
        return serverUrl.getDefaultPort() > 0 ? serverUrl.getDefaultPort() : fallbackPort;
    }

    /**
     * Builds a device log broadcast WebSocket URI, using {@code wss} only when the target
     * {@code host} and {@code port} match an {@code https} {@code serverUrl}.
     *
     * @param serverUrl the active remote server URL, or {@code null} if unknown
     * @param host the target WebSocket host
     * @param port the target WebSocket port
     * @param sessionId the active session identifier
     * @param deviceLogType the log endpoint suffix (such as {@code logcat} or {@code syslog})
     * @return the WebSocket endpoint URI
     */
    public static URI buildBroadcastUri(
            @Nullable URL serverUrl,
            String host,
            int port,
            @Nullable SessionId sessionId,
            String deviceLogType
    ) {
        String scheme = serverUrl != null
                && "https".equalsIgnoreCase(serverUrl.getProtocol())
                && matchesEndpoint(serverUrl, host, port)
                ? "wss"
                : "ws";
        String endpoint = String.format(
                "%s://%s:%s/ws/session/%s/appium/device/%s",
                scheme, formatHost(host), port, sessionId, deviceLogType
        );
        return URI.create(endpoint);
    }

    private static boolean matchesEndpoint(URL serverUrl, String host, int port) {
        if (resolveBroadcastPort(serverUrl, -1) != port) {
            return false;
        }
        String serverHost = normalizeHost(serverUrl.getHost());
        String targetHost = normalizeHost(host);
        if (serverHost.isEmpty() || targetHost.isEmpty()) {
            return false;
        }
        return serverHost.equals(targetHost)
                || LOOPBACK_HOSTS.contains(serverHost) && LOOPBACK_HOSTS.contains(targetHost);
    }

    private static String normalizeHost(String host) {
        String trimmed = host.trim().toLowerCase(Locale.ROOT);
        if (trimmed.startsWith("[") && trimmed.endsWith("]") && trimmed.length() > 2) {
            return trimmed.substring(1, trimmed.length() - 1);
        }
        return trimmed;
    }

    private static String formatHost(String host) {
        if (!host.startsWith("[") && host.indexOf(':') != host.lastIndexOf(':')) {
            return "[" + host + "]";
        }
        return host;
    }
}
