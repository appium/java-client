package io.appium.java_client.internal;

import io.appium.java_client.internal.filters.AppiumUserAgentFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppiumUserAgentFilterTest {
    @Test
    void validateUserAgent() {
        assertTrue(AppiumUserAgentFilter.USER_AGENT.startsWith("appium/"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "appium/8.2.0 (selenium/4.5.0 (java mac))",
        "APPIUM/8.2.0 (selenium/4.5.0 (java mac))",
        "something (Appium/8.2.0 (selenium/4.5.0 (java mac)))",
        "something (appium/8.2.0 (selenium/4.5.0 (java mac)))"
    })
    void validUserAgentIfContainsAppiumName(String userAgent) {
        assertEquals(AppiumUserAgentFilter.buildUserAgent(userAgent), userAgent);
    }

    @Test
    void validBuildUserAgentNoUA() {
        assertEquals(AppiumUserAgentFilter.buildUserAgent(null), AppiumUserAgentFilter.USER_AGENT);
    }

    @Test
    void validBuildUserAgentNoAppium1() {
        String ua = AppiumUserAgentFilter.buildUserAgent("selenium/4.5.0 (java mac)");
        assertTrue(ua.startsWith("appium/"));
        assertTrue(ua.endsWith("selenium/4.5.0 (java mac))"));
    }

    @Test
    void validBuildUserAgentNoAppium2() {
        String ua = AppiumUserAgentFilter.buildUserAgent("customSelenium/4.5.0 (java mac)");
        assertTrue(ua.startsWith("appium/"));
        assertTrue(ua.endsWith("customSelenium/4.5.0 (java mac))"));
    }

    @Test
    void validBuildUserAgentAlreadyHasAppium1() {
        // Won't modify since the UA already has appium prefix
        String ua = AppiumUserAgentFilter.buildUserAgent("appium/8.1.0 (selenium/4.5.0 (java mac))");
        assertEquals("appium/8.1.0 (selenium/4.5.0 (java mac))", ua);
    }

    @Test
    void validBuildUserAgentAlreadyHasAppium2() {
        // Won't modify since the UA already has appium prefix
        String ua = AppiumUserAgentFilter.buildUserAgent("something (appium/8.1.0 (selenium/4.5.0 (java mac)))");
        assertEquals("something (appium/8.1.0 (selenium/4.5.0 (java mac)))", ua);
    }
}
