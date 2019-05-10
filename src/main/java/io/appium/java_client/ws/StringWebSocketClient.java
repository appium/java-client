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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.annotation.Nullable;

public class StringWebSocketClient extends WebSocketListener implements
        CanHandleMessages<String>, CanHandleErrors, CanHandleConnects, CanHandleDisconnects {
    private final List<Consumer<String>> messageHandlers = new CopyOnWriteArrayList<>();
    private final List<Consumer<Throwable>> errorHandlers = new CopyOnWriteArrayList<>();
    private final List<Runnable> connectHandlers = new CopyOnWriteArrayList<>();
    private final List<Runnable> disconnectHandlers = new CopyOnWriteArrayList<>();

    private volatile boolean isListening = false;

    private URI endpoint;

    private void setEndpoint(URI endpoint) {
        this.endpoint = endpoint;
    }

    @Nullable
    public URI getEndpoint() {
        return this.endpoint;
    }

    public boolean isListening() {
        return isListening;
    }

    /**
     * Connects web socket client.
     *
     * @param endpoint The full address of an endpoint to connect to.
     *                 Usually starts with 'ws://'.
     */
    public void connect(URI endpoint) {
        if (endpoint.equals(this.getEndpoint()) && isListening) {
            return;
        }

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .build();
        Request request = new Request.Builder()
                .url(endpoint.toString())
                .build();
        client.newWebSocket(request, this);
        client.dispatcher().executorService().shutdown();

        setEndpoint(endpoint);
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        getConnectionHandlers().forEach(Runnable::run);
        isListening = true;
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        getDisconnectionHandlers().forEach(Runnable::run);
        isListening = false;
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        getErrorHandlers().forEach(x -> x.accept(t));
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        getMessageHandlers().forEach(x -> x.accept(text));
    }

    @Override
    public List<Consumer<String>> getMessageHandlers() {
        return messageHandlers;
    }

    @Override
    public List<Consumer<Throwable>> getErrorHandlers() {
        return errorHandlers;
    }

    @Override
    public List<Runnable> getConnectionHandlers() {
        return connectHandlers;
    }

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
