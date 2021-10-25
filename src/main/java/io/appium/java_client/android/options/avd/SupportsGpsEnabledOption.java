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

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsGpsEnabledOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String GPS_ENABLED_OPTION = "gpsEnabled";

    /**
     * Enables GPS service in the Emulator.
     * Unset by default, which means to not change the current value.
     *
     * @return self instance for chaining.
     */
    default T gpsEnabled() {
        return amend(GPS_ENABLED_OPTION, true);
    }

    /**
     * Sets whether to enable (true) or disable (false) GPS service in the Emulator.
     * Unset by default, which means to not change the current value.
     *
     * @param value Whether to enable or disable the GPS service.
     * @return self instance for chaining.
     */
    default T setGpsEnabled(boolean value) {
        return amend(GPS_ENABLED_OPTION, value);
    }

    /**
     * Get the state of GPS service on emulator.
     *
     * @return True or false.
     */
    default Optional<Boolean> getGpsEnabled() {
        return Optional.ofNullable(toSafeBoolean(getCapability(GPS_ENABLED_OPTION)));
    }
}
