package io.appium.java_client.ios;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class IOSWebViewTest extends BaseIOSWebViewTest {

    @Test public void webViewPageTestCase() throws InterruptedException {
        driver.findElementByXPath("//UIATextField[@value='Enter URL']")
            .sendKeys("www.google.com");
        driver.findElementByClassName("UIAButton").click();
        findAndSwitchToWebView();
        WebElement el = driver.findElementByClassName("gsfi");
        el.sendKeys("Appium");
        el.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        assertEquals(true, driver.getTitle().contains("Appium"));
    }
}
