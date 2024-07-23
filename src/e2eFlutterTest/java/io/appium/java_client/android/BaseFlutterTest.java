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

    /**
     * initialization.
     */
    @BeforeAll
    public static void beforeClass() {
        service = new AppiumServiceBuilder()
                .withIPAddress("127.0.0.1")
                .usingPort(PORT)
                .build();
        service.start();
    }

    @BeforeEach
    void startSession() throws MalformedURLException {
        FlutterDriverOptions flutterOptions = new FlutterDriverOptions()
                .setFlutterServerLaunchTimeout(Duration.ofSeconds(30))
                .setFlutterElementWaitTimeout(Duration.ofSeconds(3));
        if (IS_ANDROID) {
            driver = new FlutterAndroidDriver(service.getUrl(), flutterOptions
                    .setFlutterSystemPort(9999)
                    .setUiAutomator2Options(new UiAutomator2Options()
                            .setApp(System.getProperty("flutterApp"))
                            .eventTimings())
            );
        } else {
            String deviceName = System.getenv("IOS_DEVICE_NAME") != null
                    ? System.getenv("IOS_DEVICE_NAME")
                    : "iPhone 12";
            String platformVersion = System.getenv("IOS_PLATFORM_VERSION") != null
                    ? System.getenv("IOS_PLATFORM_VERSION")
                    : "14.5";
            driver = new FlutterIOSDriver(service.getUrl(), flutterOptions
                    .setXCUITestOptions(new XCUITestOptions()
                            .setApp(System.getProperty("flutterApp"))
                            .setDeviceName(deviceName)
                            .setPlatformVersion(platformVersion)
                            .setWdaLaunchTimeout(Duration.ofMinutes(4))
                            .setSimulatorStartupTimeout(Duration.ofMinutes(5))
                            .setNoReset(true)
                            .setFullReset(false)
                            .eventTimings()
                    )
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
