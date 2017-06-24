package io.appium.java_client.ios;

import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import org.junit.BeforeClass;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class AppXCUITTest extends BaseIOSTest {

    /**
     * initialization.
     */
    @BeforeClass public static void beforeClass() throws Exception {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

        if (service == null || !service.isRunning()) {
            throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");
        }

        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "TestApp.app.zip");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.1");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6");
        capabilities.setCapability(IOSMobileCapabilityType.ALLOW_TOUCHID_ENROLL, "true");
        //sometimes environment has performance problems
        capabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);
        capabilities
            .setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        driver = new IOSDriver<>(service.getUrl(), capabilities);
    }
}
