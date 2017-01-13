package io.appium.java_client.appium.element.generation.ios;

import static io.appium.java_client.MobileBy.IosUIAutomation;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;

import io.appium.java_client.appium.element.generation.BaseElementGenerationTest;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;

import java.io.File;
import java.util.function.Function;
import java.util.function.Supplier;

public class IOSElementGenerationTest extends BaseElementGenerationTest {

    private final File testApp = new File(new File("src/test/java/io/appium/java_client"),
            "TestApp.app.zip");

    private final File webViewApp = new File(new File("src/test/java/io/appium/java_client"),
            "WebViewApp.app.zip");

    private Supplier<DesiredCapabilities> serverAppCapabilitiesSupplier = () -> {
        DesiredCapabilities serverCapabilities = new DesiredCapabilities();
        serverCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
        serverCapabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT,
                500000); //some environment is too slow
        serverCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.2");
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
        serverCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.2");
        serverCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
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
        assertTrue(check(serverAppCapabilitiesSupplier,
                appFileSupplierFunction.apply(testApp),
                commonPredicate,
                IosUIAutomation(".elements().withName(\"Answer\")"),
                IOSElement.class));
    }

    @Test public void whenIOSHybridAppIsLaunched() throws Exception {
        assertTrue(check(serverAppCapabilitiesSupplier,
                appFileSupplierFunction.apply(webViewApp),
                (by, aClass) -> {
                    IOSElement element1 = (IOSElement) driver
                            .findElementByXPath("//UIATextField[@value='Enter URL']");
                    element1.sendKeys("www.google.com");
                    driver.findElementByClassName("UIAButton").click();
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
                },
                className("gsfi"),
                RemoteWebElement.class));
    }

    @Test public void whenIOSBrowserIsLaunched() {
        assertTrue(check(serverBrowserCapabilitiesSupplier,
                clientBrowserCapabilitiesSupplier,
                commonPredicate,
                className("gsfi"),
                RemoteWebElement.class));
    }

    @Test
    public void whenIOSNativeAppIsLaunched2() {
        assertTrue(check(
                () -> {
                    DesiredCapabilities serverCapabilities = serverAppCapabilitiesSupplier.get();
                    serverCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.1");
                    return serverCapabilities;
                },
                appFileSupplierFunction.apply(testApp),
                commonPredicate,
                id("IntegerA"),
                IOSElement.class));
    }

    @Test public void whenIOSBrowserIsLaunched2() {
        assertTrue(check(
                () -> {
                    DesiredCapabilities serverCapabilities = serverBrowserCapabilitiesSupplier.get();
                    serverCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.1");
                    return serverCapabilities;
                },
                clientBrowserCapabilitiesSupplier,
                commonPredicate,
                className("gsfi"),
                RemoteWebElement.class));
    }
}
