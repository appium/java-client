package io.appium.java_client.ios;

import static org.junit.Assert.assertTrue;

import io.appium.java_client.MobileBy;
import io.appium.java_client.selenium.By;
import io.appium.java_client.selenium.WebElement;
import io.appium.java_client.selenium.support.ui.ExpectedConditions;
import io.appium.java_client.selenium.support.ui.WebDriverWait;
import org.junit.Test;

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
