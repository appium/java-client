package io.appium.java_client.android;

import static io.appium.java_client.service.local.AppiumServiceBuilder.DEFAULT_APPIUM_PORT;
import static org.openqa.selenium.remote.DriverCommand.EXECUTE_SCRIPT;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.ExecutesMethod;
import io.appium.java_client.ws.MessagesHandler;
import io.appium.java_client.ws.StringWebSocketClient;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;
import java.net.URISyntaxException;

public interface ListensToLogcatMessages extends ExecutesMethod {
    StringWebSocketClient logcatClient = new StringWebSocketClient();

    /**
     * Start logcat messages broadcast via web socket.
     * This method assumes that Appium server is running on localhost and
     * is assigned to the default port (4723).
     */
    default void startLogcatBroadcast() {
        startLogcatBroadcast("localhost", DEFAULT_APPIUM_PORT);
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
        execute(EXECUTE_SCRIPT, ImmutableMap.of("script", "mobile: startLogsBroadcast"));
        final URI endpointUri;
        try {
            endpointUri = new URI(String.format("ws://%s:%s/ws/session/%s/appium/device/logcat",
                    host, port, ((RemoteWebDriver) this).getSessionId()));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
        logcatClient.connect(endpointUri);
    }

    /**
     * Adds a new log broadcasting handler.
     * Several handlers might be assigned to a single server.
     * Multiple calls to this method will cause the handler
     * to be called multiple times.
     *
     * @param handler an instance of a class, which implement string message handlers
     */
    default void addLogcatListener(MessagesHandler<String> handler) {
        logcatClient.addMessageHandler(handler);
    }

    /**
     * Removes all existing logcat message handlers.
     */
    default void removeAllLogcatListeners() {
        logcatClient.removeAllMessageHandlers();
    }

    /**
     * Stops logcat messages broadcast via web socket.
     */
    default void stopLogcatBroadcast() {
        execute(EXECUTE_SCRIPT, ImmutableMap.of("script", "mobile: stopLogsBroadcast"));
    }
}
