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

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.MobileCommand;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.http.Contents;
import io.appium.java_client.http.HttpClient;
import io.appium.java_client.http.HttpMethod;
import io.appium.java_client.http.HttpRequest;
import io.appium.java_client.http.HttpResponse;
import io.appium.java_client.http.WebSocket;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.print.PrintOptions;

import java.io.UncheckedIOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Pins the HTTP requests a driver sends for each WebDriver/Appium API call, and what it returns
 * for scripted server responses. Set GOLDEN_UPDATE=1 to regenerate the golden file.
 */
class DriverWireScenariosTest {
    private static final String ELEMENT_KEY = "element-6066-11e4-a52e-4f735466cecf";
    private static final String JSON = "application/json; charset=utf-8";
    private static final String SESSION_RESPONSE = "{\"value\":{\"sessionId\":\"s1\",\"capabilities\":"
            + "{\"platformName\":\"Android\",\"appium:automationName\":\"UiAutomator2\",\"appium:width\":1080}}}";
    private static final Pattern VERSION = Pattern.compile("\\d+\\.\\d+\\.\\d+(-SNAPSHOT)?");
    private static final Pattern UUID_PATTERN = Pattern.compile("[0-9a-f]{8}-[0-9a-f-]{27}");

    private static final class Route {
        private final String method;
        private final Pattern path;
        private final int status;
        private final String body;

        Route(String method, String path, int status, String body) {
            this.method = method;
            this.path = Pattern.compile(path);
            this.status = status;
            this.body = body;
        }
    }

    /** A scripted transport that records every request after the configured filters ran. */
    private static final class FakeTransport implements HttpClient.Factory {
        private final List<Route> routes = new ArrayList<>();
        private final List<Map<String, Object>> requests = new ArrayList<>();

        FakeTransport route(String method, String path, int status, String body) {
            routes.add(0, new Route(method, path, status, body));
            return this;
        }

        private static String normalize(String value) {
            return UUID_PATTERN.matcher(VERSION.matcher(value).replaceAll("x.y.z")).replaceAll("<uuid>")
                    .replaceAll("\\(java [a-z]+\\)", "(java os)");
        }

        @Override
        public HttpClient createClient(io.appium.java_client.http.ClientConfig config) {
            return new HttpClient() {
                private final io.appium.java_client.http.HttpHandler handler =
                        config.filter().andFinally(this::handle);

                @Override
                public HttpResponse execute(HttpRequest request) throws UncheckedIOException {
                    return handler.execute(request);
                }

                @Override
                public WebSocket openSocket(HttpRequest request, WebSocket.Listener listener) {
                    throw new UnsupportedOperationException();
                }

                private HttpResponse handle(HttpRequest request) {
                    String basePath = config.baseUri().getPath() == null ? "" : config.baseUri().getPath();
                    Map<String, Object> record = WireGoldenSupport.describeRequest(request);
                    record.put("client", config.baseUri().getScheme() + "://" + config.baseUri().getHost()
                            + (config.baseUri().getPort() < 0 ? "" : ":" + config.baseUri().getPort()));
                    record.put("uri", basePath.replaceAll("/$", "") + request.getUri());
                    @SuppressWarnings("unchecked")
                    Map<String, Object> headers = (Map<String, Object>) record.get("headers");
                    headers.replaceAll((k, v) -> normalize(String.valueOf(v)));
                    requests.add(record);
                    for (Route route : routes) {
                        if (route.method.equals(request.getMethod().name())
                                && route.path.matcher(request.getUri()).matches()) {
                            if (route.status < 0) {
                                throw new UncheckedIOException(new ConnectException("Connection refused"));
                            }
                            return new HttpResponse().setStatus(route.status).setHeader("Content-Type", JSON)
                                    .setContent(Contents.utf8String(route.body));
                        }
                    }
                    return new HttpResponse().setStatus(200).setHeader("Content-Type", JSON)
                            .setContent(Contents.utf8String("{\"value\":null}"));
                }
            };
        }

