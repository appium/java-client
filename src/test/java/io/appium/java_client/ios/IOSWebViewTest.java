package io.appium.java_client.ios;

import static java.time.Duration.ofSeconds;
import static org.junit.Assert.assertTrue;

import io.appium.java_client.MobileBy;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class IOSWebViewTest extends BaseIOSWebViewTest {

    @Test public void webViewPageTestCase() throws InterruptedException {
        new WebDriverWait(driver, ofSeconds(30))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("login")))
                .click();
        driver.findElementByAccessibilityId("webView").click();
        new WebDriverWait(driver, ofSeconds(30))
                .until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Webview")));
        findAndSwitchToWebView();
        WebElement el = driver.findElementByPartialLinkText("login");
        assertTrue(el.isDisplayed());
    }
}
