package io.appium.java_client.ios;

import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.utils.TestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.SessionNotCreatedException;

import java.time.Duration;

public class AppIOSTest extends BaseIOSTest {
    protected static final String BUNDLE_ID = "io.appium.TestApp";

    private static final String TEST_APP_ZIP = TestUtils.resourcePathToAbsolutePath("TestApp.app.zip").toString();

    @BeforeAll
    public static void beforeClass() {
        startAppiumServer();

        XCUITestOptions options = new XCUITestOptions()
                .setPlatformVersion(PLATFORM_VERSION)
                .setDeviceName(DEVICE_NAME)
                .setCommandTimeouts(Duration.ofSeconds(240))
                .setApp(TEST_APP_ZIP)
                .enableBiDi()
                .setWdaLaunchTimeout(WDA_LAUNCH_TIMEOUT);
        if (PREBUILT_WDA_PATH != null) {
            options.usePreinstalledWda().setPrebuiltWdaPath(PREBUILT_WDA_PATH);
        }
        try {
            driver = new IOSDriver(service.getUrl(), options);
        } catch (SessionNotCreatedException e) {
            options.useNewWDA();
            driver = new IOSDriver(service.getUrl(), options);
        }
    }
}
