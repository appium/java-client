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

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.PowerACState;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.options.WindowsOptions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Checks the commands that the drivers send for the features that have `mobile:` or `windows:` extensions
 * or only the driver-level commands.
 */
class ExtensionCommandsOverHttpTest {
    private static final Gson GSON = new Gson();

    private HttpServer server;
    private final List<String> requests = new ArrayList<>();
    private final List<Map<String, Object>> bodies = new ArrayList<>();

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

    private synchronized void handle(HttpExchange exchange) throws IOException {
        var key = exchange.getRequestMethod() + " " + exchange.getRequestURI().getPath();
        var body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        requests.add(key);
        bodies.add(body.isEmpty() ? Map.of() : GSON.<Map<String, Object>>fromJson(body, Map.class));
        String json;
        switch (key) {
            case "POST /session":
                json = "{\"value\":{\"sessionId\":\"s1\",\"capabilities\":{\"platformName\":\"Android\"}}}";
                break;
            case "POST /session/s1/appium/device/pull_file":
            case "POST /session/s1/appium/device/pull_folder":
                json = "{\"value\":\"" + Base64.getEncoder().encodeToString("data".getBytes(StandardCharsets.UTF_8))
                        + "\"}";
                break;
            default:
                json = "{\"value\":null}";
                break;
        }
        var bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(200, bytes.length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(bytes);
        }
        exchange.close();
    }

    private URL url() throws IOException {
        return new URL("http://127.0.0.1:" + server.getAddress().getPort());
    }

    private synchronized Map<String, Object> lastBody() {
        return bodies.get(bodies.size() - 1);
    }

    private synchronized String lastRequest() {
        return requests.get(requests.size() - 1);
    }

    @Test
    void emulatorPowerStateUsesTheDriverExtension() throws IOException {
        var driver = new AndroidDriver(url(), new UiAutomator2Options());

        driver.setPowerAC(PowerACState.OFF);

        assertEquals("POST /session/s1/execute/sync", lastRequest());
        assertEquals("mobile: powerAc", lastBody().get("script"));
        assertEquals(List.of(Map.of("state", "off")), lastBody().get("args"));
    }

    @Test
    void windowsFilesAreTransferredWithTheDriverCommands() throws IOException {
        var driver = new WindowsDriver(url(), new WindowsOptions());

        assertArrayEquals("data".getBytes(StandardCharsets.UTF_8), driver.pullFile("C:\\a.txt"));
        assertEquals("POST /session/s1/appium/device/pull_file", lastRequest());
        assertEquals(Map.of("path", "C:\\a.txt"), lastBody());

        assertArrayEquals("data".getBytes(StandardCharsets.UTF_8), driver.pullFolder("C:\\dir"));
        assertEquals("POST /session/s1/appium/device/pull_folder", lastRequest());
        assertEquals(Map.of("path", "C:\\dir"), lastBody());

        driver.pushFile("C:\\b.txt", "ZGF0YQ==".getBytes(StandardCharsets.UTF_8));
        assertEquals("POST /session/s1/appium/device/push_file", lastRequest());
        assertEquals(Map.of("path", "C:\\b.txt", "data", "ZGF0YQ=="), lastBody());
    }

    @Test
    void windowsAppIsLaunchedAndClosedWithTheDriverExtensions() throws IOException {
        var driver = new WindowsDriver(url(), new WindowsOptions());

        driver.closeApp();
        assertEquals("windows: closeApp", lastBody().get("script"));

        driver.launchApp();
        assertEquals("windows: launchApp", lastBody().get("script"));
    }
}
