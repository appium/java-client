package io.appium.java_client.ios;

import static io.appium.java_client.MobileBy.IosUIAutomation;
import static io.appium.java_client.ios.touch.IOSPressOptions.iosPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;

import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.ElementOption;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        new TouchAction(driver).tap(tapOptions().withElement(element(e))).perform();
        assertEquals(driver.findElementByXPath("//*[@name = \"Answer\"]").getText(), "6");
    }

    @Test
    public void touchWithPressureTest() {
        IOSElement intA = driver.findElementById("IntegerA");
        IOSElement intB = driver.findElementById("IntegerB");
        intA.clear();
        intB.clear();
        intA.sendKeys("2");
        intB.sendKeys("4");

        MobileElement e = driver.findElementByAccessibilityId("ComputeSumButton");
        new IOSTouchAction(driver)
                .press(iosPressOptions()
                    .withElement(element(e))
                    .withPressure(1))
                .waitAction(waitOptions(ofMillis(100)))
                .release()
                .perform();
        assertEquals(driver.findElementByXPath("//*[@name = \"Answer\"]").getText(), "6");
    }

    @Test public void swipeTest() {
        MobileElement slider = driver.findElementByClassName("UIASlider");
        Dimension size = slider.getSize();

        ElementOption press = element(slider, size.width / 2 + 2, size.height / 2);
        ElementOption move = element(slider, 1, size.height / 2);

        TouchAction swipe = new TouchAction(driver).press(press)
                .waitAction(waitOptions(ofSeconds(2)))
                .moveTo(move).release();

        swipe.perform();
        assertEquals("0%", slider.getAttribute("value"));
    }

    @Test public void multiTouchTest() {
        MobileElement e = driver.findElementByAccessibilityId("ComputeSumButton");
        MobileElement e2 = driver.findElement(IosUIAutomation(".elements().withName(\"show alert\")"));

        TouchAction tap1 = new TouchAction(driver).tap(tapOptions().withElement(element(e)));
        TouchAction tap2 = new TouchAction(driver).tap(tapOptions().withElement(element(e2)));

        new MultiTouchAction(driver).add(tap1).add(tap2).perform();

        WebDriverWait waiting = new WebDriverWait(driver, 10000);
        assertNotNull(waiting.until(alertIsPresent()));
        driver.switchTo().alert().accept();
    }
}
