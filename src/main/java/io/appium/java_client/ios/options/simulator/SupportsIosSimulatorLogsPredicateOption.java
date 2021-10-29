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

package io.appium.java_client.ios.options.simulator;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsIosSimulatorLogsPredicateOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String IOS_SIMULATOR_LOGS_PREDICATE_OPTION = "iosSimulatorLogsPredicate";

    /**
     * Set the --predicate flag in the ios simulator logs.
     *
     * @param predicate Predicate value, e.g. 'process != "locationd" AND process != "DTServiceHub"'.
     * @return self instance for chaining.
     */
    default T setIosSimulatorLogsPredicate(String predicate) {
        return amend(IOS_SIMULATOR_LOGS_PREDICATE_OPTION, predicate);
    }

    /**
     * Get Simulator log filtering predicate.
     *
     * @return Predicate value.
     */
    default Optional<String> getIosSimulatorLogsPredicate() {
        return Optional.ofNullable((String) getCapability(IOS_SIMULATOR_LOGS_PREDICATE_OPTION));
    }
}
