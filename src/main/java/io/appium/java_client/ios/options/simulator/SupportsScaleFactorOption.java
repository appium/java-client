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
 * Provides getters and setters for the {@code scaleFactor} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsScaleFactorOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code scaleFactor} capability.
     */
    String SCALE_FACTOR_OPTION = "scaleFactor";

    /**
     * Simulator scale factor. This is useful to have if the default resolution
     * of simulated device is greater than the actual display resolution.
     * So you can scale the simulator to see the whole device screen without scrolling.
     * The value must be a string containing a positive float number, for example '2.0'.
     *
     * @param scaleFactor Scale factor value.
     * @return self instance for chaining.
     */
    default T setScaleFactor(String scaleFactor) {
        return amend(SCALE_FACTOR_OPTION, scaleFactor);
    }

    /**
     * Get Simulator scale factor.
     *
     * @return Scale factor value.
     */
    default Optional<String> getScaleFactor() {
        return Optional.ofNullable((String) getCapability(SCALE_FACTOR_OPTION));
    }
}
