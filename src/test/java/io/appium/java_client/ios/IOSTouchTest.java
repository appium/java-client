package io.appium.java_client.ios;

import static io.appium.java_client.touch.RelativeOffsetOption.useRelative;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static java.time.Duration.ofSeconds;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IOSTouchTest extends AppIOSTest {

    @Test
    public void tapTest() {
        IOSElement intA = driver.findElementById("IntegerA");
        IOSElement intB = driver.findElementById("IntegerB");
        intA.clear();
        intB.clear();
        intA.sendKeys("2");
        intB.sendKeys("4");

        MobileElement e = driver.findElementByAccessibilityId("ComputeSumButton");
        new TouchAction(driver).tap(useRelative(e)).perform();
        assertEquals(driver.findElementByXPath("//*[@name = \"Answer\"]").getText(), "6");
    }

    @Test public void swipeTest() {
        MobileElement slider = driver.findElementByClassName("UIASlider");
        Dimension size = slider.getSize();

        TouchAction swipe = new TouchAction(driver).press(useRelative(slider, size.width / 2 + 2, size.height / 2))
                .waitAction(waitOptions(ofSeconds(2))).moveTo(useRelative(slider, 1, size.height / 2)).release();
        swipe.perform();
        assertEquals("0%", slider.getAttribute("value"));
    }

    @Test public void multiTouchTest() {
        MobileElement e = driver.findElementByAccessibilityId("ComputeSumButton");
        TouchAction tap1 = new TouchAction(driver).tap(useRelative(e));
        TouchAction tap2 = new TouchAction(driver).tap(useRelative(driver.findElement(MobileBy
                .IosUIAutomation(".elements().withName(\"show alert\")"))));

        new MultiTouchAction(driver).add(tap1).add(tap2).perform();

        WebDriverWait waiting = new WebDriverWait(driver, 10000);
        assertNotNull(waiting.until(alertIsPresent()));
        driver.switchTo().alert().accept();
    }
}
