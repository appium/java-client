package io.appium.java_client.ios;

import io.appium.java_client.ios.options.XCUITestOptions;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.SessionNotCreatedException;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static io.appium.java_client.AppiumBy.iOSNsPredicateString;
import static io.appium.java_client.utils.TestUtils.IOS_SIM_VODQA_RELEASE_URL;

public class AppIOSTest extends BaseIOSTest {
    protected static final String BUNDLE_ID = "org.reactjs.native.example.VodQAReactNative";
    protected static final By LOGIN_LINK_ID = accessibilityId("login");
    protected static final By PASSWORD_EDIT_PREDICATE = iOSNsPredicateString("name == \"password\"");

    @BeforeAll
    public static void beforeClass() throws MalformedURLException {
        startAppiumServer();

        XCUITestOptions options = new XCUITestOptions()
                .setPlatformVersion(PLATFORM_VERSION)
                .setDeviceName(DEVICE_NAME)
                .setCommandTimeouts(Duration.ofSeconds(240))
                .setApp(new URL(IOS_SIM_VODQA_RELEASE_URL))
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
