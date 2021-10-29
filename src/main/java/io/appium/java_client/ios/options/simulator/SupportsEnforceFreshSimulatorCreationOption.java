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

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsEnforceFreshSimulatorCreationOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String ENFORCE_FRESH_SIMULATOR_CREATION_OPTION = "enforceFreshSimulatorCreation";

    /**
     * Enforce creation of a new simulator for each new test session.
     *
     * @return self instance for chaining.
     */
    default T enforceFreshSimulatorCreation() {
        return amend(ENFORCE_FRESH_SIMULATOR_CREATION_OPTION, true);
    }

    /**
     * Creates a new simulator in session creation and deletes it in session deletion.
     * Defaults to false.
     *
     * @param value Whether to create a new simulator for each new test session.
     * @return self instance for chaining.
     */
    default T setEnforceFreshSimulatorCreation(boolean value) {
        return amend(ENFORCE_FRESH_SIMULATOR_CREATION_OPTION, value);
    }

    /**
     * Get whether to create a new simulator for each new test session.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesEnforceFreshSimulatorCreation() {
        return Optional.ofNullable(toSafeBoolean(getCapability(ENFORCE_FRESH_SIMULATOR_CREATION_OPTION)));
    }
}
