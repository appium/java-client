package io.appium.java_client.android;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class UIAutomator2Test extends BaseAndroidTest {

    /**
     * finishing.
     */

    @After
    public void afterMethod() {
        driver.rotate(new DeviceRotation(0, 0, 0));
    }

    @Test
    public void testLandscapeRightRotation() {
        new WebDriverWait(driver,20).until(ExpectedConditions
                .elementToBeClickable(driver.findElementById("android:id/content")
                        .findElement(MobileBy.AccessibilityId("Graphics"))));
        DeviceRotation landscapeRightRotation = new DeviceRotation(0, 0, 90);
        driver.rotate(landscapeRightRotation);
        assertEquals(driver.rotation(), landscapeRightRotation);
    }

    @Test
    public void testLandscapeLeftRotation() {
        new WebDriverWait(driver,20).until(ExpectedConditions
                .elementToBeClickable(driver.findElementById("android:id/content")
                        .findElement(MobileBy.AccessibilityId("Graphics"))));
        DeviceRotation landscapeLeftRotation = new DeviceRotation(0, 0, 270);
        driver.rotate(landscapeLeftRotation);
        assertEquals(driver.rotation(), landscapeLeftRotation);
    }

    @Test
    public void testPortraitUpsideDown() {
        new WebDriverWait(driver,20).until(ExpectedConditions
                .elementToBeClickable(driver.findElementById("android:id/content")
                        .findElement(MobileBy.AccessibilityId("Graphics"))));
        DeviceRotation landscapeRightRotation = new DeviceRotation(0, 0, 180);
        driver.rotate(landscapeRightRotation);
        assertEquals(driver.rotation(), landscapeRightRotation);
    }


    /**
     * ignoring.
     */
    @Ignore
    public void testToastMSGIsDisplayed() {
        final WebDriverWait wait = new WebDriverWait(driver, 30);
        Activity activity = new Activity("io.appium.android.apis", ".view.PopupMenu1");
        driver.startActivity(activity);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy
                                .AccessibilityId("Make a Popup!")));
        MobileElement popUpElement = driver.findElement(MobileBy.AccessibilityId("Make a Popup!"));
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
