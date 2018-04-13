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

package io.appium.java_client.ws;

import org.openqa.selenium.WebDriverException;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint
public class StringWebSocketClient extends WebSocketClient implements
        CanHandleMessages<String>, CanHandleErrors, CanHandleConnects, CanHandleDisconnects {
    private final List<Consumer<String>> messageHandlers = new CopyOnWriteArrayList<>();
    private final List<Consumer<Throwable>> errorHandlers = new CopyOnWriteArrayList<>();
    private final List<Runnable> connectHandlers = new CopyOnWriteArrayList<>();
    private final List<Runnable> disconnectHandlers = new CopyOnWriteArrayList<>();

    private volatile Session session;

    @Override
    public void connect(URI endpoint) {
        if (session != null) {
            if (endpoint.equals(this.getEndpoint())) {
                return;
            }

            removeAllHandlers();
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
        getConnectionHandlers().forEach(Runnable::run);
    }

    /**
     * This event if fired when the client is
     * disconnected from a web socket.
     */
    @OnClose
    public void onClose() {
        this.session = null;
        getDisconnectionHandlers().forEach(Runnable::run);
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
        getErrorHandlers().forEach(x -> x.accept(cause));
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
        getMessageHandlers().forEach(x -> x.accept(message));
    }

    /**
     * @return The list of all registered web socket messages handlers.
     */
    @Override
    public List<Consumer<String>> getMessageHandlers() {
        return messageHandlers;
    }

    /**
     * @return The list of all registered web socket error handlers.
     */
    @Override
    public List<Consumer<Throwable>> getErrorHandlers() {
        return errorHandlers;
    }

    /**
     * @return The list of all registered web socket connection handlers.
     */
    @Override
    public List<Runnable> getConnectionHandlers() {
        return connectHandlers;
    }

    /**
     * @return The list of all registered web socket disconnection handlers.
     */
    @Override
    public List<Runnable> getDisconnectionHandlers() {
        return disconnectHandlers;
    }

    /**
     * Remove all the registered handlers.
     */
    public void removeAllHandlers() {
        removeMessageHandlers();
        removeErrorHandlers();
        removeConnectionHandlers();
        removeDisconnectionHandlers();
    }
}
