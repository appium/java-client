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

import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.time.Duration;
import java.util.Optional;

public interface SupportsSimulatorStartupTimeoutOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SIMULATOR_STARTUP_TIMEOUT_OPTION = "simulatorStartupTimeout";

    /**
     * Allows to change the default timeout for Simulator startup.
     * By default, this value is set to 120000ms (2 minutes),
     * although the startup could take longer on a weak hardware
     * or if other concurrent processes use much system resources
     * during the boot up procedure.
     *
     * @param timeout Simulator startup timeout.
     * @return self instance for chaining.
     */
    default T setSimulatorStartupTimeout(Duration timeout) {
        return amend(SIMULATOR_STARTUP_TIMEOUT_OPTION, timeout.toMillis());
    }

    /**
     * Get the Simulator startup timeout.
     *
     * @return Timeout value.
     */
    default Optional<Duration> getSimulatorStartupTimeout() {
        return Optional.ofNullable(
                CapabilityHelpers.toDuration(getCapability(SIMULATOR_STARTUP_TIMEOUT_OPTION))
        );
    }
}
