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

package io.appium.java_client.android.options.app;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

/**
 * Provides getters and setters for the {@code dontStopAppOnReset} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsDontStopAppOnResetOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code dontStopAppOnReset} capability.
     */
    String DONT_STOP_APP_ON_RESET_OPTION = "dontStopAppOnReset";

    /**
     * Skips stopping the app under test during session start and deletion.
     *
     * @return self instance for chaining.
     */
    default T dontStopAppOnReset() {
        return amend(DONT_STOP_APP_ON_RESET_OPTION, true);
    }

    /**
     * Whether to skip terminating the app under test upon session start and deletion
     * when the app is already running.
     *
     * @param value True to avoid stopping the app on reset.
     * @return self instance for chaining.
     */
    default T setDontStopAppOnReset(boolean value) {
        return amend(DONT_STOP_APP_ON_RESET_OPTION, value);
    }

    /**
     * Get whether terminating the app under test on reset is skipped.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesDontStopAppOnReset() {
        return Optional.ofNullable(toSafeBoolean(getCapability(DONT_STOP_APP_ON_RESET_OPTION)));
    }
}
