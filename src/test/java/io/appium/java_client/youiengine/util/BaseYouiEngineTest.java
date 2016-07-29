package io.appium.java_client.youiengine.util;

import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.YouiEngineCapabilityType;
import io.appium.java_client.youiengine.YouiEngineDriver;
import io.appium.java_client.youiengine.frames.YouiEngineAppiumSampleApp;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;


@SuppressWarnings("ALL")
/**
 * This is a utility class to contain settings for a test class and prepare the test session.
 * Extend this to create a new test class.
 *
 * This follows the pattern found in the tutorial for writing Appium java_client tests.
 */
public class BaseYouiEngineTest {

    static {
        // Disable annoying cookie warnings.
        // WARNING: Invalid cookie header
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
                "org.apache.commons.logging.impl.NoOpLog");
    }

    public static YouiEngineDriver driver;
    public static URL serverAddress;
    public static YouiEngineAppiumSampleApp app;

    public String appPath;
    public boolean isAndroid;
    public String bundleId;

    private DesiredCapabilities capabilities = new DesiredCapabilities();

    @Rule
    public TestRule printTests = new TestWatcher() {
        protected void starting(Description description) {
            System.out.print("  test: " + description.getMethodName());
        }

        protected void finished(Description description) {
            System.out.println();
        }
    };

    private void setupCaps(String appPath) {
        capabilities.setCapability(MobileCapabilityType.APP, appPath);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "youiengine");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "0");

        if (isAndroid) {
            // The lines below can be modified to target a device or an AVD. Update accordingly.
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "30044d90e35c6200");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
            capabilities.setCapability(YouiEngineCapabilityType.APP_ADDRESS, "192.168.1.181");
            //capabilities.setCapability(AndroidMobileCapabilityType.AVD, "AVD name goes here");

        } else {
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6s Plus");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
            capabilities.setCapability(YouiEngineCapabilityType.APP_ADDRESS, "localhost");
            //capabilities.setCapability(MobileCapabilityType.UDID, "some UDID goes here");
        }
    }

    /** Run before each test. **/
    @Before
    public void setUp() throws Exception {

        isAndroid = false;

        String currentPath = System.getProperty("user.dir");
        String javaClientPath = "src/test/java/io/appium/java_client/";

        String appName = "YouiEngineAppiumSample";
        String fullAppName = isAndroid ? appName + "-debug.apk" : appName + ".app.zip";
        String iosAppPath = Paths.get(currentPath, javaClientPath + fullAppName).toAbsolutePath()
                .toString();
        String androidAppPath = Paths.get(currentPath, javaClientPath + fullAppName)
                .toAbsolutePath().toString();
        appPath = isAndroid ? androidAppPath : iosAppPath;
        bundleId = isAndroid ? "tv.youi.youiengine.youiengineappiumsample" :
                "tv.youi.YouiEngineAppiumSample";
        setupCaps(appPath);

        serverAddress = new URL("http://127.0.0.1:4723/wd/hub");

        driver = new YouiEngineDriver(serverAddress, capabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        app = new YouiEngineAppiumSampleApp(driver);
    }

    /** Run after each test. **/
    @After
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }

}
