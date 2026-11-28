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

package io.appium.java_client.ios;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import io.appium.java_client.internal.LogBroadcastEndpoints;
import io.appium.java_client.ws.StringWebSocketClient;

import java.util.function.Consumer;

import static io.appium.java_client.service.local.AppiumServiceBuilder.DEFAULT_APPIUM_PORT;

/**
 * Provides the ability to listen to syslog messages broadcast by the Appium server via web socket.
 *
 * @deprecated The logs broadcasting web socket of the Appium drivers is deprecated and will be removed
 *     together with this API. Subscribe to the BiDi {@code log.entryAdded} event instead, see the
 *     <a href="https://github.com/appium/java-client/blob/master/docs/selenium-bridge.md#bidi">Selenium
 *     bridge documentation</a>.
 */
@Deprecated(since = "11.1.0", forRemoval = true)
@SuppressWarnings("removal")
public interface ListensToSyslogMessages extends ExecutesMethod {

    /**
     * Returns the web socket client used to receive syslog messages.
     *
     * @return the syslog web socket client
     */
    @Deprecated(since = "11.1.0", forRemoval = true)
    StringWebSocketClient getSyslogClient();

    /**
     * Start syslog messages broadcast via web socket.
     * This method assumes that Appium server is running on localhost and
     * is assigned to the default port (4723).
     */
    @Deprecated(since = "11.1.0", forRemoval = true)
    default void startSyslogBroadcast() {
        startSyslogBroadcast("localhost");
    }

    /**
     * Start syslog messages broadcast via web socket.
     * This method assumes that Appium server is assigned to the default port (4723).
     * The scheme follows the Appium server this driver is connected to,
     * defaulting to {@code ws} if unset.
     *
     * @param host the name of the host where Appium server is running
     */
    @Deprecated(since = "11.1.0", forRemoval = true)
    default void startSyslogBroadcast(String host) {
        startSyslogBroadcast(host, DEFAULT_APPIUM_PORT);
    }

    /**
     * Start syslog messages broadcast via web socket.
     * The scheme follows the Appium server this driver is connected to,
     * defaulting to {@code ws} if unset.
     *
     * @param host the name of the host where Appium server is running
     * @param port the port of the host where Appium server is running.
     *             A negative port means the scheme default and is omitted from the URI.
     */
    @Deprecated(since = "11.1.0", forRemoval = true)
    default void startSyslogBroadcast(String host, int port) {
        LogBroadcastEndpoints.startBroadcast(this, this::getSyslogClient, host, port, "syslog");
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
    default void addSyslogMessagesListener(Consumer<String> handler) {
        getSyslogClient().addMessageHandler(handler);
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
    default void addSyslogErrorsListener(Consumer<Throwable> handler) {
        getSyslogClient().addErrorHandler(handler);
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
    default void addSyslogConnectionListener(Runnable handler) {
        getSyslogClient().addConnectionHandler(handler);
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
    default void addSyslogDisconnectionListener(Runnable handler) {
        getSyslogClient().addDisconnectionHandler(handler);
    }

    /**
     * Removes all existing syslog handlers.
     */
    @Deprecated(since = "11.1.0", forRemoval = true)
    default void removeAllSyslogListeners() {
        getSyslogClient().removeAllHandlers();
    }

    /**
     * Stops syslog messages broadcast via web socket.
     */
    @Deprecated(since = "11.1.0", forRemoval = true)
    default void stopSyslogBroadcast() {
        CommandExecutionHelper.executeScript(this, "mobile: stopLogsBroadcast");
    }
}
