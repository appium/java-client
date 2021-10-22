package io.appium.java_client.ios;

import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import org.junit.BeforeClass;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

import static io.appium.java_client.TestResources.testAppZip;

public class AppIOSTest extends BaseIOSTest {

    public static final String BUNDLE_ID = "io.appium.TestApp";

    @BeforeClass
    public static void beforeClass() throws Exception {
        final String ip = startAppiumServer();

        if (service == null || !service.isRunning()) {
            throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        capabilities.setCapability(IOSMobileCapabilityType.WDA_LAUNCH_TIMEOUT,
                WDA_LAUNCH_TIMEOUT.toMillis());
        //sometimes environment has performance problems
        capabilities.setCapability("commandTimeouts", "240000");
        capabilities.setCapability(MobileCapabilityType.APP, testAppZip().toAbsolutePath().toString());
        try {
            driver = new IOSDriver<>(new URL("http://" + ip + ":" + PORT + "/wd/hub"), capabilities);
        } catch (SessionNotCreatedException e) {
            capabilities.setCapability("useNewWDA", true);
            driver = new IOSDriver<>(new URL("http://" + ip + ":" + PORT + "/wd/hub"), capabilities);
        }
    }
}