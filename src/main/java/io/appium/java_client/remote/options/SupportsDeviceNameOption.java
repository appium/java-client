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

public interface SupportsDeviceNameOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String DEVICE_NAME_OPTION = "deviceName";

    /**
     * Set the name of the device.
     *
     * @param deviceName is the name of the device.
     * @return this MobileOptions, for chaining.
     */
    default T setDeviceName(String deviceName) {
        return amend(DEVICE_NAME_OPTION, deviceName);
    }

    /**
     * Get the name of the device.
     *
     * @return String representing the name of the device.
     */
    default Optional<String> getDeviceName() {
        return Optional.ofNullable((String) getCapability(DEVICE_NAME_OPTION));
    }
}
