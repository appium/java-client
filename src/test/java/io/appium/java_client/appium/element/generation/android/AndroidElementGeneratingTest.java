package io.appium.java_client.appium.element.generation.android;

import static io.appium.java_client.MobileBy.AndroidUIAutomator;
import static io.appium.java_client.TestResources.apiDemosApk;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.By.name;
import static org.openqa.selenium.By.tagName;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.appium.element.generation.BaseElementGenerationTest;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class AndroidElementGeneratingTest extends BaseElementGenerationTest {

    private final Supplier<DesiredCapabilities> commonCapabilitiesSupplier = () -> {
        DesiredCapabilities serverCapabilities = new DesiredCapabilities();
        serverCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        serverCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        serverCapabilities.setCapability(MobileCapabilityType.APP, apiDemosApk().toAbsolutePath().toString());
        return serverCapabilities;
    };

    @Test
    public void whenAndroidNativeAppIsLaunched() {
        assertTrue(check(() -> {
            DesiredCapabilities clientCapabilities = commonCapabilitiesSupplier.get();
            clientCapabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
            clientCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
            return clientCapabilities;
        }, commonPredicate, AndroidUIAutomator("new UiSelector().clickable(true)")
        ));
    }

    @Test
    public void whenAndroidHybridAppIsLaunched() {
        assertTrue(check(() -> {
            DesiredCapabilities clientCapabilities = commonCapabilitiesSupplier.get();
            clientCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "io.appium.android.apis");
            clientCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".view.WebView1");
            return clientCapabilities;
        }, (by, aClass) -> {
            driver.context("WEBVIEW_io.appium.android.apis");
            return commonPredicate.test(by, aClass);
        }, tagName("a")));
    }

    @Test
    public void whenAndroidBrowserIsLaunched() {
        assertTrue(check(() -> {
            DesiredCapabilities clientCapabilities = commonCapabilitiesSupplier.get();
            clientCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
            clientCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.BROWSER);
            clientCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
            return clientCapabilities;
        }, (by, aClass) -> {
            driver.get("https://www.google.com");
            return commonPredicate.test(by, aClass);
        }, name("q")));
    }

    private boolean check(Supplier<Capabilities> capabilitiesSupplier,
                          BiPredicate<By, Class<? extends WebElement>> filter,
                          By by) {
        service = AppiumDriverLocalService.buildDefaultService();
        driver = new AppiumDriver<>(service, capabilitiesSupplier.get());
        return filter.test(by, AndroidElement.class);
    }
}
