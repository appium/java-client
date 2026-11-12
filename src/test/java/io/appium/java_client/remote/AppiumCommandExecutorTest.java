package io.appium.java_client.remote;

import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.MobileCommand;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.http.ClientConfig;
import io.appium.java_client.http.Contents;
import io.appium.java_client.http.HttpClient;
import io.appium.java_client.http.HttpRequest;
import io.appium.java_client.http.HttpResponse;
import io.appium.java_client.http.WebSocket;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.openqa.selenium.SessionNotCreatedException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppiumCommandExecutorTest {
    private static final String APPIUM_URL = "https://appium.example.com";
    private static final File NODE = new File(Path.of(System.getProperty("java.home"), "bin", "java").toString());

    @TempDir
    Path tempDir;

    private static final class RecordingHttpClient implements HttpClient {
        private final ClientConfig config;
        private final AtomicBoolean closed = new AtomicBoolean(false);
        private final String sessionCapabilitiesJson;

        RecordingHttpClient(ClientConfig config, String sessionCapabilitiesJson) {
            this.config = config;
            this.sessionCapabilitiesJson = sessionCapabilitiesJson;
        }

        @Override
        public HttpResponse execute(HttpRequest request) {
            String body = "/session".equals(request.getUri())
                    ? "{\"value\":{\"sessionId\":\"s1\",\"capabilities\":" + sessionCapabilitiesJson + "}}"
                    : "{\"value\":null}";
            return new HttpResponse()
                    .setStatus(200)
                    .setHeader("Content-Type", "application/json; charset=utf-8")
                    .setContent(Contents.utf8String(body));
        }

        @Override
        public WebSocket openSocket(HttpRequest request, WebSocket.Listener listener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void close() {
            closed.set(true);
        }
    }

    private AppiumCommandExecutor createExecutor() {
        URL baseUrl;
        try {
            baseUrl = new URL(APPIUM_URL);
        } catch (MalformedURLException e) {
            throw new AssertionError(e);
        }
        AppiumClientConfig clientConfig = AppiumClientConfig.defaultConfig().baseUrl(baseUrl);
        return new AppiumCommandExecutor(MobileCommand.commandRepository, clientConfig);
    }

    @Test
    void getAdditionalCommands() {
        assertNotNull(createExecutor().getAdditionalCommands());
    }

    @Test
    void getHttpClientFactory() {
        assertNotNull(createExecutor().getHttpClientFactory());
    }

    @Test
    void overrideServerUrl() throws MalformedURLException {
        List<RecordingHttpClient> created = new ArrayList<>();
        HttpClient.Factory factory = cfg -> {
            RecordingHttpClient client = new RecordingHttpClient(cfg, "{\"platformName\":\"Android\"}");
            created.add(client);
            return client;
        };
        AppiumClientConfig initialConfig = AppiumClientConfig.defaultConfig()
                .baseUrl(new URL(APPIUM_URL))
                .readTimeout(Duration.ofSeconds(45));
        AppiumCommandExecutor executor = new AppiumCommandExecutor(
                MobileCommand.commandRepository, null, factory, initialConfig);
        final HttpClient firstClient = executor.getClient();

        URL overrideUrl = new URL("https://203.0.113.1:4443/wd/hub");
        executor.overrideServerUrl(overrideUrl);

        assertEquals(2, created.size());
        assertTrue(created.get(0).closed.get());
        assertFalse(created.get(1).closed.get());
        assertNotSame(firstClient, executor.getClient());
        assertSame(created.get(1), executor.getClient());
        assertEquals(overrideUrl, executor.getAddressOfRemoteServer());
        assertEquals(overrideUrl, executor.getAppiumClientConfig().baseUrl());
        assertEquals(URI.create("https://203.0.113.1:4443/wd/hub"), executor.getAppiumClientConfig().baseUri());
        assertEquals(Duration.ofSeconds(45), executor.getAppiumClientConfig().readTimeout());
    }

    @Test
    void overrideServerUrlRejectsLoopbackTarget() throws MalformedURLException {
        List<RecordingHttpClient> created = new ArrayList<>();
        HttpClient.Factory factory = cfg -> {
            RecordingHttpClient client = new RecordingHttpClient(cfg, "{\"platformName\":\"Android\"}");
            created.add(client);
            return client;
        };
        URL initialUrl = new URL(APPIUM_URL);
        AppiumCommandExecutor executor = new AppiumCommandExecutor(
                MobileCommand.commandRepository, null, factory,
                AppiumClientConfig.defaultConfig().baseUrl(initialUrl));

        assertThrows(SessionNotCreatedException.class,
                () -> executor.overrideServerUrl(new URL("https://127.0.0.1:4443/wd/hub")));
        assertEquals(1, created.size());
        assertFalse(created.get(0).closed.get());
        assertEquals(initialUrl, executor.getAddressOfRemoteServer());
        assertEquals(initialUrl, executor.getAppiumClientConfig().baseUrl());
    }

    @Test
    void populatesBaseUrlFromLocalServiceWhenUnsetInClientConfig() throws IOException {
        File mainJs = Files.createFile(tempDir.resolve("main.js")).toFile();
        var service = new AppiumServiceBuilder()
                .usingDriverExecutable(NODE)
                .withAppiumJS(mainJs)
                .usingPort(4777)
                .withIPAddress("127.0.0.1")
                .build();
        AppiumClientConfig customConfig = AppiumClientConfig.defaultConfig()
                .readTimeout(Duration.ofSeconds(90));

        AppiumCommandExecutor executor = new AppiumCommandExecutor(
                MobileCommand.commandRepository, service, customConfig);

        assertEquals(service.getUrl(), executor.getAddressOfRemoteServer());
        assertEquals(service.getUrl(), executor.getAppiumClientConfig().baseUrl());
        assertEquals(Duration.ofSeconds(90), executor.getAppiumClientConfig().readTimeout());
    }

    @Test
    void directConnectSupportsUppercaseProtocolIpv6LiteralAndRelativePath() throws MalformedURLException {
        String caps = "{\"platformName\":\"Android\",\"appium:automationName\":\"UiAutomator2\","
                + "\"appium:directConnectProtocol\":\"HTTPS\","
                + "\"appium:directConnectHost\":\"2001:db8::1\","
                + "\"appium:directConnectPort\":4443,"
                + "\"appium:directConnectPath\":\"wd/hub\"}";
        HttpClient.Factory factory = cfg -> new RecordingHttpClient(cfg, caps);
        AppiumCommandExecutor executor = new AppiumCommandExecutor(
                MobileCommand.commandRepository, null, factory,
                AppiumClientConfig.defaultConfig().baseUrl(new URL(APPIUM_URL)).directConnect(true));

        AndroidDriver driver = new AndroidDriver(executor, new UiAutomator2Options().setDeviceName("emu"));

        URL expected = new URL("https://[2001:db8::1]:4443/wd/hub");
        assertEquals(expected, executor.getAddressOfRemoteServer());
        assertEquals(expected, executor.getAppiumClientConfig().baseUrl());
        assertEquals(expected, driver.getRemoteAddress());

        URL nextOverride = new URL("https://203.0.113.2:4443/wd/hub");
        executor.overrideServerUrl(nextOverride);
        assertEquals(nextOverride, driver.getRemoteAddress());
    }

    @Test
    void directConnectIgnoresBlankCapabilityValues() throws MalformedURLException {
        String caps = "{\"platformName\":\"Android\",\"appium:automationName\":\"UiAutomator2\","
                + "\"appium:directConnectProtocol\":\"\","
                + "\"appium:directConnectHost\":\"\","
                + "\"appium:directConnectPort\":\"\","
                + "\"appium:directConnectPath\":\"\"}";
        HttpClient.Factory factory = cfg -> new RecordingHttpClient(cfg, caps);
        URL initialUrl = new URL(APPIUM_URL);
        AppiumCommandExecutor executor = new AppiumCommandExecutor(
                MobileCommand.commandRepository, null, factory,
                AppiumClientConfig.defaultConfig().baseUrl(initialUrl).directConnect(true));

        AndroidDriver driver = new AndroidDriver(executor, new UiAutomator2Options().setDeviceName("emu"));

        assertEquals(initialUrl, executor.getAddressOfRemoteServer());
        assertEquals(initialUrl, driver.getRemoteAddress());
    }
}
