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
import io.appium.java_client.ws.StringWebSocketClient;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import java.net.URI;
import java.net.URL;
import java.util.function.Consumer;

import static io.appium.java_client.service.local.AppiumServiceBuilder.DEFAULT_APPIUM_PORT;

public interface ListensToLogcatMessages extends ExecutesMethod {
    StringWebSocketClient getLogcatClient();

    /**
     * Start logcat messages broadcast via web socket.
     * This method assumes that Appium server is running on localhost and
     * is assigned to the default port (4723).
     */
    default void startLogcatBroadcast() {
        startLogcatBroadcast("127.0.0.1");
    }

    /**
     * Start logcat messages broadcast via web socket.
     * This method assumes that Appium server is assigned to the default port (4723).
     *
     * @param host the name of the host where Appium server is running
     */
    default void startLogcatBroadcast(String host) {
        startLogcatBroadcast(host, DEFAULT_APPIUM_PORT);
    }

    /**
     * Start logcat messages broadcast via web socket.
     *
     * @param host the name of the host where Appium server is running
     * @param port the port of the host where Appium server is running
     */
    default void startLogcatBroadcast(String host, int port) {
        var remoteWebDriver = (RemoteWebDriver) this;
        URL serverUrl = ((HttpCommandExecutor) remoteWebDriver.getCommandExecutor()).getAddressOfRemoteServer();
        var scheme = "https".equals(serverUrl.getProtocol()) ? "wss" : "ws";
        CommandExecutionHelper.executeScript(this, "mobile: startLogsBroadcast");
        SessionId sessionId = remoteWebDriver.getSessionId();
        var endpoint = String.format("%s://%s:%s/ws/session/%s/appium/device/logcat", scheme, host, port, sessionId);
        getLogcatClient().connect(URI.create(endpoint));
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
