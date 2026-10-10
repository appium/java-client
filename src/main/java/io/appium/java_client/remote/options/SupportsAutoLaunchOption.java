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

package io.appium.java_client.remote.options;

import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

/**
 * Provides getters and setters for the {@code autoLaunch} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsAutoLaunchOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code autoLaunch} capability.
     */
    String AUTO_LAUNCH_OPTION = "autoLaunch";

    /**
     * Enables launching of the application under test automatically
     * when a test starts.
     *
     * @return self instance for chaining.
     */
    default T autoLaunch() {
        return amend(AUTO_LAUNCH_OPTION, true);
    }

    /**
     * Whether to launch the application under test automatically
     * when a test starts (true, the default value).
     *
     * @param value Whether to enable or disable automatic app launching.
     * @return self instance for chaining.
     */
    default T setAutoLaunch(boolean value) {
        return amend(AUTO_LAUNCH_OPTION, value);
    }

    /**
     * Get whether to launch the application under test automatically
     * when a test starts.
     *
     * @return True if the app should be launched automatically.
     */
    default Optional<Boolean> doesAutoLaunch() {
        return Optional.ofNullable(toSafeBoolean(getCapability(AUTO_LAUNCH_OPTION)));
    }
}
