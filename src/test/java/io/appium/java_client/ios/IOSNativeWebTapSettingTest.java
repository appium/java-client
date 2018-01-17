package io.appium.java_client.ios;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.WebElement;

public class IOSNativeWebTapSettingTest extends BaseSafariTest {

    @Test public void nativeWebTapSettingTest() {
        driver.get("https://saucelabs.com/test/guinea-pig");

        // do a click with nativeWebTap turned on, and assert we get to the right page
        driver.nativeWebTap(true);
        WebElement el = driver.findElementById("i am a link");
        el.click();
        assertEquals(true, driver.getTitle().contains("I am another page title"));
        driver.navigate().back();

        // now do a click with it turned off and assert the same behavior
        assertEquals(true, driver.getTitle().contains("I am a page title"));
        driver.nativeWebTap(false);
        el = driver.findElementById("i am a link");
        el.click();
        assertEquals(true, driver.getTitle().contains("I am another page title"));
    }
}
