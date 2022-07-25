package io.appium.java_client.ios;

import io.appium.java_client.ios.options.XCUITestOptions;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.SessionNotCreatedException;

import java.time.Duration;

import static io.appium.java_client.TestResources.testAppZip;

public class AppIOSTest extends BaseIOSTest {

    public static final String BUNDLE_ID = "io.appium.TestApp";

    @BeforeAll
    public static void beforeClass() throws Exception {
        startAppiumServer();

        XCUITestOptions options = new XCUITestOptions()
                .setPlatformVersion(PLATFORM_VERSION)
                .setDeviceName(DEVICE_NAME)
                .setCommandTimeouts(Duration.ofSeconds(240))
                .setApp(testAppZip().toAbsolutePath().toString())
                .setWdaLaunchTimeout(WDA_LAUNCH_TIMEOUT);
        try {
            driver = new IOSDriver(service.getUrl(), options);
        } catch (SessionNotCreatedException e) {
            options.useNewWDA();
            driver = new IOSDriver(service.getUrl(), options);
        }
    }
}