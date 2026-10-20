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
import io.appium.java_client.android.options.app.SupportsAppPackageOption;
import io.appium.java_client.android.options.app.SupportsAppWaitActivityOption;
import io.appium.java_client.android.options.app.SupportsAppWaitDurationOption;
import io.appium.java_client.android.options.app.SupportsAppWaitForLaunchOption;
import io.appium.java_client.android.options.app.SupportsAppWaitPackageOption;
import io.appium.java_client.android.options.app.SupportsAutoGrantPermissionsOption;
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
import io.appium.java_client.remote.options.SupportsEnforceAppInstallOption;
import io.appium.java_client.remote.options.SupportsIsHeadlessOption;
import io.appium.java_client.remote.options.SupportsLanguageOption;
import io.appium.java_client.remote.options.SupportsLocaleOption;
import io.appium.java_client.remote.options.SupportsOrientationOption;
import io.appium.java_client.remote.options.SupportsOtherAppsOption;
import io.appium.java_client.remote.options.SupportsSkipLogCaptureOption;
import io.appium.java_client.remote.options.SupportsUdidOption;
import org.openqa.selenium.Capabilities;

import java.util.Map;
import java.util.Optional;

/**
 * Provides options specific to the UiAutomator2 Driver.
 *
 * <p>For more details, refer to the
 * <a href="https://appium.github.io/appium-uiautomator2-driver/latest/reference/capabilities/">capabilities documentation</a></p>
 */
