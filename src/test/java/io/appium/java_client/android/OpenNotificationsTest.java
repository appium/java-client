package io.appium.java_client.android;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.openqa.selenium.By.xpath;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OpenNotificationsTest extends BaseAndroidTest {
    @Test
    public void openNotification() {
        driver.closeApp();
        driver.openNotifications();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        assertNotEquals(0, wait.until(input -> {
            List<WebElement> result = input
                    .findElements(xpath("//android.widget.Switch[contains(@content-desc, 'Wi-Fi')]"));

            return result.isEmpty() ? null : result;
        }).size());
    }
}
