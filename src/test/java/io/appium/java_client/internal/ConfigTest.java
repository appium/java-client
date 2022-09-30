package io.appium.java_client.internal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.appium.java_client.AddAppiumUserAgent;
import org.junit.jupiter.api.Test;

class ConfigTest {
    private static final String SELENIUM_EXISTING_KEY = "selenium.version";

    private static final String MISSING_KEY = "bla";

    @Test
    void verifyGettingExistingValue() {
        assertThat(Config.main().getValue(SELENIUM_EXISTING_KEY, String.class).length(), greaterThan(0));
        assertTrue(Config.main().getOptionalValue(SELENIUM_EXISTING_KEY, String.class).isPresent());

        assertThat(Config.main().getValue(AddAppiumUserAgent.VERSION_KEY, String.class).length(), greaterThan(0));
        assertTrue(Config.main().getOptionalValue(AddAppiumUserAgent.VERSION_KEY, String.class).isPresent());
    }

    @Test
    void verifyGettingNonExistingValue() {
        assertThrows(IllegalArgumentException.class, () -> Config.main().getValue(MISSING_KEY, String.class));
    }

    @Test
    void verifyGettingExistingValueWithWrongClass() {
        assertThrows(ClassCastException.class, () -> Config.main().getValue(SELENIUM_EXISTING_KEY, Integer.class));
        assertThrows(ClassCastException.class, () -> Config.main().getValue(AddAppiumUserAgent.VERSION_KEY, Integer.class));
    }

    @Test
    void verifyGettingNonExistingOptionalValue() {
        assertFalse(Config.main().getOptionalValue(MISSING_KEY, String.class).isPresent());
    }
}
