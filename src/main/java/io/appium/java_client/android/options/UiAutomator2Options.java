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

package io.appium.java_client.android.options;

import io.appium.java_client.android.options.adb.SupportsAdbExecTimeoutOption;
import io.appium.java_client.android.options.adb.SupportsAdbPortOption;
import io.appium.java_client.android.options.adb.SupportsAllowDelayAdbOption;
import io.appium.java_client.android.options.adb.SupportsBuildToolsVersionOption;
import io.appium.java_client.android.options.adb.SupportsClearDeviceLogsOnStartOption;
import io.appium.java_client.android.options.adb.SupportsIgnoreHiddenApiPolicyErrorOption;
import io.appium.java_client.android.options.adb.SupportsLogcatFilterSpecsOption;
import io.appium.java_client.android.options.adb.SupportsLogcatFormatOption;
import io.appium.java_client.android.options.adb.SupportsMockLocationAppOption;
import io.appium.java_client.android.options.adb.SupportsRemoteAdbHostOption;
import io.appium.java_client.android.options.adb.SupportsSkipLogcatCaptureOption;
import io.appium.java_client.android.options.adb.SupportsSuppressKillServerOption;
import io.appium.java_client.android.options.app.SupportsAllowTestPackagesOption;
import io.appium.java_client.android.options.app.SupportsAndroidInstallTimeoutOption;
import io.appium.java_client.android.options.app.SupportsAppActivityOption;
import io.appium.java_client.android.options.app.SupportsAppWaitDurationOption;
import io.appium.java_client.android.options.app.SupportsAppPackageOption;
import io.appium.java_client.android.options.app.SupportsAppWaitActivityOption;
import io.appium.java_client.android.options.app.SupportsAppWaitForLaunchOption;
import io.appium.java_client.android.options.app.SupportsAppWaitPackageOption;
import io.appium.java_client.android.options.app.SupportsAutoGrantPermissionsOption;
import io.appium.java_client.android.options.app.SupportsEnforceAppInstallOption;
import io.appium.java_client.android.options.app.SupportsIntentActionOption;
import io.appium.java_client.android.options.app.SupportsIntentCategoryOption;
import io.appium.java_client.android.options.app.SupportsIntentFlagsOption;
import io.appium.java_client.android.options.app.SupportsOptionalIntentArgumentsOption;
import io.appium.java_client.android.options.app.SupportsRemoteAppsCacheLimitOption;
import io.appium.java_client.android.options.app.SupportsUninstallOtherPackagesOption;
import io.appium.java_client.android.options.avd.SupportsAvdArgsOption;
import io.appium.java_client.android.options.avd.SupportsAvdEnvOption;
import io.appium.java_client.android.options.avd.SupportsAvdLaunchTimeoutOption;
import io.appium.java_client.android.options.avd.SupportsAvdOption;
import io.appium.java_client.android.options.avd.SupportsAvdReadyTimeoutOption;
import io.appium.java_client.android.options.avd.SupportsGpsEnabledOption;
import io.appium.java_client.remote.options.SupportsIsHeadlessOption;
import io.appium.java_client.android.options.avd.SupportsNetworkSpeedOption;
import io.appium.java_client.android.options.context.SupportsAutoWebviewTimeoutOption;
import io.appium.java_client.android.options.context.SupportsChromeLoggingPrefsOption;
import io.appium.java_client.android.options.context.SupportsChromeOptionsOption;
import io.appium.java_client.android.options.context.SupportsChromedriverArgsOption;
import io.appium.java_client.android.options.context.SupportsChromedriverChromeMappingFileOption;
import io.appium.java_client.android.options.context.SupportsChromedriverDisableBuildCheckOption;
import io.appium.java_client.android.options.context.SupportsChromedriverExecutableDirOption;
import io.appium.java_client.android.options.context.SupportsChromedriverExecutableOption;
import io.appium.java_client.android.options.context.SupportsChromedriverPortOption;
import io.appium.java_client.android.options.context.SupportsChromedriverPortsOption;
import io.appium.java_client.android.options.context.SupportsChromedriverUseSystemExecutableOption;
import io.appium.java_client.android.options.context.SupportsEnsureWebviewsHavePagesOption;
import io.appium.java_client.android.options.context.SupportsExtractChromeAndroidPackageFromContextNameOption;
import io.appium.java_client.android.options.context.SupportsNativeWebScreenshotOption;
import io.appium.java_client.android.options.context.SupportsRecreateChromeDriverSessionsOption;
import io.appium.java_client.android.options.context.SupportsShowChromedriverLogOption;
import io.appium.java_client.android.options.context.SupportsWebviewDevtoolsPortOption;
import io.appium.java_client.android.options.localization.SupportsLocaleScriptOption;
import io.appium.java_client.android.options.locking.SupportsSkipUnlockOption;
import io.appium.java_client.android.options.locking.SupportsUnlockKeyOption;
import io.appium.java_client.android.options.locking.SupportsUnlockStrategyOption;
import io.appium.java_client.android.options.locking.SupportsUnlockSuccessTimeoutOption;
import io.appium.java_client.android.options.locking.SupportsUnlockTypeOption;
import io.appium.java_client.android.options.mjpeg.SupportsMjpegScreenshotUrlOption;
import io.appium.java_client.android.options.mjpeg.SupportsMjpegServerPortOption;
import io.appium.java_client.android.options.other.SupportsDisableSuppressAccessibilityServiceOption;
import io.appium.java_client.remote.options.SupportsSkipLogCaptureOption;
import io.appium.java_client.android.options.other.SupportsUserProfileOption;
import io.appium.java_client.android.options.server.SupportsDisableWindowAnimationOption;
import io.appium.java_client.android.options.server.SupportsSkipDeviceInitializationOption;
import io.appium.java_client.android.options.server.SupportsSkipServerInstallationOption;
import io.appium.java_client.android.options.server.SupportsSystemPortOption;
import io.appium.java_client.android.options.server.SupportsUiautomator2ServerInstallTimeoutOption;
import io.appium.java_client.android.options.server.SupportsUiautomator2ServerLaunchTimeoutOption;
import io.appium.java_client.android.options.server.SupportsUiautomator2ServerReadTimeoutOption;
import io.appium.java_client.android.options.signing.SupportsKeystoreOptions;
import io.appium.java_client.android.options.signing.SupportsNoSignOption;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.SupportsAppOption;
import io.appium.java_client.remote.options.SupportsAutoWebViewOption;
import io.appium.java_client.remote.options.SupportsClearSystemFilesOption;
import io.appium.java_client.remote.options.SupportsDeviceNameOption;
import io.appium.java_client.remote.options.SupportsEnablePerformanceLoggingOption;
import io.appium.java_client.remote.options.SupportsLanguageOption;
import io.appium.java_client.remote.options.SupportsLocaleOption;
import io.appium.java_client.remote.options.SupportsOrientationOption;
import io.appium.java_client.remote.options.SupportsOtherAppsOption;
import io.appium.java_client.remote.options.SupportsUdidOption;
import org.openqa.selenium.Capabilities;

