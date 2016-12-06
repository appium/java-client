package io.appium.java_client.ios;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class IOSSwipeGestureTest {

    private static AppiumDriverLocalService service;
    protected static IOSDriver<IOSElement> driver;

    @BeforeClass
    public static void beforeClass() throws Exception {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

        if (service == null || !service.isRunning()) {
            throw new AppiumServerHasNotBeenStartedLocallyException(
                "An appium server node is not started!");
        }

        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "UICatalog.app.zip");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.2");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
        //sometimes environment has performance problems
        capabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        driver = new IOSDriver<>(service.getUrl(), capabilities);
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

    @After public void afterMethod() {
        driver.resetApp();
    }

    @Test
    public void horizontalSwipingTest() {
        driver.findElementByAccessibilityId("Sliders").click();
        MobileElement slider = driver.findElementsByClassName("UIASlider").get(1);

        IOSTouchAction touchAction = new IOSTouchAction(driver);
        touchAction.swipe(slider,
            SwipeElementDirection.LEFT, slider.getSize().getWidth() / 2, 0, 3000).perform();
        assertEquals("0%", slider.getAttribute("value"));

        IOSTouchAction touchAction2 = new IOSTouchAction(driver);
        touchAction2.swipe(slider, SwipeElementDirection.RIGHT, 2, 0, 3000).perform();
        assertEquals("100%", slider.getAttribute("value"));
    }

    @Test
    public void verticalSwipingTest() throws InterruptedException {
        IOSElement tableView = (IOSElement) driver.findElementByClassName("UIATableView");
        MobileElement element = driver.findElementsByClassName("UIAStaticText").get(1);

        IOSTouchAction touchAction = new IOSTouchAction(driver);
        touchAction.swipe(tableView, SwipeElementDirection.UP, 20, 15, 3000).perform();
        assertFalse(element.isDisplayed());

        IOSTouchAction touchAction2 = new IOSTouchAction(driver);
        touchAction2.swipe(tableView, SwipeElementDirection.DOWN, 20, 15, 3000).perform();

        Thread.sleep(5000);
        assertTrue(element.isDisplayed());
    }

    @Test
    public void swipeFromElementToElement() {
        IOSElement e1 = (IOSElement) driver.findElementsByClassName("UIAWindow").get(1)
            .findElementByAccessibilityId("Buttons");
        IOSElement e2 = (IOSElement) driver.findElementsByClassName("UIAWindow").get(1)
            .findElementByAccessibilityId("Sliders");

        Point originalLocation = e2.getLocation();
        IOSTouchAction touchAction = new IOSTouchAction(driver);
        touchAction.swipe(e2, e1, 3000).perform();

        Point newLocation = e2.getLocation();
        assertNotEquals(originalLocation, newLocation);
    }

    @Test public void swipeFromCoordinatesToElement() {
        IOSElement e1 = (IOSElement) driver.findElementsByClassName("UIAWindow").get(1)
            .findElementByAccessibilityId("Buttons");
        IOSElement e2 = (IOSElement) driver.findElementsByClassName("UIAWindow").get(1)
            .findElementByAccessibilityId("Sliders");

        Point originalLocation = e2.getLocation();
        IOSTouchAction touchAction = new IOSTouchAction(driver);
        touchAction.swipe(originalLocation.x + 50, originalLocation.y, e1, 3000).perform();

        Point newLocation = e2.getLocation();
        assertNotEquals(originalLocation, newLocation);
    }

    @Test public void whenSwipingIsCombinedWithOtherActions() {
        IOSElement e1 = (IOSElement) driver.findElementsByClassName("UIAWindow").get(1)
            .findElementByAccessibilityId("Buttons");
        IOSElement e2 = (IOSElement) driver.findElementsByClassName("UIAWindow").get(1)
            .findElementByAccessibilityId("Sliders");
        IOSElement e3 = (IOSElement) driver.findElementsByClassName("UIAWindow").get(1)
            .findElementByAccessibilityId("Date Picker");

        Coordinates originalCoordinates = e3.getCoordinates();

        IOSTouchAction touchAction = new IOSTouchAction(driver);
        touchAction.swipe(e1, e2, 3000).moveTo(e3).perform();

        assertNotEquals(originalCoordinates, e3.getCoordinates());
    }

    @Test public void swipeChainingTest() {
        IOSElement e1 = (IOSElement) driver.findElementsByClassName("UIAWindow").get(1)
            .findElementByAccessibilityId("Buttons");
        IOSElement e2 = (IOSElement) driver.findElementsByClassName("UIAWindow").get(1)
            .findElementByAccessibilityId("Sliders");

        IOSTouchAction touchAction = new IOSTouchAction(driver);
        ((IOSTouchAction) touchAction.swipe(e2, e1, 3000)).swipe(e1, e2, 3000).perform();

        assertTrue(e1.isDisplayed());
    }
}
