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

public interface SupportsForceSimulatorSoftwareKeyboardPresenceOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String FORCE_SIMULATOR_SOFTWARE_KEYBOARD_PRESENCE_OPTION = "forceSimulatorSoftwareKeyboardPresence";

    /**
     * Enforce software keyboard presence.
     *
     * @return self instance for chaining.
     */
    default T forceSimulatorSoftwareKeyboardPresence() {
        return amend(FORCE_SIMULATOR_SOFTWARE_KEYBOARD_PRESENCE_OPTION, true);
    }

    /**
     * Set this option to true in order to turn software keyboard on and turn
     * hardware keyboard off in Simulator since Appium 1.22.0. This option helps
     * to avoid Keyboard is not present error. It is set to true by default.
     * Appium respects preset simulator software/hardware keyboard preference
     * when this value is false, so connectHardwareKeyboard: false and
     * forceSimulatorSoftwareKeyboardPresence: false means for Appium to keep
     * the current Simulator keyboard preferences. This option has priority
     * over connectHardwareKeyboard.
     *
     * @param value Whether to enforce software keyboard presence.
     * @return self instance for chaining.
     */
    default T setForceSimulatorSoftwareKeyboardPresence(boolean value) {
        return amend(FORCE_SIMULATOR_SOFTWARE_KEYBOARD_PRESENCE_OPTION, value);
    }

    /**
     * Get to enforce software keyboard presence.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesForceSimulatorSoftwareKeyboardPresence() {
        return Optional.ofNullable(toSafeBoolean(getCapability(FORCE_SIMULATOR_SOFTWARE_KEYBOARD_PRESENCE_OPTION)));
    }
}
