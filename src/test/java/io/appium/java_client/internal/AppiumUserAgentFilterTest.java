package io.appium.java_client.internal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.appium.java_client.AppiumUserAgentFilter;
import org.junit.jupiter.api.Test;

public class AppiumUserAgentFilterTest {
    @Test
    void validateUserAgent() {
        assertTrue(AppiumUserAgentFilter.USER_AGENT.startsWith("appium/"));
    }

    @Test
    void validUserAgentIfContainsAppiumName() {
        assertFalse(AppiumUserAgentFilter.USER_AGENT.startsWith("selenium/4.5.0 (java mac)"));
        assertTrue(AppiumUserAgentFilter.USER_AGENT.startsWith("appium/8.2.0 (selenium/4.5.0 (java mac))"));
    }
}
