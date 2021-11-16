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

package io.appium.java_client.drivers.options;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.options.EspressoOptions;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.android.options.localization.AppLocale;
import io.appium.java_client.android.options.server.EspressoBuildConfig;
import io.appium.java_client.android.options.signing.KeystoreConfig;
import io.appium.java_client.gecko.options.GeckoOptions;
import io.appium.java_client.gecko.options.Verbosity;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.ios.options.other.CommandTimeouts;
import io.appium.java_client.ios.options.simulator.Permissions;
import io.appium.java_client.mac.options.AppleScriptData;
import io.appium.java_client.mac.options.Mac2Options;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.safari.options.SafariOptions;
import io.appium.java_client.safari.options.WebrtcData;
import io.appium.java_client.screenrecording.ScreenRecordingUploadOptions;
import io.appium.java_client.windows.options.PowerShellData;
import io.appium.java_client.windows.options.WindowsOptions;
import org.junit.Test;
import org.openqa.selenium.Platform;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("ConstantConditions")
public class OptionsBuildingTest {
    @Test
    public void canBuildXcuiTestOptions() throws MalformedURLException {
        XCUITestOptions options = new XCUITestOptions();
        assertEquals(Platform.IOS, options.getPlatformName());
        assertEquals(AutomationName.IOS_XCUI_TEST, options.getAutomationName().orElse(null));
        options.setNewCommandTimeout(Duration.ofSeconds(10))
                .noReset()
                .setWdaBaseUrl("http://localhost:8000")
                .setPermissions(new Permissions()
                        .withAppPermissions("com.apple.MobileSafari",
                                ImmutableMap.of("calendar", "YES")))
                .setSafariSocketChunkSize(10)
                .setCommandTimeouts(new CommandTimeouts()
                        .withCommandTimeout("yolo", Duration.ofSeconds(1)));
        assertEquals(Duration.ofSeconds(10), options.getNewCommandTimeout().orElse(null));
        assertEquals(new URL("http://localhost:8000"), options.getWdaBaseUrl().orElse(null));
        assertNotNull(options.getPermissions()
                .map((v) -> v.getAppPermissions("com.apple.MobileSafari"))
                .orElse(null));
        assertEquals(10L, (long) options.getSafariSocketChunkSize().orElse(0));
        assertEquals(1L, options.getCommandTimeouts().orElse(null).left()
                .getCommandTimeout("yolo").orElse(null).getSeconds());
    }

