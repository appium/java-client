package io.appium.java_client.ws;

/**
 * This is the basic interface for all web socket message handlers.
 */
public interface MessagesHandler {
    /**
     * This event is fired when the client is
     * successfully connected to a web socket.
     */
    void onConnected();

    /**
     * This event is fired when the client is
     * disconnected from a web socket.
     */
    void onDisconnected();

    /**
     * This event is fired when there is an error
     * in the web socket connection.
     * onDisconnected event is always generated after
     * onError happens.
     *
     * @param reason the actual error reason.
     */
    void onError(Throwable reason);
}
