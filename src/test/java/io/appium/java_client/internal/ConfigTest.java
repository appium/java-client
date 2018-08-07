package io.appium.java_client.internal;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConfigTest {
    private static final String EXISTING_KEY = "selenium.version";
    private static final String MISSING_KEY = "bla";

    @Test
    public void verifyGettingExistingValue() {
        assertThat(Config.main().getValue(EXISTING_KEY, String.class).length(), greaterThan(0));
        assertTrue(Config.main().getOptionalValue(EXISTING_KEY, String.class).isPresent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void verifyGettingNonExistingValue() {
        assertThat(Config.main().getValue(MISSING_KEY, String.class).length(), greaterThan(0));
    }

    @Test(expected = ClassCastException.class)
    public void verifyGettingExistingValueWithWrongClass() {
        assertThat(Config.main().getValue(EXISTING_KEY, Integer.class), greaterThan(0));
    }

    @Test
    public void verifyGettingNonExistingOptionalValue() {
        assertFalse(Config.main().getOptionalValue(MISSING_KEY, String.class).isPresent());
    }
}
