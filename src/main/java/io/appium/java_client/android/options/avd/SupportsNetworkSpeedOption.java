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

public interface SupportsNetworkSpeedOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String NETWORK_SPEED_OPTION = "networkSpeed";

    /**
     * Sets the desired network speed limit for the emulator.
     * It is only applied if the emulator is not running before
     * the test starts. See emulator command line arguments description
     * for more details.
     *
     * @param speed Speed value.
     * @return self instance for chaining.
     */
    default T setNetworkSpeed(String speed) {
        return amend(NETWORK_SPEED_OPTION, speed);
    }

    /**
     * Get the desired network speed limit for the emulator.
     *
     * @return Speed value.
     */
    default Optional<String> getNetworkSpeed() {
        return Optional.ofNullable((String) getCapability(NETWORK_SPEED_OPTION));
    }
}
