package io.appium.java_client.ios;

import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import org.junit.BeforeClass;
import org.openqa.selenium.SessionNotCreatedException;

import java.net.URL;
import java.time.Duration;

import static io.appium.java_client.TestResources.testAppZip;

public class AppIOSTest extends BaseIOSTest {

    public static final String BUNDLE_ID = "io.appium.TestApp";

    @BeforeClass
    public static void beforeClass() throws Exception {
        final String ip = startAppiumServer();

        if (service == null || !service.isRunning()) {
            throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");
        }

        XCUITestOptions options = new XCUITestOptions()
                .setDeviceName(DEVICE_NAME)
                .setCommandTimeouts(Duration.ofSeconds(240))
                .setApp(testAppZip().toAbsolutePath().toString())
                .setWdaLaunchTimeout(WDA_LAUNCH_TIMEOUT);
        try {
            driver = new IOSDriver(new URL("http://" + ip + ":" + PORT + "/wd/hub"), options);
        } catch (SessionNotCreatedException e) {
            options.useNewWDA();
            driver = new IOSDriver(new URL("http://" + ip + ":" + PORT + "/wd/hub"), options);
        }
    }
}