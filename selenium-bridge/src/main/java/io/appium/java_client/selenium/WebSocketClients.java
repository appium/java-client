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

package io.appium.java_client.selenium;

import org.openqa.selenium.remote.http.HttpClient;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Creates the HTTP clients of the BiDi connections with a factory and remembers them, so that the connections
 * can be closed without quitting the session.
 */
final class WebSocketClients {
    private final HttpClient.Factory factory;
    private final List<HttpClient> clients = new CopyOnWriteArrayList<>();

    WebSocketClients(HttpClient.Factory factory) {
        this.factory = factory;
    }

    HttpClient.Factory asFactory() {
        return config -> {
            var client = factory.createClient(config);
            clients.add(client);
            return client;
        };
    }

    void closeAll() {
        clients.forEach(HttpClient::close);
        clients.clear();
    }
}
