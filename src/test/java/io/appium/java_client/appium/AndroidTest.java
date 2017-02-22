package io.appium.java_client.appium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.StartsActivity;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.Response;

import java.io.File;
import java.util.Map;

public class AndroidTest {

    private static AppiumDriverLocalService service;
    private static AppiumDriver<AndroidElement> driver;
    private StartsActivity startsActivity;

    /**
     * initialization.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

        if (service == null || !service.isRunning()) {
            throw new AppiumServerHasNotBeenStartedLocallyException(
                    "An appium server node is not started!");
        }

        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "ApiDemos-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
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

    @Before
    public void setUp() throws Exception {
        startsActivity = new StartsActivity() {
            @Override
            public Response execute(String driverCommand, Map<String, ?> parameters) {
                return driver.execute(driverCommand, parameters);
            }

            @Override
            public Response execute(String driverCommand) {
                return driver.execute(driverCommand);
            }
        };
        Activity activity = new Activity("io.appium.android.apis", ".ApiDemos");
        startsActivity.startActivity(activity);
    }

    @Test
    public void findByAccessibilityIdFromDriverTest() {
        assertNotEquals(driver.findElementByAccessibilityId("Graphics").getText(), null);
        assertEquals(driver.findElementsByAccessibilityId("Graphics").size(), 1);
    }

    @Test  public void findByAndroidUIAutomatorFromDriverTest() {
        assertNotEquals(driver
                .findElement(MobileBy
                        .AndroidUIAutomator("new UiSelector().clickable(true)")).getText(), null);
        assertNotEquals(driver
                .findElements(MobileBy
                        .AndroidUIAutomator("new UiSelector().clickable(true)")).size(), 0);
        assertNotEquals(driver
                .findElements(MobileBy
                        .AndroidUIAutomator("new UiSelector().clickable(true)")).size(), 1);
    }

    @Test public void findByAccessibilityIdFromElementTest() {
        assertNotEquals(driver.findElementById("android:id/content")
                .findElement(MobileBy.AccessibilityId("Graphics")).getText(), null);
        assertEquals(driver.findElementById("android:id/content")
                .findElements(MobileBy.AccessibilityId("Graphics")).size(), 1);
    }

    @Test public void findByAndroidUIAutomatorFromElementTest() {
        assertNotEquals(driver.findElementById("android:id/content")
                .findElement(MobileBy
                        .AndroidUIAutomator("new UiSelector().clickable(true)")).getText(), null);
        assertNotEquals(driver.findElementById("android:id/content")
                .findElements(MobileBy
                        .AndroidUIAutomator("new UiSelector().clickable(true)")).size(), 0);
        assertNotEquals(driver.findElementById("android:id/content")
                .findElements(MobileBy
                        .AndroidUIAutomator("new UiSelector().clickable(true)")).size(), 1);
    }

    @Test public void replaceValueTest() {
        String originalValue = "original value";

        Activity activity = new Activity("io.appium.android.apis", ".view.Controls1");
        startsActivity.startActivity(activity);
        AndroidElement editElement = driver
                .findElement(MobileBy
                .AndroidUIAutomator("resourceId(\"io.appium.android.apis:id/edit\")"));
        editElement.sendKeys(originalValue);
        assertEquals(originalValue, editElement.getText());
        String replacedValue = "replaced value";
        editElement.replaceValue(replacedValue);
        assertEquals(replacedValue, editElement.getText());
    }

    @Test public void scrollingToSubElement() {
        driver.findElementByAccessibilityId("Views").click();
        AndroidElement list = driver.findElement(By.id("android:id/list"));
        MobileElement radioGroup = list
                .findElement(MobileBy
                        .AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
                                + "new UiSelector().text(\"Radio Group\"));"));
        assertNotNull(radioGroup.getLocation());
    }

}
