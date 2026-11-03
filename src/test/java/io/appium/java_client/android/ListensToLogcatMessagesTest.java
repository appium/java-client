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

package io.appium.java_client.android;

import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.http.Contents;
import io.appium.java_client.http.HttpClient;
import io.appium.java_client.http.HttpRequest;
import io.appium.java_client.http.HttpResponse;
import io.appium.java_client.http.Message;
import io.appium.java_client.http.WebSocket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Checks the logcat socket URL without a device. The HTTP client is scripted.
 */
class ListensToLogcatMessagesTest {
    private static final String SESSION_RESPONSE = "{\"value\":{\"sessionId\":\"s1\",\"capabilities\":"
            + "{\"platformName\":\"Android\",\"appium:automationName\":\"UiAutomator2\"}}}";

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvSource({
        "http://127.0.0.1:4723, ws://127.0.0.1:4723/ws/session/s1/appium/device/logcat",
        "http://10.0.0.8:5000, ws://10.0.0.8:5000/ws/session/s1/appium/device/logcat",
        "http://10.0.0.8:5000/, ws://10.0.0.8:5000/ws/session/s1/appium/device/logcat",
        "http://10.0.0.8:5000/wd/hub, ws://10.0.0.8:5000/ws/session/s1/appium/device/logcat",
        "http://127.0.0.1:4723/wd/hub/, ws://127.0.0.1:4723/ws/session/s1/appium/device/logcat",
        "https://appium.example.com/wd/hub, wss://appium.example.com/ws/session/s1/appium/device/logcat",
        "https://appium.example.com:8443/custom/, wss://appium.example.com:8443/ws/session/s1/appium/device/logcat",
        "http://[::1]:4723/wd/hub, ws://[::1]:4723/ws/session/s1/appium/device/logcat"
    })
    void noArgUsesTheConnectedServer(String serverUrl, String expectedSocket) throws Exception {
        List<String> events = broadcast(serverUrl, AndroidDriver::startLogcatBroadcast);
        assertEquals(List.of(
                "POST /session/s1/execute/sync",
                "WS " + expectedSocket
        ), events);
    }

    @Test
    void explicitHostKeepsDefaultPortAndServerScheme() throws Exception {
        List<String> events = broadcast("https://appium.example.com:8443/wd/hub",
                driver -> driver.startLogcatBroadcast("logs.internal"));
        assertEquals(List.of(
                "POST /session/s1/execute/sync",
                "WS wss://logs.internal:4723/ws/session/s1/appium/device/logcat"
        ), events);
    }

    @Test
    void explicitHostAndPortStayOnTheGivenEndpoint() throws Exception {
        List<String> events = broadcast("http://10.0.0.8:5000/wd/hub/",
                driver -> driver.startLogcatBroadcast("logs.internal", 9000));
        assertEquals(List.of(
                "POST /session/s1/execute/sync",
                "WS ws://logs.internal:9000/ws/session/s1/appium/device/logcat"
        ), events);
    }

    private static List<String> broadcast(String serverUrl, Consumer<AndroidDriver> action) throws Exception {
        ScriptedServer server = new ScriptedServer();
        URL remoteAddress = URI.create(serverUrl).toURL();
        AndroidDriver driver = new AndroidDriver(remoteAddress, server, new UiAutomator2Options().setDeviceName("emu"));
        try {
            server.events.clear();
            action.accept(driver);
            return List.copyOf(server.events);
        } finally {
            driver.quit();
        }
    }

    /** Records HTTP and web socket calls and answers the session handshake. */
    private static final class ScriptedServer implements HttpClient.Factory {
        private final List<String> events = new ArrayList<>();

        @Override
        public HttpClient createClient(io.appium.java_client.http.ClientConfig config) {
            return new HttpClient() {
                @Override
                public HttpResponse execute(HttpRequest request) {
                    events.add(request.getMethod() + " " + request.getUri());
                    String body = "POST".equals(request.getMethod().name()) && "/session".equals(request.getUri())
                            ? SESSION_RESPONSE : "{\"value\":null}";
                    return new HttpResponse().setStatus(200)
                            .setHeader("Content-Type", "application/json; charset=utf-8")
                            .setContent(Contents.utf8String(body));
                }

                @Override
                public WebSocket openSocket(HttpRequest request, WebSocket.Listener listener) {
                    events.add("WS " + request.getUri());
                    return new NoopSocket();
                }
            };
        }
    }

    private static final class NoopSocket implements WebSocket {
        @Override
        public WebSocket send(Message message) {
            return this;
        }

        @Override
        public void close() {
        }
    }
}
