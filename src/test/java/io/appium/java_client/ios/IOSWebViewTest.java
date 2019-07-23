package io.appium.java_client.ios;

import static org.junit.Assert.assertTrue;

import io.appium.java_client.MobileBy;
import org.junit.Test;
import org.openqa.seleniumone.By;
import org.openqa.seleniumone.WebElement;
import org.openqa.seleniumone.support.ui.ExpectedConditions;
import org.openqa.seleniumone.support.ui.WebDriverWait;

public class IOSWebViewTest extends BaseIOSWebViewTest {

    @Test public void webViewPageTestCase() throws InterruptedException {
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.presenceOfElementLocated(By.id("login")))
                .click();
        driver.findElementByAccessibilityId("webView").click();
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Webview")));
        findAndSwitchToWebView();
        WebElement el = driver.findElementByPartialLinkText("login");
        assertTrue(el.isDisplayed());
    }
}
