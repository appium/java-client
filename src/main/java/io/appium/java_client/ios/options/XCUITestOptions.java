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
        // TODO: WebDriverAgent options: https://github.com/appium/appium-xcuitest-driver#webdriveragent
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
