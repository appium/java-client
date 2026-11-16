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

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import io.appium.java_client.remote.AppiumCommandExecutor;
import io.appium.java_client.remote.AppiumRemoteWebDriver;
import io.appium.java_client.remote.DirectConnect;
import io.appium.java_client.remote.SessionId;
import io.appium.java_client.ws.StringWebSocketClient;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Shared endpoint resolution and WebSocket startup helpers for device log broadcast listeners.
 */
@NullMarked
public final class LogBroadcastEndpoints {
    private static final Set<String> LOOPBACK_HOSTS = Set.of("localhost", "127.0.0.1", "::1", "0:0:0:0:0:0:0:1");
    private static final Set<String> WILDCARD_HOSTS = Set.of("0.0.0.0", "::", "0:0:0:0:0:0:0:0");

    private LogBroadcastEndpoints() {
    }

    /**
     * Resolves the active remote server URL from a driver's command executor.
     *
     * @param driver the driver executing log broadcast commands
     * @return the current remote server URL, or {@code null} if unavailable
     */
    @Nullable
    public static URL resolveServerUrl(ExecutesMethod driver) {
        return getExecutor(driver).getAddressOfRemoteServer();
    }

    /**
     * Resolves the host for a log broadcast WebSocket connection from the server URL,
     * mapping missing or wildcard bind hosts ({@code 0.0.0.0}, {@code [::]}) to {@code fallbackHost}.
     *
     * @param serverUrl the active remote server URL, or {@code null} if unknown
     * @param fallbackHost the loopback host to return when {@code serverUrl} has no usable host
     * @return the resolved broadcast host
     */
    public static String resolveHost(@Nullable URL serverUrl, String fallbackHost) {
        String host = serverUrl != null ? serverUrl.getHost() : "";
        if (host.isEmpty() || WILDCARD_HOSTS.contains(normalizeHost(host))) {
            return fallbackHost;
        }
        return host;
    }

    /**
     * Resolves the port for a log broadcast WebSocket connection from the server URL.
     *
     * @param serverUrl the active remote server URL, or {@code null} if unknown
     * @param fallbackPort the port to return when {@code serverUrl} defines neither an explicit nor default port
     * @return the resolved broadcast port
     */
    public static int resolvePort(@Nullable URL serverUrl, int fallbackPort) {
        if (serverUrl == null) {
            return fallbackPort;
        }
        if (serverUrl.getPort() > 0) {
            return serverUrl.getPort();
        }
        return serverUrl.getDefaultPort() > 0 ? serverUrl.getDefaultPort() : fallbackPort;
    }

    /**
     * Starts server-side log streaming and connects the WebSocket client to the resolved broadcast endpoint.
     *
     * @param driver the driver executing {@code mobile: startLogsBroadcast}
     * @param client the target WebSocket client
     * @param host the target WebSocket host
     * @param port the target WebSocket port
     * @param deviceLogType the log endpoint suffix (such as {@code logcat} or {@code syslog})
     */
    public static void startBroadcast(
            ExecutesMethod driver,
            StringWebSocketClient client,
            String host,
            int port,
            String deviceLogType
    ) {
        var remoteWebDriver = (AppiumRemoteWebDriver) driver;
        var executor = getExecutor(driver);
        CommandExecutionHelper.executeScript(driver, "mobile: startLogsBroadcast");
        URI endpoint = buildBroadcastUri(
                executor.getAddressOfRemoteServer(),
                executor.getReplacedHttpServerUrls(),
                host,
                port,
                remoteWebDriver.getSessionId(),
                deviceLogType
        );
        client.connect(endpoint);
    }

    /**
     * Builds a device log broadcast WebSocket URI. Uses {@code wss} when {@code serverUrl} uses
     * {@code https}, unless the requested {@code host:port} matches a plain-{@code http} server URL
     * previously replaced (e.g. by {@code directConnect}) and does not match the active {@code serverUrl}.
     *
     * @param serverUrl the active remote server URL, or {@code null} if unknown
     * @param replacedHttpServerUrls plain-{@code http} server URLs previously replaced on the executor
     * @param host the target WebSocket host
     * @param port the target WebSocket port
     * @param sessionId the active session identifier
     * @param deviceLogType the log endpoint suffix (such as {@code logcat} or {@code syslog})
     * @return the WebSocket endpoint URI
     */
    public static URI buildBroadcastUri(
            @Nullable URL serverUrl,
            List<URL> replacedHttpServerUrls,
            String host,
            int port,
            @Nullable SessionId sessionId,
            String deviceLogType
    ) {
        String scheme = chooseScheme(serverUrl, replacedHttpServerUrls, host, port);
        String endpoint = String.format(
                "%s://%s:%s/ws/session/%s/appium/device/%s",
                scheme, DirectConnect.formatHost(host), port, sessionId, deviceLogType
        );
        return URI.create(endpoint);
    }

    private static AppiumCommandExecutor getExecutor(ExecutesMethod driver) {
        var remoteWebDriver = (AppiumRemoteWebDriver) driver;
        return (AppiumCommandExecutor) remoteWebDriver.getCommandExecutor();
    }

    private static String chooseScheme(
            @Nullable URL serverUrl,
            List<URL> replacedHttpServerUrls,
            String host,
            int port
    ) {
        if (serverUrl == null || !"https".equalsIgnoreCase(serverUrl.getProtocol())) {
            return "ws";
        }
        if (!matchesEndpoint(serverUrl, host, port)
                && replacedHttpServerUrls.stream().anyMatch(url -> matchesEndpoint(url, host, port))) {
            return "ws";
        }
        return "wss";
    }

    private static boolean matchesEndpoint(URL candidateUrl, String host, int port) {
        if (resolvePort(candidateUrl, -1) != port) {
            return false;
        }
        String candidateHost = normalizeComparableHost(candidateUrl.getHost());
        String targetHost = normalizeComparableHost(host);
        if (candidateHost.isEmpty() || targetHost.isEmpty()) {
            return false;
        }
        return candidateHost.equals(targetHost)
                || LOOPBACK_HOSTS.contains(candidateHost) && LOOPBACK_HOSTS.contains(targetHost);
    }

    private static String normalizeComparableHost(String host) {
        String normalized = normalizeHost(host);
        return WILDCARD_HOSTS.contains(normalized) ? "127.0.0.1" : normalized;
    }

    private static String normalizeHost(String host) {
        String trimmed = host.trim().toLowerCase(Locale.ROOT);
        if (trimmed.startsWith("[") && trimmed.endsWith("]") && trimmed.length() > 2) {
            return trimmed.substring(1, trimmed.length() - 1);
        }
        return trimmed;
    }
}
