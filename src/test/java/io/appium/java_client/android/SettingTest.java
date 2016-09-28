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
        assertJSONElementContains(Setting.WAIT_ACTION_ACKNOWLEDGMENT_TIMEOUT, 5);

        driver.configuratorSetKeyInjectionDelay(4);
        assertJSONElementContains(Setting.KEY_INJECTION_DELAY, 4);

        driver.configuratorSetScrollAcknowledgmentTimeout(3);
        assertJSONElementContains(Setting.WAIT_SCROLL_ACKNOWLEDGMENT_TIMEOUT, 3);

        driver.configuratorSetWaitForIdleTimeout(6);
        assertJSONElementContains(Setting.WAIT_FOR_IDLE_TIMEOUT, 6);

        driver.configuratorSetWaitForSelectorTimeout(1);
        assertJSONElementContains(Setting.WAIT_FOR_SELECTOR_TIMEOUT, 1);
    }

    private void assertJSONElementContains(Setting setting, int value) {
        assertEquals(driver.getSettings().get(setting.toString())
                .getAsInt(), value);
    }
}
