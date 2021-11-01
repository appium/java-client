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

package io.appium.java_client.gecko.options;

import io.appium.java_client.mac.options.SupportsSystemPortOption;
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

import java.util.Map;

/**
 * https://github.com/appium/appium-geckodriver#usage
 */
public class GeckoOptions extends BaseOptions<GeckoOptions> implements
        SupportsBrowserNameOption<GeckoOptions>,
        SupportsBrowserVersionOption<GeckoOptions>,
        SupportsMarionettePortOption<GeckoOptions>,
        SupportsSystemPortOption<GeckoOptions>,
        SupportsVerbosityOption<GeckoOptions>,
        SupportsAndroidStorageOption<GeckoOptions>,
        SupportsMozFirefoxOptionsOption<GeckoOptions>,
        SupportsAcceptInsecureCertsOption<GeckoOptions>,
        SupportsPageLoadStrategyOption<GeckoOptions>,
        SupportsSetWindowRectOption<GeckoOptions>,
        SupportsProxyOption<GeckoOptions>,
        SupportsUnhandledPromptBehaviorOption<GeckoOptions> {
    public GeckoOptions() {
        setCommonOptions();
    }

    public GeckoOptions(Capabilities source) {
        super(source);
        setCommonOptions();
    }

    public GeckoOptions(Map<String, ?> source) {
        super(source);
        setCommonOptions();
    }

    private void setCommonOptions() {
        setAutomationName(AutomationName.GECKO);
    }
}
