package io.appium.java_client.remote;

import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.MobileCommand;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    void overrideServerUrl() {
        assertDoesNotThrow(() -> createExecutor().overrideServerUrl(new URL("https://direct.example.com")));
    }
}
