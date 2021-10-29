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
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.ios.options.other.CommandTimeouts;
import io.appium.java_client.ios.options.simulator.Permissions;
import io.appium.java_client.mac.options.Mac2Options;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.windows.options.RunScript;
import io.appium.java_client.windows.options.WindowsOptions;
import org.junit.Test;
import org.openqa.selenium.Platform;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("ConstantConditions")
public class OptionsBuildingTest {
    @Test
    public void canBuildXcuiTestOptions() throws MalformedURLException {
        XCUITestOptions options = new XCUITestOptions();
        assertEquals(options.getPlatformName(), Platform.IOS);
        assertEquals(options.getAutomationName().orElse(null), AutomationName.IOS_XCUI_TEST);
        options.setNewCommandTimeout(Duration.ofSeconds(10))
                .noReset()
                .setWdaBaseUrl("http://localhost:8000")
                .setPermissions(new Permissions()
                        .withAppPermissions("com.apple.MobileSafari",
                                ImmutableMap.of("calendar", "YES")))
                .setSafariSocketChunkSize(10)
                .setCommandTimeouts(new CommandTimeouts()
                        .withCommandTimeout("yolo", Duration.ofSeconds(1)));
        assertEquals(options.getNewCommandTimeout().orElse(null), Duration.ofSeconds(10));
        assertEquals(options.getWdaBaseUrl().orElse(null), new URL("http://localhost:8000"));
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
        assertEquals(options.getPlatformName(), Platform.ANDROID);
        assertEquals(options.getAutomationName().orElse(null), AutomationName.ANDROID_UIAUTOMATOR2);
        options.setNewCommandTimeout(Duration.ofSeconds(10))
                .noReset()
                .setAdbExecTimeout(Duration.ofSeconds(3))
                .suppressKillServer()
                .setMjpegScreenshotUrl(new URL("http://yolo.com"))
                .setKeystoreConfig(new KeystoreConfig("path", "password", "keyAlias", "keyPassword"));
        assertEquals(options.getNewCommandTimeout().orElse(null), Duration.ofSeconds(10));
        assertEquals(options.getAdbExecTimeout().orElse(null), Duration.ofSeconds(3));
        assertEquals(options.getMjpegScreenshotUrl().orElse(null), new URL("http://yolo.com"));
        assertEquals(options.getKeystoreConfig().orElse(null).getPath(), "path");
        assertEquals(options.getKeystoreConfig().orElse(null).getKeyAlias(), "keyAlias");
        assertTrue(options.doesSuppressKillServer().orElse(false));
    }

    @Test
    public void canBuildEspressoOptions() {
        EspressoOptions options = new EspressoOptions();
        assertEquals(options.getPlatformName(), Platform.ANDROID);
        assertEquals(options.getAutomationName().orElse(null), AutomationName.ESPRESSO);
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
        assertEquals(options.getNewCommandTimeout().orElse(null), Duration.ofSeconds(10));
        assertEquals(options.getAppLocale().orElse(null).getCountry().orElse(null), "CN");
        assertEquals(options.getEspressoBuildConfig().orElse(null)
                        .left().getAdditionalAppDependencies().orElse(null).size(), 2);
        assertTrue(options.doesForceEspressoRebuild().orElse(false));
    }

    @Test
    public void canBuildWindowsOptions() {
        WindowsOptions options = new WindowsOptions();
        assertEquals(options.getPlatformName(), Platform.WINDOWS);
        assertEquals(options.getAutomationName().orElse(null), AutomationName.WINDOWS);
        options.setNewCommandTimeout(Duration.ofSeconds(10))
                .setPrerun(new RunScript()
                        .withScript("yolo prescript"))
                .setPostrun(new RunScript()
                        .withCommand("yolo command"));
        assertEquals(options.getNewCommandTimeout().orElse(null), Duration.ofSeconds(10));
        assertEquals(options.getPrerun().orElse(null).getScript().orElse(null), "yolo prescript");
        assertEquals(options.getPostrun().orElse(null).getCommand().orElse(null), "yolo command");
    }

    @Test
    public void canBuildMac2Options() {
        Mac2Options options = new Mac2Options();
        assertEquals(options.getPlatformName(), Platform.MAC);
        assertEquals(options.getAutomationName().orElse(null), AutomationName.MAC2);
        options.setNewCommandTimeout(Duration.ofSeconds(10))
                .skipAppKill()
                .setPrerun(new io.appium.java_client.mac.options.RunScript()
                        .withScript("yolo prescript"))
                .setPostrun(new io.appium.java_client.mac.options.RunScript()
                        .withCommand("yolo command"));
        assertEquals(options.getNewCommandTimeout().orElse(null), Duration.ofSeconds(10));
        assertEquals(options.getPrerun().orElse(null).getScript().orElse(null), "yolo prescript");
        assertEquals(options.getPostrun().orElse(null).getCommand().orElse(null), "yolo command");
        assertTrue(options.doesSkipAppKill().orElse(false));
        assertFalse(options.doesEventTimings().isPresent());
    }
}
