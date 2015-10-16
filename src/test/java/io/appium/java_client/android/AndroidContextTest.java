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

import io.appium.java_client.NoSuchContextException;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class AndroidContextTest {

    private static AndroidDriver<?> driver;
    private static AppiumDriverLocalService service;

    /**
     * initialization.
     */
    @BeforeClass public static void beforeClass() throws Exception {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

        if (service == null || !service.isRunning()) {
            throw new RuntimeException("An appium server node is not started!");
        }

        if (service == null || !service.isRunning()) {
            throw new RuntimeException("An appium server node is not started!");
        }

        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "ApiDemos-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
        driver = new AndroidDriver<WebElement>(service.getUrl(), capabilities);
        driver.startActivity("io.appium.android.apis", ".view.WebView1");
        Thread.sleep(20000);
    }

    /**
     * finishing.
     */
    @AfterClass public static void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }

        if (service.isRunning()) {
            service.stop();
        }
    }

    @Test public void testGetContext() {
        assertEquals("NATIVE_APP", driver.getContext());
    }

    @Test public void testGetContextHandles() {
        assertEquals(driver.getContextHandles().size(), 2);
    }

    @Test public void testSwitchContext() {
        driver.getContextHandles();
        driver.context("WEBVIEW_io.appium.android.apis");
        assertEquals(driver.getContext(), "WEBVIEW_io.appium.android.apis");
        driver.context("NATIVE_APP");
    }

    @Test(expected = NoSuchContextException.class) public void testContextError() {
        driver.context("Planet of the Ape-ium");
    }

}
