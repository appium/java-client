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

import java.time.Duration;
import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toDuration;

/**
 * Provides getters and setters for the {@code ms:waitForAppLaunch} capability.
 * The driver treats values up to 120 as seconds and greater values as milliseconds.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsWaitForAppLaunchOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code ms:waitForAppLaunch} capability.
     */
    String MS_WAIT_FOR_APP_LAUNCH_OPTION = "ms:waitForAppLaunch";

    /**
     * The time to wait for the application window after the app launch is initiated.
     * Whole seconds up to 120 are sent as seconds, anything else is sent as milliseconds.
     * Durations shorter than 121 ms cannot be expressed and are rounded up to one second.
     *
     * @param timeout The timeout value.
     * @return self instance for chaining.
     */
    default T setWaitForAppLaunch(Duration timeout) {
        long ms = timeout.toMillis();
        if (ms <= 120_000 && ms % 1000 == 0) {
            return amend(MS_WAIT_FOR_APP_LAUNCH_OPTION, ms / 1000);
        }
        return amend(MS_WAIT_FOR_APP_LAUNCH_OPTION, ms > 120 ? ms : 1);
    }

    /**
     * Get the time to wait for the application window after the app launch is initiated.
     *
     * @return The timeout value.
     */
    default Optional<Duration> doesWaitForAppLaunch() {
        return Optional.ofNullable(toDuration(getCapability(MS_WAIT_FOR_APP_LAUNCH_OPTION), raw ->
                raw > 120 ? Duration.ofMillis(raw) : Duration.ofSeconds(raw)));
    }
}
