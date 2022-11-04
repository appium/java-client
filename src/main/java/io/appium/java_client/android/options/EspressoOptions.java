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
import io.appium.java_client.android.options.app.SupportsActivityOptionsOption;
import io.appium.java_client.android.options.app.SupportsAllowTestPackagesOption;
import io.appium.java_client.android.options.app.SupportsAndroidInstallTimeoutOption;
import io.appium.java_client.android.options.app.SupportsAppActivityOption;
import io.appium.java_client.android.options.app.SupportsAppPackageOption;
import io.appium.java_client.android.options.app.SupportsAppWaitActivityOption;
import io.appium.java_client.android.options.app.SupportsAppWaitDurationOption;
import io.appium.java_client.android.options.app.SupportsAppWaitPackageOption;
import io.appium.java_client.android.options.app.SupportsAutoGrantPermissionsOption;
import io.appium.java_client.android.options.app.SupportsEnforceAppInstallOption;
import io.appium.java_client.android.options.app.SupportsIntentOptionsOption;
import io.appium.java_client.android.options.app.SupportsRemoteAppsCacheLimitOption;
import io.appium.java_client.android.options.app.SupportsUninstallOtherPackagesOption;
import io.appium.java_client.android.options.avd.SupportsAvdArgsOption;
import io.appium.java_client.android.options.avd.SupportsAvdEnvOption;
import io.appium.java_client.android.options.avd.SupportsAvdLaunchTimeoutOption;
import io.appium.java_client.android.options.avd.SupportsAvdOption;
import io.appium.java_client.android.options.avd.SupportsAvdReadyTimeoutOption;
import io.appium.java_client.android.options.avd.SupportsGpsEnabledOption;
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
import io.appium.java_client.android.options.localization.SupportsAppLocaleOption;
import io.appium.java_client.android.options.localization.SupportsLocaleScriptOption;
import io.appium.java_client.android.options.locking.SupportsSkipUnlockOption;
import io.appium.java_client.android.options.locking.SupportsUnlockKeyOption;
import io.appium.java_client.android.options.locking.SupportsUnlockStrategyOption;
import io.appium.java_client.android.options.locking.SupportsUnlockSuccessTimeoutOption;
import io.appium.java_client.android.options.locking.SupportsUnlockTypeOption;
import io.appium.java_client.android.options.mjpeg.SupportsMjpegScreenshotUrlOption;
import io.appium.java_client.android.options.mjpeg.SupportsMjpegServerPortOption;
import io.appium.java_client.android.options.other.SupportsDisableSuppressAccessibilityServiceOption;
import io.appium.java_client.android.options.server.SupportsEspressoBuildConfigOption;
import io.appium.java_client.android.options.server.SupportsEspressoServerLaunchTimeoutOption;
import io.appium.java_client.android.options.server.SupportsForceEspressoRebuildOption;
import io.appium.java_client.android.options.server.SupportsShowGradleLogOption;
import io.appium.java_client.android.options.server.SupportsSkipServerInstallationOption;
import io.appium.java_client.android.options.server.SupportsSystemPortOption;
import io.appium.java_client.android.options.signing.SupportsKeystoreOptions;
import io.appium.java_client.android.options.signing.SupportsNoSignOption;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.SupportsAppOption;
import io.appium.java_client.remote.options.SupportsAutoWebViewOption;
import io.appium.java_client.remote.options.SupportsDeviceNameOption;
import io.appium.java_client.remote.options.SupportsIsHeadlessOption;
import io.appium.java_client.remote.options.SupportsLanguageOption;
import io.appium.java_client.remote.options.SupportsLocaleOption;
import io.appium.java_client.remote.options.SupportsOrientationOption;
import io.appium.java_client.remote.options.SupportsOtherAppsOption;
import io.appium.java_client.remote.options.SupportsSkipLogCaptureOption;
import io.appium.java_client.remote.options.SupportsUdidOption;
import org.openqa.selenium.Capabilities;

import java.util.Map;

/**
 * https://github.com/appium/appium-espresso-driver#capabilities
 */
