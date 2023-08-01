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

public interface SupportsSimulatorTracePointerOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SIMULATOR_TRACE_POINTER_OPTION = "simulatorTracePointer";

    /**
     * Enforce highlight of pointer moves in the Simulator window.
     *
     * @return self instance for chaining.
     */
    default T simulatorTracePointer() {
        return amend(SIMULATOR_TRACE_POINTER_OPTION, true);
    }

    /**
     * Whether to highlight pointer moves in the Simulator window.
     * The Simulator UI client must be shut down before the session
     * startup in order for this capability to be applied properly.
     * false by default.
     *
     * @param value Whether to highlight pointer moves in the Simulator window.
     * @return self instance for chaining.
     */
    default T setSimulatorTracePointer(boolean value) {
        return amend(SIMULATOR_TRACE_POINTER_OPTION, value);
    }

    /**
     * Get whether to highlight pointer moves in the Simulator window.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesSimulatorTracePointer() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SIMULATOR_TRACE_POINTER_OPTION)));
    }

    /**
     * Get whether to highlight pointer moves in the Simulator window.
     *
     * @deprecated use {@link SupportsSimulatorTracePointerOption#doesSimulatorTracePointer()} instead
     */
    @Deprecated
    default Optional<Boolean> doesSimulatorTracePointerd() {
        return doesSimulatorTracePointer();
    }
}
