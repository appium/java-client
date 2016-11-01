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

package io.appium.java_client.ios;

import static org.junit.Assert.assertEquals;

import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class XCUIAutomationTest {

    protected static IOSDriver<MobileElement> driver;
    private static AppiumDriverLocalService service;

    /**
     * initialization.
     */
    @BeforeClass public static void beforeClass() throws Exception {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

        if (service == null || !service.isRunning()) {
            throw new AppiumServerHasNotBeenStartedLocallyException(
                "An appium server node is not started!");
        }

        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "TestApp.app.zip");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.0");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6");
        //sometimes environment has performance problems
        capabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);
        capabilities
            .setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        driver = new IOSDriver<>(service.getUrl(), capabilities);
    }

    /**
     * finishing.
     */
    @AfterClass public static void afterClass() {
        if (driver != null) {
            driver.quit();
        }
        if (service != null) {
            service.stop();
        }
    }

    @After public void afterMethod() {
        driver.rotate(new DeviceRotation(0, 0, 0));
    }

    @Test public void testLandscapeRightRotation() {
        DeviceRotation landscapeRightRotation = new DeviceRotation(0, 0, 90);
        driver.rotate(landscapeRightRotation);
        assertEquals(driver.rotation(), landscapeRightRotation);
    }

    @Test public void testLandscapeLeftRotation() {
        DeviceRotation landscapeLeftRotation = new DeviceRotation(0, 0, 270);
        driver.rotate(landscapeLeftRotation);
        assertEquals(driver.rotation(), landscapeLeftRotation);
    }
}
