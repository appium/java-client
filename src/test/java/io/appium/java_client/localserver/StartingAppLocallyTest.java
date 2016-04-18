/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.java_client.localserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class StartingAppLocallyTest {

    @Test public void startingAndroidAppWithCapabilitiesOnlyTest() {
        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "ApiDemos-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);

        AndroidDriver<MobileElement> driver = new AndroidDriver<>(capabilities);
        try {
            Capabilities caps = driver.getCapabilities();

            assertEquals(true, caps.getCapability(MobileCapabilityType.AUTOMATION_NAME)
                .equals(AutomationName.APPIUM));
            assertEquals(true, caps.getCapability(MobileCapabilityType.PLATFORM_NAME)
                .equals(MobilePlatform.ANDROID));
            assertNotEquals(null, caps.getCapability(MobileCapabilityType.DEVICE_NAME));
            assertEquals(true,
                caps.getCapability(MobileCapabilityType.APP).equals(app.getAbsolutePath()));
        } finally {
            driver.quit();
        }
    }

    @Test public void startingAndroidAppWithCapabilitiesAndServiceTest() {
        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "ApiDemos-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

        AppiumServiceBuilder builder =
            new AppiumServiceBuilder().withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.STRICT_CAPS);

        AndroidDriver<MobileElement> driver = new AndroidDriver<>(builder, capabilities);
        try {
            Capabilities caps = driver.getCapabilities();

            assertEquals(true, caps.getCapability(MobileCapabilityType.PLATFORM_NAME)
                .equals(MobilePlatform.ANDROID));
            assertNotEquals(null, caps.getCapability(MobileCapabilityType.DEVICE_NAME));
        } finally {
            driver.quit();
        }
    }

    @Test public void startingAndroidAppWithCapabilitiesOnServerSideTest() {
        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "ApiDemos-debug.apk");

        File pageFactoryDir = new File("src/test/java/io/appium/java_client/pagefactory_tests");
        File chrome = new File(pageFactoryDir, "chromedriver.exe");

        DesiredCapabilities serverCapabilities = new DesiredCapabilities();
        serverCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        serverCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        serverCapabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
        serverCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
        serverCapabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        serverCapabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE,
            chrome.getAbsolutePath());

        AppiumServiceBuilder builder =
            new AppiumServiceBuilder().withCapabilities(serverCapabilities);

        DesiredCapabilities clientCapabilities = new DesiredCapabilities();
        clientCapabilities
            .setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "io.appium.android.apis");
        clientCapabilities
            .setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".view.WebView1");

        AndroidDriver<MobileElement> driver = new AndroidDriver<>(builder, clientCapabilities);
        try {
            Capabilities caps = driver.getCapabilities();

            assertEquals(true, caps.getCapability(MobileCapabilityType.PLATFORM_NAME)
                .equals(MobilePlatform.ANDROID));
            assertNotEquals(null, caps.getCapability(MobileCapabilityType.DEVICE_NAME));
        } finally {
            driver.quit();
        }
    }

    @Test public void startingAndroidAppWithCapabilitiesAndFlagsOnServerSideTest() {
        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "ApiDemos-debug.apk");

        File pageFactoryDir = new File("src/test/java/io/appium/java_client/pagefactory_tests");
        File chrome = new File(pageFactoryDir, "chromedriver.exe");

        DesiredCapabilities serverCapabilities = new DesiredCapabilities();
        serverCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        serverCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        serverCapabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
        serverCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
        serverCapabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        serverCapabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE,
            chrome.getAbsolutePath());

        AppiumServiceBuilder builder =
            new AppiumServiceBuilder().withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.STRICT_CAPS).withCapabilities(serverCapabilities);

        DesiredCapabilities clientCapabilities = new DesiredCapabilities();
        clientCapabilities
            .setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "io.appium.android.apis");
        clientCapabilities
            .setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".view.WebView1");

        AndroidDriver<MobileElement> driver = new AndroidDriver<>(builder, clientCapabilities);
        try {
            Capabilities caps = driver.getCapabilities();

            assertEquals(true, caps.getCapability(MobileCapabilityType.PLATFORM_NAME)
                .equals(MobilePlatform.ANDROID));
            assertNotEquals(null, caps.getCapability(MobileCapabilityType.DEVICE_NAME));
        } finally {
            driver.quit();
        }
    }

    @Test public void startingIOSAppWithCapabilitiesOnlyTest() {
        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "UICatalog.app.zip");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.2");
        //sometimes environment has performance problems
        capabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);

        IOSDriver<MobileElement> driver = new IOSDriver<>(capabilities);
        try {
            Capabilities caps = driver.getCapabilities();

            assertEquals(true, caps.getCapability(MobileCapabilityType.AUTOMATION_NAME)
                .equals(AutomationName.APPIUM));
            assertEquals(true,
                caps.getCapability(MobileCapabilityType.PLATFORM_NAME).equals(MobilePlatform.IOS));
            assertNotEquals(null, caps.getCapability(MobileCapabilityType.DEVICE_NAME));
            assertEquals(true,
                caps.getCapability(MobileCapabilityType.PLATFORM_VERSION).equals("9.2"));
            assertEquals(true,
                caps.getCapability(MobileCapabilityType.APP).equals(app.getAbsolutePath()));
        } finally {
            driver.quit();
        }
    }


    @Test public void startingIOSAppWithCapabilitiesAndServiseTest() {
        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "UICatalog.app.zip");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.2");
        //sometimes environment has performance problems
        capabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);

        AppiumServiceBuilder builder =
            new AppiumServiceBuilder().withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.STRICT_CAPS);

        IOSDriver<MobileElement> driver = new IOSDriver<>(builder, capabilities);
        try {
            Capabilities caps = driver.getCapabilities();
            assertEquals(true,
                caps.getCapability(MobileCapabilityType.PLATFORM_NAME).equals(MobilePlatform.IOS));
            assertNotEquals(null, caps.getCapability(MobileCapabilityType.DEVICE_NAME));
        } finally {
            driver.quit();
        }
    }

    @Test public void startingIOSAppWithCapabilitiesOnServerSideTest() {
        DesiredCapabilities serverCapabilities = new DesiredCapabilities();
        serverCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
        serverCapabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT,
            500000); //some environment is too slow
        serverCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.2");

        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "UICatalog.app.zip");
        DesiredCapabilities clientCapabilities = new DesiredCapabilities();
        clientCapabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

        AppiumServiceBuilder builder =
            new AppiumServiceBuilder().withCapabilities(serverCapabilities);

        IOSDriver<MobileElement> driver = new IOSDriver<>(builder, clientCapabilities);
        try {
            Capabilities caps = driver.getCapabilities();
            assertEquals(true,
                caps.getCapability(MobileCapabilityType.PLATFORM_NAME).equals(MobilePlatform.IOS));
            assertNotEquals(null, caps.getCapability(MobileCapabilityType.DEVICE_NAME));
        } finally {
            driver.quit();
        }
    }

    @Test public void startingIOSAppWithCapabilitiesAndFlagsOnServerSideTest() {
        DesiredCapabilities serverCapabilities = new DesiredCapabilities();
        serverCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
        serverCapabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT,
            500000); //some environment is too slow
        serverCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.2");

        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "UICatalog.app.zip");
        DesiredCapabilities clientCapabilities = new DesiredCapabilities();
        clientCapabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

        AppiumServiceBuilder builder =
            new AppiumServiceBuilder().withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.STRICT_CAPS).withCapabilities(serverCapabilities);

        IOSDriver<MobileElement> driver = new IOSDriver<>(builder, clientCapabilities);
        try {
            Capabilities caps = driver.getCapabilities();
            assertEquals(true,
                caps.getCapability(MobileCapabilityType.PLATFORM_NAME).equals(MobilePlatform.IOS));
            assertNotEquals(null, caps.getCapability(MobileCapabilityType.DEVICE_NAME));
        } finally {
            driver.quit();
        }
    }
}
