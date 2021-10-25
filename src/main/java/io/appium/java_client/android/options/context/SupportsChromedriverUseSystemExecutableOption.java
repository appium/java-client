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

package io.appium.java_client.android.options.context;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsChromedriverUseSystemExecutableOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String CHROMEDRIVER_USE_SYSTEM_EXECUTABLE_OPTION = "chromedriverUseSystemExecutable";

    /**
     * Enforce the usage of chromedriver,
     * which gets downloaded by Appium automatically upon installation.
     *
     * @return self instance for chaining.
     */
    default T chromedriverUseSystemExecutable() {
        return amend(CHROMEDRIVER_USE_SYSTEM_EXECUTABLE_OPTION, true);
    }

    /**
     * Set it to true in order to enforce the usage of chromedriver, which gets
     * downloaded by Appium automatically upon installation. This driver might not
     * be compatible with the destination browser or a web view. false by default.
     *
     * @param value Whether to use the system chromedriver.
     * @return self instance for chaining.
     */
    default T setChromedriverUseSystemExecutable(boolean value) {
        return amend(CHROMEDRIVER_USE_SYSTEM_EXECUTABLE_OPTION, value);
    }

    /**
     * Get whether to use the system chromedriver.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesChromedriverUseSystemExecutable() {
        return Optional.ofNullable(toSafeBoolean(getCapability(CHROMEDRIVER_USE_SYSTEM_EXECUTABLE_OPTION)));
    }
}
