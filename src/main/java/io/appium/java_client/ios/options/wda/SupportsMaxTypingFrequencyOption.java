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

package io.appium.java_client.ios.options.wda;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toInteger;

public interface SupportsMaxTypingFrequencyOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String MAX_TYPING_FREQUENCY_OPTION = "maxTypingFrequency";

    /**
     * Maximum frequency of keystrokes for typing and clear. If your tests
     * are failing because of typing errors, you may want to adjust this.
     * Defaults to 60 keystrokes per minute.
     *
     * @param frequency The number of keystrokes per minute.
     * @return self instance for chaining.
     */
    default T setMaxTypingFrequency(int frequency) {
        return amend(MAX_TYPING_FREQUENCY_OPTION, frequency);
    }

    /**
     * Get the number of keystrokes per minute.
     *
     * @return The number of keystrokes per minute.
     */
    default Optional<Integer> getMaxTypingFrequency() {
        return Optional.ofNullable(toInteger(getCapability(MAX_TYPING_FREQUENCY_OPTION)));
    }
}
