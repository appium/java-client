package io.appium.java_client.internal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.SessionNotCreatedException;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DirectConnectUrlSafetyTest {

    @Test
    void allowsDocumentationNetIpLiteral() throws MalformedURLException {
        // RFC 5737 TEST-NET-3; parsed locally without DNS
        assertDoesNotThrow(() -> DirectConnectUrlSafety.requireSafeOverrideTarget(
                new URL("https://203.0.113.1/wd/hub")));
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @ValueSource(strings = {
        "https://127.0.0.1:4443/wd/hub",
        "https://localhost:4443/wd/hub",
        "https://169.254.169.254/wd/hub",
        "https://[::1]:4443/wd/hub",
        "https://0.0.0.0:4443/wd/hub",
        "https://[::]:4443/wd/hub",
        "https://224.0.0.1:4443/wd/hub",
    })
    void rejectsDisallowedOverrideTargets(String urlSpec) throws MalformedURLException {
        assertThrows(SessionNotCreatedException.class,
                () -> DirectConnectUrlSafety.requireSafeOverrideTarget(new URL(urlSpec)));
    }
}
