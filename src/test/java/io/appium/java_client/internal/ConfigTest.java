package io.appium.java_client.internal;

import io.appium.java_client.internal.filters.AppiumUserAgentFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigTest {
    private static final String SELENIUM_EXISTING_KEY = "selenium.version";

    private static final String MISSING_KEY = "bla";

    @ParameterizedTest
    @ValueSource(strings = {SELENIUM_EXISTING_KEY, AppiumUserAgentFilter.VERSION_KEY})
    void verifyGettingExistingValue(String key) {
        assertThat(Config.main().getValue(key, String.class).length(), greaterThan(0));
        assertTrue(Config.main().getOptionalValue(key, String.class).isPresent());
    }

    @Test
    void verifyGettingNonExistingValue() {
        assertThrows(IllegalArgumentException.class, () -> Config.main().getValue(MISSING_KEY, String.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {SELENIUM_EXISTING_KEY, AppiumUserAgentFilter.VERSION_KEY})
    void verifyGettingExistingValueWithWrongClass(String key) {
        assertThrows(ClassCastException.class, () -> Config.main().getValue(key, Integer.class));
    }

    @Test
    void verifyGettingNonExistingOptionalValue() {
        assertFalse(Config.main().getOptionalValue(MISSING_KEY, String.class).isPresent());
    }
}
