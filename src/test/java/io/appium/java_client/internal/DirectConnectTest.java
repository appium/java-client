package io.appium.java_client.internal;

import io.appium.java_client.remote.DirectConnect;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DirectConnectTest {

    @Test
    void hasValidDirectConnectValuesWithoutAppiumPrefix() throws MalformedURLException {
        Map<String, String> responseValue = new HashMap<>();
        responseValue.put("directConnectProtocol", "https");
        responseValue.put("directConnectPath", "/path/to");
        responseValue.put("directConnectHost", "host");
        responseValue.put("directConnectPort", "8080");
        DirectConnect directConnect = new DirectConnect(responseValue);
        assertTrue(directConnect.isValid());
        assertEquals(directConnect.getUrl().toString(), "https://host:8080/path/to");
    }

    @Test
    void hasValidDirectConnectValuesWithAppiumPrefix() throws MalformedURLException {
        Map<String, String> responseValue = new HashMap<>();
        responseValue.put("appium:directConnectProtocol", "https");
        responseValue.put("appium:directConnectPath", "/path/to");
        responseValue.put("appium:directConnectHost", "host");
        responseValue.put("appium:directConnectPort", "8080");
        DirectConnect directConnect = new DirectConnect(responseValue);
        assertTrue(directConnect.isValid());
        assertEquals(directConnect.getUrl().toString(), "https://host:8080/path/to");
    }

    @Test
    void hasValidDirectConnectStringPort() {
        Map<String, String> responseValue = new HashMap<>();
        responseValue.put("appium:directConnectProtocol", "https");
        responseValue.put("appium:directConnectPath", "/path/to");
        responseValue.put("appium:directConnectHost", "host");
        responseValue.put("appium:directConnectPort", "port");
        DirectConnect directConnect = new DirectConnect(responseValue);
        assertTrue(directConnect.isValid());
        assertThrowsExactly(MalformedURLException.class, directConnect::getUrl);
    }

    @Test
    void hasInvalidDirectConnect() {
        Map<String, String> responseValue = new HashMap<>();
        DirectConnect directConnect = new DirectConnect(responseValue);
        assertFalse(directConnect.isValid());
        assertThrowsExactly(MalformedURLException.class, directConnect::getUrl);
    }

    @Test
    void formatsBareIpv6HostAndPreservesBracketedIpv6Host() throws MalformedURLException {
        Map<String, String> bareIpv6 = Map.of(
                "appium:directConnectProtocol", "https",
                "appium:directConnectHost", "2001:db8::1",
                "appium:directConnectPort", "4443",
                "appium:directConnectPath", "/wd/hub"
        );
        DirectConnect bare = new DirectConnect(bareIpv6);
        assertTrue(bare.isValid());
        assertEquals("https://[2001:db8::1]:4443/wd/hub", bare.getUrl().toString());

        Map<String, String> bracketedIpv6 = Map.of(
                "appium:directConnectProtocol", "https",
                "appium:directConnectHost", "[2001:db8::1]",
                "appium:directConnectPort", "4443",
                "appium:directConnectPath", "/wd/hub"
        );
        DirectConnect bracketed = new DirectConnect(bracketedIpv6);
        assertTrue(bracketed.isValid());
        assertEquals("https://[2001:db8::1]:4443/wd/hub", bracketed.getUrl().toString());
    }

    @Test
    void normalizesEmptyAndRelativeDirectConnectPath() throws MalformedURLException {
        DirectConnect emptyPath = new DirectConnect(Map.of(
                "appium:directConnectProtocol", "https",
                "appium:directConnectHost", "203.0.113.1",
                "appium:directConnectPort", "4443",
                "appium:directConnectPath", ""
        ));
        assertTrue(emptyPath.isValid());
        assertEquals("https://203.0.113.1:4443", emptyPath.getUrl().toString());

        DirectConnect relativePath = new DirectConnect(Map.of(
                "appium:directConnectProtocol", "https",
                "appium:directConnectHost", "203.0.113.1",
                "appium:directConnectPort", "4443",
                "appium:directConnectPath", "wd/hub"
        ));
        assertTrue(relativePath.isValid());
        assertEquals("https://203.0.113.1:4443/wd/hub", relativePath.getUrl().toString());
    }

    @Test
    void rejectsSingleColonHostAndBlankFields() {
        DirectConnect embeddedPortHost = new DirectConnect(Map.of(
                "appium:directConnectProtocol", "https",
                "appium:directConnectHost", "evil.example.com:8443",
                "appium:directConnectPort", "4443",
                "appium:directConnectPath", "/wd/hub"
        ));
        assertTrue(embeddedPortHost.isValid());
        assertThrowsExactly(MalformedURLException.class, embeddedPortHost::getUrl);

        DirectConnect blankHost = new DirectConnect(Map.of(
                "appium:directConnectProtocol", "https",
                "appium:directConnectHost", "   ",
                "appium:directConnectPort", "4443",
                "appium:directConnectPath", ""
        ));
        assertFalse(blankHost.isValid());
        assertThrowsExactly(MalformedURLException.class, blankHost::getUrl);
    }
}
