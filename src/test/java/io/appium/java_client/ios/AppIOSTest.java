package io.appium.java_client.ios;

import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.BeforeClass;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

import static io.appium.java_client.TestResources.testAppZip;

public class AppIOSTest extends BaseIOSTest {

    public static final String BUNDLE_ID = "io.appium.TestApp";

    @BeforeClass
    public static void beforeClass() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        //sometimes environment has performance problems
        capabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);
        capabilities.setCapability("commandTimeouts", "120000");
        capabilities.setCapability(MobileCapabilityType.APP, testAppZip().toAbsolutePath().toString());
        driver = new IOSDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities);
    }
}