public class EspressoOptions extends BaseOptions<EspressoOptions> implements
        // General options: https://github.com/appium/appium-uiautomator2-driver#general
        SupportsDeviceNameOption<EspressoOptions>,
        SupportsUdidOption<EspressoOptions>,
        // Driver/Server options: https://github.com/appium/appium-uiautomator2-driver#driverserver
        SupportsSystemPortOption<EspressoOptions>,
        SupportsSkipServerInstallationOption<EspressoOptions>,
        SupportsEspressoServerLaunchTimeoutOption<EspressoOptions>,
        SupportsForceEspressoRebuildOption<EspressoOptions>,
        SupportsShowGradleLogOption<EspressoOptions>,
        SupportsOrientationOption<EspressoOptions>,
        SupportsEspressoBuildConfigOption<EspressoOptions>,
        // App options: https://github.com/appium/appium-uiautomator2-driver#app
        SupportsAppOption<EspressoOptions>,
        SupportsAppPackageOption<EspressoOptions>,
        SupportsAppActivityOption<EspressoOptions>,
        SupportsAppWaitActivityOption<EspressoOptions>,
        SupportsAppWaitPackageOption<EspressoOptions>,
        SupportsAppWaitDurationOption<EspressoOptions>,
        SupportsAndroidInstallTimeoutOption<EspressoOptions>,
        SupportsIntentOptionsOption<EspressoOptions>,
        SupportsActivityOptionsOption<EspressoOptions>,
        SupportsAutoGrantPermissionsOption<EspressoOptions>,
        SupportsOtherAppsOption<EspressoOptions>,
        SupportsUninstallOtherPackagesOption<EspressoOptions>,
        SupportsAllowTestPackagesOption<EspressoOptions>,
        SupportsRemoteAppsCacheLimitOption<EspressoOptions>,
        SupportsEnforceAppInstallOption<EspressoOptions>,
        // App localization options: https://github.com/appium/appium-uiautomator2-driver#app-localization
        SupportsLocaleScriptOption<EspressoOptions>,
        SupportsLanguageOption<EspressoOptions>,
        SupportsLocaleOption<EspressoOptions>,
        SupportsAppLocaleOption<EspressoOptions>,
        // ADB options: https://github.com/appium/appium-uiautomator2-driver#adb
        SupportsAdbPortOption<EspressoOptions>,
        SupportsRemoteAdbHostOption<EspressoOptions>,
        SupportsAdbExecTimeoutOption<EspressoOptions>,
        SupportsClearDeviceLogsOnStartOption<EspressoOptions>,
        SupportsBuildToolsVersionOption<EspressoOptions>,
        SupportsSkipLogcatCaptureOption<EspressoOptions>,
        SupportsSuppressKillServerOption<EspressoOptions>,
        SupportsIgnoreHiddenApiPolicyErrorOption<EspressoOptions>,
        SupportsMockLocationAppOption<EspressoOptions>,
        SupportsLogcatFormatOption<EspressoOptions>,
        SupportsLogcatFilterSpecsOption<EspressoOptions>,
        SupportsAllowDelayAdbOption<EspressoOptions>,
        // AVD options: https://github.com/appium/appium-uiautomator2-driver#emulator-android-virtual-device
        SupportsAvdOption<EspressoOptions>,
        SupportsAvdLaunchTimeoutOption<EspressoOptions>,
        SupportsAvdReadyTimeoutOption<EspressoOptions>,
        SupportsAvdArgsOption<EspressoOptions>,
        SupportsAvdEnvOption<EspressoOptions>,
        SupportsNetworkSpeedOption<EspressoOptions>,
        SupportsGpsEnabledOption<EspressoOptions>,
        SupportsIsHeadlessOption<EspressoOptions>,
        // App signing options: https://github.com/appium/appium-uiautomator2-driver#app-signing
        SupportsKeystoreOptions<EspressoOptions>,
        SupportsNoSignOption<EspressoOptions>,
        // Device locking options: https://github.com/appium/appium-uiautomator2-driver#device-locking
        SupportsSkipUnlockOption<EspressoOptions>,
        SupportsUnlockTypeOption<EspressoOptions>,
        SupportsUnlockKeyOption<EspressoOptions>,
        SupportsUnlockStrategyOption<EspressoOptions>,
        SupportsUnlockSuccessTimeoutOption<EspressoOptions>,
        // MJPEG options: https://github.com/appium/appium-uiautomator2-driver#mjpeg
        SupportsMjpegServerPortOption<EspressoOptions>,
        SupportsMjpegScreenshotUrlOption<EspressoOptions>,
        // Web Context options: https://github.com/appium/appium-uiautomator2-driver#web-context
        SupportsAutoWebViewOption<EspressoOptions>,
        SupportsWebviewDevtoolsPortOption<EspressoOptions>,
        SupportsEnsureWebviewsHavePagesOption<EspressoOptions>,
        SupportsChromedriverPortOption<EspressoOptions>,
        SupportsChromedriverPortsOption<EspressoOptions>,
        SupportsChromedriverArgsOption<EspressoOptions>,
        SupportsChromedriverExecutableOption<EspressoOptions>,
        SupportsChromedriverExecutableDirOption<EspressoOptions>,
        SupportsChromedriverChromeMappingFileOption<EspressoOptions>,
        SupportsChromedriverUseSystemExecutableOption<EspressoOptions>,
        SupportsChromedriverDisableBuildCheckOption<EspressoOptions>,
        SupportsAutoWebviewTimeoutOption<EspressoOptions>,
        SupportsRecreateChromeDriverSessionsOption<EspressoOptions>,
        SupportsNativeWebScreenshotOption<EspressoOptions>,
        SupportsExtractChromeAndroidPackageFromContextNameOption<EspressoOptions>,
        SupportsShowChromedriverLogOption<EspressoOptions>,
        SupportsChromeOptionsOption<EspressoOptions>,
        SupportsChromeLoggingPrefsOption<EspressoOptions>,
        // Other options: https://github.com/appium/appium-uiautomator2-driver#other
        SupportsDisableSuppressAccessibilityServiceOption<EspressoOptions>,
        SupportsSkipLogCaptureOption<EspressoOptions> {
    public EspressoOptions() {
        setCommonOptions();
    }

    public EspressoOptions(Capabilities source) {
        super(source);
        setCommonOptions();
    }

    public EspressoOptions(Map<String, ?> source) {
        super(source);
        setCommonOptions();
    }

    private void setCommonOptions() {
        setPlatformName(MobilePlatform.ANDROID);
        setAutomationName(AutomationName.ESPRESSO);
    }
}
