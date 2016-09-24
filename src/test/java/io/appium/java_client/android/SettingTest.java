package io.appium.java_client.android;

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
}
