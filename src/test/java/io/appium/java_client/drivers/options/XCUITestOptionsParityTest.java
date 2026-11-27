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

import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.ios.options.app.SupportsAppTimeZoneOption;
import io.appium.java_client.ios.options.app.SupportsInitialDeeplinkUrlOption;
import io.appium.java_client.ios.options.other.SupportsIosSyslogFileOption;
import io.appium.java_client.ios.options.simulator.SupportsAutoFillPasswordsOption;
import io.appium.java_client.ios.options.simulator.SupportsReduceTransparencyOption;
import io.appium.java_client.ios.options.simulator.SupportsSimulatorLogLevelOption;
import io.appium.java_client.ios.options.simulator.SupportsSkipSyncUiDialogTranslationOption;
import io.appium.java_client.ios.options.wda.SupportsAppLaunchStateTimeoutSecOption;
import io.appium.java_client.ios.options.wda.SupportsIosInstallPauseOption;
import io.appium.java_client.ios.options.wda.SupportsMjpegScreenshotUrlOption;
import io.appium.java_client.ios.options.wda.SupportsPrebuildWdaOption;
import io.appium.java_client.ios.options.wda.SupportsUpdatedWdaBundleIdSuffixOption;
import io.appium.java_client.ios.options.wda.SupportsWdaBindingIpOption;
import io.appium.java_client.ios.options.wda.SupportsWdaRemotePortOption;
import io.appium.java_client.ios.options.webview.SupportsIgnoredWebviewBundleIdsOption;
import io.appium.java_client.ios.options.webview.SupportsWebviewAtomWaitTimeoutOption;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.ImmutableCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class XCUITestOptionsParityTest {

    @Test
    void xcuiTestNewOptionsDefaultToEmpty() {
        var options = new XCUITestOptions();
        assertEquals(Optional.empty(), options.getAppTimeZone());
        assertEquals(Optional.empty(), options.getInitialDeeplinkUrl());
        assertEquals(Optional.empty(), options.getAppLaunchStateTimeoutSec());
        assertEquals(Optional.empty(), options.getIosInstallPause());
        assertEquals(Optional.empty(), options.getWdaRemotePort());
        assertEquals(Optional.empty(), options.getWdaBindingIp());
        assertEquals(Optional.empty(), options.getUpdatedWdaBundleIdSuffix());
        assertEquals(Optional.empty(), options.doesPrebuildWda());
        assertEquals(Optional.empty(), options.getMjpegScreenshotUrl());
        assertEquals(Optional.empty(), options.doesReduceTransparency());
        assertEquals(Optional.empty(), options.doesAutoFillPasswords());
        assertEquals(Optional.empty(), options.getSimulatorLogLevel());
        assertEquals(Optional.empty(), options.doesSkipSyncUiDialogTranslation());
        assertEquals(Optional.empty(), options.getIgnoredWebviewBundleIds());
        assertEquals(Optional.empty(), options.getWebviewAtomWaitTimeout());
        assertEquals(Optional.empty(), options.getIosSyslogFile());
    }

    @Test
    void xcuiTestChainedMethodsPopulateCapabilities() throws MalformedURLException {
        var mjpegUrl = new URL("http://127.0.0.1:9100");
        var options = new XCUITestOptions()
                .setAppTimeZone("America/Los_Angeles")
                .setInitialDeeplinkUrl("myapp://home")
                .setAppLaunchStateTimeoutSec(Duration.ofMillis(10500))
                .setIosInstallPause(Duration.ofSeconds(8))
                .setWdaRemotePort(8101)
                .setWdaBindingIp("127.0.0.1")
                .setUpdatedWdaBundleIdSuffix("")
                .prebuildWda()
                .setMjpegScreenshotUrl(mjpegUrl)
                .reduceTransparency()
                .setAutoFillPasswords(false)
                .setSimulatorLogLevel("debug")
                .skipSyncUiDialogTranslation()
                .setIgnoredWebviewBundleIds(List.of("com.apple.amsengagementd"))
                .setWebviewAtomWaitTimeout(Duration.ofSeconds(20))
                .setIosSyslogFile("/tmp/ios-syslog.log");

        assertEquals(Optional.of("America/Los_Angeles"), options.getAppTimeZone());
        assertEquals(Optional.of("myapp://home"), options.getInitialDeeplinkUrl());
        assertEquals(Optional.of(Duration.ofMillis(10500)), options.getAppLaunchStateTimeoutSec());
        assertEquals(Optional.of(Duration.ofSeconds(8)), options.getIosInstallPause());
        assertEquals(Optional.of(8101), options.getWdaRemotePort());
        assertEquals(Optional.of("127.0.0.1"), options.getWdaBindingIp());
        assertEquals(Optional.of(""), options.getUpdatedWdaBundleIdSuffix());
        assertEquals(Optional.of(true), options.doesPrebuildWda());
        assertEquals(Optional.of(mjpegUrl), options.getMjpegScreenshotUrl());
        assertEquals(Optional.of(true), options.doesReduceTransparency());
        assertEquals(Optional.of(false), options.doesAutoFillPasswords());
        assertEquals(Optional.of("debug"), options.getSimulatorLogLevel());
        assertEquals(Optional.of(true), options.doesSkipSyncUiDialogTranslation());
        assertEquals(Optional.of(List.of("com.apple.amsengagementd")), options.getIgnoredWebviewBundleIds());
        assertEquals(Optional.of(Duration.ofSeconds(20)), options.getWebviewAtomWaitTimeout());
        assertEquals(Optional.of("/tmp/ios-syslog.log"), options.getIosSyslogFile());

        assertEquals("America/Los_Angeles",
                options.getCapability(SupportsAppTimeZoneOption.APP_TIME_ZONE_OPTION));
        assertEquals("myapp://home",
                options.getCapability(SupportsInitialDeeplinkUrlOption.INITIAL_DEEPLINK_URL_OPTION));
        assertEquals(10.5,
                options.getCapability(SupportsAppLaunchStateTimeoutSecOption.APP_LAUNCH_STATE_TIMEOUT_SEC_OPTION));
        assertEquals(8000L,
                options.getCapability(SupportsIosInstallPauseOption.IOS_INSTALL_PAUSE_OPTION));
        assertEquals(8101,
                options.getCapability(SupportsWdaRemotePortOption.WDA_REMOTE_PORT_OPTION));
        assertEquals("127.0.0.1",
                options.getCapability(SupportsWdaBindingIpOption.WDA_BINDING_IP_OPTION));
        assertEquals("",
                options.getCapability(SupportsUpdatedWdaBundleIdSuffixOption.UPDATED_WDA_BUNDLE_ID_SUFFIX_OPTION));
        assertEquals(true,
                options.getCapability(SupportsPrebuildWdaOption.PREBUILD_WDA_OPTION));
        assertEquals("http://127.0.0.1:9100",
                options.getCapability(SupportsMjpegScreenshotUrlOption.MJPEG_SCREENSHOT_URL_OPTION));
        assertEquals(true,
                options.getCapability(SupportsReduceTransparencyOption.REDUCE_TRANSPARENCY_OPTION));
        assertEquals(false,
                options.getCapability(SupportsAutoFillPasswordsOption.AUTO_FILL_PASSWORDS_OPTION));
        assertEquals("debug",
                options.getCapability(SupportsSimulatorLogLevelOption.SIMULATOR_LOG_LEVEL_OPTION));
        assertEquals(true, options.getCapability(
                SupportsSkipSyncUiDialogTranslationOption.SKIP_SYNC_UI_DIALOG_TRANSLATION_OPTION));
        assertEquals(List.of("com.apple.amsengagementd"),
                options.getCapability(SupportsIgnoredWebviewBundleIdsOption.IGNORED_WEBVIEW_BUNDLE_IDS_OPTION));
        assertEquals(20000L,
                options.getCapability(SupportsWebviewAtomWaitTimeoutOption.WEBVIEW_ATOM_WAIT_TIMEOUT_OPTION));
        assertEquals("/tmp/ios-syslog.log",
                options.getCapability(SupportsIosSyslogFileOption.IOS_SYSLOG_FILE_OPTION));

        options.setPrebuildWda(false)
                .setReduceTransparency(false)
                .setAutoFillPasswords(true)
                .setSkipSyncUiDialogTranslation(false)
                .setMjpegScreenshotUrl("http://127.0.0.1:9200");
        assertEquals(Optional.of(false), options.doesPrebuildWda());
        assertEquals(Optional.of(false), options.doesReduceTransparency());
        assertEquals(Optional.of(true), options.doesAutoFillPasswords());
        assertEquals(Optional.of(false), options.doesSkipSyncUiDialogTranslation());
        assertEquals(Optional.of(new URL("http://127.0.0.1:9200")), options.getMjpegScreenshotUrl());
    }

    @Test
    void copyConstructorsCoerceAndPreserveNewOptions() throws MalformedURLException {
        var fromMap = new XCUITestOptions(Map.ofEntries(
                Map.entry("appTimeZone", "UTC"),
                Map.entry("appium:initialDeeplinkUrl", "https://example.com/path"),
                Map.entry("appLaunchStateTimeoutSec", "10.5"),
                Map.entry("appium:iosInstallPause", "8000"),
                Map.entry("wdaRemotePort", "8100"),
                Map.entry("appium:wdaBindingIP", "127.0.0.1"),
                Map.entry("updatedWDABundleIdSuffix", ".custom"),
                Map.entry("appium:prebuildWDA", "true"),
                Map.entry("mjpegScreenshotUrl", "http://127.0.0.1:9100"),
                Map.entry("appium:reduceTransparency", "false"),
                Map.entry("autoFillPasswords", "true"),
                Map.entry("appium:simulatorLogLevel", "info"),
                Map.entry("skipSyncUiDialogTranslation", "true"),
                Map.entry("appium:ignoredWebviewBundleIds", List.of("com.apple.WebKit")),
                Map.entry("webviewAtomWaitTimeout", "20000"),
                Map.entry("appium:iosSyslogFile", "/var/log/syslog.log")
        ));

        assertEquals(Optional.of("UTC"), fromMap.getAppTimeZone());
        assertEquals(Optional.of("https://example.com/path"), fromMap.getInitialDeeplinkUrl());
        assertEquals(Optional.of(Duration.ofMillis(10500)), fromMap.getAppLaunchStateTimeoutSec());
        assertEquals(Optional.of(Duration.ofSeconds(8)), fromMap.getIosInstallPause());
        assertEquals(Optional.of(8100), fromMap.getWdaRemotePort());
        assertEquals(Optional.of("127.0.0.1"), fromMap.getWdaBindingIp());
        assertEquals(Optional.of(".custom"), fromMap.getUpdatedWdaBundleIdSuffix());
        assertEquals(Optional.of(true), fromMap.doesPrebuildWda());
        assertEquals(Optional.of(new URL("http://127.0.0.1:9100")), fromMap.getMjpegScreenshotUrl());
        assertEquals(Optional.of(false), fromMap.doesReduceTransparency());
        assertEquals(Optional.of(true), fromMap.doesAutoFillPasswords());
        assertEquals(Optional.of("info"), fromMap.getSimulatorLogLevel());
        assertEquals(Optional.of(true), fromMap.doesSkipSyncUiDialogTranslation());
        assertEquals(Optional.of(List.of("com.apple.WebKit")), fromMap.getIgnoredWebviewBundleIds());
        assertEquals(Optional.of(Duration.ofSeconds(20)), fromMap.getWebviewAtomWaitTimeout());
        assertEquals(Optional.of("/var/log/syslog.log"), fromMap.getIosSyslogFile());

        var fromCaps = new XCUITestOptions(new ImmutableCapabilities(Map.of(
                "appium:appTimeZone", "Europe/London",
                "appium:wdaRemotePort", 8200,
                "appium:prebuildWDA", false,
                "appium:webviewAtomWaitTimeout", 15000
        )));
        assertEquals(Optional.of("Europe/London"), fromCaps.getAppTimeZone());
        assertEquals(Optional.of(8200), fromCaps.getWdaRemotePort());
        assertEquals(Optional.of(false), fromCaps.doesPrebuildWda());
        assertEquals(Optional.of(Duration.ofSeconds(15)), fromCaps.getWebviewAtomWaitTimeout());
    }

    @Test
    void ignoredWebviewBundleIdsParsesJsonArrayStringsAndRejectsInvalidValues() {
        var jsonOptions = new XCUITestOptions(Map.of(
                "appium:ignoredWebviewBundleIds",
                "[\"com.apple.amsengagementd\", \"com.apple.WebKit\"]"
        ));
        assertEquals(
                Optional.of(List.of("com.apple.amsengagementd", "com.apple.WebKit")),
                jsonOptions.getIgnoredWebviewBundleIds()
        );

        var emptyJsonOptions = new XCUITestOptions(Map.of("ignoredWebviewBundleIds", "[]"));
        assertEquals(Optional.of(List.of()), emptyJsonOptions.getIgnoredWebviewBundleIds());

        var objectJsonOptions = new XCUITestOptions(Map.of("appium:ignoredWebviewBundleIds", "{}"));
        assertThrows(IllegalArgumentException.class, objectJsonOptions::getIgnoredWebviewBundleIds);

        var numericArrayOptions = new XCUITestOptions(Map.of("appium:ignoredWebviewBundleIds", "[123]"));
        assertThrows(IllegalArgumentException.class, numericArrayOptions::getIgnoredWebviewBundleIds);

        var malformedJsonOptions = new XCUITestOptions(Map.of("appium:ignoredWebviewBundleIds", "not-json"));
        assertThrows(IllegalArgumentException.class, malformedJsonOptions::getIgnoredWebviewBundleIds);
    }
}
