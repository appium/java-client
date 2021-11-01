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

package io.appium.java_client.safari.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsSafariDeviceNameOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SAFARI_DEVICE_NAME_OPTION = "safari:deviceName";

    /**
     * safaridriver will only create a session using hosts whose device name
     * matches the value of safari:deviceName. Device names are compared
     * case-insensitively. NOTE: Device names for connected devices are shown in
     * iTunes. If Xcode is installed, device names for connected devices are available
     * via the output of instruments(1) and in the Devices and Simulators window
     * (accessed in Xcode via "Window -&gt; Devices and Simulators").
     *
     * @param deviceName Device name.
     * @return self instance for chaining.
     */
    default T setSafariDeviceName(String deviceName) {
        return amend(SAFARI_DEVICE_NAME_OPTION, deviceName);
    }

    /**
     * Get the name of the device.
     *
     * @return String representing the name of the device.
     */
    default Optional<String> getSafariDeviceName() {
        return Optional.ofNullable((String) getCapability(SAFARI_DEVICE_NAME_OPTION));
    }
}
