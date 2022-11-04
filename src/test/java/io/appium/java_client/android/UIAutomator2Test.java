package io.appium.java_client.android;

import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UIAutomator2Test extends BaseAndroidTest {

    @AfterEach
    public void afterMethod() {
        driver.rotate(new DeviceRotation(0, 0, 0));
    }

    @Test
    public void testLandscapeRightRotation() {
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions
                .elementToBeClickable(driver.findElement(By.id("android:id/content"))
                        .findElement(AppiumBy.accessibilityId("Graphics"))));
        DeviceRotation landscapeRightRotation = new DeviceRotation(0, 0, 90);
        driver.rotate(landscapeRightRotation);
        assertEquals(driver.rotation(), landscapeRightRotation);
    }

    @Test
    public void testLandscapeLeftRotation() {
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions
                .elementToBeClickable(driver.findElement(By.id("android:id/content"))
                        .findElement(AppiumBy.accessibilityId("Graphics"))));
        DeviceRotation landscapeLeftRotation = new DeviceRotation(0, 0, 270);
        driver.rotate(landscapeLeftRotation);
        assertEquals(driver.rotation(), landscapeLeftRotation);
    }

    @Test
    public void testPortraitUpsideDown() {
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions
                .elementToBeClickable(driver.findElement(By.id("android:id/content"))
                        .findElement(AppiumBy.accessibilityId("Graphics"))));
        DeviceRotation landscapeRightRotation = new DeviceRotation(0, 0, 180);
        driver.rotate(landscapeRightRotation);
        assertEquals(driver.rotation(), landscapeRightRotation);
    }

    /**
     * ignoring.
     */
    @Disabled
    @Test
    public void testToastMSGIsDisplayed() {
        final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        Activity activity = new Activity("io.appium.android.apis", ".view.PopupMenu1");
        driver.startActivity(activity);

        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy
                .accessibilityId("Make a Popup!")));
        WebElement popUpElement = driver.findElement(AppiumBy.accessibilityId("Make a Popup!"));
        wait.until(ExpectedConditions.elementToBeClickable(popUpElement)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(".//*[@text='Search']"))).click();
        assertNotNull(wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@text='Clicked popup menu item Search']"))));

        wait.until(ExpectedConditions.elementToBeClickable(popUpElement)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(".//*[@text='Add']"))).click();
        assertNotNull(wait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//*[@text='Clicked popup menu item Add']"))));

        wait.until(ExpectedConditions.elementToBeClickable(popUpElement)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(".//*[@text='Edit']"))).click();
        assertNotNull(wait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//*[@text='Clicked popup menu item Edit']"))));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(".//*[@text='Share']"))).click();
        assertNotNull(wait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//*[@text='Clicked popup menu item Share']"))));
    }
}
