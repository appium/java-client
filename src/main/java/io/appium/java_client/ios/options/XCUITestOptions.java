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

package io.appium.java_client.ios.options;

import io.appium.java_client.ios.options.app.SupportsAppInstallStrategyOption;
import io.appium.java_client.ios.options.app.SupportsAppPushTimeoutOption;
import io.appium.java_client.ios.options.app.SupportsBundleIdOption;
import io.appium.java_client.ios.options.app.SupportsLocalizableStringsDirOption;
import io.appium.java_client.ios.options.general.SupportsIncludeDeviceCapsToSessionInfoOption;
import io.appium.java_client.ios.options.general.SupportsResetLocationServiceOption;
import io.appium.java_client.ios.options.other.SupportsCommandTimeoutsOption;
import io.appium.java_client.ios.options.other.SupportsLaunchWithIdbOption;
import io.appium.java_client.ios.options.other.SupportsResetOnSessionStartOnlyOption;
import io.appium.java_client.ios.options.other.SupportsShowIosLogOption;
import io.appium.java_client.ios.options.other.SupportsUseJsonSourceOption;
import io.appium.java_client.ios.options.simulator.SupportsCalendarAccessAuthorizedOption;
import io.appium.java_client.ios.options.simulator.SupportsCalendarFormatOption;
import io.appium.java_client.ios.options.simulator.SupportsConnectHardwareKeyboardOption;
import io.appium.java_client.ios.options.simulator.SupportsCustomSslCertOption;
import io.appium.java_client.ios.options.simulator.SupportsEnforceFreshSimulatorCreationOption;
import io.appium.java_client.ios.options.simulator.SupportsForceSimulatorSoftwareKeyboardPresenceOption;
import io.appium.java_client.ios.options.simulator.SupportsIosSimulatorLogsPredicateOption;
import io.appium.java_client.ios.options.simulator.SupportsKeepKeyChainsOption;
import io.appium.java_client.ios.options.simulator.SupportsKeychainsExcludePatternsOption;
import io.appium.java_client.ios.options.simulator.SupportsPermissionsOption;
import io.appium.java_client.ios.options.simulator.SupportsReduceMotionOption;
import io.appium.java_client.ios.options.simulator.SupportsScaleFactorOption;
import io.appium.java_client.ios.options.simulator.SupportsShutdownOtherSimulatorsOption;
import io.appium.java_client.ios.options.simulator.SupportsSimulatorDevicesSetPathOption;
import io.appium.java_client.ios.options.simulator.SupportsSimulatorPasteboardAutomaticSyncOption;
import io.appium.java_client.ios.options.simulator.SupportsSimulatorStartupTimeoutOption;
import io.appium.java_client.ios.options.simulator.SupportsSimulatorTracePointerOption;
import io.appium.java_client.ios.options.simulator.SupportsSimulatorWindowCenterOption;
import io.appium.java_client.ios.options.simulator.SupportsWebkitResponseTimeoutOption;
import io.appium.java_client.ios.options.wda.SupportsAllowProvisioningDeviceRegistrationOption;
import io.appium.java_client.ios.options.wda.SupportsAutoAcceptAlertsOption;
import io.appium.java_client.ios.options.wda.SupportsAutoDismissAlertsOption;
import io.appium.java_client.ios.options.wda.SupportsDerivedDataPathOption;
import io.appium.java_client.ios.options.wda.SupportsDisableAutomaticScreenshotsOption;
import io.appium.java_client.ios.options.wda.SupportsForceAppLaunchOption;
import io.appium.java_client.ios.options.wda.SupportsKeychainOptions;
import io.appium.java_client.ios.options.wda.SupportsMaxTypingFrequencyOption;
import io.appium.java_client.ios.options.wda.SupportsMjpegServerPortOption;
import io.appium.java_client.ios.options.wda.SupportsProcessArgumentsOption;
import io.appium.java_client.ios.options.wda.SupportsResultBundlePathOption;
import io.appium.java_client.ios.options.wda.SupportsScreenshotQualityOption;
import io.appium.java_client.ios.options.wda.SupportsShouldTerminateAppOption;
import io.appium.java_client.ios.options.wda.SupportsShouldUseSingletonTestManagerOption;
import io.appium.java_client.ios.options.wda.SupportsShowXcodeLogOption;
import io.appium.java_client.ios.options.wda.SupportsSimpleIsVisibleCheckOption;
import io.appium.java_client.ios.options.wda.SupportsUpdatedWdaBundleIdOption;
import io.appium.java_client.ios.options.wda.SupportsUseNativeCachingStrategyOption;
import io.appium.java_client.ios.options.wda.SupportsUseNewWdaOption;
import io.appium.java_client.ios.options.wda.SupportsUsePrebuiltWdaOption;
import io.appium.java_client.ios.options.wda.SupportsUseSimpleBuildTestOption;
import io.appium.java_client.ios.options.wda.SupportsUseXctestrunFileOption;
import io.appium.java_client.ios.options.wda.SupportsWaitForIdleTimeoutOption;
import io.appium.java_client.ios.options.wda.SupportsWaitForQuiescenceOption;
import io.appium.java_client.ios.options.wda.SupportsWdaBaseUrlOption;
import io.appium.java_client.ios.options.wda.SupportsWdaConnectionTimeoutOption;
import io.appium.java_client.ios.options.wda.SupportsWdaEventloopIdleDelayOption;
import io.appium.java_client.ios.options.wda.SupportsWdaLaunchTimeoutOption;
import io.appium.java_client.ios.options.wda.SupportsWdaLocalPortOption;
import io.appium.java_client.ios.options.wda.SupportsWdaStartupRetriesOption;
import io.appium.java_client.ios.options.wda.SupportsWdaStartupRetryIntervalOption;
import io.appium.java_client.ios.options.wda.SupportsWebDriverAgentUrlOption;
import io.appium.java_client.ios.options.wda.SupportsXcodeCertificateOptions;
import io.appium.java_client.ios.options.webview.SupportsAbsoluteWebLocationsOption;
import io.appium.java_client.ios.options.webview.SupportsAdditionalWebviewBundleIdsOption;
import io.appium.java_client.ios.options.webview.SupportsEnableAsyncExecuteFromHttpsOption;
import io.appium.java_client.ios.options.webview.SupportsFullContextListOption;
import io.appium.java_client.ios.options.webview.SupportsIncludeSafariInWebviewsOption;
import io.appium.java_client.ios.options.webview.SupportsNativeWebTapOption;
import io.appium.java_client.ios.options.webview.SupportsNativeWebTapStrictOption;
import io.appium.java_client.ios.options.webview.SupportsSafariAllowPopupsOption;
import io.appium.java_client.ios.options.webview.SupportsSafariGarbageCollectOption;
import io.appium.java_client.ios.options.webview.SupportsSafariIgnoreFraudWarningOption;
import io.appium.java_client.ios.options.webview.SupportsSafariIgnoreWebHostnamesOption;
import io.appium.java_client.ios.options.webview.SupportsSafariInitialUrlOption;
import io.appium.java_client.ios.options.webview.SupportsSafariLogAllCommunicationHexDumpOption;
import io.appium.java_client.ios.options.webview.SupportsSafariLogAllCommunicationOption;
import io.appium.java_client.ios.options.webview.SupportsSafariOpenLinksInBackgroundOption;
import io.appium.java_client.ios.options.webview.SupportsSafariSocketChunkSizeOption;
import io.appium.java_client.ios.options.webview.SupportsSafariWebInspectorMaxFrameLengthOption;
import io.appium.java_client.ios.options.webview.SupportsWebviewConnectRetriesOption;
import io.appium.java_client.ios.options.webview.SupportsWebviewConnectTimeoutOption;
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

