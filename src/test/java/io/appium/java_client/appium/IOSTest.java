package io.appium.java_client.appium;

import static org.junit.Assert.assertNotEquals;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class IOSTest {
    private static AppiumDriverLocalService service;
    private static AppiumDriver<IOSElement> driver;

    /**
     * initialization.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

        if (service == null || !service.isRunning()) {
            throw new RuntimeException("An appium server node is not started!");
        }

        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "TestApp.app.zip");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.2");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
        //sometimes environment has performance problems
        capabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        driver = new AppiumDriver<>(service.getUrl(), capabilities);
    }

    /**
     * finishing.
     */
    @AfterClass
    public static void afterClass() {
        if (driver != null) {
            driver.quit();
        }
        if (service != null) {
            service.stop();
        }
    }

    @Test
    public void findByAccessibilityIdFromDriverTest() {
        assertNotEquals(driver
                .findElementByAccessibilityId("ComputeSumButton")
                .getText(), null);
        assertNotEquals(driver
                .findElementsByAccessibilityId("ComputeSumButton")
                .size(), 0);
    }

    @Test public void findByByIosUIAutomationFromDriverTest() {
        assertNotEquals(driver
                .findElement(MobileBy.IosUIAutomation(".elements().withName(\"Answer\")"))
                .getText(), null);
        assertNotEquals(driver
                .findElements(MobileBy.IosUIAutomation(".elements().withName(\"Answer\")"))
                .size(), 0);
    }

    @Test public void findByAccessibilityIdFromElementTest() {
        assertNotEquals(driver.findElementsByClassName("UIAWindow").get(1)
                .findElementsByAccessibilityId("ComputeSumButton").size(), 0);
    }

    @Test public void findByByIosUIAutomationTest() {
        assertNotEquals((driver.findElementsByClassName("UIAWindow")
                .get(1))
                .findElementByIosUIAutomation(".elements().withName(\"Answer\")").getText(), null);
    }
}
