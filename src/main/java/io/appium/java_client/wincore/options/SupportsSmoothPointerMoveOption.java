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

package io.appium.java_client.wincore.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

/**
 * Provides getters and setters for the {@code smoothPointerMove} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsSmoothPointerMoveOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code smoothPointerMove} capability.
     */
    String SMOOTH_POINTER_MOVE_OPTION = "smoothPointerMove";

    /**
     * The CSS easing function used to interpolate the mouse pointer movement, for example {@code ease-in}.
     *
     * @param value The capability value.
     * @return self instance for chaining.
     */
    default T setSmoothPointerMove(String value) {
        return amend(SMOOTH_POINTER_MOVE_OPTION, value);
    }

    /**
     * Get the CSS easing function used to interpolate the mouse pointer movement.
     *
     * @return The capability value.
     */
    default Optional<String> getSmoothPointerMove() {
        return Optional.ofNullable((String) getCapability(SMOOTH_POINTER_MOVE_OPTION));
    }
}
