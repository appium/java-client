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

package io.appium.java_client.http;

import io.appium.java_client.internal.http.JdkHttpClient;

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;

/**
 * Sends requests to a server and opens WebSockets. Implementations apply
 * the filter of the {@link ClientConfig} they were created with.
 */
public interface HttpClient extends Closeable, HttpHandler {
    WebSocket openSocket(HttpRequest request, WebSocket.Listener listener);

    default CompletableFuture<HttpResponse> executeAsync(HttpRequest req) {
        return CompletableFuture.supplyAsync(() -> execute(req));
    }

    @Override
    default void close() {
    }

    /**
     * Creates clients. Implement it to plug in a different HTTP stack.
     */
    interface Factory {
        /**
         * The factory of the default transport, which is based on {@code java.net.http}.
         *
         * @return the default factory
         */
        static Factory createDefault() {
            return new JdkHttpClient.Factory();
        }

        HttpClient createClient(ClientConfig config);
    }
}
