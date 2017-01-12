package io.appium.java_client.appium.element.generation.android;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class AndroidElementGeneratingTest {

    @Test public void whenAndroidNativeAppIsLaunched() {
        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "ApiDemos-debug.apk");

        DesiredCapabilities serverCapabilities = new DesiredCapabilities();
        serverCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        serverCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        serverCapabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

        AppiumServiceBuilder builder = new AppiumServiceBuilder().withCapabilities(serverCapabilities);

        DesiredCapabilities clientCapabilities = new DesiredCapabilities();
        clientCapabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
        clientCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);

        AppiumDriver<?> driver = new AppiumDriver<>(builder, clientCapabilities);
        try {
            WebElement element = driver.findElement(MobileBy
                    .AndroidUIAutomator("new UiSelector().clickable(true)"));
            assertTrue(element.getClass().equals(AndroidElement.class));
        } finally {
            driver.quit();
        }
    }

    @Test public void whenAndroidHybridAppIsLaunched() {
        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "ApiDemos-debug.apk");

        DesiredCapabilities serverCapabilities = new DesiredCapabilities();
        serverCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        serverCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        serverCapabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

        AppiumServiceBuilder builder = new AppiumServiceBuilder().withCapabilities(serverCapabilities);

        DesiredCapabilities clientCapabilities = new DesiredCapabilities();
        clientCapabilities
                .setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "io.appium.android.apis");
        clientCapabilities
                .setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".view.WebView1");

        AppiumDriver<?> driver = new AppiumDriver<>(builder, clientCapabilities);
        try {
            driver.context("WEBVIEW_io.appium.android.apis");
            WebElement element = driver.findElement(By.tagName("a"));
            assertTrue(element.getClass().equals(RemoteWebElement.class));
        } finally {
            driver.quit();
        }
    }

    @Test public void whenAndroidBrowserIsLaunched() {
        DesiredCapabilities serverCapabilities = new DesiredCapabilities();
        serverCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        serverCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.BROWSER);

        AppiumServiceBuilder builder = new AppiumServiceBuilder().withCapabilities(serverCapabilities);

        DesiredCapabilities clientCapabilities = new DesiredCapabilities();
        clientCapabilities
                .setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");

        AppiumDriver<?> driver = new AppiumDriver<>(builder, clientCapabilities);
        try {
            driver.get("https://www.google.com");
            WebElement element = driver.findElementByClassName("gsfi");
            assertTrue(element.getClass().equals(RemoteWebElement.class));
        }
        finally {
            driver.quit();
        }
    }
}