        static FakeTransport withDefaults() {
            String el = "{\"" + ELEMENT_KEY + "\":\"el1\"}";
            String el2 = "{\"" + ELEMENT_KEY + "\":\"el2\"}";
            return new FakeTransport()
                    .route("POST", "/session", 200, SESSION_RESPONSE)
                    .route("POST", "/session/s1/element", 200, "{\"value\":" + el + "}")
                    .route("POST", "/session/s1/elements", 200, "{\"value\":[" + el + "," + el2 + "]}")
                    .route("POST", "/session/s1/element/el1/element", 200, "{\"value\":" + el2 + "}")
                    .route("POST", "/session/s1/element/el1/elements", 200, "{\"value\":[" + el2 + "]}")
                    .route("GET", "/session/s1/element/active", 200, "{\"value\":" + el + "}")
                    .route("GET", "/session/s1/window/handles", 200, "{\"value\":[\"w1\",\"w2\"]}")
                    .route("GET", "/session/s1/window", 200, "{\"value\":\"w1\"}")
                    .route("GET", "/session/s1/title", 200, "{\"value\":\"Title\"}")
                    .route("GET", "/session/s1/url", 200, "{\"value\":\"https://example.com/\"}")
                    .route("GET", "/session/s1/source", 200, "{\"value\":\"<hierarchy/>\"}")
                    .route("GET", "/session/s1/screenshot", 200, "{\"value\":\"aGVsbG8=\\r\\n\"}")
                    .route("GET", "/session/s1/element/el1/screenshot", 200, "{\"value\":\"aGVsbG8=\"}")
                    .route("POST", "/session/s1/print", 200, "{\"value\":\"aGVsbG8=\"}")
                    .route("GET", "/session/s1/element/el1/text", 200, "{\"value\":\"hi\"}")
                    .route("GET", "/session/s1/element/el1/name", 200, "{\"value\":\"button\"}")
                    .route("GET", "/session/s1/element/el1/attribute/[^/]+", 200, "{\"value\":\"attr\"}")
                    .route("GET", "/session/s1/element/el1/property/[^/]+", 200, "{\"value\":\"prop\"}")
                    .route("GET", "/session/s1/element/el1/css/[^/]+", 200, "{\"value\":\"red\"}")
                    .route("GET", "/session/s1/element/el1/(displayed|enabled|selected)", 200, "{\"value\":true}")
                    .route("GET", "/session/s1/element/el1/rect", 200,
                            "{\"value\":{\"x\":1,\"y\":2,\"width\":3,\"height\":4}}")
                    .route("GET", "/session/s1/window/rect", 200,
                            "{\"value\":{\"x\":0,\"y\":0,\"width\":1080,\"height\":1920}}")
                    .route("POST", "/session/s1/window/rect", 200,
                            "{\"value\":{\"x\":0,\"y\":0,\"width\":300,\"height\":400}}")
                    .route("GET", "/session/s1/cookie", 200,
                            "{\"value\":[{\"name\":\"a\",\"value\":\"b\",\"path\":\"/\",\"domain\":\"d\","
                                    + "\"secure\":false,\"httpOnly\":true,\"expiry\":1700000000}]}")
                    .route("GET", "/session/s1/timeouts", 200,
                            "{\"value\":{\"implicit\":1000,\"pageLoad\":2000,\"script\":3000}}")
                    .route("GET", "/session/s1/alert/text", 200, "{\"value\":\"alert text\"}")
                    .route("POST", "/session/s1/execute/(sync|async)", 200,
                            "{\"value\":[" + el + ",{\"k\":1,\"d\":1.5},\"s\",true,null]}")
                    .route("POST", "/session/s1/window/new", 200,
                            "{\"value\":{\"handle\":\"w3\",\"type\":\"tab\"}}")
                    .route("GET", "/session/s1/context", 200, "{\"value\":\"NATIVE_APP\"}")
                    .route("GET", "/session/s1/contexts", 200, "{\"value\":[\"NATIVE_APP\",\"WEBVIEW_1\"]}")
                    .route("GET", "/session/s1/orientation", 200, "{\"value\":\"PORTRAIT\"}")
                    .route("GET", "/session/s1/appium/settings", 200, "{\"value\":{\"waitForIdleTimeout\":10000}}")
                    .route("GET", "/status", 200, "{\"value\":{\"ready\":true,\"build\":{\"version\":\"2.0.0\"}}}")
                    .route("DELETE", "/session/s1/window", 200, "{\"value\":[]}")
                    .route("GET", "/session/s1/se/log/types", 200, "{\"value\":[\"logcat\"]}")
                    .route("POST", "/session/s1/se/log", 200,
                            "{\"value\":[{\"timestamp\":1,\"level\":\"INFO\",\"message\":\"m\"}]}");
        }
    }

