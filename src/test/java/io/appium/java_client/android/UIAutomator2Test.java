package io.appium.java_client.android;


import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UIAutomator2Test {
    private static AppiumDriverLocalService service;
    protected static AndroidDriver driver;

    /**
     * initialization.
     */
    @BeforeClass public static void beforeClass() throws Exception {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

        if (service == null || !service.isRunning()) {
            throw new AppiumServerHasNotBeenStartedLocallyException(
                "An appium server node is not started!");
        }

        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "ApiDemos-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
        driver = new AndroidDriver(service.getUrl(), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    /**
     * finishing.
     */
    @AfterClass public static void afterClass() {
        if (driver != null) {
            driver.quit();
        }
        if (service != null) {
            service.stop();
        }
    }


    @After public void afterMethod() {
        driver.rotate(new DeviceRotation(0, 0, 0));
    }

    @Test public void testLandscapeRightRotation() {
        DeviceRotation landscapeRightRotation = new DeviceRotation(0, 0, 90);
        driver.rotate(landscapeRightRotation);
        assertEquals(driver.rotation(), landscapeRightRotation);
    }

    @Test public void testLandscapeLeftRotation() {
        DeviceRotation landscapeLeftRotation = new DeviceRotation(0, 0, 270);
        driver.rotate(landscapeLeftRotation);
        assertEquals(driver.rotation(), landscapeLeftRotation);
    }

    @Test public void testPortraitUpsideDown() {
        DeviceRotation landscapeRightRotation = new DeviceRotation(0, 0, 180);
        driver.rotate(landscapeRightRotation);
        assertEquals(driver.rotation(), landscapeRightRotation);
    }

    @Test public void testToastMSGIsDisplayed() throws InterruptedException {
        final WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.startActivity("io.appium.android.apis", ".view.PopupMenu1");

        MobileElement popUpElement = driver.findElement(MobileBy.AccessibilityId("Make a Popup!"));
        popUpElement.click();
        driver.findElement(By.xpath(".//*[@text='Search']")).click();
        assertNotNull(wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[@text='Clicked popup menu item Search']"))));

        popUpElement.click();
        driver.findElement(By.xpath(".//*[@text='Add']")).click();
        assertNotNull(wait.until(ExpectedConditions
            .presenceOfElementLocated(By.xpath("//*[@text='Clicked popup menu item Add']"))));

        popUpElement.click();
        driver.findElement(By.xpath(".//*[@text='Edit']")).click();
        assertNotNull(wait.until(ExpectedConditions
            .presenceOfElementLocated(By.xpath("//*[@text='Clicked popup menu item Edit']"))));

        driver.findElement(By.xpath(".//*[@text='Share']")).click();
        assertNotNull(wait.until(ExpectedConditions
            .presenceOfElementLocated(By.xpath("//*[@text='Clicked popup menu item Share']"))));
    }
}
