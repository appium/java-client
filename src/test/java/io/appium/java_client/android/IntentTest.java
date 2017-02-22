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
    protected static AndroidDriver<?> driver;

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
        Activity activity = new Activity();
        activity.setAppPackage("com.android.mms");
        activity.setAppActivity(".ui.ComposeMessageActivity");
        activity.setIntentAction("android.intent.action.SEND");
        activity.setIntentCategory("android.intent.category.DEFAULT");
        activity.setIntentFlags("0x4000000");
        activity.setOptionalIntentArguments("-d \"TestIntent\" -t \"text/plain\"");
        driver.startActivity(activity);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test public void startActivityWithDefaultIntentAndDefaultCategoryWithOptionalArgs() {
        Activity activity = new Activity();
        activity.setAppPackage("com.prgguru.android");
        activity.setAppActivity(".GreetingActivity");
        activity.setIntentAction("android.intent.action.MAIN");
        activity.setIntentCategory("android.intent.category.DEFAULT");
        activity.setIntentFlags("0x4000000");
        activity.setOptionalIntentArguments("--es \"USERNAME\" \"AppiumIntentTest\" -t \"text/plain\"");
        driver.startActivity(activity);
        assertEquals(driver.findElementById("com.prgguru.android:id/textView1").getText(),
            "Welcome AppiumIntentTest");
    }
}
