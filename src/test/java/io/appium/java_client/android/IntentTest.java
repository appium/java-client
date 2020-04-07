package io.appium.java_client.android;

import static io.appium.java_client.TestResources.intentExampleApk;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.function.Predicate;

public class IntentTest {
    private static AppiumDriverLocalService service;
    protected static AndroidDriver<?> driver;

    /**
     * initialization.
     */
    @BeforeClass public static void beforeClass() {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

        if (service == null || !service.isRunning()) {
            throw new RuntimeException("An appium server node is not started!");
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.APP, intentExampleApk().toAbsolutePath());
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
        Predicate<AndroidDriver> predicate = driver -> {
            Activity activity = new Activity("com.android.mms",
                    ".ui.ComposeMessageActivity")
                    .setIntentAction("android.intent.action.SEND")
                    .setIntentCategory("android.intent.category.DEFAULT")
                    .setIntentFlags("0x4000000")
                    .setOptionalIntentArguments("-d \"TestIntent\" -t \"text/plain\"");
            driver.startActivity(activity);
            return true;
        };
        assertTrue(predicate.test(driver));

    }

    @Test public void startActivityWithDefaultIntentAndDefaultCategoryWithOptionalArgs() {
        final Activity activity = new Activity("com.prgguru.android", ".GreetingActivity")
                .setIntentAction("android.intent.action.MAIN")
                .setIntentCategory("android.intent.category.DEFAULT")
                .setIntentFlags("0x4000000")
                .setOptionalIntentArguments("--es \"USERNAME\" \"AppiumIntentTest\" -t \"text/plain\"");
        driver.startActivity(activity);
        assertEquals(driver.findElementById("com.prgguru.android:id/textView1").getText(),
            "Welcome AppiumIntentTest");
    }
}
