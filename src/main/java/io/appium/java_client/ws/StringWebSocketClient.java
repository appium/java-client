package io.appium.java_client.ws;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint
public class StringWebSocketClient extends WebSocketClient
        implements CanHandleMessages<StringMessagesHandler> {
    private final List<StringMessagesHandler> messageHandlers = new CopyOnWriteArrayList<>();
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
     * @param config endpoint config
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        messageHandlers().forEach(MessagesHandler::onConnected);
    }

    /**
     * This event if fired when the client is
     * disconnected from a web socket.
     *
     * @param session the actual web socket session instance
     * @param reason connection close reason
     */
    @OnClose
    public void onClose(Session session, CloseReason reason) {
        this.session = null;
        messageHandlers().forEach(MessagesHandler::onDisconnected);
    }

    /**
     * This event if fired when there is an unexpected
     * error in web socket connection.
     *
     * @param session the actual web socket session instance
     * @param reason the actual error reason
     */
    @OnError
    public void onError(Session session, Throwable reason) {
        this.session = null;
        messageHandlers().forEach(x -> {
            x.onError(reason);
            x.onDisconnected();
        });
        throw new RuntimeException(reason);
    }

    /**
     * This event if fired when there is a
     * new message from the web socket.
     *
     * @param message the actual message content.
     */
    @OnMessage
    public void onMessage(String message) {
        messageHandlers().forEach(x -> x.onMessage(message));
    }

    /**
     * @return The list of all registered web socket messages handlers.
     */
    @Override
    public List<StringMessagesHandler> messageHandlers() {
        return messageHandlers;
    }
}
