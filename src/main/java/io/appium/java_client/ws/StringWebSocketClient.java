package io.appium.java_client.ws;

import org.openqa.selenium.WebDriverException;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint
public class StringWebSocketClient extends WebSocketClient
        implements CanHandleMessages<MessagesHandler<String>> {
    private final List<MessagesHandler<String>> messageHandlers = new CopyOnWriteArrayList<>();
    private volatile Session session;

    @Override
    public void connect(URI endpoint) {
        if (session != null) {
            if (endpoint.equals(this.getEndpoint())) {
                return;
            }

            removeAllMessageHandlers();
            try {
                session.close();
            } catch (IOException e) {
                // ignore
            }
            session = null;
        }
        super.connect(endpoint);
    }

    /**
     * This event if fired when the client is successfully
     * connected to a web socket.
     *
     * @param session the actual web socket session instance
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        getMessageHandlers().forEach(MessagesHandler::onConnected);
    }

    /**
     * This event if fired when the client is
     * disconnected from a web socket.
     */
    @OnClose
    public void onClose() {
        this.session = null;
        getMessageHandlers().forEach(MessagesHandler::onDisconnected);
    }

    /**
     * This event if fired when there is an unexpected
     * error in web socket connection.
     *
     * @param cause the actual error reason
     */
    @OnError
    public void onError(Throwable cause) {
        this.session = null;
        getMessageHandlers().forEach(x -> x.onError(cause));
        throw new WebDriverException(cause);
    }

    /**
     * This event if fired when there is a
     * new message from the web socket.
     *
     * @param message the actual message content.
     */
    @OnMessage
    public void onMessage(String message) {
        getMessageHandlers().forEach(x -> x.onMessage(message));
    }

    /**
     * @return The list of all registered web socket messages handlers.
     */
    @Override
    public List<MessagesHandler<String>> getMessageHandlers() {
        return messageHandlers;
    }
}
