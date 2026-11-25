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

/**
 * Provides getters and setters for the {@code reduceTransparency} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsReduceTransparencyOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code reduceTransparency} capability.
     */
    String REDUCE_TRANSPARENCY_OPTION = "reduceTransparency";

    /**
     * Enforce reduce transparency accessibility preference on simulators.
     *
     * @return self instance for chaining.
     */
    default T reduceTransparency() {
        return amend(REDUCE_TRANSPARENCY_OPTION, true);
    }

    /**
     * Allows turning on or off the reduce transparency accessibility preference to help
     * reduce screenshot image distortion during tests. Only available on simulators.
     *
     * @param value Whether to enable reduce transparency.
     * @return self instance for chaining.
     */
    default T setReduceTransparency(boolean value) {
        return amend(REDUCE_TRANSPARENCY_OPTION, value);
    }

    /**
     * Get whether to reduce transparency accessibility preference.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesReduceTransparency() {
        return Optional.ofNullable(toSafeBoolean(getCapability(REDUCE_TRANSPARENCY_OPTION)));
    }
}
