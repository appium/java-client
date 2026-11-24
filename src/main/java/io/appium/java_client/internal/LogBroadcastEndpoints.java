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
import io.appium.java_client.remote.SessionId;
import io.appium.java_client.ws.StringWebSocketClient;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * Shared endpoint resolution and WebSocket startup helpers for device log broadcast listeners.
 */
@NullMarked
@SuppressWarnings("removal")
public final class LogBroadcastEndpoints {
    private LogBroadcastEndpoints() {
    }

    /**
     * Starts server-side log streaming and connects the WebSocket client to the currently connected
     * Appium server endpoint using its host, port, and scheme.
     *
     * @param driver the driver executing {@code mobile: startLogsBroadcast}
     * @param clientSupplier supplier for the target WebSocket client, invoked after starting server streaming
     * @param deviceLogType the log endpoint suffix (such as {@code logcat} or {@code syslog})
     */
    public static void startBroadcast(
            ExecutesMethod driver,
            Supplier<StringWebSocketClient> clientSupplier,
            String deviceLogType
    ) {
        var remoteWebDriver = (AppiumRemoteWebDriver) driver;
        var executor = (AppiumCommandExecutor) remoteWebDriver.getCommandExecutor();
        URL serverUrl = executor.getAddressOfRemoteServer();
        if (serverUrl == null) {
            throw new IllegalArgumentException("The Appium server address is not set");
        }
        startBroadcast(
                remoteWebDriver,
                driver,
                clientSupplier,
                serverUrl,
                serverUrl.getHost(),
                serverUrl.getPort(),
                deviceLogType
        );
    }

    /**
     * Starts server-side log streaming and connects the WebSocket client to the broadcast endpoint.
     * The WebSocket scheme ({@code ws} or {@code wss}) is derived from the executor's initial server URL
     * before any {@code directConnect} or server URL override so existing callers keep their original scheme.
     *
     * @param driver the driver executing {@code mobile: startLogsBroadcast}
     * @param clientSupplier supplier for the target WebSocket client, invoked after starting server streaming
     * @param host the target WebSocket host
     * @param port the target WebSocket port, or a negative value to omit the port
     * @param deviceLogType the log endpoint suffix (such as {@code logcat} or {@code syslog})
     */
    public static void startBroadcast(
            ExecutesMethod driver,
            Supplier<StringWebSocketClient> clientSupplier,
            String host,
            int port,
            String deviceLogType
    ) {
        var remoteWebDriver = (AppiumRemoteWebDriver) driver;
        var executor = (AppiumCommandExecutor) remoteWebDriver.getCommandExecutor();
        startBroadcast(
                remoteWebDriver,
                driver,
                clientSupplier,
                CommandExecutorInternals.getInitialServerUrl(executor),
                host,
                port,
                deviceLogType
        );
    }

    private static void startBroadcast(
            AppiumRemoteWebDriver remoteWebDriver,
            ExecutesMethod driver,
            Supplier<StringWebSocketClient> clientSupplier,
            @Nullable URL serverUrl,
            String host,
            int port,
            String deviceLogType
    ) {
        URI endpoint = buildBroadcastUri(
                serverUrl,
                host,
                port,
                remoteWebDriver.getSessionId(),
                deviceLogType
        );
        CommandExecutionHelper.executeScript(driver, "mobile: startLogsBroadcast");
        clientSupplier.get().connect(endpoint);
    }

    /**
     * Builds a device log broadcast WebSocket URI using {@code wss} when {@code serverUrl} uses
     * {@code https} or {@code wss}, or {@code ws} otherwise.
     *
     * @param serverUrl the server URL used to choose the WebSocket scheme, or {@code null} if unknown
     * @param host the target WebSocket host (bare IPv6 literals are bracketed automatically)
     * @param port the target WebSocket port, or a negative value to omit the port
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
        String protocol = serverUrl == null ? null : serverUrl.getProtocol();
        String scheme = "https".equalsIgnoreCase(protocol) || "wss".equalsIgnoreCase(protocol) ? "wss" : "ws";
        String validatedHost = requireNonNull(host, "Host");
        if (validatedHost.isBlank()) {
            throw new IllegalArgumentException("Host must not be blank");
        }
        String path = "/ws/session/" + sessionId + "/appium/device/" + deviceLogType;
        int normalizedPort = port < 0 ? -1 : port;
        try {
            URI endpoint = new URI(
                    scheme,
                    null,
                    Strings.formatHost(validatedHost),
                    normalizedPort,
                    path,
                    null,
                    null
            );
            if (endpoint.getHost() == null
                    || endpoint.getPort() != normalizedPort
                    || endpoint.getRawUserInfo() != null
                    || endpoint.getRawQuery() != null
                    || endpoint.getRawFragment() != null
                    || !path.equals(endpoint.getPath())) {
                throw new IllegalArgumentException("Cannot build the log broadcast web socket endpoint");
            }
            return endpoint;
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Cannot build the log broadcast web socket endpoint", e);
        }
    }
}
