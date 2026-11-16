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

import java.net.URL;
import java.util.function.Consumer;

import static io.appium.java_client.service.local.AppiumServiceBuilder.DEFAULT_APPIUM_PORT;

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
     * This method uses the current remote server host (mapping wildcard addresses to {@code 127.0.0.1})
     * and port when available, falling back to {@code 127.0.0.1:4723}.
     */
    default void startLogcatBroadcast() {
        URL serverUrl = LogBroadcastEndpoints.resolveServerUrl(this);
        startLogcatBroadcast(
                LogBroadcastEndpoints.resolveHost(serverUrl, "127.0.0.1"),
                LogBroadcastEndpoints.resolvePort(serverUrl, DEFAULT_APPIUM_PORT)
        );
    }

    /**
     * Start logcat messages broadcast via web socket.
     * This method uses the current remote server port when available,
     * falling back to the default port (4723).
     *
     * @param host the name of the host where Appium server is running
     */
    default void startLogcatBroadcast(String host) {
        startLogcatBroadcast(
                host,
                LogBroadcastEndpoints.resolvePort(LogBroadcastEndpoints.resolveServerUrl(this), DEFAULT_APPIUM_PORT)
        );
    }

    /**
     * Start logcat messages broadcast via web socket.
     *
     * @param host the name of the host where Appium server is running
     * @param port the port of the host where Appium server is running
     */
    default void startLogcatBroadcast(String host, int port) {
        LogBroadcastEndpoints.startBroadcast(this, getLogcatClient(), host, port, "logcat");
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
