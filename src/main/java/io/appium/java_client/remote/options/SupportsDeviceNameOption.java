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

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Capabilities;

public interface SupportsDeviceNameOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Set the name of the device.
     *
     * @param deviceName is the name of the device.
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#DEVICE_NAME
     */
    default T setDeviceName(String deviceName) {
        return amend(MobileCapabilityType.DEVICE_NAME, deviceName);
    }

    /**
     * Get the name of the device.
     *
     * @return String representing the name of the device.
     * @see MobileCapabilityType#DEVICE_NAME
     */
    default String getDeviceName() {
        return (String) getCapability(MobileCapabilityType.DEVICE_NAME);
    }
}
