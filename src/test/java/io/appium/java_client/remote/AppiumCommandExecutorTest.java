package io.appium.java_client.remote;

import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.MobileCommand;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AppiumCommandExecutorTest {
    private static final String APPIUM_URL = "https://appium.example.com";

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
    void keepsTheHttpClientFactoryPassedToTheConstructor() throws MalformedURLException {
        HttpClient.Factory factory = HttpClient.Factory.createDefault();
        AppiumCommandExecutor executor = new AppiumCommandExecutor(
                MobileCommand.commandRepository, new URL(APPIUM_URL), factory);

        assertSame(factory, executor.getHttpClientFactory());
    }

    @Test
    void usesADefaultHttpClientFactoryWhenNoneIsPassed() throws MalformedURLException {
        AppiumCommandExecutor executor = new AppiumCommandExecutor(
                MobileCommand.commandRepository, new URL(APPIUM_URL), (HttpClient.Factory) null);

        assertNotNull(executor.getHttpClientFactory());
    }

    @Test
    void overrideServerUrl() throws MalformedURLException {
        assertDoesNotThrow(() -> createExecutor().overrideServerUrl(new URL("https://203.0.113.1/wd/hub")));
    }

    @Test
    void overrideServerUrlRejectsLoopbackTarget() throws MalformedURLException {
        assertThrows(SessionNotCreatedException.class,
                () -> createExecutor().overrideServerUrl(new URL("https://127.0.0.1:4443/wd/hub")));
    }
}
