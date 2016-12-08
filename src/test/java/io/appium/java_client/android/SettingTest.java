package io.appium.java_client.android;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SettingTest extends BaseAndroidTest {

    @Test public void ignoreUnimportantViewsTest() {
        driver.ignoreUnimportantViews(true);
        assertEquals(true, driver.getSettings()
                .get(Setting.IGNORE_UNIMPORTANT_VIEWS.toString()));

        driver.ignoreUnimportantViews(false);
        assertEquals(false, driver.getSettings()
                .get(Setting.IGNORE_UNIMPORTANT_VIEWS.toString()));
    }

    @Test public void configuratorTest() {
        driver.configuratorSetActionAcknowledgmentTimeout(500);
        assertJSONElementContains(Setting.WAIT_ACTION_ACKNOWLEDGMENT_TIMEOUT, 500);

        driver.configuratorSetKeyInjectionDelay(400);
        assertJSONElementContains(Setting.KEY_INJECTION_DELAY, 400);

        driver.configuratorSetScrollAcknowledgmentTimeout(300);
        assertJSONElementContains(Setting.WAIT_SCROLL_ACKNOWLEDGMENT_TIMEOUT, 300);

        driver.configuratorSetWaitForIdleTimeout(600);
        assertJSONElementContains(Setting.WAIT_FOR_IDLE_TIMEOUT, 600);

        driver.configuratorSetWaitForSelectorTimeout(1000);
        assertJSONElementContains(Setting.WAIT_FOR_SELECTOR_TIMEOUT, 1000);
    }

    private void assertJSONElementContains(Setting setting, long value) {
        assertEquals(driver.getSettings().get(setting.toString()), value);
    }
}
