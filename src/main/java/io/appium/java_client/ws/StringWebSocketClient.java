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

import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.WebSocket;

import javax.annotation.Nullable;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class StringWebSocketClient implements WebSocket.Listener,
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

        ClientConfig clientConfig = ClientConfig.defaultConfig()
                .readTimeout(Duration.ZERO)
                .baseUri(endpoint); // To avoid NPE in org.openqa.selenium.remote.http.netty.NettyMessages (line 78)
        HttpClient client = HttpClient.Factory.createDefault().createClient(clientConfig);
        HttpRequest request = new HttpRequest(HttpMethod.GET, endpoint.toString());
        client.openSocket(request, this);
        onOpen();

        setEndpoint(endpoint);
    }

    public void onOpen() {
        getConnectionHandlers().forEach(Runnable::run);
        isListening = true;
    }

    @Override
    public void onClose(int code, String reason) {
        getDisconnectionHandlers().forEach(Runnable::run);
        isListening = false;
    }

    @Override
    public void onError(Throwable t) {
        getErrorHandlers().forEach(x -> x.accept(t));
    }

    @Override
    public void onText(CharSequence data) {
        String text = data.toString();
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
