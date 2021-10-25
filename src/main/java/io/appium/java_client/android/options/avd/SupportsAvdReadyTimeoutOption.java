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

package io.appium.java_client.android.options.avd;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.time.Duration;
import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toDuration;

public interface SupportsAvdReadyTimeoutOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String AVD_READY_TIMEOUT_OPTION = "avdReadyTimeout";

    /**
     * Maximum timeout to wait until Android Emulator is fully booted and is ready for usage.
     * 60000 ms by default
     *
     * @param timeout Timeout value.
     * @return self instance for chaining.
     */
    default T setAvdReadyTimeout(Duration timeout) {
        return amend(AVD_READY_TIMEOUT_OPTION, timeout.toMillis());
    }

    /**
     * Get the timeout to wait until Android Emulator is fully booted and is ready for usage.
     *
     * @return The timeout value.
     */
    default Optional<Duration> getAvdReadyTimeout() {
        return Optional.ofNullable(
                toDuration(getCapability(AVD_READY_TIMEOUT_OPTION))
        );
    }
}
