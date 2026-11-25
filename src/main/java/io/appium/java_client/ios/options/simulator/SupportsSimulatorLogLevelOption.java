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

/**
 * Provides getters and setters for the {@code simulatorLogLevel} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsSimulatorLogLevelOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code simulatorLogLevel} capability.
     */
    String SIMULATOR_LOG_LEVEL_OPTION = "simulatorLogLevel";

    /**
     * Customizes the minimum log level for logs collected from simulators.
     * Possible values are {@code default} (the default), {@code info}, and {@code debug}.
     *
     * @param level Simulator log level.
     * @return self instance for chaining.
     */
    default T setSimulatorLogLevel(String level) {
        return amend(SIMULATOR_LOG_LEVEL_OPTION, level);
    }

    /**
     * Get the minimum log level for simulator logs.
     *
     * @return Simulator log level.
     */
    default Optional<String> getSimulatorLogLevel() {
        return Optional.ofNullable((String) getCapability(SIMULATOR_LOG_LEVEL_OPTION));
    }
}
