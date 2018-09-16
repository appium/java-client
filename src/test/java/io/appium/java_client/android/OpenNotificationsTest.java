package io.appium.java_client.android;

import static org.junit.Assert.assertNotEquals;
import static org.openqa.selenium.By.id;

import org.junit.Test;

import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class OpenNotificationsTest extends BaseAndroidTest {
    @Test
    public void openNotification() {
        driver.closeApp();
        driver.openNotifications();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        assertNotEquals(0, wait.until(input -> {
            List<AndroidElement> result = input
                    .findElements(id("com.android.systemui:id/settings_button"));

            return result.isEmpty() ? null : result;
        }).size());
    }
}
