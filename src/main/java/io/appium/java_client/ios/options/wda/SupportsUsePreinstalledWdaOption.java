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

public interface SupportsUsePreinstalledWdaOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String USE_PREINSTALLED_WDA_OPTION = "usePreinstalledWDA";

    /**
     * Whether to launch a preinstalled WebDriverAgentRunner application using a custom XCTest API client.
     *
     * @return self instance for chaining.
     */
    default T usePreinstalledWda() {
        return amend(USE_PREINSTALLED_WDA_OPTION, true);
    }

    /**
     * Whether to launch a preinstalled WebDriverAgentRunner application using a custom XCTest API client.
     * Defaults to false.
     *
     * @param value Either true or false.
     * @return self instance for chaining.
     */
    default T setUsePreinstalledWda(boolean value) {
        return amend(USE_PREINSTALLED_WDA_OPTION, value);
    }

    /**
     * Get whether to launch a preinstalled WebDriverAgentRunner application using a custom XCTest API client.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesUsePreinstalledWda() {
        return Optional.ofNullable(toSafeBoolean(getCapability(USE_PREINSTALLED_WDA_OPTION)));
    }
}
