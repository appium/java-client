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

package io.appium.java_client.android;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import io.appium.java_client.remote.AppiumCommandExecutor;
import io.appium.java_client.remote.AppiumRemoteWebDriver;
import io.appium.java_client.ws.StringWebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.function.Consumer;

import static io.appium.java_client.service.local.AppiumServiceBuilder.DEFAULT_APPIUM_PORT;
import static java.util.Objects.requireNonNull;

/** Provides listening to logcat messages broadcast via web socket. */
public interface ListensToLogcatMessages extends ExecutesMethod {
    /**
     * Gets the web socket client used for receiving logcat messages.
     *
     * @return the logcat web socket client.
     */
    StringWebSocketClient getLogcatClient();

    /**
     * Start logcat messages broadcast via web socket.
     * The host, port and scheme are taken from the Appium server this driver is connected to.
     * {@code https} and {@code wss} become {@code wss}; any other scheme becomes {@code ws}.
     * A port omitted from the server URL is omitted from the socket URI.
     * Appium serves this socket at the server root
     * ({@code /ws/session/.../appium/device/logcat}), so an HTTP base path such as
     * {@code /wd/hub} is not added.
     */
    default void startLogcatBroadcast() {
        URL serverUrl = remoteServerUrl();
        startLogcatBroadcast(serverUrl.getHost(), serverUrl.getPort());
    }

    /**
     * Start logcat messages broadcast via web socket.
     * This method assumes that Appium server is assigned to the default port (4723).
     * The scheme still follows the server this driver is connected to.
     *
     * @param host the name of the host where Appium server is running
     */
    default void startLogcatBroadcast(String host) {
        startLogcatBroadcast(host, DEFAULT_APPIUM_PORT);
    }

    /**
     * Start logcat messages broadcast via web socket.
     * The scheme follows the server this driver is connected to.
     * The socket path is the server-root logcat route; an HTTP base path is not added.
     *
     * @param host the name of the host where Appium server is running
     * @param port the port of the host where Appium server is running.
     *             A negative port means the scheme default and is omitted from the URI.
     */
    default void startLogcatBroadcast(String host, int port) {
        var remoteWebDriver = (AppiumRemoteWebDriver) this;
        URI endpoint = logcatEndpoint(remoteServerUrl(), host, port,
                String.valueOf(remoteWebDriver.getSessionId()));
        CommandExecutionHelper.executeScript(this, "mobile: startLogsBroadcast");
        getLogcatClient().connect(endpoint);
    }

    /**
     * Returns the Appium server URL commands are sent to.
     *
     * @return the remote server URL
     */
    private URL remoteServerUrl() {
        var remoteWebDriver = (AppiumRemoteWebDriver) this;
        URL serverUrl = ((AppiumCommandExecutor) remoteWebDriver.getCommandExecutor()).getAddressOfRemoteServer();
        if (serverUrl == null) {
            throw new IllegalArgumentException("The Appium server address is not set");
        }
        return serverUrl;
    }

    /**
     * Builds the logcat web socket URI.
     * The socket is registered at the server root, so {@code serverUrl}'s HTTP base path is not included.
     *
     * @param serverUrl the Appium server the driver is connected to. Only its scheme is used.
     * @param host the socket host
     * @param port the socket port, or a negative value to omit the port
     * @param sessionId the current session id
     * @return the absolute web socket URI
     */
    private static URI logcatEndpoint(URL serverUrl, String host, int port, String sessionId) {
        try {
            return new URI(websocketScheme(serverUrl.getProtocol()), null, requireNonNull(host, "Host"), port,
                    "/ws/session/" + sessionId + "/appium/device/logcat", null, null);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Cannot build the logcat web socket endpoint", e);
        }
    }

    private static String websocketScheme(String protocol) {
        return "https".equalsIgnoreCase(protocol) || "wss".equalsIgnoreCase(protocol) ? "wss" : "ws";
    }

    /**
     * Adds a new log messages broadcasting handler.
     * Several handlers might be assigned to a single server.
     * Multiple calls to this method will cause such handler
     * to be called multiple times.
     *
     * @param handler a function, which accepts a single argument, which is the actual log message
     */
    default void addLogcatMessagesListener(Consumer<String> handler) {
        getLogcatClient().addMessageHandler(handler);
    }

    /**
     * Adds a new log broadcasting errors handler.
     * Several handlers might be assigned to a single server.
     * Multiple calls to this method will cause such handler
     * to be called multiple times.
     *
     * @param handler a function, which accepts a single argument, which is the actual exception instance
     */
    default void addLogcatErrorsListener(Consumer<Throwable> handler) {
        getLogcatClient().addErrorHandler(handler);
    }

    /**
     * Adds a new log broadcasting connection handler.
     * Several handlers might be assigned to a single server.
     * Multiple calls to this method will cause such handler
     * to be called multiple times.
     *
     * @param handler a function, which is executed as soon as the client is successfully
     *                connected to the web socket
     */
    default void addLogcatConnectionListener(Runnable handler) {
        getLogcatClient().addConnectionHandler(handler);
    }

    /**
     * Adds a new log broadcasting disconnection handler.
     * Several handlers might be assigned to a single server.
     * Multiple calls to this method will cause such handler
     * to be called multiple times.
     *
     * @param handler a function, which is executed as soon as the client is successfully
     *                disconnected from the web socket
     */
    default void addLogcatDisconnectionListener(Runnable handler) {
        getLogcatClient().addDisconnectionHandler(handler);
    }

    /**
     * Removes all existing logcat handlers.
     */
    default void removeAllLogcatListeners() {
        getLogcatClient().removeAllHandlers();
    }

    /**
     * Stops logcat messages broadcast via web socket.
     */
    default void stopLogcatBroadcast() {
        removeAllLogcatListeners();
        CommandExecutionHelper.executeScript(this, "mobile: stopLogsBroadcast");
    }
}
