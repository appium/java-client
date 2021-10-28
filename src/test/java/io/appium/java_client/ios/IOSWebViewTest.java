package io.appium.java_client.ios;

import static org.junit.Assert.assertTrue;

import java.time.Duration;

import io.appium.java_client.AppiumBy;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IOSWebViewTest extends BaseIOSWebViewTest {
    private static final Duration LOOKUP_TIMEOUT = Duration.ofSeconds(30);

    @Test
    public void webViewPageTestCase() throws InterruptedException {
        new WebDriverWait(driver, LOOKUP_TIMEOUT)
                .until(ExpectedConditions.presenceOfElementLocated(By.id("login")))
                .click();
        new WebDriverWait(driver, LOOKUP_TIMEOUT)
                .until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("webView")))
                .click();
        new WebDriverWait(driver, LOOKUP_TIMEOUT)
                .until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("Webview")));
        findAndSwitchToWebView();
        assertTrue(driver.findElement(By.partialLinkText("login")).isDisplayed());
    }
}
