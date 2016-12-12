package io.appium.java_client.ios;

import static org.junit.Assert.assertEquals;

import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import org.junit.Test;
import org.openqa.selenium.Dimension;

public class IOSTouchTest extends AppIOSTest {

    @Test
    public void tapTest() {
        driver.findElementById("IntegerA").sendKeys("2");
        driver.findElementById("IntegerB").sendKeys("4");

        MobileElement e = driver.findElementByAccessibilityId("ComputeSumButton");
        new TouchAction(driver).tap(e).perform();
        assertEquals(driver.findElementByXPath("//*[@name = \"Answer\"]").getText(), "6");
    }

    @Test public void swipeTest() {
        MobileElement slider = driver.findElementByClassName("UIASlider");
        Dimension size = slider.getSize();

        TouchAction swipe = new TouchAction(driver).press(slider, size.width/2, size.height/2)
                .waitAction(2000).moveTo(slider).release();
        swipe.perform();
        assertEquals("1%", slider.getAttribute("value"));
    }

    @Test public void multiTouchTest() {
        driver.findElementById("IntegerA").sendKeys("2");
        driver.findElementById("IntegerB").sendKeys("4");

        MobileElement e = driver.findElementByAccessibilityId("ComputeSumButton");
        TouchAction tap = new TouchAction(driver).tap(e).perform();

        MobileElement slider = driver.findElementByClassName("UIASlider");
        Dimension size = slider.getSize();

        TouchAction swipe = new TouchAction(driver).press(slider, size.width/2, size.height/2)
                .waitAction(2000).moveTo(slider).release();

        new MultiTouchAction(driver).add(tap).add(swipe).perform();
        assertEquals(driver.findElementByXPath("//*[@name = \"Answer\"]").getText(), "6");
        assertEquals("1%", slider.getAttribute("value"));
    }

}
