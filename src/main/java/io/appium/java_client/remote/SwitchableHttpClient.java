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

package io.appium.java_client.remote;

import org.jspecify.annotations.NullMarked;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.WebSocket;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

/**
 * An {@link HttpClient} that forwards every call to another client, which can be replaced.
 * Direct connect uses it to point the executor to a new server URL without touching the
 * client field inside {@link org.openqa.selenium.remote.HttpCommandExecutor}.
 */
@NullMarked
final class SwitchableHttpClient implements HttpClient {

    private volatile HttpClient delegate;

    SwitchableHttpClient(HttpClient delegate) {
        this.delegate = requireNonNull(delegate);
    }

    /**
     * Sends all later calls to the given client and closes the previous one.
     *
     * @param newDelegate the client to use from now on
     */
    void switchTo(HttpClient newDelegate) {
        HttpClient previous = this.delegate;
        this.delegate = requireNonNull(newDelegate);
        previous.close();
    }

    @Override
    public HttpResponse execute(HttpRequest req) {
        return delegate.execute(req);
    }

    @Override
    public CompletableFuture<HttpResponse> executeAsync(HttpRequest req) {
        return delegate.executeAsync(req);
    }

    @Override
    public WebSocket openSocket(HttpRequest request, WebSocket.Listener listener) {
        return delegate.openSocket(request, listener);
    }

    @Override
    public void close() {
        delegate.close();
    }
}
