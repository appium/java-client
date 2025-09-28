package io.appium.java_client.android;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.flutter.FlutterDriverOptions;
import io.appium.java_client.flutter.FlutterIntegrationTestDriver;
import io.appium.java_client.flutter.android.FlutterAndroidDriver;
import io.appium.java_client.flutter.commands.ScrollParameter;
import io.appium.java_client.flutter.ios.FlutterIOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Optional;

class BaseFlutterTest {

    private static final boolean IS_ANDROID = Optional
            .ofNullable(System.getProperty("platform"))
            .orElse("android")
            .equalsIgnoreCase("android");
    private static final String APP_ID = IS_ANDROID
            ? "com.example.appium_testing_app" : "com.example.appiumTestingApp";
    protected static final int PORT = 4723;

    private static AppiumDriverLocalService service;
    protected static FlutterIntegrationTestDriver driver;
    protected static final By LOGIN_BUTTON = AppiumBy.flutterText("Login");
    private static String PREBUILT_WDA_PATH = System.getenv("PREBUILT_WDA_PATH");

    /**
     * initialization.
     */
    @BeforeAll
    public static void beforeClass() {
        service = new AppiumServiceBuilder()
                .withIPAddress("127.0.0.1")
                .usingPort(PORT)
                // Flutter driver mocking command requires adb_shell permission to set certain permissions
                // to the AUT. This can be removed once the server logic is updated to use a different approach
                // for setting the permission
                .withArgument(GeneralServerFlag.ALLOW_INSECURE, "*:adb_shell")
                .build();
        service.start();
    }

    @BeforeEach
    void startSession() throws MalformedURLException {
        FlutterDriverOptions flutterOptions = new FlutterDriverOptions()
                .setFlutterServerLaunchTimeout(Duration.ofMinutes(2))
                .setFlutterSystemPort(9999)
                .setFlutterElementWaitTimeout(Duration.ofSeconds(10))
                .setFlutterEnableMockCamera(true);

        if (IS_ANDROID) {
            driver = new FlutterAndroidDriver(service.getUrl(), flutterOptions
                    .setUiAutomator2Options(new UiAutomator2Options()
                            .setApp(System.getProperty("flutterApp"))
                            .setAutoGrantPermissions(true)
                            .eventTimings())
            );
        } else {
            String deviceName = System.getenv("IOS_DEVICE_NAME") != null
                    ? System.getenv("IOS_DEVICE_NAME")
                    : "iPhone 12";
            String platformVersion = System.getenv("IOS_PLATFORM_VERSION") != null
                    ? System.getenv("IOS_PLATFORM_VERSION")
                    : "14.5";
            XCUITestOptions xcuiTestOptions = new XCUITestOptions()
                    .setApp(System.getProperty("flutterApp"))
                    .setDeviceName(deviceName)
                    .setPlatformVersion(platformVersion)
                    .setWdaLaunchTimeout(Duration.ofMinutes(4))
                    .setSimulatorStartupTimeout(Duration.ofMinutes(5))
                    .eventTimings();
            if (PREBUILT_WDA_PATH != null) {
                xcuiTestOptions.usePreinstalledWda().setPrebuiltWdaPath(PREBUILT_WDA_PATH);
            }
            driver = new FlutterIOSDriver(
                    service.getUrl(),
                    flutterOptions.setXCUITestOptions(xcuiTestOptions)
            );
        }
    }

    @AfterEach
    void stopSession() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterAll
    static void afterClass() {
        if (service.isRunning()) {
            service.stop();
        }
    }

    void openScreen(String screenTitle) {
        ScrollParameter scrollOptions = new ScrollParameter(
                AppiumBy.flutterText(screenTitle), ScrollParameter.ScrollDirection.DOWN);
        WebElement element = driver.scrollTillVisible(scrollOptions);
        element.click();
    }
}
