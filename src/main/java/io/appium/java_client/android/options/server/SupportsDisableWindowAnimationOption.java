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

package io.appium.java_client.android.options.server;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsDisableWindowAnimationOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String DISABLE_WINDOWS_ANIMATION_OPTION = "disableWindowAnimation";

    /**
     * Disables window animations when starting the instrumentation process.
     *
     * @return self instance for chaining.
     */
    default T setDisableWindowAnimation() {
        return amend(DISABLE_WINDOWS_ANIMATION_OPTION, true);
    }

    /**
     * Set whether to disable window animations when starting the instrumentation process.
     * false by default
     *
     * @param value True to disable window animations.
     * @return self instance for chaining.
     */
    default T setDisableWindowAnimation(boolean value) {
        return amend(DISABLE_WINDOWS_ANIMATION_OPTION, value);
    }

    /**
     * Get whether window animations when starting the instrumentation process
     * are disabled.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesDisableWindowAnimation() {
        return Optional.ofNullable(toSafeBoolean(getCapability(DISABLE_WINDOWS_ANIMATION_OPTION)));
    }
}
