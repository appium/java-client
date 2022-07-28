package io.appium.java_client.internal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ConfigTest {
    private static final String EXISTING_KEY = "selenium.version";
    private static final String MISSING_KEY = "bla";

    @Test
    public void verifyGettingExistingValue() {
        assertThat(Config.main().getValue(EXISTING_KEY, String.class).length(), greaterThan(0));
        assertTrue(Config.main().getOptionalValue(EXISTING_KEY, String.class).isPresent());
    }

    @Test
    public void verifyGettingNonExistingValue() {
        assertThrows(IllegalArgumentException.class,
                () -> assertThat(Config.main().getValue(MISSING_KEY, String.class).length(), greaterThan(0)));
    }

    @Test
    public void verifyGettingExistingValueWithWrongClass() {
        assertThrows(ClassCastException.class,
                () -> assertThat(Config.main().getValue(EXISTING_KEY, Integer.class), greaterThan(0)));
    }

    @Test
    public void verifyGettingNonExistingOptionalValue() {
        assertFalse(Config.main().getOptionalValue(MISSING_KEY, String.class).isPresent());
    }
}
