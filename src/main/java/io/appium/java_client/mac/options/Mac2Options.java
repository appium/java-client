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

package io.appium.java_client.mac.options;

import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.remote.options.BaseOptions;
import org.openqa.selenium.Capabilities;

import java.util.Map;

/**
 * https://github.com/appium/appium-mac2-driver#capabilities
 */
public class Mac2Options extends BaseOptions<Mac2Options> implements
        SupportsSystemPortOption<Mac2Options>,
        SupportsSystemHostOption<Mac2Options>,
        SupportsWebDriverAgentMacUrlOption<Mac2Options>,
        SupportsBootstrapRootOption<Mac2Options>,
        SupportsBundleIdOption<Mac2Options>,
        SupportsArgumentsOption<Mac2Options>,
        SupportsEnvironmentOption<Mac2Options>,
        SupportsServerStartupTimeoutOption<Mac2Options>,
        SupportsSkipAppKillOption<Mac2Options>,
        SupportsShowServerLogsOption<Mac2Options>,
        SupportsPrerunOption<Mac2Options>,
        SupportsPostrunOption<Mac2Options> {
    public Mac2Options() {
        setCommonOptions();
    }

    public Mac2Options(Capabilities source) {
        super(source);
        setCommonOptions();
    }

    public Mac2Options(Map<String, ?> source) {
        super(source);
        setCommonOptions();
    }

    private void setCommonOptions() {
        setPlatformName(MobilePlatform.MAC);
        setAutomationName(AutomationName.MAC2);
    }
}
