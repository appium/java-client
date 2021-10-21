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
import org.openqa.selenium.Platform;
import org.openqa.selenium.ScreenOrientation;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class IOSOptionsTest {
    private IOSOptions iosOptions = new IOSOptions();

    @Test
    public void setsPlatformNameByDefault() {
        assertEquals(Platform.IOS, iosOptions.getPlatformName());
    }

    @Test
    public void acceptsExistingCapabilities() {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("deviceName", "Pixel");
        capabilities.setCapability("platformVersion", "10");
        capabilities.setCapability("newCommandTimeout", 60);

        iosOptions = new IOSOptions(capabilities);

        assertEquals("Pixel", iosOptions.getDeviceName());
        assertEquals("10", iosOptions.getPlatformVersion());
        assertEquals(Duration.ofSeconds(60), iosOptions.getNewCommandTimeout());
    }

    @Test
    public void acceptsMobileCapabilities() throws MalformedURLException {
        iosOptions.setApp(new URL("http://example.com/myapp.apk"))
                .setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2)
                .setPlatformVersion("10")
                .setDeviceName("Pixel")
                .setOtherApps("/path/to/app.apk")
                .setLocale("fr_CA")
                .setUdid("1ae203187fc012g")
                .setOrientation(ScreenOrientation.LANDSCAPE)
                .setNewCommandTimeout(Duration.ofSeconds(60))
                .setLanguage("fr");

        assertEquals("http://example.com/myapp.apk", iosOptions.getApp());
        assertEquals(AutomationName.ANDROID_UIAUTOMATOR2, iosOptions.getAutomationName());
        assertEquals("10", iosOptions.getPlatformVersion());
        assertEquals("Pixel", iosOptions.getDeviceName());
        assertEquals("/path/to/app.apk", iosOptions.getOtherApps());
        assertEquals("fr_CA", iosOptions.getLocale());
        assertEquals("1ae203187fc012g", iosOptions.getUdid());
        assertEquals(ScreenOrientation.LANDSCAPE, iosOptions.getOrientation());
        assertEquals(Duration.ofSeconds(60), iosOptions.getNewCommandTimeout());
        assertEquals("fr", iosOptions.getLanguage());
    }
}
