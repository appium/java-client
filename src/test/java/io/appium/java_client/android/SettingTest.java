package io.appium.java_client.android;

import static org.junit.Assert.assertEquals;

import io.appium.java_client.Setting;
import org.junit.Test;

import java.time.Duration;

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
        driver.configuratorSetActionAcknowledgmentTimeout(Duration.ofMillis(500));
        assertJSONElementContains(Setting.WAIT_ACTION_ACKNOWLEDGMENT_TIMEOUT, 500);

        driver.configuratorSetKeyInjectionDelay(Duration.ofMillis(400));
        assertJSONElementContains(Setting.KEY_INJECTION_DELAY, 400);

        driver.configuratorSetScrollAcknowledgmentTimeout(Duration.ofMillis(300));
        assertJSONElementContains(Setting.WAIT_SCROLL_ACKNOWLEDGMENT_TIMEOUT, 300);

        driver.configuratorSetWaitForIdleTimeout(Duration.ofMillis(600));
        assertJSONElementContains(Setting.WAIT_FOR_IDLE_TIMEOUT, 600);

        driver.configuratorSetWaitForSelectorTimeout(Duration.ofSeconds(1));
        assertJSONElementContains(Setting.WAIT_FOR_SELECTOR_TIMEOUT, 1000);
    }

    @Test public void testNormalizeTagNames() {
        assertEquals(false, driver.getSettings()
            .get(Setting.NORMALIZE_TAG_NAMES.toString()));
        driver.normalizeTagNames(true);
        assertEquals(true, driver.getSettings()
            .get(Setting.NORMALIZE_TAG_NAMES.toString()));
    }

    @Test public void testSetShouldUseCompactResponses() {
        assertEquals(true, driver.getSettings()
            .get(Setting.SHOULD_USE_COMPACT_RESPONSES.toString()));
        driver.setShouldUseCompactResponses(false);
        assertEquals(false, driver.getSettings()
            .get(Setting.SHOULD_USE_COMPACT_RESPONSES.toString()));
    }

    @Test public void testSetElementResponseAttributes() {
        assertEquals("", driver.getSettings()
            .get(Setting.ELEMENT_RESPONSE_ATTRIBUTES.toString()));
        driver.setElementResponseAttributes("type,label");
        assertEquals("type,label", driver.getSettings()
            .get(Setting.ELEMENT_RESPONSE_ATTRIBUTES.toString()));
    }

    @Test public void testAllowInvisibleElements() {
        assertEquals(false, driver.getSettings()
            .get(Setting.ALLOW_INVISIBLE_ELEMENTS.toString()));
        driver.allowInvisibleElements(true);
        assertEquals(true, driver.getSettings()
            .get(Setting.ALLOW_INVISIBLE_ELEMENTS.toString()));
    }

    @Test public void testEnableNotificationListener() {
        assertEquals(true, driver.getSettings()
            .get(Setting.ENABLE_NOTIFICATION_LISTENER.toString()));
        driver.enableNotificationListener(false);
        assertEquals(false, driver.getSettings()
            .get(Setting.ENABLE_NOTIFICATION_LISTENER.toString()));
    }

    @Test public void testShutdownOnPowerDisconnect() {
        assertEquals(true, driver.getSettings()
            .get(Setting.SHUTDOWN_ON_POWER_DISCONNECT.toString()));
        driver.shutdownOnPowerDisconnect(false);
        assertEquals(false, driver.getSettings()
            .get(Setting.SHUTDOWN_ON_POWER_DISCONNECT.toString()));
    }

    private void assertJSONElementContains(Setting setting, long value) {
        assertEquals(driver.getSettings().get(setting.toString()), value);
    }
}
