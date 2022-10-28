package io.appium.java_client.internal;

import io.appium.java_client.AppiumUserAgentFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class AppiumUserAgentFilterTest {
    @Test
    void validateUserAgent() {
        assertTrue(AppiumUserAgentFilter.USER_AGENT.startsWith("appium/"));
    }

    public static Stream<Arguments> userAgentParams() {
        return Stream.of(
                Arguments.of("selenium/4.5.0 (java mac)", false),
                Arguments.of("appium/8.2.0 (selenium/4.5.0 (java mac))", true),
                Arguments.of("something (appium/8.2.0 (selenium/4.5.0 (java mac)))", true)
        );
    }

    @ParameterizedTest
    @MethodSource("userAgentParams")
    void validUserAgentIfContainsAppiumName(String userAgent, boolean expected) {
        assertEquals(new AppiumUserAgentFilter().containsAppiumName(userAgent), expected);
    }

    @Test
    void validBuildUserAgentNoUA() {
        assertEquals(new AppiumUserAgentFilter().buildUserAgent(null), AppiumUserAgentFilter.USER_AGENT);
    }

    @Test
    void validBuildUserAgentNoAppium1() {
        String ua = new AppiumUserAgentFilter().buildUserAgent("selenium/4.5.0 (java mac)");
        assertTrue(ua.startsWith("appium/"));
        assertTrue(ua.endsWith("selenium/4.5.0 (java mac))"));
    }

    @Test
    void validBuildUserAgentNoAppium2() {
        String ua = new AppiumUserAgentFilter().buildUserAgent("customSelenium/4.5.0 (java mac)");
        assertTrue(ua.startsWith("appium/"));
        assertTrue(ua.endsWith("customSelenium/4.5.0 (java mac))"));
    }

    @Test
    void validBuildUserAgentAlreadyHasAppium1() {
        // Won't modify since the UA already has appium prefix
        String ua = new AppiumUserAgentFilter().buildUserAgent("appium/8.2.0 (selenium/4.5.0 (java mac))");
        assertEquals("appium/8.2.0 (selenium/4.5.0 (java mac))", ua);
    }

    @Test
    void validBuildUserAgentAlreadyHasAppium2() {
        // Won't modify since the UA already has appium prefix
        String ua = new AppiumUserAgentFilter().buildUserAgent("something (appium/8.2.0 (selenium/4.5.0 (java mac)))");
        assertEquals("something (appium/8.2.0 (selenium/4.5.0 (java mac)))", ua);
    }
}
