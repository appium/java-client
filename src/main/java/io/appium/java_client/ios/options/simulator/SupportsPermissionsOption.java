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

package io.appium.java_client.ios.options.simulator;

import com.google.gson.JsonObject;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsPermissionsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String PERMISSIONS_OPTION = "permissions";

    /**
     * Allows to set permissions for the specified application bundle on
     * Simulator only. The capability value is expected to be a valid JSON
     * with {"bundleId1": {"serviceName1": "serviceStatus1", ...}, ...}
     * format. Since Xcode SDK 11.4 Apple provides native APIs to interact with
     * application settings. Check the output of xcrun simctl privacy booted
     * command to get the list of available permission names. Use yes, no
     * and unset as values in order to grant, revoke or reset the corresponding
     * permission. Below Xcode SDK 11.4 it is required that applesimutils package
     * is installed and available in PATH. The list of available service names
     * and statuses can be found at https://github.com/wix/AppleSimulatorUtils.
     *
     * @param json For example {"com.apple.mobilecal": {"calendar": "YES"}}
     * @return self instance for chaining.
     */
    default T setPermissions(JsonObject json) {
        return amend(PERMISSIONS_OPTION, json.toString());
    }

    /**
     * Allows to set permissions for the specified application bundle on
     * Simulator only. The capability value is expected to be a valid JSON
     * string with {"bundleId1": {"serviceName1": "serviceStatus1", ...}, ...}
     * format. Since Xcode SDK 11.4 Apple provides native APIs to interact with
     * application settings. Check the output of xcrun simctl privacy booted
     * command to get the list of available permission names. Use yes, no
     * and unset as values in order to grant, revoke or reset the corresponding
     * permission. Below Xcode SDK 11.4 it is required that applesimutils package
     * is installed and available in PATH. The list of available service names
     * and statuses can be found at https://github.com/wix/AppleSimulatorUtils.
     *
     * @param json For example {"com.apple.mobilecal": {"calendar": "YES"}}
     * @return self instance for chaining.
     */
    default T setPermissions(String json) {
        return amend(PERMISSIONS_OPTION, json);
    }

    /**
     * Get Simulator permissions..
     *
     * @return Permissions json.
     */
    default Optional<String> getPermissions() {
        return Optional.ofNullable((String) getCapability(PERMISSIONS_OPTION));
    }
}