import java.util.Map;

/**
 * https://github.com/appium/appium-uiautomator2-driver#capabilities
 */
public class UiAutomator2Options extends BaseOptions<UiAutomator2Options> implements
        // General options: https://github.com/appium/appium-uiautomator2-driver#general
        SupportsDeviceNameOption<UiAutomator2Options>,
        SupportsUdidOption<UiAutomator2Options>,
        // Driver/Server options: https://github.com/appium/appium-uiautomator2-driver#driverserver
        SupportsSystemPortOption<UiAutomator2Options>,
        SupportsSkipServerInstallationOption<UiAutomator2Options>,
        SupportsUiautomator2ServerLaunchTimeoutOption<UiAutomator2Options>,
        SupportsUiautomator2ServerInstallTimeoutOption<UiAutomator2Options>,
        SupportsUiautomator2ServerReadTimeoutOption<UiAutomator2Options>,
        SupportsDisableWindowAnimationOption<UiAutomator2Options>,
        SupportsSkipDeviceInitializationOption<UiAutomator2Options>,
        SupportsOrientationOption<UiAutomator2Options>,
        SupportsClearSystemFilesOption<UiAutomator2Options>,
        SupportsEnablePerformanceLoggingOption<UiAutomator2Options>,
        // App options: https://github.com/appium/appium-uiautomator2-driver#app
        SupportsAppOption<UiAutomator2Options>,
        SupportsAppPackageOption<UiAutomator2Options>,
        SupportsAppActivityOption<UiAutomator2Options>,
        SupportsAppWaitActivityOption<UiAutomator2Options>,
        SupportsAppWaitPackageOption<UiAutomator2Options>,
        SupportsAppWaitDurationOption<UiAutomator2Options>,
        SupportsAndroidInstallTimeoutOption<UiAutomator2Options>,
        SupportsAppWaitForLaunchOption<UiAutomator2Options>,
        SupportsIntentCategoryOption<UiAutomator2Options>,
        SupportsIntentActionOption<UiAutomator2Options>,
        SupportsIntentFlagsOption<UiAutomator2Options>,
        SupportsOptionalIntentArgumentsOption<UiAutomator2Options>,
        SupportsAutoGrantPermissionsOption<UiAutomator2Options>,
        SupportsOtherAppsOption<UiAutomator2Options>,
        SupportsUninstallOtherPackagesOption<UiAutomator2Options>,
        SupportsAllowTestPackagesOption<UiAutomator2Options>,
        SupportsRemoteAppsCacheLimitOption<UiAutomator2Options>,
        SupportsEnforceAppInstallOption<UiAutomator2Options>,
        // App localization options: https://github.com/appium/appium-uiautomator2-driver#app-localization
        SupportsLocaleScriptOption<UiAutomator2Options>,
        SupportsLanguageOption<UiAutomator2Options>,
        SupportsLocaleOption<UiAutomator2Options>,
        // ADB options: https://github.com/appium/appium-uiautomator2-driver#adb
        SupportsAdbPortOption<UiAutomator2Options>,
        SupportsRemoteAdbHostOption<UiAutomator2Options>,
        SupportsAdbExecTimeoutOption<UiAutomator2Options>,
        SupportsClearDeviceLogsOnStartOption<UiAutomator2Options>,
        SupportsBuildToolsVersionOption<UiAutomator2Options>,
        SupportsSkipLogcatCaptureOption<UiAutomator2Options>,
        SupportsSuppressKillServerOption<UiAutomator2Options>,
        SupportsIgnoreHiddenApiPolicyErrorOption<UiAutomator2Options>,
        SupportsMockLocationAppOption<UiAutomator2Options>,
        SupportsLogcatFormatOption<UiAutomator2Options>,
        SupportsLogcatFilterSpecsOption<UiAutomator2Options>,
        SupportsAllowDelayAdbOption<UiAutomator2Options>,
        // AVD options: https://github.com/appium/appium-uiautomator2-driver#emulator-android-virtual-device
        SupportsAvdOption<UiAutomator2Options>,
        SupportsAvdLaunchTimeoutOption<UiAutomator2Options>,
        SupportsAvdReadyTimeoutOption<UiAutomator2Options>,
        SupportsAvdArgsOption<UiAutomator2Options>,
        SupportsAvdEnvOption<UiAutomator2Options>,
        SupportsNetworkSpeedOption<UiAutomator2Options>,
        SupportsGpsEnabledOption<UiAutomator2Options>,
        SupportsIsHeadlessOption<UiAutomator2Options>,
        // App signing options: https://github.com/appium/appium-uiautomator2-driver#app-signing
        SupportsKeystoreOptions<UiAutomator2Options>,
        SupportsNoSignOption<UiAutomator2Options>,
        // Device locking options: https://github.com/appium/appium-uiautomator2-driver#device-locking
        SupportsSkipUnlockOption<UiAutomator2Options>,
        SupportsUnlockTypeOption<UiAutomator2Options>,
        SupportsUnlockKeyOption<UiAutomator2Options>,
        SupportsUnlockStrategyOption<UiAutomator2Options>,
        SupportsUnlockSuccessTimeoutOption<UiAutomator2Options>,
        // MJPEG options: https://github.com/appium/appium-uiautomator2-driver#mjpeg
        SupportsMjpegServerPortOption<UiAutomator2Options>,
        SupportsMjpegScreenshotUrlOption<UiAutomator2Options>,
        // Web Context options: https://github.com/appium/appium-uiautomator2-driver#web-context
        SupportsAutoWebViewOption<UiAutomator2Options>,
        SupportsWebviewDevtoolsPortOption<UiAutomator2Options>,
        SupportsEnsureWebviewsHavePagesOption<UiAutomator2Options>,
        SupportsChromedriverPortOption<UiAutomator2Options>,
        SupportsChromedriverPortsOption<UiAutomator2Options>,
        SupportsChromedriverArgsOption<UiAutomator2Options>,
        SupportsChromedriverExecutableOption<UiAutomator2Options>,
        SupportsChromedriverExecutableDirOption<UiAutomator2Options>,
        SupportsChromedriverChromeMappingFileOption<UiAutomator2Options>,
        SupportsChromedriverUseSystemExecutableOption<UiAutomator2Options>,
        SupportsChromedriverDisableBuildCheckOption<UiAutomator2Options>,
        SupportsAutoWebviewTimeoutOption<UiAutomator2Options>,
        SupportsRecreateChromeDriverSessionsOption<UiAutomator2Options>,
        SupportsNativeWebScreenshotOption<UiAutomator2Options>,
        SupportsExtractChromeAndroidPackageFromContextNameOption<UiAutomator2Options>,
        SupportsShowChromedriverLogOption<UiAutomator2Options>,
        SupportsChromeOptionsOption<UiAutomator2Options>,
        SupportsChromeLoggingPrefsOption<UiAutomator2Options>,
        // Other options: https://github.com/appium/appium-uiautomator2-driver#other
        SupportsDisableSuppressAccessibilityServiceOption<UiAutomator2Options>,
        SupportsUserProfileOption<UiAutomator2Options>,
        SupportsSkipLogCaptureOption<UiAutomator2Options> {
    public UiAutomator2Options() {
        setCommonOptions();
    }

    public UiAutomator2Options(Capabilities source) {
        super(source);
        setCommonOptions();
    }

    public UiAutomator2Options(Map<String, ?> source) {
        super(source);
        setCommonOptions();
    }

    private void setCommonOptions() {
        setPlatformName(MobilePlatform.ANDROID);
        setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);
    }
}
