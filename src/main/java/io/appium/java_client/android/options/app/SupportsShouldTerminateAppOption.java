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
 * Provides getters and setters for the {@code shouldTerminateApp} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsShouldTerminateAppOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code shouldTerminateApp} capability.
     */
    String SHOULD_TERMINATE_APP_OPTION = "shouldTerminateApp";

    /**
     * Terminates the app upon session deletion even when {@code noReset} is enabled.
     *
     * @return self instance for chaining.
     */
    default T shouldTerminateApp() {
        return amend(SHOULD_TERMINATE_APP_OPTION, true);
    }

    /**
     * Whether to stop the app upon session deletion even if {@code noReset} is set.
     *
     * @param value True to enforce app termination on session deletion.
     * @return self instance for chaining.
     */
    default T setShouldTerminateApp(boolean value) {
        return amend(SHOULD_TERMINATE_APP_OPTION, value);
    }

    /**
     * Get whether app termination on session deletion is enforced.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesTerminateApp() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SHOULD_TERMINATE_APP_OPTION)));
    }
}
