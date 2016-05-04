package io.appium.java_client.android;

import static org.junit.Assert.assertEquals;

import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class IntentTest {
    private static AppiumDriverLocalService service;
    protected static AndroidDriver<AndroidElement> driver;

    /**
     * initialization.
     */
    @BeforeClass public static void beforeClass() throws Exception {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

        if (service == null || !service.isRunning()) {
            throw new RuntimeException("An appium server node is not started!");
        }

        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "IntentExample.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        driver = new AndroidDriver<>(service.getUrl(), capabilities);
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


    @Test public void startActivityWithIntent() {
        driver.startActivity("com.android.mms", ".ui.ComposeMessageActivity", null, null,
            "android.intent.action.SEND", "android.intent.category.DEFAULT", "0x4000000",
            "-d \"TestIntent\" -t \"text/plain\"");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test public void startActivityWithDefaultIntentAndDefaultCategoryWithOptionalArgs() {
        driver.startActivity("com.prgguru.android", ".GreetingActivity", null, null,
            "android.intent.action.MAIN", "android.intent.category.DEFAULT", "0x4000000",
            "--es \"USERNAME\" \"AppiumIntentTest\" -t \"text/plain\"");
        assertEquals(driver.findElementById("com.prgguru.android:id/textView1").getText(),
            "Welcome AppiumIntentTest");
    }
}