    private static final class Context {
        private final AndroidDriver driver;
        private final WebElement element;

        Context(AndroidDriver driver, WebElement element) {
            this.driver = driver;
            this.element = element;
        }
    }

    private static final Map<String, Function<Context, Object>> SCENARIOS = new LinkedHashMap<>();

    static {
        SCENARIOS.put("get", c -> {
            c.driver.get("https://example.com/a?b=c");
            return null;
        });
        SCENARIOS.put("getCurrentUrl", c -> c.driver.getCurrentUrl());
        SCENARIOS.put("getTitle", c -> c.driver.getTitle());
        SCENARIOS.put("getPageSource", c -> c.driver.getPageSource());
        SCENARIOS.put("navigate", c -> {
            c.driver.navigate().to("https://example.com/");
            c.driver.navigate().back();
            c.driver.navigate().forward();
            c.driver.navigate().refresh();
            return null;
        });
        for (var by : new By[]{By.id("a"), By.name("n"), By.className("c"), By.cssSelector(".x > y"),
                By.xpath("//a[@b='c']"), By.linkText("l"), By.partialLinkText("p"), By.tagName("t"),
                AppiumBy.accessibilityId("acc"), AppiumBy.androidUIAutomator("new UiSelector()"),
                AppiumBy.iOSClassChain("**/XCUIElementTypeButton"), AppiumBy.id("appium-id"),
                AppiumBy.image("aGVsbG8="), AppiumBy.className("cn")}) {
            SCENARIOS.put("findElement " + by, c -> c.driver.findElement(by));
            SCENARIOS.put("findElements " + by, c -> c.driver.findElements(by));
            SCENARIOS.put("findChildElement " + by, c -> c.element.findElement(by));
            SCENARIOS.put("findChildElements " + by, c -> c.element.findElements(by));
        }
        SCENARIOS.put("findElement chained", c -> c.driver.findElement(
                new io.appium.java_client.support.pagefactory.ByChained(By.id("a"), By.id("b"))));
        SCENARIOS.put("click", c -> {
            c.element.click();
            return null;
        });
        SCENARIOS.put("clear", c -> {
            c.element.clear();
            return null;
        });
        SCENARIOS.put("sendKeys", c -> {
            c.element.sendKeys("ab", "c");
            return null;
        });
        SCENARIOS.put("submit", c -> {
            c.element.submit();
            return null;
        });
        SCENARIOS.put("getText", c -> c.element.getText());
        SCENARIOS.put("getTagName", c -> c.element.getTagName());
        SCENARIOS.put("getAttribute", c -> c.element.getAttribute("content-desc"));
        SCENARIOS.put("getDomAttribute", c -> c.element.getDomAttribute("x"));
        SCENARIOS.put("getDomProperty", c -> c.element.getDomProperty("x"));
        SCENARIOS.put("getCssValue", c -> c.element.getCssValue("color"));
        SCENARIOS.put("isDisplayed", c -> c.element.isDisplayed());
        SCENARIOS.put("isEnabled", c -> c.element.isEnabled());
        SCENARIOS.put("isSelected", c -> c.element.isSelected());
        SCENARIOS.put("getRect", c -> c.element.getRect());
        SCENARIOS.put("getLocation", c -> c.element.getLocation());
        SCENARIOS.put("getSize", c -> c.element.getSize());
        SCENARIOS.put("elementScreenshot", c -> c.element.getScreenshotAs(OutputType.BYTES));
        SCENARIOS.put("screenshotBase64", c -> c.driver.getScreenshotAs(OutputType.BASE64));
        SCENARIOS.put("screenshotBytes", c -> c.driver.getScreenshotAs(OutputType.BYTES));
        SCENARIOS.put("print", c -> c.driver.print(new PrintOptions()));
        SCENARIOS.put("executeScript", c -> c.driver.executeScript("mobile: shell",
                Map.of("command", "ls", "args", List.of("-l"))));
        SCENARIOS.put("executeScriptWithElements", c -> c.driver.executeScript("return 1",
                c.element, List.of(c.element, Map.of("e", c.element)), new Object[]{c.element, 1, "s", null}));
        SCENARIOS.put("executeAsyncScript", c -> c.driver.executeAsyncScript("cb()", 1));
        SCENARIOS.put("timeouts", c -> {
            c.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            c.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(6));
            c.driver.manage().timeouts().scriptTimeout(Duration.ofMillis(7500));
            return List.of(c.driver.manage().timeouts().getImplicitWaitTimeout(),
                    c.driver.manage().timeouts().getPageLoadTimeout(),
                    c.driver.manage().timeouts().getScriptTimeout());
        });
        SCENARIOS.put("window", c -> {
            var window = c.driver.manage().window();
            window.setSize(new org.openqa.selenium.Dimension(300, 400));
            window.setPosition(new org.openqa.selenium.Point(5, 6));
            window.maximize();
            window.minimize();
            window.fullscreen();
            return List.of(window.getSize(), window.getPosition());
        });
        SCENARIOS.put("cookies", c -> {
            c.driver.manage().addCookie(new Cookie.Builder("n", "v").path("/").build());
            final var all = c.driver.manage().getCookies();
            c.driver.manage().getCookieNamed("a");
            c.driver.manage().deleteCookieNamed("a");
            c.driver.manage().deleteCookie(new Cookie("b", "c"));
            c.driver.manage().deleteAllCookies();
            return all;
        });
        SCENARIOS.put("logs", c -> {
            var types = c.driver.manage().logs().getAvailableLogTypes();
            c.driver.manage().logs().get("logcat");
            return types;
        });
        SCENARIOS.put("windowHandles", c -> List.of(c.driver.getWindowHandle(), c.driver.getWindowHandles()));
        SCENARIOS.put("switchToWindowAndFrame", c -> {
            c.driver.switchTo().window("w2");
            c.driver.switchTo().frame(0);
            c.driver.switchTo().frame("name");
            c.driver.switchTo().frame(c.element);
            c.driver.switchTo().parentFrame();
            c.driver.switchTo().defaultContent();
            c.driver.switchTo().newWindow(WindowType.TAB);
            return c.driver.switchTo().activeElement();
        });
        SCENARIOS.put("alert", c -> {
            var alert = c.driver.switchTo().alert();
            alert.sendKeys("text");
            alert.accept();
            alert.dismiss();
            return alert.getText();
        });
        SCENARIOS.put("perform", c -> {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence sequence = new Sequence(finger, 0);
            sequence.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), 1, 2));
            sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            c.driver.perform(List.of(sequence));
            c.driver.resetInputState();
            return null;
        });
        SCENARIOS.put("quit", c -> {
            c.driver.quit();
            return null;
        });
        SCENARIOS.put("close", c -> {
            c.driver.close();
            return null;
        });
        SCENARIOS.put("getStatus", c -> c.driver.getStatus());
        SCENARIOS.put("getCapabilities", c -> c.driver.getCapabilities().asMap());
        SCENARIOS.put("getSessionId", c -> c.driver.getSessionId().toString());
        SCENARIOS.put("customCommand", c -> {
            c.driver.addCommand(HttpMethod.POST, "/session/:sessionId/appium/custom", "custom");
            return c.driver.execute("custom", Map.of("a", 1));
        });
        SCENARIOS.put("contexts", c -> List.of(c.driver.getContext(), c.driver.getContextHandles()));
        SCENARIOS.put("switchContext", c -> {
            c.driver.context("WEBVIEW_1");
            return null;
        });
        SCENARIOS.put("orientation", c -> {
            c.driver.rotate(org.openqa.selenium.ScreenOrientation.LANDSCAPE);
            return c.driver.getOrientation();
        });
        SCENARIOS.put("settings", c -> c.driver.getSettings());
    }

    private static String elementId(WebElement element) {
        return ((AppiumWebElement) element).getId();
    }

    private static Object summarize(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof WebElement) {
            return "element:" + elementId((WebElement) value);
        }
        if (value instanceof byte[]) {
            return "bytes:" + new String((byte[]) value, java.nio.charset.StandardCharsets.UTF_8);
        }
        if (value instanceof Map) {
            Map<String, Object> result = new TreeMap<>();
            ((Map<?, ?>) value).forEach((k, v) -> result.put(String.valueOf(k), summarize(v)));
            return result;
        }
        if (value instanceof Collection) {
            List<Object> result = new ArrayList<>();
            ((Collection<?>) value).forEach(v -> result.add(summarize(v)));
            return result;
        }
        if (value instanceof org.openqa.selenium.Pdf) {
            return "pdf:" + ((org.openqa.selenium.Pdf) value).getContent();
        }
        if (value instanceof Enum) {
            return "enum:" + ((Enum<?>) value).name();
        }
        if (value instanceof Rectangle) {
            return WireGoldenSupport.typed(value.toString());
        }
        return value.getClass().getSimpleName() + ":" + value;
    }

    private static Object thrown(Throwable t) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("thrown", t.getClass().getName());
        result.put("message", t instanceof WebDriverException ? WireGoldenSupport.stableMessage((WebDriverException) t)
                : String.valueOf(t.getMessage()));
        return result;
    }

    private static URL serverUrl() {
        try {
            return new URL("https://appium.example.com:4723/wd/hub");
        } catch (MalformedURLException e) {
            throw new AssertionError(e);
        }
    }

    private static AndroidDriver newDriver(FakeTransport transport) {
        return new AndroidDriver(serverUrl(), transport, new UiAutomator2Options().setDeviceName("emu"));
    }

    private static AndroidDriver newDriver(FakeTransport transport, AppiumClientConfig config) {
        var executor = new AppiumCommandExecutor(MobileCommand.commandRepository, null, transport,
                config.baseUrl(serverUrl()));
        return new AndroidDriver(executor, new UiAutomator2Options().setDeviceName("emu"));
    }

    @Test
    void sessionCreation() {
        var transport = FakeTransport.withDefaults();
        Map<String, Object> result = new LinkedHashMap<>();
        var driver = newDriver(transport);
        result.put("requests", new ArrayList<>(transport.requests));
        result.put("sessionId", driver.getSessionId().toString());
        result.put("capabilities", summarize(driver.getCapabilities().asMap()));
        WireGoldenSupport.verifyGolden("driver-session.json", result);
    }

    @Test
    void apiCalls() {
        Map<String, Object> result = new LinkedHashMap<>();
        SCENARIOS.forEach((name, scenario) -> {
            var transport = FakeTransport.withDefaults();
            var driver = newDriver(transport);
            var element = driver.findElement(By.id("root"));
            transport.requests.clear();
            Map<String, Object> described = new LinkedHashMap<>();
            try {
                described.put("result", summarize(scenario.apply(new Context(driver, element))));
            } catch (Throwable t) {
                described.put("result", thrown(t));
            }
            described.put("requests", new ArrayList<>(transport.requests));
            result.put(name, described);
        });
        WireGoldenSupport.verifyGolden("driver-calls.json", result);
    }

    private static final class Failure {
        private final FakeTransport transport;
        private final Function<AndroidDriver, Object> action;

        Failure(FakeTransport transport, Function<AndroidDriver, Object> action) {
            this.transport = transport;
            this.action = action;
        }
    }

    @Test
    void failures() {
        String noSuchElement = "{\"value\":{\"error\":\"no such element\",\"message\":\"nope\","
                + "\"stacktrace\":\"\"}}";
        Map<String, Failure> setups = new LinkedHashMap<>();
        setups.put("noSuchElement", new Failure(
                FakeTransport.withDefaults().route("POST", "/session/s1/element", 404, noSuchElement),
                d -> d.findElement(By.id("a"))));
        setups.put("emptyElements", new Failure(
                FakeTransport.withDefaults().route("POST", "/session/s1/elements", 200, "{\"value\":[]}"),
                d -> d.findElements(By.id("a"))));
        setups.put("unknownError", new Failure(
                FakeTransport.withDefaults().route("POST", "/session/s1/element", 500,
                        "{\"value\":{\"error\":\"unknown error\",\"message\":\"boom\",\"stacktrace\":\"\"}}"),
                d -> d.findElement(By.id("a"))));
        setups.put("noSuchContext", new Failure(
                FakeTransport.withDefaults().route("POST", "/session/s1/context", 404,
                        "{\"value\":{\"error\":\"no such context\",\"message\":\"No such context found\"}}"),
                d -> d.context("X")));
        setups.put("unsupported", new Failure(
                FakeTransport.withDefaults().route("POST", "/session/s1/element", 404,
                        "{\"value\":{\"error\":\"unknown command\",\"message\":\"not implemented\"}}"),
                d -> d.findElement(By.id("a"))));
        setups.put("connectionRefused", new Failure(
                FakeTransport.withDefaults().route("POST", "/session/s1/element", -1, ""),
                d -> d.findElement(By.id("a"))));
        setups.put("staleElement", new Failure(
                FakeTransport.withDefaults().route("GET", "/session/s1/element/el1/text", 404,
                        "{\"value\":{\"error\":\"stale element reference\",\"message\":\"stale\"}}"),
                d -> d.findElement(By.id("a")).getText()));
        Map<String, Object> result = new LinkedHashMap<>();
        setups.forEach((name, failure) -> {
            var driver = newDriver(failure.transport);
            failure.transport.requests.clear();
            Map<String, Object> described = new LinkedHashMap<>();
            try {
                described.put("result", summarize(failure.action.apply(driver)));
            } catch (Throwable t) {
                described.put("result", thrown(t));
            }
            described.put("requests", new ArrayList<>(failure.transport.requests));
            result.put(name, described);
        });
        WireGoldenSupport.verifyGolden("driver-failures.json", result);
    }

    @Test
    void sessionCreationFailures() {
        Map<String, FakeTransport> setups = new LinkedHashMap<>();
        setups.put("sessionNotCreated", FakeTransport.withDefaults().route("POST", "/session", 500,
                "{\"value\":{\"error\":\"session not created\",\"message\":\"no device\","
                        + "\"stacktrace\":\"\"}}"));
        setups.put("jsonWireResponse", FakeTransport.withDefaults().route("POST", "/session", 200,
                "{\"status\":0,\"sessionId\":\"s1\",\"value\":{\"platformName\":\"Android\"}}"));
        setups.put("emptyResponse", FakeTransport.withDefaults().route("POST", "/session", 200, "{\"value\":null}"));
        setups.put("connectionRefused", FakeTransport.withDefaults().route("POST", "/session", -1, ""));
        Map<String, Object> result = new LinkedHashMap<>();
        setups.forEach((name, transport) -> {
            Map<String, Object> described = new LinkedHashMap<>();
            try {
                described.put("unexpectedSession", newDriver(transport).getSessionId().toString());
            } catch (Throwable t) {
                described.put("result", thrown(t));
            }
            described.put("requests", new ArrayList<>(transport.requests));
            result.put(name, described);
        });
        WireGoldenSupport.verifyGolden("driver-session-failures.json", result);
    }

    @Test
    void directConnect() {
        String capabilities = "{\"platformName\":\"Android\",\"appium:directConnectProtocol\":\"https\","
                + "\"appium:directConnectHost\":\"%s\",\"appium:directConnectPort\":4443,"
                + "\"appium:directConnectPath\":\"/wd/hub\"}";
        Map<String, FakeTransport> setups = new LinkedHashMap<>();
        setups.put("valid", FakeTransport.withDefaults().route("POST", "/session", 200,
                "{\"value\":{\"sessionId\":\"s1\",\"capabilities\":" + String.format(capabilities, "203.0.113.1")
                        + "}}"));
        setups.put("loopbackRejected", FakeTransport.withDefaults().route("POST", "/session", 200,
                "{\"value\":{\"sessionId\":\"s1\",\"capabilities\":" + String.format(capabilities, "127.0.0.1")
                        + "}}"));
        Map<String, Object> result = new LinkedHashMap<>();
        setups.forEach((name, transport) -> {
            Map<String, Object> described = new LinkedHashMap<>();
            try {
                var driver = newDriver(transport, AppiumClientConfig.defaultConfig().directConnect(true));
                transport.requests.clear();
                driver.getTitle();
            } catch (Throwable t) {
                described.put("result", thrown(t));
            }
            described.put("requests", new ArrayList<>(transport.requests));
            result.put(name, described);
        });
        WireGoldenSupport.verifyGolden("driver-direct-connect.json", result);
    }
}
