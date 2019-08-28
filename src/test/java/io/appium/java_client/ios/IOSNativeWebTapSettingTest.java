package io.appium.java_client.ios;

import static java.time.Duration.ofSeconds;
import static org.junit.Assert.assertTrue;

import io.appium.java_client.selenium.WebElement;
import io.appium.java_client.selenium.support.ui.ExpectedConditions;
import io.appium.java_client.selenium.support.ui.WebDriverWait;
import org.junit.Test;

public class IOSNativeWebTapSettingTest extends BaseSafariTest {

    @Test public void nativeWebTapSettingTest() {
        assertTrue(driver.isBrowser());
        driver.get("https://saucelabs.com/test/guinea-pig");

        // do a click with nativeWebTap turned on, and assert we get to the right page
        driver.nativeWebTap(true);
        WebElement el = driver.findElementById("i am a link");
        el.click();
        assertTrue(new WebDriverWait(driver, ofSeconds(30))
                .until(ExpectedConditions.titleIs("I am another page title - Sauce Labs")));
        driver.navigate().back();

        // now do a click with it turned off and assert the same behavior
        assertTrue(new WebDriverWait(driver, ofSeconds(30))
                .until(ExpectedConditions.titleIs("I am a page title - Sauce Labs")));
        driver.nativeWebTap(false);
        el = driver.findElementById("i am a link");
        el.click();
        assertTrue(new WebDriverWait(driver, ofSeconds(30))
                .until(ExpectedConditions.titleIs("I am another page title - Sauce Labs")));
    }
}
