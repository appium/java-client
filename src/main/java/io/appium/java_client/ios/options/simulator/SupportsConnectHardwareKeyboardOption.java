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

public interface SupportsConnectHardwareKeyboardOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String CONNECT_HARDWARE_KEYBOARD_OPTION = "connectHardwareKeyboard";

    /**
     * Enforce connecting of hardware keyboard to Simulator.
     *
     * @return self instance for chaining.
     */
    default T connectHardwareKeyboard() {
        return amend(CONNECT_HARDWARE_KEYBOARD_OPTION, true);
    }

    /**
     * Set this option to true in order to enable hardware keyboard in Simulator.
     * The preference works only when Appium launches a simulator instance with
     * this value. It is set to false by default, because this helps to workaround
     * some XCTest bugs. connectHardwareKeyboard: true makes
     * forceSimulatorSoftwareKeyboardPresence: false if no explicit value is set
     * for forceSimulatorSoftwareKeyboardPresence capability since Appium 1.22.0.
     *
     * @param value Whether to connect hardware keyboard to Simulator.
     * @return self instance for chaining.
     */
    default T setConnectHardwareKeyboard(boolean value) {
        return amend(CONNECT_HARDWARE_KEYBOARD_OPTION, value);
    }

    /**
     * Get whether to connect hardware keyboard to Simulator.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesConnectHardwareKeyboard() {
        return Optional.ofNullable(toSafeBoolean(getCapability(CONNECT_HARDWARE_KEYBOARD_OPTION)));
    }
}
