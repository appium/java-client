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

import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobilePlatform;
import org.junit.Test;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.ScreenOrientation;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class AndroidOptionsTest {
    private AndroidMobileOptions androidMobileOptions = new AndroidMobileOptions();

    @Test
    public void setsPlatformNameByDefault() {
        assertEquals(MobilePlatform.ANDROID, androidMobileOptions.getPlatformName());
    }

    @Test
    public void acceptsExistingCapabilities() {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("deviceName", "Pixel");
        capabilities.setCapability("platformVersion", "10");
        capabilities.setCapability("newCommandTimeout", 60);

        androidMobileOptions = new AndroidMobileOptions(capabilities);

        assertEquals("Pixel", androidMobileOptions.getDeviceName());
        assertEquals("10", androidMobileOptions.getPlatformVersion());
        assertEquals(60, androidMobileOptions.getNewCommandTimeout());
    }

    @Test
    public void acceptsMobileCapabilities() throws MalformedURLException {
        androidMobileOptions.setApp(new URL("http://example.com/myapp.apk"))
                .setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2)
                .setPlatformVersion("10")
                .setDeviceName("Pixel")
                .setOtherApps("/path/to/app.apk")
                .setLocale("fr_CA")
                .setUdid("1ae203187fc012g")
                .setOrientation(ScreenOrientation.LANDSCAPE)
                .setNewCommandTimeout(60)
                .setLanguage("fr");

        assertEquals("http://example.com/myapp.apk", androidMobileOptions.getApp());
        assertEquals(AutomationName.ANDROID_UIAUTOMATOR2, androidMobileOptions.getAutomationName());
        assertEquals("10", androidMobileOptions.getPlatformVersion());
        assertEquals("Pixel", androidMobileOptions.getDeviceName());
        assertEquals("/path/to/app.apk", androidMobileOptions.getOtherApps());
        assertEquals("fr_CA", androidMobileOptions.getLocale());
        assertEquals("1ae203187fc012g", androidMobileOptions.getUdid());
        assertEquals(ScreenOrientation.LANDSCAPE, androidMobileOptions.getOrientation());
        assertEquals(60, androidMobileOptions.getNewCommandTimeout());
        assertEquals("fr", androidMobileOptions.getLanguage());
    }

    @Test
    public void acceptsMobileBooleanCapabilityDefaults() {
        androidMobileOptions.setClearSystemFiles()
                .setAutoWebview()
                .setEnablePerformanceLogging()
                .setEventTimings()
                .setAutoWebview()
                .setFullReset()
                .setPrintPageSourceOnFindFailure();

        assertTrue(androidMobileOptions.isClearSystemFiles());
        assertTrue(androidMobileOptions.isAutoWebview());
        assertTrue(androidMobileOptions.isEnablePerformanceLogging());
        assertTrue(androidMobileOptions.isEventTimings());
        assertTrue(androidMobileOptions.isAutoWebview());
        assertTrue(androidMobileOptions.isFullReset());
        assertTrue(androidMobileOptions.isPrintPageSourceOnFindFailure());
    }

    @Test
    public void setsMobileBooleanCapabilities() {
        androidMobileOptions.setClearSystemFiles(false)
                .setAutoWebview(false)
                .setEnablePerformanceLogging(false)
                .setEventTimings(false)
                .setAutoWebview(false)
                .setFullReset(false)
                .setPrintPageSourceOnFindFailure(false);

        assertFalse(androidMobileOptions.isClearSystemFiles());
        assertFalse(androidMobileOptions.isAutoWebview());
        assertFalse(androidMobileOptions.isEnablePerformanceLogging());
        assertFalse(androidMobileOptions.isEventTimings());
        assertFalse(androidMobileOptions.isAutoWebview());
        assertFalse(androidMobileOptions.isFullReset());
        assertFalse(androidMobileOptions.isPrintPageSourceOnFindFailure());
    }
}
