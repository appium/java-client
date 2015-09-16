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

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class StartingAppLocallyTest {

    @Test
    public void startingAndroidAppWithCapabilitiesOnlyTest(){
        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "ApiDemos-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);

        AndroidDriver<MobileElement> driver = new AndroidDriver<>(capabilities);;
        try {
            Capabilities caps = driver.getCapabilities();

            assertEquals(true, caps.getCapability(MobileCapabilityType.AUTOMATION_NAME).equals(AutomationName.APPIUM));
            assertEquals(true, caps.getCapability(MobileCapabilityType.PLATFORM_NAME).equals(MobilePlatform.ANDROID));
            assertNotEquals(null, caps.getCapability(MobileCapabilityType.DEVICE_NAME));
            assertEquals(true, caps.getCapability(MobileCapabilityType.APP).equals(app.getAbsolutePath()));
        }
        finally {
            driver.quit();
        }
    }

    @Test
    public void startingAndroidAppWithCapabilitiesAndServiceTest(){
        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "ApiDemos-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");

        AppiumServiceBuilder builder = new AppiumServiceBuilder().withArgument(GeneralServerFlag.AUTOMATION_NAME, AutomationName.APPIUM).
                withArgument(GeneralServerFlag.APP, app.getAbsolutePath());

        AndroidDriver<MobileElement> driver = new AndroidDriver<>(builder, capabilities);;
        try {
            Capabilities caps = driver.getCapabilities();

            assertEquals(true, caps.getCapability(MobileCapabilityType.PLATFORM_NAME).equals(MobilePlatform.ANDROID));
            assertNotEquals(null, caps.getCapability(MobileCapabilityType.DEVICE_NAME));
        }
        finally {
            driver.quit();
        }
    }

    @Test
    public void startingIOSAppWithCapabilitiesOnlyTest(){
        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "UICatalog.app.zip");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);

        IOSDriver<MobileElement> driver = new IOSDriver<>(capabilities);;
        try {
            Capabilities caps = driver.getCapabilities();

            assertEquals(true, caps.getCapability(MobileCapabilityType.AUTOMATION_NAME).equals(AutomationName.APPIUM));
            assertEquals(true, caps.getCapability(MobileCapabilityType.PLATFORM_NAME).equals(MobilePlatform.IOS));
            assertNotEquals(null, caps.getCapability(MobileCapabilityType.DEVICE_NAME));
            assertEquals(true, caps.getCapability(MobileCapabilityType.PLATFORM_VERSION).equals("7.1"));
            assertEquals(true, caps.getCapability(MobileCapabilityType.APP).equals(app.getAbsolutePath()));
        }
        finally {
            driver.quit();
        }
    }


    @Test
    public void startingIOSAppWithCapabilitiesAndServiseTest(){
        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "UICatalog.app.zip");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");

        AppiumServiceBuilder builder = new AppiumServiceBuilder().withArgument(GeneralServerFlag.AUTOMATION_NAME, AutomationName.APPIUM).
                withArgument(GeneralServerFlag.APP, app.getAbsolutePath()).withArgument(GeneralServerFlag.PLATFORM_VERSION, "7.1");

        IOSDriver<MobileElement> driver = new IOSDriver<>(builder, capabilities);;
        try {
            Capabilities caps = driver.getCapabilities();
            assertEquals(true, caps.getCapability(MobileCapabilityType.PLATFORM_NAME).equals(MobilePlatform.IOS));
            assertNotEquals(null, caps.getCapability(MobileCapabilityType.DEVICE_NAME));
        }
        finally {
            driver.quit();
        }
    }

}