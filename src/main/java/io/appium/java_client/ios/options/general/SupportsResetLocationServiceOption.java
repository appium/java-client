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

package io.appium.java_client.ios.options.general;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsResetLocationServiceOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String RESET_LOCATION_SERVICE_OPTION = "resetLocationService";

    /**
     * Set to reset the location service in the session deletion on real device.
     *
     * @return self instance for chaining.
     */
    default T resetLocationService() {
        return amend(RESET_LOCATION_SERVICE_OPTION, true);
    }

    /**
     * Whether reset the location service in the session deletion on real device.
     * Defaults to false.
     *
     * @param value Whether to reset the location service in the session deletion on real device.
     * @return self instance for chaining.
     */
    default T setResetLocationService(boolean value) {
        return amend(RESET_LOCATION_SERVICE_OPTION, value);
    }

    /**
     * Get whether to reset the location service in the session deletion on real device.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesResetLocationService() {
        return Optional.ofNullable(toSafeBoolean(getCapability(RESET_LOCATION_SERVICE_OPTION)));
    }
}
