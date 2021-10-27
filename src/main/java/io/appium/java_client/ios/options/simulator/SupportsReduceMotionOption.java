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

public interface SupportsReduceMotionOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String REDUCE_MOTION_OPTION = "reduceMotion";

    /**
     * Enforce reduce motion accessibility preference.
     *
     * @return self instance for chaining.
     */
    default T reduceMotion() {
        return amend(REDUCE_MOTION_OPTION, true);
    }

    /**
     * It allows to turn on/off reduce motion accessibility preference.
     * Setting reduceMotion on helps to reduce flakiness during tests.
     * Only on simulators.
     *
     * @param value Whether to turn on/off reduce motion accessibility preference.
     * @return self instance for chaining.
     */
    default T setReduceMotion(boolean value) {
        return amend(REDUCE_MOTION_OPTION, value);
    }

    /**
     * Get whether to reduce motion accessibility preference.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesReduceMotion() {
        return Optional.ofNullable(toSafeBoolean(getCapability(REDUCE_MOTION_OPTION)));
    }
}
