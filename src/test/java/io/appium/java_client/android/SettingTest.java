package io.appium.java_client.android;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SettingTest extends BaseAndroidTest {

    @Test public void ignoreUnimportantViewsTest() {
        driver.ignoreUnimportantViews(true);
        boolean ignoreViews =
                driver.getSettings().get(Setting.IGNORE_UNIMPORTANT_VIEWS.toString())
                        .getAsBoolean();
        assertTrue(ignoreViews);
        driver.ignoreUnimportantViews(false);
        ignoreViews = driver.getSettings().get(Setting.IGNORE_UNIMPORTANT_VIEWS.toString())
                .getAsBoolean();
        assertFalse(ignoreViews);
    }

    @Test public void configuratorTest() {
        driver.configuratorSetActionAcknowledgmentTimeout(5);
        assertJSONElementContains("setActionAcknowledgmentTimeout", 5);

        driver.configuratorSetKeyInjectionDelay(4);
        assertJSONElementContains("setKeyInjectionDelay", 4);

        driver.configuratorSetScrollAcknowledgmentTimeout(3);
        assertJSONElementContains("setScrollAcknowledgmentTimeout", 3);

        driver.configuratorSetWaitForIdleTimeout(6);
        assertJSONElementContains("setWaitForIdleTimeout", 6);

        driver.configuratorSetWaitForSelectorTimeout(1);
        assertJSONElementContains("setWaitForSelectorTimeout", 1);
    }

    private void assertJSONElementContains(String method, int value) {
        driver.getSettings().get(Setting.CONFIGURATOR.toString());
        assertEquals(driver.getSettings().get(Setting.CONFIGURATOR.toString()).
                getAsJsonObject().get("method").getAsString(), method);
        assertEquals(driver.getSettings().get(Setting.CONFIGURATOR.toString()).
                getAsJsonObject().get("value").getAsInt(), value);
    }
}