public class UiAutomator2Options extends BaseOptions<UiAutomator2Options> implements
        // Device Under Test options: https://appium.github.io/appium-uiautomator2-driver/latest/reference/capabilities/#device-under-test
        SupportsDeviceNameOption<UiAutomator2Options>,
        SupportsUdidOption<UiAutomator2Options>,
        SupportsSkipDeviceInitializationOption<UiAutomator2Options>,
        SupportsSkipLogcatCaptureOption<UiAutomator2Options>,
        SupportsClearDeviceLogsOnStartOption<UiAutomator2Options>,
        SupportsLogcatFormatOption<UiAutomator2Options>,
        SupportsLogcatFilterSpecsOption<UiAutomator2Options>,
        SupportsIgnoreHiddenApiPolicyErrorOption<UiAutomator2Options>,
        SupportsDisableSuppressAccessibilityServiceOption<UiAutomator2Options>,
        SupportsDisableWindowAnimationOption<UiAutomator2Options>,
        SupportsLanguageOption<UiAutomator2Options>,
        SupportsLocaleOption<UiAutomator2Options>,
        SupportsLocaleScriptOption<UiAutomator2Options>,
        SupportsOrientationOption<UiAutomator2Options>,
        SupportsGpsEnabledOption<UiAutomator2Options>,
        SupportsMockLocationAppOption<UiAutomator2Options>,
        SupportsSkipUnlockOption<UiAutomator2Options>,
        SupportsUnlockTypeOption<UiAutomator2Options>,
        SupportsUnlockKeyOption<UiAutomator2Options>,
        SupportsUnlockStrategyOption<UiAutomator2Options>,
        SupportsUnlockSuccessTimeoutOption<UiAutomator2Options>,
        // AVD options: https://appium.github.io/appium-uiautomator2-driver/latest/reference/capabilities/#emulator-avd
        SupportsAvdOption<UiAutomator2Options>,
        SupportsAvdLaunchTimeoutOption<UiAutomator2Options>,
        SupportsAvdReadyTimeoutOption<UiAutomator2Options>,
        SupportsAvdArgsOption<UiAutomator2Options>,
        SupportsAvdEnvOption<UiAutomator2Options>,
        SupportsIsHeadlessOption<UiAutomator2Options>,
        SupportsAllowDelayAdbOption<UiAutomator2Options>,
        SupportsNetworkSpeedOption<UiAutomator2Options>,
        // ADB options: https://appium.github.io/appium-uiautomator2-driver/latest/reference/capabilities/#adb
        SupportsAdbPortOption<UiAutomator2Options>,
        SupportsRemoteAdbHostOption<UiAutomator2Options>,
        SupportsAdbExecTimeoutOption<UiAutomator2Options>,
        SupportsBuildToolsVersionOption<UiAutomator2Options>,
        SupportsSuppressKillServerOption<UiAutomator2Options>,
        // UIA2 Server options: https://appium.github.io/appium-uiautomator2-driver/latest/reference/capabilities/#uiautomator2-server
        SupportsSystemPortOption<UiAutomator2Options>,
        SupportsUiautomator2ServerInstallTimeoutOption<UiAutomator2Options>,
        SupportsUiautomator2ServerLaunchTimeoutOption<UiAutomator2Options>,
        SupportsUiautomator2ServerReadTimeoutOption<UiAutomator2Options>,
        SupportsSkipServerInstallationOption<UiAutomator2Options>,
        SupportsMjpegServerPortOption<UiAutomator2Options>,
        SupportsMjpegScreenshotUrlOption<UiAutomator2Options>,
        // App management options: https://appium.github.io/appium-uiautomator2-driver/latest/reference/capabilities/#app-management
        SupportsAppOption<UiAutomator2Options>,
        SupportsAppPackageOption<UiAutomator2Options>,
        SupportsAppActivityOption<UiAutomator2Options>,
        SupportsAppWaitPackageOption<UiAutomator2Options>,
        SupportsAppWaitActivityOption<UiAutomator2Options>,
        SupportsAppWaitForLaunchOption<UiAutomator2Options>,
        SupportsAppWaitDurationOption<UiAutomator2Options>,
        SupportsIntentCategoryOption<UiAutomator2Options>,
        SupportsIntentActionOption<UiAutomator2Options>,
        SupportsIntentFlagsOption<UiAutomator2Options>,
        SupportsOptionalIntentArgumentsOption<UiAutomator2Options>,
        SupportsAndroidInstallTimeoutOption<UiAutomator2Options>,
        SupportsEnforceAppInstallOption<UiAutomator2Options>,
        SupportsAutoGrantPermissionsOption<UiAutomator2Options>,
        SupportsUserProfileOption<UiAutomator2Options>,
        SupportsOtherAppsOption<UiAutomator2Options>,
        SupportsUninstallOtherPackagesOption<UiAutomator2Options>,
        SupportsAllowTestPackagesOption<UiAutomator2Options>,
        SupportsRemoteAppsCacheLimitOption<UiAutomator2Options>,
        // App signing options: https://appium.github.io/appium-uiautomator2-driver/latest/reference/capabilities/#app-signing
        SupportsKeystoreOptions<UiAutomator2Options>,
        SupportsNoSignOption<UiAutomator2Options>,        
        // Web Context options: https://appium.github.io/appium-uiautomator2-driver/latest/reference/capabilities/#web-context
        SupportsAutoWebViewOption<UiAutomator2Options>,
        SupportsAutoWebviewTimeoutOption<UiAutomator2Options>,
        SupportsWebviewDevtoolsPortOption<UiAutomator2Options>,
        SupportsEnsureWebviewsHavePagesOption<UiAutomator2Options>,
        SupportsChromeOptionsOption<UiAutomator2Options>,
        SupportsChromeLoggingPrefsOption<UiAutomator2Options>,
        SupportsChromedriverPortOption<UiAutomator2Options>,
        SupportsChromedriverPortsOption<UiAutomator2Options>,
        SupportsChromedriverArgsOption<UiAutomator2Options>,
        SupportsChromedriverExecutableOption<UiAutomator2Options>,
        SupportsChromedriverExecutableDirOption<UiAutomator2Options>,
        SupportsChromedriverChromeMappingFileOption<UiAutomator2Options>,
        SupportsChromedriverUseSystemExecutableOption<UiAutomator2Options>,
        SupportsChromedriverDisableBuildCheckOption<UiAutomator2Options>,
        SupportsRecreateChromeDriverSessionsOption<UiAutomator2Options>,
        SupportsNativeWebScreenshotOption<UiAutomator2Options>,
        SupportsExtractChromeAndroidPackageFromContextNameOption<UiAutomator2Options>,
        SupportsShowChromedriverLogOption<UiAutomator2Options>,
        // Other options (not defined by the driver)
        SupportsClearSystemFilesOption<UiAutomator2Options>,
        SupportsEnablePerformanceLoggingOption<UiAutomator2Options>,
        SupportsSkipLogCaptureOption<UiAutomator2Options> {
    /**
     * Creates options with the default platform and automation names set.
     */
    public UiAutomator2Options() {
        setCommonOptions();
    }

    /**
     * Creates options with the default platform and automation names set,
     * and copies the capabilities from the given source.
     *
     * @param source Capabilities to copy.
     */
    public UiAutomator2Options(Capabilities source) {
        super(source);
        setCommonOptions();
    }

    /**
     * Creates options with the default platform and automation names set,
     * and copies the capabilities from the given map.
     *
     * @param source Capabilities map to copy.
     */
    public UiAutomator2Options(Map<String, ?> source) {
        super(source);
        setCommonOptions();
    }

    private void setCommonOptions() {
        setPlatformName(MobilePlatform.ANDROID);
        setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);
    }

    /**
     * Skips capturing system logs (iOS only).
     *
     * @return self instance for chaining.
     * @deprecated Not an Android capability, use {@code skipLogcatCapture} instead.
     */
    @Override
    @Deprecated
    public UiAutomator2Options skipLogCapture() {
        return SupportsSkipLogCaptureOption.super.skipLogCapture();
    }

    /**
     * Sets whether to skip capturing system logs (iOS only).
     *
     * @param value Capability value.
     * @return self instance for chaining.
     * @deprecated Not an Android capability, use {@code skipLogcatCapture} instead.
     */
    @Override
    @Deprecated
    public UiAutomator2Options setSkipLogCapture(boolean value) {
        return SupportsSkipLogCaptureOption.super.setSkipLogCapture(value);
    }

    /**
     * Gets whether to skip capturing system logs (iOS only).
     *
     * @return the value.
     * @deprecated Not an Android capability, use {@code skipLogcatCapture} instead.
     */
    @Override
    @Deprecated
    public Optional<Boolean> doesSkipLogCapture() {
        return SupportsSkipLogCaptureOption.super.doesSkipLogCapture();
    }

    /**
     * Clears system files (iOS only).
     *
     * @return self instance for chaining.
     * @deprecated The Android drivers never read this capability.
     */
    @Override
    @Deprecated
    public UiAutomator2Options clearSystemFiles() {
        return SupportsClearSystemFilesOption.super.clearSystemFiles();
    }

    /**
     * Sets whether to clear system files (iOS only).
     *
     * @param bool Capability value.
     * @return self instance for chaining.
     * @deprecated The Android drivers never read this capability.
     */
    @Override
    @Deprecated
    public UiAutomator2Options setClearSystemFiles(boolean bool) {
        return SupportsClearSystemFilesOption.super.setClearSystemFiles(bool);
    }

    /**
     * Gets whether to clear system files (iOS only).
     *
     * @return the value.
     * @deprecated The Android drivers never read this capability.
     */
    @Override
    @Deprecated
    public Optional<Boolean> doesClearSystemFiles() {
        return SupportsClearSystemFilesOption.super.doesClearSystemFiles();
    }

    /**
     * Enables performance logging.
     *
     * @return self instance for chaining.
     * @deprecated Deprecated by the Android driver, use {@code chromeLoggingPrefs} with {@code performance: ALL}.
     */
    @Override
    @Deprecated
    public UiAutomator2Options enablePerformanceLogging() {
        return SupportsEnablePerformanceLoggingOption.super.enablePerformanceLogging();
    }

    /**
     * Sets whether to enable performance logging.
     *
     * @param bool Capability value.
     * @return self instance for chaining.
     * @deprecated Deprecated by the Android driver, use {@code chromeLoggingPrefs} with {@code performance: ALL}.
     */
    @Override
    @Deprecated
    public UiAutomator2Options setEnablePerformanceLogging(boolean bool) {
        return SupportsEnablePerformanceLoggingOption.super.setEnablePerformanceLogging(bool);
    }

    /**
     * Gets whether performance logging is enabled.
     *
     * @return the value.
     * @deprecated Deprecated by the Android driver, use {@code chromeLoggingPrefs} with {@code performance: ALL}.
     */
    @Override
    @Deprecated
    public Optional<Boolean> isEnablePerformanceLogging() {
        return SupportsEnablePerformanceLoggingOption.super.isEnablePerformanceLogging();
    }
}
