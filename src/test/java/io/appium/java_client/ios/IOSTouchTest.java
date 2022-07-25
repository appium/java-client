package io.appium.java_client.ios;

import static io.appium.java_client.ios.touch.IOSPressOptions.iosPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.MultiTouchAction;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class IOSTouchTest extends AppIOSTest {

    @Test
    public void tapTest() {
        WebElement intA = driver.findElement(By.id("IntegerA"));
        WebElement intB = driver.findElement(By.id("IntegerB"));
        intA.clear();
        intB.clear();
        intA.sendKeys("2");
        intB.sendKeys("4");

        WebElement e = driver.findElement(AppiumBy.accessibilityId("ComputeSumButton"));
        new IOSTouchAction(driver).tap(tapOptions().withElement(element(e))).perform();
        assertEquals(driver.findElement(By.xpath("//*[@name = \"Answer\"]")).getText(), "6");
    }

    @Test
    public void touchWithPressureTest() {
        WebElement intA = driver.findElement(By.id("IntegerA"));
        WebElement intB = driver.findElement(By.id("IntegerB"));
        intA.clear();
        intB.clear();
        intA.sendKeys("2");
        intB.sendKeys("4");

        WebElement e = driver.findElement(AppiumBy.accessibilityId("ComputeSumButton"));
        new IOSTouchAction(driver)
                .press(iosPressOptions()
                        .withElement(element(e))
                        .withPressure(1))
                .waitAction(waitOptions(ofMillis(100)))
                .release()
                .perform();
        assertEquals(driver.findElement(By.xpath("//*[@name = \"Answer\"]")).getText(), "6");
    }

    @Test
    public void multiTouchTest() {
        WebElement e = driver.findElement(AppiumBy.accessibilityId("ComputeSumButton"));
        WebElement e2 = driver.findElement(AppiumBy.accessibilityId("show alert"));

        IOSTouchAction tap1 = new IOSTouchAction(driver).tap(tapOptions().withElement(element(e)));
        IOSTouchAction tap2 = new IOSTouchAction(driver).tap(tapOptions().withElement(element(e2)));

        new MultiTouchAction(driver).add(tap1).add(tap2).perform();

        WebDriverWait waiting = new WebDriverWait(driver, Duration.ofSeconds(10));
        assertNotNull(waiting.until(alertIsPresent()));
        driver.switchTo().alert().accept();
    }

    @Test
    public void doubleTapTest() {
        WebElement firstField = driver.findElement(By.id("IntegerA"));
        firstField.sendKeys("2");

        IOSTouchAction iosTouchAction = new IOSTouchAction(driver);
        iosTouchAction.doubleTap(element(firstField));
        WebElement editingMenu = driver.findElement(AppiumBy.className("XCUIElementTypeTextField"));
        assertNotNull(editingMenu);
    }
}
