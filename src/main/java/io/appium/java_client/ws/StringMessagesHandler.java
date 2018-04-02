package io.appium.java_client.ws;

/**
 * All classes, that handle web socket messages of String type
 * must implement this interface.
 */
public interface StringMessagesHandler extends MessagesHandler {
    /**
     * This event is fired when the client receives
     * a new string message from a web socket.
     *
     * @param message the actual message content
     */
    void onMessage(String message);
}
