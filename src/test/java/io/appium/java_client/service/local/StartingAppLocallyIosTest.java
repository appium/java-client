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

import io.appium.java_client.ios.BaseIOSTest;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;

import static io.appium.java_client.TestResources.uiCatalogAppZip;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StartingAppLocallyIosTest {
    @Test
    void startingIOSAppWithCapabilitiesOnlyTest() {
        XCUITestOptions options = new XCUITestOptions()
                .setPlatformVersion(BaseIOSTest.PLATFORM_VERSION)
                .setDeviceName(BaseIOSTest.DEVICE_NAME)
                .setApp(uiCatalogAppZip().toAbsolutePath().toString())
                .setWdaLaunchTimeout(BaseIOSTest.WDA_LAUNCH_TIMEOUT);
        IOSDriver driver = new IOSDriver(options);
        try {
            XCUITestOptions caps = new XCUITestOptions(driver.getCapabilities());

            assertEquals(AutomationName.IOS_XCUI_TEST, caps.getAutomationName().orElse(null));
            assertEquals(Platform.IOS, caps.getPlatformName());
            assertNotNull(caps.getDeviceName().orElse(null));
            assertEquals(BaseIOSTest.PLATFORM_VERSION, caps.getPlatformVersion().orElse(null));
            assertEquals(uiCatalogAppZip().toAbsolutePath().toString(), caps.getApp().orElse(null));
        } finally {
            driver.quit();
        }
    }


    @Test
    void startingIOSAppWithCapabilitiesAndServiceTest() {
        XCUITestOptions options = new XCUITestOptions()
                .setPlatformVersion(BaseIOSTest.PLATFORM_VERSION)
                .setDeviceName(BaseIOSTest.DEVICE_NAME)
                .setApp(uiCatalogAppZip().toAbsolutePath().toString())
                .setWdaLaunchTimeout(BaseIOSTest.WDA_LAUNCH_TIMEOUT);

        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.STRICT_CAPS);

        IOSDriver driver = new IOSDriver(builder, options);
        try {
            Capabilities caps = driver.getCapabilities();
            assertTrue(caps.getCapability(MobileCapabilityType.PLATFORM_NAME)
                    .toString().equalsIgnoreCase(MobilePlatform.IOS));
            assertNotNull(caps.getCapability(MobileCapabilityType.DEVICE_NAME));
        } finally {
            driver.quit();
        }
    }

    @Test
    void startingIOSAppWithCapabilitiesAndFlagsOnServerSideTest() {
        XCUITestOptions serverOptions = new XCUITestOptions()
                .setPlatformVersion(BaseIOSTest.PLATFORM_VERSION)
                .setDeviceName(BaseIOSTest.DEVICE_NAME)
                .setWdaLaunchTimeout(BaseIOSTest.WDA_LAUNCH_TIMEOUT);

        XCUITestOptions clientOptions = new XCUITestOptions()
                .setApp(uiCatalogAppZip().toAbsolutePath().toString());

        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.STRICT_CAPS)
                .withCapabilities(serverOptions);

        IOSDriver driver = new IOSDriver(builder, clientOptions);
        try {
            XCUITestOptions caps = new XCUITestOptions(driver.getCapabilities());
            assertEquals(Platform.IOS, caps.getPlatformName());
            assertNotNull(caps.getDeviceName().orElse(null));
            assertFalse(driver.isBrowser());
        } finally {
            driver.quit();
        }
    }
}
