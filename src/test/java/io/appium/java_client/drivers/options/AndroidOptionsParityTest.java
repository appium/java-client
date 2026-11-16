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

import io.appium.java_client.android.options.EspressoOptions;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.android.options.adb.SupportsAdbListenAllNetworkOption;
import io.appium.java_client.android.options.adb.SupportsAllowOfflineDevicesOption;
import io.appium.java_client.android.options.app.SupportsDontStopAppOnResetOption;
import io.appium.java_client.android.options.app.SupportsForceAppLaunchOption;
import io.appium.java_client.android.options.app.SupportsShouldTerminateAppOption;
import io.appium.java_client.android.options.context.SupportsAutoWebviewNameOption;
import io.appium.java_client.android.options.context.SupportsChromedriverForwardBiDiOption;
import io.appium.java_client.android.options.context.SupportsChromedriverGrantPermissionsOption;
import io.appium.java_client.android.options.context.SupportsEnableWebviewDetailsCollectionOption;
import io.appium.java_client.android.options.localization.SupportsTimeZoneOption;
import io.appium.java_client.android.options.other.SupportsEnableAccessibilityEventsServiceOption;
import io.appium.java_client.android.options.server.SupportsSkipSettingsAppReinstallOption;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.ImmutableCapabilities;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AndroidOptionsParityTest {

    @Test
    void uiAutomator2NewOptionsDefaultToEmpty() {
        var options = new UiAutomator2Options();
        assertEquals(Optional.empty(), options.doesAdbListenAllNetwork());
        assertEquals(Optional.empty(), options.doesAllowOfflineDevices());
        assertEquals(Optional.empty(), options.doesDontStopAppOnReset());
        assertEquals(Optional.empty(), options.doesForceAppLaunch());
        assertEquals(Optional.empty(), options.doesTerminateApp());
        assertEquals(Optional.empty(), options.getAutoWebviewName());
        assertEquals(Optional.empty(), options.doesChromedriverForwardBiDi());
        assertEquals(Optional.empty(), options.doesChromedriverGrantPermissions());
        assertEquals(Optional.empty(), options.doesEnableWebviewDetailsCollection());
        assertEquals(Optional.empty(), options.getTimeZone());
        assertEquals(Optional.empty(), options.doesEnableAccessibilityEventsService());
        assertEquals(Optional.empty(), options.doesSkipSettingsAppReinstall());
    }

    @Test
    void espressoNewOptionsDefaultToEmpty() {
        var options = new EspressoOptions();
        assertEquals(Optional.empty(), options.doesAdbListenAllNetwork());
        assertEquals(Optional.empty(), options.doesAllowOfflineDevices());
        assertEquals(Optional.empty(), options.doesDontStopAppOnReset());
        assertEquals(Optional.empty(), options.getAutoWebviewName());
        assertEquals(Optional.empty(), options.doesChromedriverForwardBiDi());
        assertEquals(Optional.empty(), options.doesChromedriverGrantPermissions());
        assertEquals(Optional.empty(), options.doesEnableWebviewDetailsCollection());
        assertEquals(Optional.empty(), options.getTimeZone());
        assertEquals(Optional.empty(), options.doesSkipSettingsAppReinstall());
    }

    @Test
    void uiAutomator2ChainedMethodsPopulateCapabilities() {
        var options = new UiAutomator2Options()
                .enableBiDi()
                .adbListenAllNetwork()
                .allowOfflineDevices()
                .dontStopAppOnReset()
                .forceAppLaunch()
                .shouldTerminateApp()
                .setAutoWebviewName("WEBVIEW_sample")
                .chromedriverForwardBiDi()
                .chromedriverGrantPermissions()
                .enableWebviewDetailsCollection()
                .setTimeZone("America/New_York")
                .enableAccessibilityEventsService()
                .skipSettingsAppReinstall();

        assertEquals(Optional.of(true), options.getWebSocketUrl());
        assertEquals(Optional.of(true), options.doesAdbListenAllNetwork());
        assertEquals(Optional.of(true), options.doesAllowOfflineDevices());
        assertEquals(Optional.of(true), options.doesDontStopAppOnReset());
        assertEquals(Optional.of(true), options.doesForceAppLaunch());
        assertEquals(Optional.of(true), options.doesTerminateApp());
        assertEquals(Optional.of("WEBVIEW_sample"), options.getAutoWebviewName());
        assertEquals(Optional.of(true), options.doesChromedriverForwardBiDi());
        assertEquals(Optional.of(true), options.doesChromedriverGrantPermissions());
        assertEquals(Optional.of(true), options.doesEnableWebviewDetailsCollection());
        assertEquals(Optional.of("America/New_York"), options.getTimeZone());
        assertEquals(Optional.of(true), options.doesEnableAccessibilityEventsService());
        assertEquals(Optional.of(true), options.doesSkipSettingsAppReinstall());

        Map<String, Object> capMap = options.asMap();
        assertEquals(true, capMap.get("appium:" + SupportsAdbListenAllNetworkOption.ADB_LISTEN_ALL_NETWORK_OPTION));
        assertEquals(true, capMap.get("appium:" + SupportsAllowOfflineDevicesOption.ALLOW_OFFLINE_DEVICES_OPTION));
        assertEquals(true, capMap.get("appium:" + SupportsDontStopAppOnResetOption.DONT_STOP_APP_ON_RESET_OPTION));
        assertEquals(true, capMap.get("appium:" + SupportsForceAppLaunchOption.FORCE_APP_LAUNCH_OPTION));
        assertEquals(true, capMap.get("appium:" + SupportsShouldTerminateAppOption.SHOULD_TERMINATE_APP_OPTION));
        assertEquals("WEBVIEW_sample", capMap.get("appium:" + SupportsAutoWebviewNameOption.AUTO_WEBVIEW_NAME_OPTION));
        assertEquals(
                true,
                capMap.get("appium:" + SupportsChromedriverForwardBiDiOption.CHROMEDRIVER_FORWARD_BIDI_OPTION)
        );
        assertEquals(
                true,
                capMap.get("appium:" + SupportsChromedriverGrantPermissionsOption.CHROMEDRIVER_GRANT_PERMISSIONS_OPTION)
        );
        assertEquals(
                true,
                capMap.get(
                        "appium:"
                                + SupportsEnableWebviewDetailsCollectionOption
                                .ENABLE_WEBVIEW_DETAILS_COLLECTION_OPTION
                )
        );
        assertEquals("America/New_York", capMap.get("appium:" + SupportsTimeZoneOption.TIME_ZONE_OPTION));
        assertEquals(
                true,
                capMap.get(
                        "appium:"
                                + SupportsEnableAccessibilityEventsServiceOption
                                .ENABLE_ACCESSIBILITY_EVENTS_SERVICE_OPTION
                )
        );
        assertEquals(
                true,
                capMap.get("appium:" + SupportsSkipSettingsAppReinstallOption.SKIP_SETTINGS_APP_REINSTALL_OPTION)
        );
    }

    @Test
    void espressoChainedMethodsPopulateCapabilities() {
        var options = new EspressoOptions()
                .enableBiDi()
                .adbListenAllNetwork()
                .allowOfflineDevices()
                .dontStopAppOnReset()
                .setAutoWebviewName("WEBVIEW_espresso")
                .chromedriverForwardBiDi()
                .chromedriverGrantPermissions()
                .enableWebviewDetailsCollection()
                .setTimeZone("Asia/Ho_Chi_Minh")
                .skipSettingsAppReinstall();

        assertEquals(Optional.of(true), options.getWebSocketUrl());
        assertEquals(Optional.of(true), options.doesAdbListenAllNetwork());
        assertEquals(Optional.of(true), options.doesAllowOfflineDevices());
        assertEquals(Optional.of(true), options.doesDontStopAppOnReset());
        assertEquals(Optional.of("WEBVIEW_espresso"), options.getAutoWebviewName());
        assertEquals(Optional.of(true), options.doesChromedriverForwardBiDi());
        assertEquals(Optional.of(true), options.doesChromedriverGrantPermissions());
        assertEquals(Optional.of(true), options.doesEnableWebviewDetailsCollection());
        assertEquals(Optional.of("Asia/Ho_Chi_Minh"), options.getTimeZone());
        assertEquals(Optional.of(true), options.doesSkipSettingsAppReinstall());

        Map<String, Object> capMap = options.asMap();
        assertEquals(true, capMap.get("appium:adbListenAllNetwork"));
        assertEquals(true, capMap.get("appium:allowOfflineDevices"));
        assertEquals(true, capMap.get("appium:dontStopAppOnReset"));
        assertEquals("WEBVIEW_espresso", capMap.get("appium:autoWebviewName"));
        assertEquals(true, capMap.get("appium:chromedriverForwardBiDi"));
        assertEquals(true, capMap.get("appium:chromedriverGrantPermissions"));
        assertEquals(true, capMap.get("appium:enableWebviewDetailsCollection"));
        assertEquals("Asia/Ho_Chi_Minh", capMap.get("appium:timeZone"));
        assertEquals(true, capMap.get("appium:skipSettingsAppReinstall"));
    }

    @Test
    void booleanSettersSupportFalseAndTrueOverrides() {
        var uia2 = new UiAutomator2Options()
                .setAdbListenAllNetwork(false)
                .setAllowOfflineDevices(false)
                .setDontStopAppOnReset(false)
                .setForceAppLaunch(false)
                .setShouldTerminateApp(false)
                .setChromedriverForwardBiDi(false)
                .setChromedriverGrantPermissions(false)
                .setEnableWebviewDetailsCollection(false)
                .setEnableAccessibilityEventsService(false)
                .setSkipSettingsAppReinstall(false);

        assertEquals(Optional.of(false), uia2.doesAdbListenAllNetwork());
        assertEquals(Optional.of(false), uia2.doesAllowOfflineDevices());
        assertEquals(Optional.of(false), uia2.doesDontStopAppOnReset());
        assertEquals(Optional.of(false), uia2.doesForceAppLaunch());
        assertEquals(Optional.of(false), uia2.doesTerminateApp());
        assertEquals(Optional.of(false), uia2.doesChromedriverForwardBiDi());
        assertEquals(Optional.of(false), uia2.doesChromedriverGrantPermissions());
        assertEquals(Optional.of(false), uia2.doesEnableWebviewDetailsCollection());
        assertEquals(Optional.of(false), uia2.doesEnableAccessibilityEventsService());
        assertEquals(Optional.of(false), uia2.doesSkipSettingsAppReinstall());

        uia2.setEnableAccessibilityEventsService(true)
                .setForceAppLaunch(true)
                .setShouldTerminateApp(true);
        assertTrue(uia2.doesEnableAccessibilityEventsService().orElse(false));
        assertTrue(uia2.doesForceAppLaunch().orElse(false));
        assertTrue(uia2.doesTerminateApp().orElse(false));

        var espresso = new EspressoOptions()
                .setAdbListenAllNetwork(false)
                .setAllowOfflineDevices(false)
                .setDontStopAppOnReset(false)
                .setChromedriverForwardBiDi(false)
                .setChromedriverGrantPermissions(false)
                .setEnableWebviewDetailsCollection(false)
                .setSkipSettingsAppReinstall(false);

        assertEquals(Optional.of(false), espresso.doesAdbListenAllNetwork());
        assertEquals(Optional.of(false), espresso.doesAllowOfflineDevices());
        assertEquals(Optional.of(false), espresso.doesDontStopAppOnReset());
        assertEquals(Optional.of(false), espresso.doesChromedriverForwardBiDi());
        assertEquals(Optional.of(false), espresso.doesChromedriverGrantPermissions());
        assertEquals(Optional.of(false), espresso.doesEnableWebviewDetailsCollection());
        assertEquals(Optional.of(false), espresso.doesSkipSettingsAppReinstall());

        espresso.setChromedriverForwardBiDi(true).setSkipSettingsAppReinstall(true);
        assertTrue(espresso.doesChromedriverForwardBiDi().orElse(false));
        assertTrue(espresso.doesSkipSettingsAppReinstall().orElse(false));
    }

    @Test
    void copyConstructorsCoerceAndPreserveNewOptions() {
        var uia2 = new UiAutomator2Options(Map.ofEntries(
                Map.entry("adbListenAllNetwork", "true"),
                Map.entry("appium:allowOfflineDevices", "false"),
                Map.entry("dontStopAppOnReset", true),
                Map.entry("forceAppLaunch", "true"),
                Map.entry("shouldTerminateApp", "false"),
                Map.entry("autoWebviewName", "WEBVIEW_chrome"),
                Map.entry("chromedriverForwardBiDi", "true"),
                Map.entry("chromedriverGrantPermissions", true),
                Map.entry("enableWebviewDetailsCollection", "false"),
                Map.entry("timeZone", "UTC"),
                Map.entry("enableAccessibilityEventsService", "true"),
                Map.entry("skipSettingsAppReinstall", "true")
        ));

        assertEquals(Optional.of(true), uia2.doesAdbListenAllNetwork());
        assertEquals(Optional.of(false), uia2.doesAllowOfflineDevices());
        assertEquals(Optional.of(true), uia2.doesDontStopAppOnReset());
        assertEquals(Optional.of(true), uia2.doesForceAppLaunch());
        assertEquals(Optional.of(false), uia2.doesTerminateApp());
        assertEquals(Optional.of("WEBVIEW_chrome"), uia2.getAutoWebviewName());
        assertEquals(Optional.of(true), uia2.doesChromedriverForwardBiDi());
        assertEquals(Optional.of(true), uia2.doesChromedriverGrantPermissions());
        assertEquals(Optional.of(false), uia2.doesEnableWebviewDetailsCollection());
        assertEquals(Optional.of("UTC"), uia2.getTimeZone());
        assertEquals(Optional.of(true), uia2.doesEnableAccessibilityEventsService());
        assertEquals(Optional.of(true), uia2.doesSkipSettingsAppReinstall());

        var espresso = new EspressoOptions(new ImmutableCapabilities(Map.of(
                "appium:chromedriverForwardBiDi", true,
                "appium:skipSettingsAppReinstall", true,
                "appium:timeZone", "Europe/London",
                "appium:autoWebviewName", "WEBVIEW_app"
        )));
        assertEquals(Optional.of(true), espresso.doesChromedriverForwardBiDi());
        assertEquals(Optional.of(true), espresso.doesSkipSettingsAppReinstall());
        assertEquals(Optional.of("Europe/London"), espresso.getTimeZone());
        assertEquals(Optional.of("WEBVIEW_app"), espresso.getAutoWebviewName());
    }
}
