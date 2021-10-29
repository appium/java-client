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

package io.appium.java_client.service.local;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.openqa.selenium.Capabilities;

import java.time.Duration;

import static io.appium.java_client.TestResources.apiDemosApk;
import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class StartingAppLocallyAndroidTest {

    @Test
    public void startingAndroidAppWithCapabilitiesOnlyTest() {
        AndroidDriver driver = new AndroidDriver(new UiAutomator2Options()
                .setDeviceName("Android Emulator")
                .autoGrantPermissions()
                .setApp(apiDemosApk().toAbsolutePath().toString()));
        try {
            Capabilities caps = driver.getCapabilities();

            assertTrue(MobilePlatform.ANDROID.equalsIgnoreCase(
                    (String) caps.getCapability(MobileCapabilityType.PLATFORM_NAME))
            );
            assertEquals(AutomationName.ANDROID_UIAUTOMATOR2, caps.getCapability(MobileCapabilityType.AUTOMATION_NAME));
            assertNotNull(caps.getCapability(MobileCapabilityType.DEVICE_NAME));
            assertEquals(apiDemosApk().toAbsolutePath().toString(), caps.getCapability(MobileCapabilityType.APP));
        } finally {
            driver.quit();
        }
    }

    @Test
    public void startingAndroidAppWithCapabilitiesAndServiceTest() {
        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.STRICT_CAPS);

        AndroidDriver driver = new AndroidDriver(builder, new UiAutomator2Options()
                .setDeviceName("Android Emulator")
                .autoGrantPermissions()
                .setApp(apiDemosApk().toAbsolutePath().toString()));
        try {
            Capabilities caps = driver.getCapabilities();

            assertTrue(MobilePlatform.ANDROID.equalsIgnoreCase(
                    (String) caps.getCapability(MobileCapabilityType.PLATFORM_NAME))
            );
            assertNotNull(caps.getCapability(MobileCapabilityType.DEVICE_NAME));
        } finally {
            driver.quit();
        }
    }

    @Test
    public void startingAndroidAppWithCapabilitiesAndFlagsOnServerSideTest() {
        UiAutomator2Options serverOptions = new UiAutomator2Options()
                .setDeviceName("Android Emulator")
                .fullReset()
                .autoGrantPermissions()
                .setNewCommandTimeout(Duration.ofSeconds(60))
                .setApp(apiDemosApk().toAbsolutePath().toString());

        WebDriverManager chromeManager = chromedriver();
        chromeManager.setup();
        serverOptions.setChromedriverExecutable(chromeManager.getDownloadedDriverPath());

        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.STRICT_CAPS)
                .withCapabilities(serverOptions);

        UiAutomator2Options clientOptions = new UiAutomator2Options()
                .setAppPackage("io.appium.android.apis")
                .setAppActivity(".view.WebView1");

        AndroidDriver driver = new AndroidDriver(builder, clientOptions);
        try {
            Capabilities caps = driver.getCapabilities();

            assertTrue(MobilePlatform.ANDROID.equalsIgnoreCase(
                    (String) caps.getCapability(MobileCapabilityType.PLATFORM_NAME))
            );
            assertNotNull(caps.getCapability(MobileCapabilityType.DEVICE_NAME));
        } finally {
            driver.quit();
        }
    }
}
