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
import io.appium.java_client.remote.options.SupportsPostrunOption;
import io.appium.java_client.remote.options.SupportsPrerunOption;
import org.openqa.selenium.Capabilities;

import java.util.Map;
import java.util.Optional;

/**
 * Provides options specific to the Appium Mac2 Driver.
 *
 * <p>For more details, refer to the
 * <a href="https://github.com/appium/appium-mac2-driver#capabilities">capabilities documentation</a></p>
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
        SupportsPrerunOption<Mac2Options, AppleScriptData>,
        SupportsPostrunOption<Mac2Options, AppleScriptData> {
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

    /**
     * An object containing either script or command key. The value of
     * each key must be a valid AppleScript script or command to be
     * executed after before Mac2Driver session is started. See
     * https://github.com/appium/appium-mac2-driver#applescript-commands-execution
     * for more details.
     *
     * @param script A valid AppleScript snippet.
     * @return self instance for chaining.
     */
    public Mac2Options setPrerun(AppleScriptData script) {
        return amend(PRERUN_OPTION, script.toMap());
    }

    /**
     * Get the prerun script.
     *
     * @return Prerun script.
     */
    public Optional<AppleScriptData> getPrerun() {
        //noinspection unchecked
        return Optional.ofNullable(getCapability(PRERUN_OPTION))
                .map(v -> new AppleScriptData((Map<String, Object>) v));
    }

    /**
     * An object containing either script or command key. The value of
     * each key must be a valid AppleScript script or command to be
     * executed after Mac2Driver session is stopped. See
     * https://github.com/appium/appium-mac2-driver#applescript-commands-execution
     * for more details.
     *
     * @param script A valid AppleScript snippet.
     * @return self instance for chaining.
     */
    public Mac2Options setPostrun(AppleScriptData script) {
        return amend(POSTRUN_OPTION, script.toMap());
    }

    /**
     * Get the postrun script.
     *
     * @return Postrun script.
     */
    public Optional<AppleScriptData> getPostrun() {
        //noinspection unchecked
        return Optional.ofNullable(getCapability(POSTRUN_OPTION))
                .map(v -> new AppleScriptData((Map<String, Object>) v));
    }
}