    @Test
    public void canBuildUiAutomator2Options() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        assertEquals(Platform.ANDROID, options.getPlatformName());
        assertEquals(AutomationName.ANDROID_UIAUTOMATOR2, options.getAutomationName().orElse(null));
        options.setNewCommandTimeout(Duration.ofSeconds(10))
                .noReset()
                .setAdbExecTimeout(Duration.ofSeconds(3))
                .suppressKillServer()
                .setMjpegScreenshotUrl(new URL("http://yolo.com"))
                .setKeystoreConfig(new KeystoreConfig("path", "password", "keyAlias", "keyPassword"));
        assertEquals(Duration.ofSeconds(10), options.getNewCommandTimeout().orElse(null));
        assertEquals(Duration.ofSeconds(3), options.getAdbExecTimeout().orElse(null));
        assertEquals(new URL("http://yolo.com"), options.getMjpegScreenshotUrl().orElse(null));
        assertEquals("path", options.getKeystoreConfig().orElse(null).getPath());
        assertEquals("keyAlias", options.getKeystoreConfig().orElse(null).getKeyAlias());
        assertTrue(options.doesSuppressKillServer().orElse(false));
    }

    @Test
    public void canBuildEspressoOptions() {
        EspressoOptions options = new EspressoOptions();
        assertEquals(Platform.ANDROID, options.getPlatformName());
        assertEquals(AutomationName.ESPRESSO, options.getAutomationName().orElse(null));
        options.setNewCommandTimeout(Duration.ofSeconds(10))
                .forceEspressoRebuild()
                .setAppLocale(new AppLocale()
                        .withCountry("CN")
                        .withLanguage("zh")
                        .withVariant("hans"))
                .setEspressoBuildConfig(new EspressoBuildConfig()
                        .withAdditionalAppDependencies(ImmutableList.of(
                                "com.dep1:1.2.3",
                                "com.dep2:1.2.3"
                        ))
                );
        assertEquals(Duration.ofSeconds(10), options.getNewCommandTimeout().orElse(null));
        assertEquals("CN", options.getAppLocale().orElse(null).getCountry().orElse(null));
        assertEquals(2, options.getEspressoBuildConfig().orElse(null)
                        .left().getAdditionalAppDependencies().orElse(null).size());
        assertTrue(options.doesForceEspressoRebuild().orElse(false));
    }

    @Test
    public void canBuildWindowsOptions() {
        WindowsOptions options = new WindowsOptions();
        assertEquals(Platform.WINDOWS, options.getPlatformName());
        assertEquals(AutomationName.WINDOWS, options.getAutomationName().orElse(null));
        options.setNewCommandTimeout(Duration.ofSeconds(10))
                .setPrerun(new PowerShellData().withScript("yolo prescript"))
                .setPostrun(new PowerShellData().withCommand("yolo command"));
        assertEquals(Duration.ofSeconds(10), options.getNewCommandTimeout().orElse(null));
        assertEquals("yolo prescript", options.getPrerun().orElse(null).getScript().orElse(null));
        assertEquals("yolo command", options.getPostrun().orElse(null).getCommand().orElse(null));
    }

    @Test
    public void canBuildMac2Options() {
        Mac2Options options = new Mac2Options();
        assertEquals(Platform.MAC, options.getPlatformName());
        assertEquals(AutomationName.MAC2, options.getAutomationName().orElse(null));
        options.setNewCommandTimeout(Duration.ofSeconds(10))
                .skipAppKill()
                .setPrerun(new AppleScriptData().withScript("yolo prescript"))
                .setPostrun(new AppleScriptData().withCommand("yolo command"));
        assertEquals(Duration.ofSeconds(10), options.getNewCommandTimeout().orElse(null));
        assertEquals("yolo prescript", options.getPrerun().orElse(null).getScript().orElse(null));
        assertEquals("yolo command", options.getPostrun().orElse(null).getCommand().orElse(null));
        assertTrue(options.doesSkipAppKill().orElse(false));
        assertFalse(options.doesEventTimings().isPresent());
    }

    @Test
    public void canBuildGeckoOptions() {
        GeckoOptions options = new GeckoOptions();
        options.setPlatformName(Platform.MAC.toString());
        assertEquals(Platform.MAC, options.getPlatformName());
        assertEquals(AutomationName.GECKO, options.getAutomationName().orElse(null));
        options.setNewCommandTimeout(Duration.ofSeconds(10))
                .setVerbosity(Verbosity.TRACE)
                .setMozFirefoxOptions(ImmutableMap.of(
                    "profile", "yolo"
                ));
        assertEquals(Duration.ofSeconds(10), options.getNewCommandTimeout().orElse(null));
        assertEquals(Verbosity.TRACE, options.getVerbosity().orElse(null));
        assertEquals("yolo", options.getMozFirefoxOptions().orElse(null)
                .get("profile"));
    }

    @Test
    public void canBuildSafariOptions() {
        SafariOptions options = new SafariOptions();
        assertEquals(Platform.IOS, options.getPlatformName());
        assertEquals(AutomationName.SAFARI, options.getAutomationName().orElse(null));
        options.setNewCommandTimeout(Duration.ofSeconds(10))
                .safariUseSimulator()
                .setWebkitWebrtc(new WebrtcData()
                        .withDisableIceCandidateFiltering(true)
                        .withDisableInsecureMediaCapture(true)
                );
        assertEquals(Duration.ofSeconds(10), options.getNewCommandTimeout().orElse(null));
        assertTrue(options.doesSafariUseSimulator().orElse(false));
        assertTrue(options.getWebkitWebrtc().orElse(null)
                .doesDisableIceCandidateFiltering().orElse(false));
        assertTrue(options.getWebkitWebrtc().orElse(null)
                .doesDisableInsecureMediaCapture().orElse(false));
    }

    @Test
    public void canBuildScreenRecordingUploadOptions() {
        ScreenRecordingUploadOptions options = ScreenRecordingUploadOptions.uploadOptions();
        assertEquals("file", options.build().get("fileFieldName"));
        Map<String, Object> optionsMap = options
                .withRemotePath("http://yolo.com")
                .withHttpMethod(ScreenRecordingUploadOptions.RequestMethod.POST)
                .withFileFieldName("file123")
                .withAuthCredentials("kim", "password")
                .build();
        assertEquals("http://yolo.com", optionsMap.get("remotePath"));
        assertEquals("POST", optionsMap.get("method"));
        assertEquals("file123", optionsMap.get("fileFieldName"));
        assertEquals("kim", optionsMap.get("user"));
        assertEquals("password", optionsMap.get("pass"));
    }
}
