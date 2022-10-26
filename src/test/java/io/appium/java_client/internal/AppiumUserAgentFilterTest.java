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
}
