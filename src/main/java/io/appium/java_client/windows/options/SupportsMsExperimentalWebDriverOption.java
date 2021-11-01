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

package io.appium.java_client.windows.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsMsExperimentalWebDriverOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String MS_EXPERIMENTAL_WEBDRIVER_OPTION = "ms:experimental-webdriver";

    /**
     * Enforce to enable experimental driver features and optimizations.
     *
     * @return self instance for chaining.
     */
    default T experimentalWebDriver() {
        return amend(MS_EXPERIMENTAL_WEBDRIVER_OPTION, true);
    }

    /**
     * Enables experimental features and optimizations. See Appium Windows
     * Driver release notes for more details on this capability. false by default.
     *
     * @param value Whether to enable experimental features and optimizations.
     * @return self instance for chaining.
     */
    default T setExperimentalWebDriver(boolean value) {
        return amend(MS_EXPERIMENTAL_WEBDRIVER_OPTION, value);
    }

    /**
     * Get whether to enable experimental features and optimizations.
     *
     * @return True or false.
     */
    default Optional<Boolean> isExperimentalWebDriver() {
        return Optional.ofNullable(toSafeBoolean(getCapability(MS_EXPERIMENTAL_WEBDRIVER_OPTION)));
    }
}
