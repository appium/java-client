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

package io.appium.java_client.ios.options.wda;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsUseNewWdaOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String USE_NEW_WDA_OPTION = "useNewWDA";

    /**
     * Enforce uninstall of any existing WebDriverAgent app on the device under test.
     *
     * @return self instance for chaining.
     */
    default T useNewWDA() {
        return amend(USE_NEW_WDA_OPTION, true);
    }

    /**
     * If true, forces uninstall of any existing WebDriverAgent app on device.
     * Set it to true if you want to apply different startup options for WebDriverAgent
     * for each session. Although, it is only guaranteed to work stable on Simulator.
     * Real devices require WebDriverAgent client to run for as long as possible without
     * reinstall/restart to avoid issues like
     * https://github.com/facebook/WebDriverAgent/issues/507. The false value
     * (the default behaviour since driver version 2.35.0) will try to
     * detect currently running WDA listener executed by previous testing session(s)
     * and reuse it if possible, which is highly recommended for real device testing
     * and to speed up suites of multiple tests in general. A new WDA session will be
     * triggered at the default URL (http://localhost:8100) if WDA is not listening and
     * webDriverAgentUrl capability is not set. The negative/unset value of useNewWDA
     * capability has no effect prior to xcuitest driver version 2.35.0.
     *
     * @param value Whether to force uninstall of any existing WebDriverAgent app on device.
     * @return self instance for chaining.
     */
    default T setUseNewWDA(boolean value) {
        return amend(USE_NEW_WDA_OPTION, value);
    }

    /**
     * Get whether to uninstall of any existing WebDriverAgent app on the device under test.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesUseNewWDA() {
        return Optional.ofNullable(toSafeBoolean(getCapability(USE_NEW_WDA_OPTION)));
    }
}
