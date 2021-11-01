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

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsSafariUseSimulatorOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SAFARI_USE_SIMULATOR_OPTION = "safari:useSimulator";

    /**
     * Enforces safaridriver to use iOS Simulator.
     *
     * @return self instance for chaining.
     */
    default T safariUseSimulator() {
        return amend(SAFARI_USE_SIMULATOR_OPTION, true);
    }

    /**
     * If the value of safari:useSimulator is true, safaridriver will only use
     * iOS Simulator hosts. If the value of safari:useSimulator is false, safaridriver
     * will not use iOS Simulator hosts. NOTE: An Xcode installation is required
     * in order to run WebDriver tests on iOS Simulator hosts.
     *
     * @param bool is whether to use iOS Simulator.
     * @return self instance for chaining.
     */
    default T setSafariUseSimulator(boolean bool) {
        return amend(SAFARI_USE_SIMULATOR_OPTION, bool);
    }

    /**
     * Get whether to use iOS Simulator.
     *
     * @return true or false.
     */
    default Optional<Boolean> doesSafariUseSimulator() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SAFARI_USE_SIMULATOR_OPTION)));
    }
}
