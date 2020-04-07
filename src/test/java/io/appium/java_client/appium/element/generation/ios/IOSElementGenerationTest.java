package io.appium.java_client.appium.element.generation.ios;

import static io.appium.java_client.MobileBy.AccessibilityId;
import static io.appium.java_client.TestResources.testAppZip;
import static io.appium.java_client.TestResources.vodQaAppZip;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.name;
import static org.openqa.selenium.By.partialLinkText;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.appium.element.generation.BaseElementGenerationTest;
import io.appium.java_client.ios.BaseIOSTest;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

public class IOSElementGenerationTest extends BaseElementGenerationTest {

    private static final File testApp = testAppZip().toFile();

    private static final File webViewApp = vodQaAppZip().toFile();

    private Supplier<DesiredCapabilities> commonAppCapabilitiesSupplier = () -> {
        DesiredCapabilities serverCapabilities = new DesiredCapabilities();
        serverCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, BaseIOSTest.DEVICE_NAME);
        serverCapabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT,
                500000); //some environment is too slow
        serverCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, BaseIOSTest.PLATFORM_VERSION);
        serverCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        return serverCapabilities;
    };

    private Function<File, Supplier<Capabilities>> appFileSupplierFunction = file -> {
        final DesiredCapabilities clientCapabilities = new DesiredCapabilities();
        return () -> {
            clientCapabilities.setCapability(MobileCapabilityType.APP, file.getAbsolutePath());
            return clientCapabilities;
        };
    };

    private final Supplier<DesiredCapabilities> serverBrowserCapabilitiesSupplier = () -> {
        DesiredCapabilities serverCapabilities = new DesiredCapabilities();
        serverCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.SAFARI);
        serverCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, BaseIOSTest.PLATFORM_VERSION);
        serverCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, BaseIOSTest.DEVICE_NAME);
        //sometimes environment has performance problems
        serverCapabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);
        return serverCapabilities;
    };

    private final Supplier<Capabilities> clientBrowserCapabilitiesSupplier = () -> {
        DesiredCapabilities clientCapabilities = new DesiredCapabilities();
        clientCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        return clientCapabilities;
    };

    @Test
    public void whenIOSNativeAppIsLaunched() {
        assertTrue(check(() -> {
            Capabilities caps = commonAppCapabilitiesSupplier.get();
            return caps.merge(appFileSupplierFunction.apply(testApp).get());
        }, commonPredicate,
        AccessibilityId("Answer")
        ));
    }

    @Ignore
    @Test
    public void whenIOSHybridAppIsLaunched() {
        assertTrue(check(() -> {
            Capabilities caps = commonAppCapabilitiesSupplier.get();
            return caps.merge(appFileSupplierFunction.apply(webViewApp).get());
        }, (by, aClass) -> {
            new WebDriverWait(driver, 30)
                    .until(ExpectedConditions.presenceOfElementLocated(id("login")))
                    .click();
            driver.findElementByAccessibilityId("webView").click();
            new WebDriverWait(driver, 30)
                    .until(ExpectedConditions
                            .presenceOfElementLocated(AccessibilityId("Webview")));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.getContextHandles().forEach((handle) -> {
                if (handle.contains("WEBVIEW")) {
                    driver.context(handle);
                }
            });
            return commonPredicate.test(by, aClass);
        }, partialLinkText("login")));
    }

    @Test
    public void whenIOSBrowserIsLaunched() {
        assertTrue(check(() -> {
            Capabilities caps = serverBrowserCapabilitiesSupplier.get();
            return caps.merge(clientBrowserCapabilitiesSupplier.get());
        }, (by, aClass) -> {
            driver.get("https://www.google.com");
            return commonPredicate.test(by, aClass);
        }, name("q")));
    }

    @Test
    public void whenIOSNativeAppIsLaunched2() {
        assertTrue(check(() -> {
            DesiredCapabilities serverCapabilities = commonAppCapabilitiesSupplier.get();
            serverCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, BaseIOSTest.PLATFORM_VERSION);
            serverCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
            return serverCapabilities.merge(appFileSupplierFunction.apply(testApp).get());
        }, commonPredicate, id("IntegerA")));
    }

    @Test
    public void whenIOSBrowserIsLaunched2() {
        assertTrue(check(() -> {
            DesiredCapabilities serverCapabilities = serverBrowserCapabilitiesSupplier.get();
            serverCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, BaseIOSTest.PLATFORM_VERSION);
            serverCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
            return serverCapabilities.merge(clientBrowserCapabilitiesSupplier.get());
        }, (by, aClass) -> {
            driver.get("https://www.google.com");
            return commonPredicate.test(by, aClass);
        }, name("q")));
    }

    private boolean check(Supplier<Capabilities> capabilitiesSupplier,
                          BiPredicate<By, Class<? extends WebElement>> filter,
                          By by) {
        service = AppiumDriverLocalService.buildDefaultService();
        Capabilities caps = capabilitiesSupplier.get();
        DesiredCapabilities fixedCaps = new DesiredCapabilities(caps);
        fixedCaps.setCapability("commandTimeouts", "120000");
        try {
            driver = new AppiumDriver<>(service, fixedCaps);
        } catch (SessionNotCreatedException e) {
            fixedCaps.setCapability("useNewWDA", true);
            driver = new AppiumDriver<>(service, fixedCaps);
        }
        return filter.test(by, IOSElement.class);
    }
}
