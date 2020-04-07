package io.appium.java_client.ios;

import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import org.junit.BeforeClass;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URL;

import static io.appium.java_client.TestResources.uiCatalogAppZip;

public class UICatalogIOSTest extends BaseIOSTest {

    @BeforeClass
    public static void beforeClass() throws IOException {
        final String ip = startAppiumServer();

        if (service == null || !service.isRunning()) {
            throw new AppiumServerHasNotBeenStartedLocallyException(
                    "An appium server node is not started!");
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.2");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6");
        //sometimes environment has performance problems
        capabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);
        capabilities.setCapability(MobileCapabilityType.APP, uiCatalogAppZip().toAbsolutePath());
        driver = new IOSDriver<>(new URL("http://" + ip + ":" + PORT + "/wd/hub"), capabilities);
    }
}
