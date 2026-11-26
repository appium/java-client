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

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.bidi.BiDiException;
import org.openqa.selenium.bidi.HasBiDi;
import org.openqa.selenium.bidi.log.GenericLogEntry;
import org.openqa.selenium.bidi.module.LogInspector;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SeleniumBridgeTest {
    private static final String ELEMENT_KEY = "element-6066-11e4-a52e-4f735466cecf";

    private HttpServer server;
    private FakeBiDiServer biDiServer;
    private final List<String> requests = new CopyOnWriteArrayList<>();
    private String webSocketUrl;
    private String extraCapabilities = "";
    private boolean failQuit;

    @BeforeEach
    void startServers() throws IOException {
        biDiServer = new FakeBiDiServer();
        webSocketUrl = "ws://127.0.0.1:" + biDiServer.port() + "/session/s1";
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", this::handle);
        server.start();
    }

    @AfterEach
    void stopServers() throws IOException {
        server.stop(0);
        biDiServer.close();
    }

    private void handle(HttpExchange exchange) throws IOException {
        var key = exchange.getRequestMethod() + " " + exchange.getRequestURI().getPath();
        var requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        requests.add(key);
        var status = 200;
        String body;
        switch (key) {
            case "POST /session":
                body = "{\"value\":{\"sessionId\":\"s1\",\"capabilities\":{\"platformName\":\"Android\","
                        + (webSocketUrl == null ? "" : "\"webSocketUrl\":\"" + webSocketUrl + "\",")
                        + extraCapabilities
                        + "\"appium:automationName\":\"UiAutomator2\"}}}";
                break;
            case "GET /session/s1/title":
                body = "{\"value\":\"A title\"}";
                break;
            case "POST /session/s1/element":
                if (requestBody.contains("missing")) {
                    status = 404;
                    body = "{\"value\":{\"error\":\"no such element\",\"message\":\"not here\",\"stacktrace\":\"\"}}";
                } else {
                    body = "{\"value\":{\"" + ELEMENT_KEY + "\":\"el1\"}}";
                }
                break;
            case "GET /session/s1/element/el1/text":
                body = "{\"value\":\"element text\"}";
                break;
            case "DELETE /session/s1":
                if (failQuit) {
                    status = 500;
                    body = "{\"value\":{\"error\":\"unknown error\",\"message\":\"quit failed\",\"stacktrace\":\"\"}}";
                } else {
                    body = "{\"value\":null}";
                }
                break;
            default:
                body = "{\"value\":null}";
                break;
        }
        var bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(bytes);
        }
    }

    private AndroidDriver newAppiumDriver() throws IOException {
        return new AndroidDriver(new URL("http://127.0.0.1:" + server.getAddress().getPort()),
                new UiAutomator2Options().setDeviceName("Android Emulator"));
    }

    @Test
    void sharesTheSessionOfTheAppiumDriver() throws IOException {
        var appiumDriver = newAppiumDriver();

        var seleniumDriver = SeleniumBridge.asRemoteWebDriver(appiumDriver);

        assertInstanceOf(RemoteWebDriver.class, seleniumDriver);
        assertEquals("s1", String.valueOf(seleniumDriver.getSessionId()));
        assertSame(appiumDriver, seleniumDriver.getWrappedDriver());
        assertEquals("UiAutomator2", seleniumDriver.getCapabilities().getCapability("appium:automationName"));
        assertEquals(appiumDriver.getCapabilities().getPlatformName(),
                seleniumDriver.getCapabilities().getPlatformName());
        requests.clear();
        assertEquals("A title", seleniumDriver.getTitle());
        var element = seleniumDriver.findElement(By.id("x"));
        assertInstanceOf(RemoteWebElement.class, element);
        assertEquals("element text", element.getText());
        assertEquals(List.of("GET /session/s1/title", "POST /session/s1/element",
                "GET /session/s1/element/el1/text"), requests);
    }

    @Test
    void worksWithTheSeleniumAugmenter() throws IOException {
        var augmented = new Augmenter().augment(SeleniumBridge.asRemoteWebDriver(newAppiumDriver()));

        assertEquals("A title", augmented.getTitle());
    }

    @Test
    void mapsErrorsToSeleniumExceptions() throws IOException {
        var seleniumDriver = SeleniumBridge.asRemoteWebDriver(newAppiumDriver());

        assertThrows(NoSuchElementException.class, () -> seleniumDriver.findElement(By.id("missing")));
    }

    @Test
    void quitsTheSessionOfTheAppiumDriver() throws IOException {
        var appiumDriver = newAppiumDriver();
        var seleniumDriver = SeleniumBridge.asRemoteWebDriver(appiumDriver);
        requests.clear();

        seleniumDriver.quit();
        appiumDriver.quit();

        assertSessionClosed(appiumDriver, seleniumDriver);
        assertEquals(1, biDiServer.connections());
    }

    @Test
    void doesNotSendDuplicateDeleteWhenDelegateQuitFirst() throws IOException {
        var appiumDriver = newAppiumDriver();
        var seleniumDriver = SeleniumBridge.asRemoteWebDriver(appiumDriver);
        requests.clear();

        appiumDriver.quit();
        seleniumDriver.quit();

        assertSessionClosed(appiumDriver, seleniumDriver);
    }

    @Test
    void clearsSessionAndEvictsBridgeWhenQuitFails() throws Exception {
        var appiumDriver = newAppiumDriver();
        var seleniumDriver = SeleniumBridge.asRemoteWebDriver(appiumDriver);
        failQuit = true;
        requests.clear();

        assertThrows(WebDriverException.class, seleniumDriver::quit);

        assertSessionClosed(appiumDriver, seleniumDriver);
        assertTrue(biDiServer.awaitClosedConnections(1, 5, TimeUnit.SECONDS));
    }

    private void assertSessionClosed(AndroidDriver appiumDriver, BridgedRemoteWebDriver seleniumDriver) {
        assertEquals(List.of("DELETE /session/s1"), requests);
        assertNull(seleniumDriver.getSessionId());
        assertNull(appiumDriver.getSessionId());
        assertThrows(SessionNotCreatedException.class, () -> SeleniumBridge.asRemoteWebDriver(appiumDriver));
    }

    @Test
    void forwardsHttpVersionConfigurationToSeleniumClientConfig() throws IOException {
        var config = AppiumClientConfig.defaultConfig()
                .baseUri(URI.create("http://127.0.0.1:" + server.getAddress().getPort()))
                .version("HTTP_2");
        var appiumDriver = new AndroidDriver(config,
                new UiAutomator2Options().setDeviceName("Android Emulator"));
        var capturedConfig = new AtomicReference<ClientConfig>();
        var defaultFactory = HttpClient.Factory.createDefault();

        var seleniumDriver = SeleniumBridge.asRemoteWebDriver(appiumDriver, clientConfig -> {
            capturedConfig.set(clientConfig);
            return defaultFactory.createClient(clientConfig);
        });
        try {
            assertNotNull(capturedConfig.get());
            assertEquals("HTTP_2", capturedConfig.get().version());
        } finally {
            seleniumDriver.quit();
        }
    }

    @Test
    void connectsBiDiFromTheWebSocketUrlCapability() throws IOException {
        var seleniumDriver = SeleniumBridge.asRemoteWebDriver(newAppiumDriver());

        assertInstanceOf(HasBiDi.class, seleniumDriver);
        assertNotNull(((HasBiDi) seleniumDriver).getHandle());
        assertEquals(1, biDiServer.connections());
    }

    @Test
    void deliversBiDiEventsToSeleniumModules() throws Exception {
        var seleniumDriver = SeleniumBridge.asRemoteWebDriver(newAppiumDriver());
        var logs = new CopyOnWriteArrayList<GenericLogEntry>();

        try (var logInspector = new LogInspector(seleniumDriver)) {
            logInspector.onGenericLog(logs::add);
            assertTrue(biDiServer.awaitEvent(5, TimeUnit.SECONDS));
            for (int i = 0; i < 50 && logs.isEmpty(); i++) {
                Thread.sleep(100);
            }
        }

        assertEquals(1, logs.size());
        assertEquals("hello", logs.get(0).getText());
        seleniumDriver.quit();
    }

    @Test
    void worksWithTheSeleniumAugmenterWhenItAugmentsTheDriver() throws IOException {
        extraCapabilities = "\"se:cdp\":\"ws://127.0.0.1:1/devtools\",";
        var seleniumDriver = SeleniumBridge.asRemoteWebDriver(newAppiumDriver());

        var augmented = new Augmenter().augment(seleniumDriver);

        assertInstanceOf(HasDevTools.class, augmented);
        assertEquals("A title", augmented.getTitle());
    }

    @Test
    void keepsTheSessionOfTheAppiumDriverIfTheBridgeCannotBeCreated() throws IOException {
        var appiumDriver = newAppiumDriver();
        requests.clear();

        assertThrows(IllegalStateException.class, () -> SeleniumBridge.asRemoteWebDriver(appiumDriver, config -> {
            throw new IllegalStateException("bad factory");
        }));

        assertEquals("A title", appiumDriver.getTitle());
        assertFalse(requests.contains("DELETE /session/s1"));
    }

    @Test
    void opensOneBiDiConnectionForRepeatedCalls() throws IOException {
        var appiumDriver = newAppiumDriver();

        var first = SeleniumBridge.asRemoteWebDriver(appiumDriver);
        var second = SeleniumBridge.asRemoteWebDriver(appiumDriver);
        var third = SeleniumBridge.asRemoteWebDriver(appiumDriver);

        assertSame(first, second);
        assertSame(first, third);
        assertEquals(1, biDiServer.connections());
    }

    @Test
    void closesTheBiDiConnectionWithoutQuittingTheSession() throws Exception {
        var appiumDriver = newAppiumDriver();
        var first = SeleniumBridge.asRemoteWebDriver(appiumDriver);
        requests.clear();

        first.closeBiDi();

        assertTrue(biDiServer.awaitClosedConnections(1, 5, TimeUnit.SECONDS));
        assertFalse(requests.contains("DELETE /session/s1"));
        assertEquals("A title", first.getTitle());
        var second = SeleniumBridge.asRemoteWebDriver(appiumDriver);
        assertNotSame(first, second);
        assertEquals(2, biDiServer.connections());
    }

    @Test
    void closesTheBiDiConnectionOnQuit() throws Exception {
        var seleniumDriver = SeleniumBridge.asRemoteWebDriver(newAppiumDriver());

        seleniumDriver.quit();

        assertTrue(biDiServer.awaitClosedConnections(1, 5, TimeUnit.SECONDS));
    }

    @Test
    void failsToProvideBiDiWithoutTheWebSocketUrlCapability() throws IOException {
        webSocketUrl = null;
        var seleniumDriver = SeleniumBridge.asRemoteWebDriver(newAppiumDriver());

        assertThrows(BiDiException.class, seleniumDriver::getHandle);
    }
}
