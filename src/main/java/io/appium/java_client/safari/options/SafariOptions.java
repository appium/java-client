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

package io.appium.java_client.safari.options;

import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.SupportsAcceptInsecureCertsOption;
import io.appium.java_client.remote.options.SupportsBrowserNameOption;
import io.appium.java_client.remote.options.SupportsBrowserVersionOption;
import io.appium.java_client.remote.options.SupportsPageLoadStrategyOption;
import io.appium.java_client.remote.options.SupportsProxyOption;
import io.appium.java_client.remote.options.SupportsSetWindowRectOption;
import io.appium.java_client.remote.options.SupportsUnhandledPromptBehaviorOption;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;

import java.util.Map;

/**
 * Provides options specific to the Safari Driver.
 *
 * <p>For more details, refer to the
 * <a href="https://github.com/appium/appium-safari-driver#usage">capabilities documentation</a></p>
 */
public class SafariOptions extends BaseOptions<SafariOptions> implements
        SupportsBrowserNameOption<SafariOptions>,
        SupportsBrowserVersionOption<SafariOptions>,
        SupportsSafariPlatformVersionOption<SafariOptions>,
        SupportsSafariPlatformBuildVersionOption<SafariOptions>,
        SupportsSafariUseSimulatorOption<SafariOptions>,
        SupportsSafariDeviceTypeOption<SafariOptions>,
        SupportsSafariDeviceNameOption<SafariOptions>,
        SupportsSafariDeviceUdidOption<SafariOptions>,
        SupportsSafariAutomaticInspectionOption<SafariOptions>,
        SupportsSafariAutomaticProfilingOption<SafariOptions>,
        SupportsWebkitWebrtcOption<SafariOptions>,
        SupportsAcceptInsecureCertsOption<SafariOptions>,
        SupportsPageLoadStrategyOption<SafariOptions>,
        SupportsSetWindowRectOption<SafariOptions>,
        SupportsProxyOption<SafariOptions>,
        SupportsUnhandledPromptBehaviorOption<SafariOptions> {
    public SafariOptions() {
        setCommonOptions();
    }

    public SafariOptions(Capabilities source) {
        super(source);
        setCommonOptions();
    }

    public SafariOptions(Map<String, ?> source) {
        super(source);
        setCommonOptions();
    }

    private void setCommonOptions() {
        setPlatformName(Platform.IOS.toString());
        setAutomationName(AutomationName.SAFARI);
    }
}
