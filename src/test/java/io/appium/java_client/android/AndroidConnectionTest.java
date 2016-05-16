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

package io.appium.java_client.android;

import static org.junit.Assert.assertEquals;

import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class AndroidConnectionTest {

    private static AppiumDriverLocalService service;
    private static AndroidDriver<MobileElement> driver;

    /**
     * initialization.
     */
    @BeforeClass public static void beforeClass() throws Exception {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

        if (service == null || !service.isRunning()) {
            throw new RuntimeException("An appium server node is not started!");
        }

        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "ApiDemos-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        driver = new AndroidDriver<>(service.getUrl(), capabilities);
    }

    /**
     * finishing.
     */
    @AfterClass public static void afterClass() {
        if (driver != null) {
            try {
                driver.setConnection(Connection.DATA);
            } finally {
                driver.quit();
            }
        }
        if (service != null) {
            service.stop();
        }
    }

    @Test public void setWiFi() {
        driver.setConnection(Connection.WIFI);
        assertEquals(Connection.WIFI,
            driver.getConnection());
    }

    @Test public void setNoneAndAirplane() {
        driver.setConnection(Connection.NONE);
        assertEquals(Connection.NONE,
            driver.getConnection());
        driver.setConnection(Connection.AIRPLANE);
        assertEquals(Connection.AIRPLANE,
            driver.getConnection());
    }

    @Test public void setAll() {
        driver.setConnection(Connection.ALL);
        assertEquals(Connection.ALL,
            driver.getConnection());
    }
}
