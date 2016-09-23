package io.appium.java_client.android;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.appium.java_client.android.settings.Setting;
import org.junit.Test;

public class SettingTest extends BaseAndroidTest {

    @Test public void ignoreUnimportantViewsTest() {
        driver.setSetting(Setting.IGNORE_UNIMPORTANT_VIEWS,
                Setting.IgnoreUnimportantViews.YES.value());
        boolean ignoreViews =
                driver.getSettings().get(Setting.IGNORE_UNIMPORTANT_VIEWS.toString())
                        .getAsBoolean();
        assertTrue(ignoreViews);
        driver.setSetting(Setting.IGNORE_UNIMPORTANT_VIEWS,
                Setting.IgnoreUnimportantViews.NO.value());
        ignoreViews = driver.getSettings().get(Setting.IGNORE_UNIMPORTANT_VIEWS.toString())
                .getAsBoolean();
        assertFalse(ignoreViews);
    }
}
