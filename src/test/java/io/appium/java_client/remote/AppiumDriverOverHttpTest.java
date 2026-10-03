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

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Runs a driver against a real HTTP server, to cover the whole stack including the HTTP client.
 */
class AppiumDriverOverHttpTest {
    private static final String ELEMENT_KEY = "element-6066-11e4-a52e-4f735466cecf";

    private HttpServer server;
    private final List<String> requests = new CopyOnWriteArrayList<>();
    private final Map<String, Map<String, String>> headersByRequest = new ConcurrentHashMap<>();
    private final Map<String, String> bodiesByRequest = new ConcurrentHashMap<>();

    @BeforeEach
    void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", this::handle);
        server.start();
    }

    @AfterEach
    void stopServer() {
        server.stop(0);
    }

    private void handle(HttpExchange exchange) throws IOException {
        String key = exchange.getRequestMethod() + " " + exchange.getRequestURI().getPath();
        requests.add(key);
        Map<String, String> headers = new ConcurrentHashMap<>();
        exchange.getRequestHeaders().forEach((k, v) -> headers.put(k.toLowerCase(), String.join(",", v)));
        headers.put("x-protocol", exchange.getProtocol());
        headersByRequest.put(key, headers);
        bodiesByRequest.put(key, new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));
        int status = 200;
        String body;
        switch (key) {
            case "POST /session":
                body = "{\"value\":{\"sessionId\":\"s1\",\"capabilities\":{\"platformName\":\"Android\","
                        + "\"appium:automationName\":\"UiAutomator2\"}}}";
                break;
            case "GET /session/s1/title":
                body = "{\"value\":\"A title\"}";
                break;
            case "POST /session/s1/element":
                String using = bodiesByRequest.get(key);
                if (using.contains("missing")) {
                    status = 404;
                    body = "{\"value\":{\"error\":\"no such element\",\"message\":\"not here\","
                            + "\"stacktrace\":\"\"}}";
                } else {
                    body = "{\"value\":{\"" + ELEMENT_KEY + "\":\"el1\"}}";
                }
                break;
            case "GET /session/s1/element/el1/text":
                body = "{\"value\":\"element text\"}";
                break;
            case "DELETE /session/s1":
                body = "{\"value\":null}";
                break;
            default:
                status = 404;
                body = "{\"value\":{\"error\":\"unknown command\",\"message\":\"" + key + "\"}}";
                break;
        }
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(bytes);
        }
        exchange.close();
    }

    private AndroidDriver newDriver() throws IOException {
        return new AndroidDriver(new URL("http://127.0.0.1:" + server.getAddress().getPort()),
                new UiAutomator2Options().setDeviceName("emu"));
    }

    @Test
    void runsASessionOverTheDefaultHttpClient() throws IOException {
        var driver = newDriver();

        assertEquals("s1", driver.getSessionId().toString());
        assertEquals("A title", driver.getTitle());
        var element = driver.findElement(By.id("something"));
        assertInstanceOf(AppiumWebElement.class, element);
        assertEquals("element text", element.getText());
        driver.quit();

        assertEquals(List.of("POST /session", "GET /session/s1/title", "POST /session/s1/element",
                "GET /session/s1/element/el1/text", "DELETE /session/s1"), requests);
    }

    @Test
    void sendsTheExpectedHeaders() throws IOException {
        var driver = newDriver();
        driver.getTitle();

        var newSession = headersByRequest.get("POST /session");
        assertTrue(newSession.get("user-agent").startsWith("appium/"), newSession.get("user-agent"));
        assertTrue(newSession.get("user-agent").contains("(selenium/"));
        assertNotNull(newSession.get("x-idempotency-key"));
        assertEquals("application/json; charset=utf-8", newSession.get("content-type"));
        assertEquals("HTTP/1.1", newSession.get("x-protocol"));
        var title = headersByRequest.get("GET /session/s1/title");
        assertTrue(title.get("user-agent").startsWith("appium/"));
        assertEquals("no-cache", title.get("cache-control"));
        assertEquals("application/json; charset=utf-8", title.get("content-type"));
        assertTrue(!title.containsKey("x-idempotency-key"));
    }

    @Test
    void mapsErrorResponsesToExceptions() throws IOException {
        var driver = newDriver();

        var error = assertThrows(NoSuchElementException.class, () -> driver.findElement(By.id("missing")));

        assertTrue(error.getRawMessage().startsWith("not here"));
    }

    @Test
    void reportsAnUnreachableServer() throws IOException {
        var driver = newDriver();
        server.stop(0);

        var error = assertThrows(WebDriverException.class, driver::getTitle);

        assertTrue(error.getMessage().contains("Connection refused") || error instanceof UnreachableBrowserException,
                error.getMessage());
    }
}
