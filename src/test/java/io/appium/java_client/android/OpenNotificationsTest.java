package io.appium.java_client.android;

import static org.junit.Assert.assertNotEquals;

import com.google.common.base.Function;

import org.junit.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class OpenNotificationsTest extends BaseAndroidTest {
    @Test
    public void openNotification() throws Exception {
        driver.closeApp();
        driver.openNotifications();
        WebDriverWait wait = new WebDriverWait(driver, 20000);
        assertNotEquals(0, wait.until(new Function<WebDriver, List<AndroidElement>>() {
            @Override
            public List<AndroidElement> apply(WebDriver input) {
                List<AndroidElement> result = driver
                        .findElementsById("com.android.systemui:id/carrier_label");

                if (result.size() == 0) {
                    return null;
                }

                return result;
            }
        }).size());
    }
}
