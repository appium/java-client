package io.appium.java_client.ios;

import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import org.junit.BeforeClass;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URL;

public class AppXCUITTest extends BaseIOSTest {

    /**
     * initialization.
     */
    @BeforeClass public static void beforeClass() throws Exception {
        String apiKey = System.getenv("SAUCE_API_KEY");
        String sauceURL = "http://AppiumJavaClient"
            + ":" + apiKey + "@ondemand.saucelabs.com:80/wd/hub";
        DesiredCapabilities caps = DesiredCapabilities.iphone();
        caps.setCapability("appiumVersion", "1.6.3");
        caps.setCapability("deviceName","iPhone 7 Simulator");
        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("platformVersion","10.0");
        caps.setCapability("platformName", "iOS");
        caps.setCapability("browserName", "");
        caps.setCapability("public", "public");
        caps.setCapability("automationName", "xcuitest");
        caps.setCapability("app","sauce-storage:TestApp.app.zip");
        driver =  new IOSDriver<>(new URL(sauceURL), caps);
    }
}
