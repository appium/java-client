package io.appium.java_client.android;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class OpenNotificationsTest extends BaseAndroidTest {
    @Test
    public void openNotification() throws Exception {
        driver.openNotifications();
        assertNotEquals(0, driver
                .findElementsById("com.android.systemui:id/carrier_label").size());
        driver.openNotifications();
    }
}
