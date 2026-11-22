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
import java.net.URL;
import java.util.function.Supplier;

/**
 * Shared endpoint resolution and WebSocket startup helpers for device log broadcast listeners.
 */
@NullMarked
@SuppressWarnings("removal")
public final class LogBroadcastEndpoints {
    private LogBroadcastEndpoints() {
    }

    /**
     * Starts server-side log streaming and connects the WebSocket client to the broadcast endpoint.
     * The WebSocket scheme ({@code ws} or {@code wss}) is derived from the executor's initial server URL
     * before any {@code directConnect} or server URL override so existing callers keep their original scheme.
     *
     * @param driver the driver executing {@code mobile: startLogsBroadcast}
     * @param clientSupplier supplier for the target WebSocket client, invoked after starting server streaming
     * @param host the target WebSocket host
     * @param port the target WebSocket port
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
        CommandExecutionHelper.executeScript(driver, "mobile: startLogsBroadcast");
        URI endpoint = buildBroadcastUri(
                CommandExecutorInternals.getInitialServerUrl(executor),
                host,
                port,
                remoteWebDriver.getSessionId(),
                deviceLogType
        );
        clientSupplier.get().connect(endpoint);
    }

    /**
     * Builds a device log broadcast WebSocket URI using {@code wss} when {@code serverUrl} uses
     * {@code https}, or {@code ws} otherwise.
     *
     * @param serverUrl the server URL used to choose the WebSocket scheme, or {@code null} if unknown
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
        String scheme = serverUrl != null && "https".equalsIgnoreCase(serverUrl.getProtocol()) ? "wss" : "ws";
        String endpoint = scheme + "://" + Strings.formatHost(host) + ":" + port
                + "/ws/session/" + sessionId + "/appium/device/" + deviceLogType;
        return URI.create(endpoint);
    }
}
