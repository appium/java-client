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

package io.appium.java_client.remote;

import org.junit.Test;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.ScreenOrientation;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MobileOptionsTest {
    private MobileOptions mobileOptions = new MobileOptions<>();

    @Test
    public void acceptsExistingCapabilities() {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("deviceName", "Pixel");
        capabilities.setCapability("platformVersion", "10");
        capabilities.setCapability("newCommandTimeout", 60);

        mobileOptions = new MobileOptions<>(capabilities);

        assertEquals("Pixel", mobileOptions.getDeviceName());
        assertEquals("10", mobileOptions.getPlatformVersion());
        assertEquals(Duration.ofSeconds(60), mobileOptions.getNewCommandTimeout());
    }

    @Test
    public void acceptsMobileCapabilities() throws MalformedURLException {
        ArrayList<String> paths = new ArrayList<>();
        paths.add("/path/to/app.apk");

        mobileOptions.setApp(new URL("http://example.com/myapp.apk"))
                .setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2)
                .setPlatformVersion("10")
                .setDeviceName("Pixel")
                .setOtherApps(paths)
                .setLocale("fr_CA")
                .setUdid("1ae203187fc012g")
                .setOrientation(ScreenOrientation.LANDSCAPE)
                .setNewCommandTimeout(Duration.ofSeconds(60))
                .setLanguage("fr");

        assertEquals("http://example.com/myapp.apk", mobileOptions.getApp());
        assertEquals(AutomationName.ANDROID_UIAUTOMATOR2, mobileOptions.getAutomationName());
        assertEquals("10", mobileOptions.getPlatformVersion());
        assertEquals("Pixel", mobileOptions.getDeviceName());
        assertEquals(paths, mobileOptions.getOtherApps());
        assertEquals("fr_CA", mobileOptions.getLocale());
        assertEquals("1ae203187fc012g", mobileOptions.getUdid());
        assertEquals(ScreenOrientation.LANDSCAPE, mobileOptions.getOrientation());
        assertEquals(Duration.ofSeconds(60), mobileOptions.getNewCommandTimeout());
        assertEquals("fr", mobileOptions.getLanguage());
    }

    @Test
    public void acceptsMobileBooleanCapabilityDefaults() {
        mobileOptions.setClearSystemFiles()
                .setAutoWebview()
                .setEnablePerformanceLogging()
                .setEventTimings()
                .setAutoWebview()
                .setFullReset()
                .setPrintPageSourceOnFindFailure();

        assertTrue(mobileOptions.doesClearSystemFiles());
        assertTrue(mobileOptions.doesAutoWebview());
        assertTrue(mobileOptions.isEnablePerformanceLogging());
        assertTrue(mobileOptions.doesEventTimings());
        assertTrue(mobileOptions.doesAutoWebview());
        assertTrue(mobileOptions.doesFullReset());
        assertTrue(mobileOptions.doesPrintPageSourceOnFindFailure());
    }

    @Test
    public void setsMobileBooleanCapabilities() {
        mobileOptions.setClearSystemFiles(false)
                .setAutoWebview(false)
                .setEnablePerformanceLogging(false)
                .setEventTimings(false)
                .setAutoWebview(false)
                .setFullReset(false)
                .setPrintPageSourceOnFindFailure(false);

        assertFalse(mobileOptions.doesClearSystemFiles());
        assertFalse(mobileOptions.doesAutoWebview());
        assertFalse(mobileOptions.isEnablePerformanceLogging());
        assertFalse(mobileOptions.doesEventTimings());
        assertFalse(mobileOptions.doesAutoWebview());
        assertFalse(mobileOptions.doesFullReset());
        assertFalse(mobileOptions.doesPrintPageSourceOnFindFailure());
    }
}
