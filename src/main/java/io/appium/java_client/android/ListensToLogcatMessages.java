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
import io.appium.java_client.internal.LogBroadcastEndpoints;
import io.appium.java_client.ws.StringWebSocketClient;

import java.util.function.Consumer;

import static io.appium.java_client.service.local.AppiumServiceBuilder.DEFAULT_APPIUM_PORT;

/**
 * Provides listening to logcat messages broadcast via web socket.
 *
 * @deprecated The logs broadcasting web socket of the Appium drivers is deprecated and will be removed
 *     together with this API. Subscribe to the BiDi {@code log.entryAdded} event instead, see the
 *     <a href="https://github.com/appium/java-client/blob/master/docs/selenium-bridge.md#bidi">Selenium
 *     bridge documentation</a>.
 */
@Deprecated(since = "11.1.0", forRemoval = true)
@SuppressWarnings("removal")
public interface ListensToLogcatMessages extends ExecutesMethod {
    /**
     * Gets the web socket client used for receiving logcat messages.
     *
     * @return the logcat web socket client.
     */
    @Deprecated(since = "11.1.0", forRemoval = true)
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
    @Deprecated(since = "11.1.0", forRemoval = true)
    default void startLogcatBroadcast() {
        LogBroadcastEndpoints.startBroadcast(this, this::getLogcatClient, "logcat");
    }

    /**
     * Start logcat messages broadcast via web socket.
     * This method assumes that Appium server is assigned to the default port (4723).
     * The scheme follows the initial Appium server URL configured on the executor,
     * defaulting to {@code ws} if unset.
     *
     * @param host the name of the host where Appium server is running
     */
    @Deprecated(since = "11.1.0", forRemoval = true)
    default void startLogcatBroadcast(String host) {
        startLogcatBroadcast(host, DEFAULT_APPIUM_PORT);
    }

    /**
     * Start logcat messages broadcast via web socket.
     * The scheme follows the initial Appium server URL configured on the executor,
     * defaulting to {@code ws} if unset.
     * The socket path is the server-root logcat route; an HTTP base path is not added.
     *
     * @param host the name of the host where Appium server is running
     * @param port the port of the host where Appium server is running.
     *             A negative port means the scheme default and is omitted from the URI.
     */
    @Deprecated(since = "11.1.0", forRemoval = true)
    default void startLogcatBroadcast(String host, int port) {
        LogBroadcastEndpoints.startBroadcast(this, this::getLogcatClient, host, port, "logcat");
    }

    /**
     * Adds a new log messages broadcasting handler.
     * Several handlers might be assigned to a single server.
     * Multiple calls to this method will cause such handler
     * to be called multiple times.
     *
     * @param handler a function, which accepts a single argument, which is the actual log message
     */
    @Deprecated(since = "11.1.0", forRemoval = true)
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
    @Deprecated(since = "11.1.0", forRemoval = true)
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
    @Deprecated(since = "11.1.0", forRemoval = true)
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
    @Deprecated(since = "11.1.0", forRemoval = true)
    default void addLogcatDisconnectionListener(Runnable handler) {
        getLogcatClient().addDisconnectionHandler(handler);
    }

    /**
     * Removes all existing logcat handlers.
     */
    @Deprecated(since = "11.1.0", forRemoval = true)
    default void removeAllLogcatListeners() {
        getLogcatClient().removeAllHandlers();
    }

    /**
     * Stops logcat messages broadcast via web socket.
     */
    @Deprecated(since = "11.1.0", forRemoval = true)
    default void stopLogcatBroadcast() {
        removeAllLogcatListeners();
        CommandExecutionHelper.executeScript(this, "mobile: stopLogsBroadcast");
    }
}
