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

import io.appium.java_client.remote.options.BaseMapOptionData;

import java.util.Map;
import java.util.Optional;

public class Permissions extends BaseMapOptionData<Permissions> {
    public Permissions() {
    }

    public Permissions(Map<String, Object> permissions) {
        super(permissions);
    }

    public Permissions(String json) {
        super(json);
    }

    /**
     * Since Xcode SDK 11.4 Apple provides native APIs to interact with
     * application settings. Check the output of `xcrun simctl privacy booted`
     * command to get the list of available permission names. Use yes, no
     * and unset as values in order to grant, revoke or reset the corresponding
     * permission. Below Xcode SDK 11.4 it is required that applesimutils package
     * is installed and available in PATH. The list of available service names
     * and statuses can be found at https://github.com/wix/AppleSimulatorUtils.
     * For example: {"com.apple.mobilecal": {"calendar": "YES"}}
     *
     * @param bundleId The app identifier to change permissions for.
     * @param mapping Permissions mapping, where keys are perm names and vales are YES/NO.
     * @return self instance for chaining.
     */
    public Permissions withAppPermissions(String bundleId, Map<String, String> mapping) {
        return assignOptionValue(bundleId, mapping);
    }

    /**
     * Get permissions mapping for the given app bundle identifier.
     *
     * @param bundleId App bundle identifier.
     * @return Permissions mapping.
     */
    public Optional<Map<String, String>> getAppPermissions(String bundleId) {
        return getOptionValue(bundleId);
    }
}
