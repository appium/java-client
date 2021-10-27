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

public interface SupportsSimulatorWindowCenterOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SIMULATOR_WINDOW_CENTER_OPTION = "simulatorWindowCenter";

    /**
     * Allows to explicitly set the coordinates of Simulator window center
     * for Xcode9+ SDK. This capability only has an effect if Simulator
     * window has not been opened yet for the current session before it started.
     * e.g. "{-100.0,100.0}" or "{500,500}", spaces are not allowed
     *
     * @param coordinates Window center coordinates.
     * @return self instance for chaining.
     */
    default T setSimulatorWindowCenter(String coordinates) {
        return amend(SIMULATOR_WINDOW_CENTER_OPTION, coordinates);
    }

    /**
     * Get Simulator window center coordinates.
     *
     * @return Coordinates string.
     */
    default Optional<String> getSimulatorWindowCenter() {
        return Optional.ofNullable((String) getCapability(SIMULATOR_WINDOW_CENTER_OPTION));
    }
}