/**
 * Provides options specific to the XCUITest Driver.
 *
 * <p>For more details, refer to the
 * <a href="https://appium.github.io/appium-xcuitest-driver/latest/reference/capabilities/">capabilities documentation</a></p>
 */
public class XCUITestOptions extends BaseOptions<XCUITestOptions> implements
        // General options: https://github.com/appium/appium-xcuitest-driver#general
        SupportsDeviceNameOption<XCUITestOptions>,
        SupportsUdidOption<XCUITestOptions>,
        SupportsIncludeDeviceCapsToSessionInfoOption<XCUITestOptions>,
        SupportsResetLocationServiceOption<XCUITestOptions>,
        // Localization Options
        SupportsLocalizableStringsDirOption<XCUITestOptions>,
        SupportsLanguageOption<XCUITestOptions>,
        SupportsLocaleOption<XCUITestOptions>,
        // App Options: https://github.com/appium/appium-xcuitest-driver#app
        SupportsAppOption<XCUITestOptions>,
        SupportsBundleIdOption<XCUITestOptions>,
        SupportsOtherAppsOption<XCUITestOptions>,
        SupportsAppPushTimeoutOption<XCUITestOptions>,
        SupportsAppInstallStrategyOption<XCUITestOptions>,
        SupportsEnforceAppInstallOption<XCUITestOptions>,
        // WebDriverAgent options: https://github.com/appium/appium-xcuitest-driver#webdriveragent
        SupportsXcodeCertificateOptions<XCUITestOptions>,
        SupportsKeychainOptions<XCUITestOptions>,
        SupportsUpdatedWdaBundleIdOption<XCUITestOptions>,
        SupportsDerivedDataPathOption<XCUITestOptions>,
        SupportsWebDriverAgentUrlOption<XCUITestOptions>,
        SupportsUseNewWdaOption<XCUITestOptions>,
        SupportsWdaLaunchTimeoutOption<XCUITestOptions>,
        SupportsWdaConnectionTimeoutOption<XCUITestOptions>,
        SupportsWdaStartupRetriesOption<XCUITestOptions>,
        SupportsWdaStartupRetryIntervalOption<XCUITestOptions>,
        SupportsWdaLocalPortOption<XCUITestOptions>,
        SupportsWdaBaseUrlOption<XCUITestOptions>,
        SupportsShowXcodeLogOption<XCUITestOptions>,
        SupportsUsePrebuiltWdaOption<XCUITestOptions>,
        SupportsShouldUseSingletonTestManagerOption<XCUITestOptions>,
        SupportsWaitForIdleTimeoutOption<XCUITestOptions>,
        SupportsUseXctestrunFileOption<XCUITestOptions>,
        SupportsUseSimpleBuildTestOption<XCUITestOptions>,
        SupportsWdaEventloopIdleDelayOption<XCUITestOptions>,
        SupportsProcessArgumentsOption<XCUITestOptions>,
        SupportsAllowProvisioningDeviceRegistrationOption<XCUITestOptions>,
        SupportsResultBundlePathOption<XCUITestOptions>,
        SupportsMaxTypingFrequencyOption<XCUITestOptions>,
        SupportsSimpleIsVisibleCheckOption<XCUITestOptions>,
        SupportsWaitForQuiescenceOption<XCUITestOptions>,
        SupportsMjpegServerPortOption<XCUITestOptions>,
        SupportsScreenshotQualityOption<XCUITestOptions>,
        SupportsAutoAcceptAlertsOption<XCUITestOptions>,
        SupportsAutoDismissAlertsOption<XCUITestOptions>,
        SupportsDisableAutomaticScreenshotsOption<XCUITestOptions>,
        SupportsShouldTerminateAppOption<XCUITestOptions>,
        SupportsForceAppLaunchOption<XCUITestOptions>,
        SupportsUseNativeCachingStrategyOption<XCUITestOptions>,
        // Simulator options: https://github.com/appium/appium-xcuitest-driver#simulator
        SupportsOrientationOption<XCUITestOptions>,
        SupportsScaleFactorOption<XCUITestOptions>,
        SupportsConnectHardwareKeyboardOption<XCUITestOptions>,
        SupportsForceSimulatorSoftwareKeyboardPresenceOption<XCUITestOptions>,
        SupportsCalendarAccessAuthorizedOption<XCUITestOptions>,
        SupportsCalendarFormatOption<XCUITestOptions>,
        SupportsIsHeadlessOption<XCUITestOptions>,
        SupportsSimulatorWindowCenterOption<XCUITestOptions>,
        SupportsSimulatorStartupTimeoutOption<XCUITestOptions>,
        SupportsSimulatorTracePointerOption<XCUITestOptions>,
        SupportsShutdownOtherSimulatorsOption<XCUITestOptions>,
        SupportsEnforceFreshSimulatorCreationOption<XCUITestOptions>,
        SupportsKeepKeyChainsOption<XCUITestOptions>,
        SupportsKeychainsExcludePatternsOption<XCUITestOptions>,
        SupportsReduceMotionOption<XCUITestOptions>,
        SupportsPermissionsOption<XCUITestOptions>,
        SupportsIosSimulatorLogsPredicateOption<XCUITestOptions>,
        SupportsSimulatorPasteboardAutomaticSyncOption<XCUITestOptions>,
        SupportsSimulatorDevicesSetPathOption<XCUITestOptions>,
        SupportsCustomSslCertOption<XCUITestOptions>,
        // Web context options: https://github.com/appium/appium-xcuitest-driver#web-context
        SupportsAutoWebViewOption<XCUITestOptions>,
        SupportsAbsoluteWebLocationsOption<XCUITestOptions>,
        SupportsSafariGarbageCollectOption<XCUITestOptions>,
        SupportsIncludeSafariInWebviewsOption<XCUITestOptions>,
        SupportsSafariLogAllCommunicationOption<XCUITestOptions>,
        SupportsSafariLogAllCommunicationHexDumpOption<XCUITestOptions>,
        SupportsSafariSocketChunkSizeOption<XCUITestOptions>,
        SupportsSafariWebInspectorMaxFrameLengthOption<XCUITestOptions>,
        SupportsAdditionalWebviewBundleIdsOption<XCUITestOptions>,
        SupportsWebviewConnectTimeoutOption<XCUITestOptions>,
        SupportsSafariIgnoreWebHostnamesOption<XCUITestOptions>,
        SupportsNativeWebTapOption<XCUITestOptions>,
        SupportsNativeWebTapStrictOption<XCUITestOptions>,
        SupportsSafariInitialUrlOption<XCUITestOptions>,
        SupportsSafariAllowPopupsOption<XCUITestOptions>,
        SupportsSafariIgnoreFraudWarningOption<XCUITestOptions>,
        SupportsSafariOpenLinksInBackgroundOption<XCUITestOptions>,
        SupportsWebviewConnectRetriesOption<XCUITestOptions>,
        SupportsWebkitResponseTimeoutOption<XCUITestOptions>,
        SupportsEnableAsyncExecuteFromHttpsOption<XCUITestOptions>,
        SupportsFullContextListOption<XCUITestOptions>,
        SupportsEnablePerformanceLoggingOption<XCUITestOptions>,
        // Other options: https://github.com/appium/appium-xcuitest-driver#other
        SupportsResetOnSessionStartOnlyOption<XCUITestOptions>,
        SupportsCommandTimeoutsOption<XCUITestOptions>,
        SupportsUseJsonSourceOption<XCUITestOptions>,
        SupportsSkipLogCaptureOption<XCUITestOptions>,
        SupportsLaunchWithIdbOption<XCUITestOptions>,
        SupportsShowIosLogOption<XCUITestOptions>,
        SupportsClearSystemFilesOption<XCUITestOptions> {

    public XCUITestOptions() {
        setCommonOptions();
    }

    public XCUITestOptions(Capabilities source) {
        super(source);
        setCommonOptions();
    }

    public XCUITestOptions(Map<String, ?> source) {
        super(source);
        setCommonOptions();
    }

    private void setCommonOptions() {
        setPlatformName(MobilePlatform.IOS);
        setAutomationName(AutomationName.IOS_XCUI_TEST);
    }
}
