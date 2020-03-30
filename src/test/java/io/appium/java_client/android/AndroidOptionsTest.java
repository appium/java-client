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
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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
        capabilities.setCapability("newCommandTimeout", Duration.ofSeconds(60));

        androidMobileOptions = new AndroidMobileOptions(capabilities);

        assertEquals("Pixel", androidMobileOptions.getDeviceName());
        assertEquals("10", androidMobileOptions.getPlatformVersion());
        assertEquals(Duration.ofSeconds(60), androidMobileOptions.getNewCommandTimeout());
    }

    @Test
    public void acceptsMobileCapabilities() throws MalformedURLException {
        ArrayList<String> paths = new ArrayList<>();
        paths.add("/path/to/app.apk");

        androidMobileOptions.setApp(new URL("http://example.com/myapp.apk"))
                .setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2)
                .setPlatformVersion("10")
                .setDeviceName("Pixel")
                .setOtherApps(paths)
                .setLocale("fr_CA")
                .setUdid("1ae203187fc012g")
                .setOrientation(ScreenOrientation.LANDSCAPE)
                .setNewCommandTimeout(Duration.ofSeconds(60))
                .setLanguage("fr");

        assertEquals("http://example.com/myapp.apk", androidMobileOptions.getApp());
        assertEquals(AutomationName.ANDROID_UIAUTOMATOR2, androidMobileOptions.getAutomationName());
        assertEquals("10", androidMobileOptions.getPlatformVersion());
        assertEquals("Pixel", androidMobileOptions.getDeviceName());
        assertEquals(paths, androidMobileOptions.getOtherApps());
        assertEquals("fr_CA", androidMobileOptions.getLocale());
        assertEquals("1ae203187fc012g", androidMobileOptions.getUdid());
        assertEquals(ScreenOrientation.LANDSCAPE, androidMobileOptions.getOrientation());
        assertEquals(Duration.ofSeconds(60), androidMobileOptions.getNewCommandTimeout());
        assertEquals("fr", androidMobileOptions.getLanguage());
    }

    @Test
    public void acceptsChromeDriverOptions() {
        androidMobileOptions = new AndroidMobileOptions();
        ChromeOptions chromeOptions = new ChromeOptions();
        androidMobileOptions.setChromeOptions(chromeOptions);

        assertEquals(chromeOptions, androidMobileOptions.getChromeOptions());
    }

    @Test
    public void setsAndroidBooleanDefaultCapabilities() {
        androidMobileOptions.setAllowTestPackages()
                .setAndroidNaturalOrientation()
                .setAutoGrantPermissions()
                .setAutoLaunch()
                .setChromedriverDisableBuildCheck()
                .setChromedriverUseSystemExecutable()
                .setDisableAndroidWatchers()
                .setDisableWindowAnimation()
                .setDontStopAppOnReset()
                .setEnforceAppInstall()
                .setEnsureWebviewsHavePages()
                .setGpsEnabled()
                .setIgnoreUnimportantViews()
                .setNativeWebScreenshot()
                .setNoSign()
                .setRecreateChromeDriverSessions()
                .setResetKeyboard()
                .setSkipDeviceInitialization()
                .setSkipLogcatCapture()
                .setSkipUnlock()
                .setUnicodeKeyboard()
                .setUseKeystore();

        assertTrue(androidMobileOptions.doesAllowTestPackages());
        assertTrue(androidMobileOptions.isAndroidNaturalOrientation());
        assertTrue(androidMobileOptions.doesAutoGrantPermissions());
        assertTrue(androidMobileOptions.doesAutoLaunch());
        assertTrue(androidMobileOptions.doesChromedriverDisableBuildCheck());
        assertTrue(androidMobileOptions.doesChromedriverUseSystemExecutable());
        assertTrue(androidMobileOptions.doesDisableAndroidWatchers());
        assertTrue(androidMobileOptions.doesDisableWindowAnimation());
        assertTrue(androidMobileOptions.isDontStopAppOnReset());
        assertTrue(androidMobileOptions.doesEnforceAppInstall());
        assertTrue(androidMobileOptions.doesEnsureWebviewsHavePages());
        assertTrue(androidMobileOptions.isGpsEnabled());
        assertTrue(androidMobileOptions.doesIgnoreUnimportantViews());
        assertTrue(androidMobileOptions.doesNativeWebScreenshot());
        assertTrue(androidMobileOptions.isNoSign());
        assertTrue(androidMobileOptions.doesRecreateChromeDriverSessions());
        assertTrue(androidMobileOptions.doesResetKeyboard());
        assertTrue(androidMobileOptions.doesSkipDeviceInitialization());
        assertTrue(androidMobileOptions.doesSkipLogcatCapture());
        assertTrue(androidMobileOptions.doesSkipUnlock());
        assertTrue(androidMobileOptions.isUnicodeKeyboard());
        assertTrue(androidMobileOptions.doesUseKeystore());
    }

    @Test
    public void setsAndroidBooleanValueCapabilities() {
        androidMobileOptions.setAllowTestPackages(false)
                .setAndroidNaturalOrientation(false)
                .setAutoGrantPermissions(false)
                .setAutoLaunch(false)
                .setChromedriverDisableBuildCheck(false)
                .setChromedriverUseSystemExecutable(false)
                .setDisableAndroidWatchers(false)
                .setDisableWindowAnimation(false)
                .setDontStopAppOnReset(false)
                .setEnforceAppInstall(false)
                .setEnsureWebviewsHavePages(false)
                .setGpsEnabled(false)
                .setIgnoreUnimportantViews(false)
                .setIsHeadless(false)
                .setNativeWebScreenshot(false)
                .setNoSign(false)
                .setRecreateChromeDriverSessions(false)
                .setResetKeyboard(false)
                .setSkipDeviceInitialization(false)
                .setSkipLogcatCapture(false)
                .setSkipUnlock(false)
                .setUnicodeKeyboard(false)
                .setUseKeystore(false);

        assertFalse(androidMobileOptions.doesAllowTestPackages());
        assertFalse(androidMobileOptions.isAndroidNaturalOrientation());
        assertFalse(androidMobileOptions.doesAutoGrantPermissions());
        assertFalse(androidMobileOptions.doesAutoLaunch());
        assertFalse(androidMobileOptions.doesChromedriverDisableBuildCheck());
        assertFalse(androidMobileOptions.doesChromedriverUseSystemExecutable());
        assertFalse(androidMobileOptions.doesDisableAndroidWatchers());
        assertFalse(androidMobileOptions.doesDisableWindowAnimation());
        assertFalse(androidMobileOptions.isDontStopAppOnReset());
        assertFalse(androidMobileOptions.doesEnforceAppInstall());
        assertFalse(androidMobileOptions.doesEnsureWebviewsHavePages());
        assertFalse(androidMobileOptions.isGpsEnabled());
        assertFalse(androidMobileOptions.doesIgnoreUnimportantViews());
        assertFalse(androidMobileOptions.isHeadless());
        assertFalse(androidMobileOptions.doesNativeWebScreenshot());
        assertFalse(androidMobileOptions.isNoSign());
        assertFalse(androidMobileOptions.doesRecreateChromeDriverSessions());
        assertFalse(androidMobileOptions.doesResetKeyboard());
        assertFalse(androidMobileOptions.doesSkipDeviceInitialization());
        assertFalse(androidMobileOptions.doesSkipLogcatCapture());
        assertFalse(androidMobileOptions.doesSkipUnlock());
        assertFalse(androidMobileOptions.isUnicodeKeyboard());
        assertFalse(androidMobileOptions.doesUseKeystore());
    }

    @Test
    public void setsAndroidNumberCapabilities() {
        List<Integer> ports = new ArrayList<>();
        ports.add(9000);
        ports.add(9005);

        androidMobileOptions.setAdbExecTimeout(Duration.ofMillis(500))
                .setAndroidDeviceReadyTimeout(Duration.ofSeconds(5))
                .setAndroidInstallTimeout(Duration.ofSeconds(300))
                .setAppWaitDuration(Duration.ofMillis(8000))
                .setAutoWebviewTimeout(Duration.ofMillis(9000))
                .setAvdLaunchTimeout(Duration.ofMillis(10000))
                .setAvdReadyTimeout(Duration.ofMillis(11000))
                .setChromedriverPort(1234)
                .setChromedriverPorts(ports)
                .setDeviceReadyTimeout(Duration.ofSeconds(33))
                .setRemoteAppsCacheLimit(4);

        assertEquals(Duration.ofMillis(500), androidMobileOptions.getAdbExecTimeout());
        assertEquals(Duration.ofSeconds(5), androidMobileOptions.getAndroidDeviceReadyTimeout());
        assertEquals(Duration.ofSeconds(300), androidMobileOptions.getAndroidInstallTimeout());
        assertEquals(Duration.ofMillis(8000), androidMobileOptions.getAppWaitDuration());
        assertEquals(Duration.ofMillis(9000), androidMobileOptions.getAutoWebviewTimeout());
        assertEquals(Duration.ofMillis(10000), androidMobileOptions.getAvdLaunchTimeout());
        assertEquals(Duration.ofMillis(11000), androidMobileOptions.getAvdReadyTimeout());
        assertEquals(1234, androidMobileOptions.getChromedriverPort());
        assertEquals(ports, androidMobileOptions.getChromedriverPorts());
        assertEquals(Duration.ofSeconds(33), androidMobileOptions.getDeviceReadyTimeout());
        assertEquals(4, androidMobileOptions.getRemoteAppsCacheLimit());
    }

    @Test
    public void setsAndroidStringCapabilities() {
        List<String> chromedriverArgs = new ArrayList<>();
        chromedriverArgs.add("--disable-gpu");
        chromedriverArgs.add("--disable-web-security");

        List<String> uninstallPackages = new ArrayList<>();
        uninstallPackages.add("io.appium.example1");
        uninstallPackages.add("io.appium.example2");

        androidMobileOptions.setAdbPort("1234")
                .setAndroidCoverage("com.my.Pkg/com.my.Pkg.instrumentation.MyInstrumentation")
                .setAndroidCoverageEndIntent("com.example.pkg.END_EMMA")
                .setAndroidDeviceSocket("chrome_devtools_remote")
                .setAndroidInstallPath("/sdcard/Downloads/")
                .setAndroidScreenshotPath("/sdcard/screenshots/")
                .setAppActivity(".MainActivity")
                .setAppPackage("com.example.android.myApp")
                .setAppWaitActivity("SplashActivity,OtherActivity")
                .setAppWaitPackage("com.example.android.myApp")
                .setAvd("api19")
                .setAvdArgs("-netfast")
                .setBuildToolsVersion("28.0.3")
                .setChromedriverArgs(chromedriverArgs)
                .setChromedriverChromeMappingFile("/abs/path/to/mapping.json")
                .setChromedriverExecutable("/abs/path/to/webdriver")
                .setChromedriverExecutableDir("/abs/path/to/chromedriver/directory")
                .setIntentAction("android.intent.action.MAIN")
                .setIntentCategory("android.intent.category.LAUNCHER")
                .setIntentFlags("0x10200000")
                .setKeyAlias("androiddebugkey")
                .setKeyPassword("foo")
                .setKeystorePassword("foo")
                .setKeystorePath("/path/to.keystore")
                .setLocaleScript("Cyrl")
                .setNetworkSpeed("hscsd")
                .setOptionalIntentArguments("--esn 2222")
                .setRemoteAdbHost("192.168.0.101")
                .setSystemPort("8201")
                .setUninstallOtherPackages(uninstallPackages)
                .setUnlockKey("1111")
                .setUnlockType("password")
                .setWebviewDevtoolsPort("9543");

        assertEquals("1234", androidMobileOptions.getAdbPort());
        assertEquals("com.my.Pkg/com.my.Pkg.instrumentation.MyInstrumentation", androidMobileOptions.getAndroidCoverage());
        assertEquals("com.example.pkg.END_EMMA", androidMobileOptions.getAndroidCoverageEndIntent());
        assertEquals("chrome_devtools_remote", androidMobileOptions.getAndroidDeviceSocket());
        assertEquals("/sdcard/Downloads/", androidMobileOptions.getAndroidInstallPath());
        assertEquals("/sdcard/screenshots/", androidMobileOptions.getAndroidScreenshotPath());
        assertEquals(".MainActivity", androidMobileOptions.getAppActivity());
        assertEquals("com.example.android.myApp", androidMobileOptions.getAppPackage());
        assertEquals("SplashActivity,OtherActivity", androidMobileOptions.getAppWaitActivity());
        assertEquals("com.example.android.myApp", androidMobileOptions.getAppWaitPackage());
        assertEquals("api19", androidMobileOptions.getAvd());
        assertEquals("-netfast", androidMobileOptions.getAvdArgs());
        assertEquals("28.0.3", androidMobileOptions.getBuildToolsVersion());
        assertEquals(chromedriverArgs, androidMobileOptions.getChromedriverArgs());
        assertEquals("/abs/path/to/mapping.json", androidMobileOptions.getChromedriverChromeMappingFile());
        assertEquals("/abs/path/to/webdriver", androidMobileOptions.getChromedriverExecutable());
        assertEquals("/abs/path/to/chromedriver/directory", androidMobileOptions.getChromedriverExecutableDir());
        assertEquals("android.intent.action.MAIN", androidMobileOptions.getIntentAction());
        assertEquals("android.intent.category.LAUNCHER", androidMobileOptions.getIntentCategory());
        assertEquals("0x10200000", androidMobileOptions.getIntentFlags());
        assertEquals("androiddebugkey", androidMobileOptions.getKeyAlias());
        assertEquals("foo", androidMobileOptions.getKeyPassword());
        assertEquals("foo", androidMobileOptions.getKeystorePassword());
        assertEquals("/path/to.keystore", androidMobileOptions.getKeystorePath());
        assertEquals("Cyrl", androidMobileOptions.getLocaleScript());
        assertEquals("hscsd", androidMobileOptions.getNetworkSpeed());
        assertEquals("--esn 2222", androidMobileOptions.getOptionalIntentArguments());
        assertEquals("192.168.0.101", androidMobileOptions.getRemoteAdbHost());
        assertEquals("8201", androidMobileOptions.getSystemPort());
        assertEquals(uninstallPackages, androidMobileOptions.getUninstallOtherPackages());
        assertEquals("1111", androidMobileOptions.getUnlockKey());
        assertEquals("password", androidMobileOptions.getUnlockType());
        assertEquals("9543", androidMobileOptions.getWebviewDevtoolsPort());
    }
}
