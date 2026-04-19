package io.appium.java_client.internal;

import org.junit.jupiter.api.Test;
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

    @Test
    void rejectsIpv4Loopback() throws MalformedURLException {
        assertThrows(SessionNotCreatedException.class, () -> DirectConnectUrlSafety.requireSafeOverrideTarget(
                new URL("https://127.0.0.1:4443/wd/hub")));
    }

    @Test
    void rejectsLocalhost() throws MalformedURLException {
        assertThrows(SessionNotCreatedException.class, () -> DirectConnectUrlSafety.requireSafeOverrideTarget(
                new URL("https://localhost:4443/wd/hub")));
    }

    @Test
    void rejectsLinkLocalMetadataIp() throws MalformedURLException {
        assertThrows(SessionNotCreatedException.class, () -> DirectConnectUrlSafety.requireSafeOverrideTarget(
                new URL("https://169.254.169.254/wd/hub")));
    }

    @Test
    void rejectsIpv6Loopback() throws MalformedURLException {
        assertThrows(SessionNotCreatedException.class, () -> DirectConnectUrlSafety.requireSafeOverrideTarget(
                new URL("https://[::1]:4443/wd/hub")));
    }
}
