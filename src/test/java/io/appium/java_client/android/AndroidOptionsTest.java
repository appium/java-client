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
import org.openqa.selenium.Platform;
import org.openqa.selenium.ScreenOrientation;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class AndroidOptionsTest {

    @Test
    public void setsPlatformNameByDefault() {
        assertEquals(Platform.ANDROID, new AndroidOptions().getPlatformName());
    }

    @Test
    public void acceptsExistingCapabilities() {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("deviceName", "Pixel");
        capabilities.setCapability("platformVersion", "10");
        capabilities.setCapability("newCommandTimeout", 60);

        AndroidOptions androidOptions = new AndroidOptions(capabilities);

        assertEquals("Pixel", androidOptions.getDeviceName());
        assertEquals("10", androidOptions.getPlatformVersion());
        assertEquals(Duration.ofSeconds(60), androidOptions.getNewCommandTimeout());
    }

    @Test
    public void acceptsMobileCapabilities() throws MalformedURLException {
        AndroidOptions androidOptions = new AndroidOptions();
        androidOptions.setApp(new URL("http://example.com/myapp.apk"))
                .setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2)
                .setPlatformVersion("10")
                .setDeviceName("Pixel")
                .setOtherApps("/path/to/app.apk")
                .setLocale("fr_CA")
                .setUdid("1ae203187fc012g")
                .setOrientation(ScreenOrientation.LANDSCAPE)
                .setNewCommandTimeout(Duration.ofSeconds(60))
                .setLanguage("fr");

        assertEquals("http://example.com/myapp.apk", androidOptions.getApp());
        assertEquals(AutomationName.ANDROID_UIAUTOMATOR2, androidOptions.getAutomationName());
        assertEquals("10", androidOptions.getPlatformVersion());
        assertEquals("Pixel", androidOptions.getDeviceName());
        assertEquals("/path/to/app.apk", androidOptions.getOtherApps());
        assertEquals("fr_CA", androidOptions.getLocale());
        assertEquals("1ae203187fc012g", androidOptions.getUdid());
        assertEquals(ScreenOrientation.LANDSCAPE, androidOptions.getOrientation());
        assertEquals(Duration.ofSeconds(60), androidOptions.getNewCommandTimeout());
        assertEquals("fr", androidOptions.getLanguage());
    }
}
