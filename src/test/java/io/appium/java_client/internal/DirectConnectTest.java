package io.appium.java_client.internal;

import io.appium.java_client.remote.DirectConnect;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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
    void hasValidDirectConnectInvalid() {
        Map<String, String> responseValue = new HashMap<>();
        DirectConnect directConnect = new DirectConnect(responseValue);
        assertFalse(directConnect.isValid());
    }
}
