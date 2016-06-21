package io.appium.java_client.pagefactory_tests.widgets;


import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.TimeOutDuration;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class PartiallyOverrideNegativeWidgetTest {

    private static AndroidDriver<?> driver;
    private static AppiumDriverLocalService service;
    private static PartiallyOverrideRottenTomatoes rottenTomatoes;

    /**
     * initialization.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "android-rottentomatoes-demo-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        driver = new AndroidDriver<>(service.getUrl(), capabilities);

        rottenTomatoes = new PartiallyOverrideRottenTomatoes();
        PageFactory.initElements(
                new AppiumFieldDecorator(driver, new TimeOutDuration(5, TimeUnit.SECONDS)),
                rottenTomatoes);
    }

    /**
     * finishing.
     */
    @AfterClass
    public static void afterClass() throws Exception {
        if (driver != null) {
            driver.quit();
        }

        if (service != null) {
            service.stop();
        }
    }

    @Test(expected = InstantiationException.class)
    public void gettingOfAnElement() {
        rottenTomatoes.checkSimpleReview();
    }
}
