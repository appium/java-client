package io.appium.java_client.ios;

import static io.appium.java_client.ios.touch.IOSPressOptions.iosPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;

import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IOSTouchTest extends AppIOSTest {

    @Test
    public void tapTest() {
        IOSElement intA = driver.findElement(By.id("IntegerA"));
        IOSElement intB = driver.findElement(By.id("IntegerB"));
        intA.clear();
        intB.clear();
        intA.sendKeys("2");
        intB.sendKeys("4");

        MobileElement e = driver.findElementByAccessibilityId("ComputeSumButton");
        new IOSTouchAction(driver).tap(tapOptions().withElement(element(e))).perform();
        assertEquals(driver.findElement(By.xpath("//*[@name = \"Answer\"]")).getText(), "6");
    }

    @Test
    public void touchWithPressureTest() {
        IOSElement intA = driver.findElement(By.id("IntegerA"));
        IOSElement intB = driver.findElement(By.id("IntegerB"));
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
        assertEquals(driver.findElement(By.xpath("//*[@name = \"Answer\"]")).getText(), "6");
    }

    @Test public void multiTouchTest() {
        MobileElement e = driver.findElementByAccessibilityId("ComputeSumButton");
        MobileElement e2 = driver.findElementByAccessibilityId("show alert");

        IOSTouchAction tap1 = new IOSTouchAction(driver).tap(tapOptions().withElement(element(e)));
        IOSTouchAction tap2 = new IOSTouchAction(driver).tap(tapOptions().withElement(element(e2)));

        new MultiTouchAction(driver).add(tap1).add(tap2).perform();

        WebDriverWait waiting = new WebDriverWait(driver, 10);
        assertNotNull(waiting.until(alertIsPresent()));
        driver.switchTo().alert().accept();
    }

    @Test public void doubleTapTest() {
        IOSElement firstField = driver.findElement(By.id("IntegerA"));
        firstField.sendKeys("2");

        IOSTouchAction iosTouchAction = new IOSTouchAction(driver);
        iosTouchAction.doubleTap(element(firstField));
        IOSElement editingMenu = driver.findElement(By.className("XCUIElementTypeTextField"));
        assertNotNull(editingMenu);
    }
}
