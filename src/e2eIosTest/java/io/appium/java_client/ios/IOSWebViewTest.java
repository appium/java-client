package io.appium.java_client.ios;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.TestUtils;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IOSWebViewTest extends BaseIOSWebViewTest {
    private static final Duration LOOKUP_TIMEOUT = Duration.ofSeconds(30);

    @Test
    public void webViewPageTestCase() throws InterruptedException {
        // this test is not stable in the CI env
        Assumptions.assumeFalse(TestUtils.isCiEnv());

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
