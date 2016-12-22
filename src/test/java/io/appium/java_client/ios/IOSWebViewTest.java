package io.appium.java_client.ios;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class IOSWebViewTest extends BaseIOSWebViewTest {

    @Test public void webViewPageTestCase() throws Throwable {
        driver.findElementByXPath("//UIATextField[@value='Enter URL']")
            .sendKeys("www.google.com");
        driver.findElementByClassName("UIAButton").click();
        Thread.sleep(10000);
        driver.getContextHandles().forEach((handle) -> {
            if (handle.contains("WEBVIEW")) {
                driver.context(handle);
            }
        });
        WebElement el = driver.findElementByClassName("gsfi");
        el.sendKeys("Appium");
        el.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        assertEquals(true, driver.getTitle().contains("Appium"));
    }
}
