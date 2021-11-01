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

public interface SupportsSafariDeviceTypeOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SAFARI_DEVICE_TYPE_OPTION = "safari:deviceType";

    /**
     * If the value of safari:deviceType is 'iPhone', safaridriver will only create a session
     * using an iPhone device or iPhone simulator. If the value of safari:deviceType is 'iPad',
     * safaridriver will only create a session using an iPad device or iPad simulator.
     * Values of safari:deviceType are compared case-insensitively.
     *
     * @param deviceType Device type name.
     * @return self instance for chaining.
     */
    default T setSafariDeviceType(String deviceType) {
        return amend(SAFARI_DEVICE_TYPE_OPTION, deviceType);
    }

    /**
     * Get the type of the device.
     *
     * @return String representing the type of the device.
     */
    default Optional<String> getSafariDeviceType() {
        return Optional.ofNullable((String) getCapability(SAFARI_DEVICE_TYPE_OPTION));
    }
}
