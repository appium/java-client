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

/**
 * https://github.com/appium/appium-xcuitest-driver#capabilities
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
        // TODO: Simulator options: https://github.com/appium/appium-xcuitest-driver#simulator
        SupportsOrientationOption<XCUITestOptions>,
        // TODO: Web context options: https://github.com/appium/appium-xcuitest-driver#web-context
        SupportsAutoWebViewOption<XCUITestOptions>,
        // TODO: Other options: https://github.com/appium/appium-xcuitest-driver#other
        SupportsClearSystemFilesOption<XCUITestOptions>,
        SupportsEnablePerformanceLoggingOption<XCUITestOptions> {

    public XCUITestOptions() {
        setCommonOptions();
    }

    public XCUITestOptions(Capabilities source) {
        super(source);
        setCommonOptions();
    }

    private void setCommonOptions() {
        setPlatformName(MobilePlatform.IOS);
        setAutomationName(AutomationName.IOS_XCUI_TEST);
    }
}
