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

import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobilePlatform;
import org.junit.Test;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.ScreenOrientation;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class IOSOptionsTest {
    private IOSMobileOptions iosMobileOptions = new IOSMobileOptions();

    @Test
    public void setsPlatformNameByDefault() {
        assertEquals(MobilePlatform.IOS, iosMobileOptions.getPlatformName());
    }

    @Test
    public void acceptsExistingCapabilities() {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("deviceName", "Pixel");
        capabilities.setCapability("platformVersion", "10");
        capabilities.setCapability("newCommandTimeout", 60);

        iosMobileOptions = new IOSMobileOptions(capabilities);

        assertEquals("Pixel", iosMobileOptions.getDeviceName());
        assertEquals("10", iosMobileOptions.getPlatformVersion());
        assertEquals(60, iosMobileOptions.getNewCommandTimeout());
    }

    @Test
    public void acceptsMobileCapabilities() throws MalformedURLException {
        iosMobileOptions.setApp(new URL("http://example.com/myapp.apk"))
                .setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2)
                .setPlatformVersion("10")
                .setDeviceName("Pixel")
                .setOtherApps("/path/to/app.apk")
                .setLocale("fr_CA")
                .setUdid("1ae203187fc012g")
                .setOrientation(ScreenOrientation.LANDSCAPE)
                .setNewCommandTimeout(60)
                .setLanguage("fr");

        assertEquals("http://example.com/myapp.apk", iosMobileOptions.getApp());
        assertEquals(AutomationName.ANDROID_UIAUTOMATOR2, iosMobileOptions.getAutomationName());
        assertEquals("10", iosMobileOptions.getPlatformVersion());
        assertEquals("Pixel", iosMobileOptions.getDeviceName());
        assertEquals("/path/to/app.apk", iosMobileOptions.getOtherApps());
        assertEquals("fr_CA", iosMobileOptions.getLocale());
        assertEquals("1ae203187fc012g", iosMobileOptions.getUdid());
        assertEquals(ScreenOrientation.LANDSCAPE, iosMobileOptions.getOrientation());
        assertEquals(60, iosMobileOptions.getNewCommandTimeout());
        assertEquals("fr", iosMobileOptions.getLanguage());
    }

    @Test
    public void acceptsMobileBooleanCapabilityDefaults() {
        iosMobileOptions.setClearSystemFiles()
                .setAutoWebview()
                .setEnablePerformanceLogging()
                .setEventTimings()
                .setAutoWebview()
                .setFullReset()
                .setPrintPageSourceOnFindFailure();

        assertTrue(iosMobileOptions.isClearSystemFiles());
        assertTrue(iosMobileOptions.isAutoWebview());
        assertTrue(iosMobileOptions.isEnablePerformanceLogging());
        assertTrue(iosMobileOptions.isEventTimings());
        assertTrue(iosMobileOptions.isAutoWebview());
        assertTrue(iosMobileOptions.isFullReset());
        assertTrue(iosMobileOptions.isPrintPageSourceOnFindFailure());
    }

    @Test
    public void setsMobileBooleanCapabilities() {
        iosMobileOptions.setClearSystemFiles(false)
                .setAutoWebview(false)
                .setEnablePerformanceLogging(false)
                .setEventTimings(false)
                .setAutoWebview(false)
                .setFullReset(false)
                .setPrintPageSourceOnFindFailure(false);

        assertFalse(iosMobileOptions.isClearSystemFiles());
        assertFalse(iosMobileOptions.isAutoWebview());
        assertFalse(iosMobileOptions.isEnablePerformanceLogging());
        assertFalse(iosMobileOptions.isEventTimings());
        assertFalse(iosMobileOptions.isAutoWebview());
        assertFalse(iosMobileOptions.isFullReset());
        assertFalse(iosMobileOptions.isPrintPageSourceOnFindFailure());
    }
}
