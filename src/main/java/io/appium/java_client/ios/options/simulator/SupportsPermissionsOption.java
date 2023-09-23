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

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsPermissionsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String PERMISSIONS_OPTION = "permissions";

    /**
     * Allows setting of permissions for the specified application bundle on
     * Simulator only.
     *
     * @param permissions Permissions mapping.
     * @return self instance for chaining.
     */
    default T setPermissions(Permissions permissions) {
        return amend(PERMISSIONS_OPTION, permissions.toString());
    }

    /**
     * Get Simulator permissions.
     *
     * @return Permissions object.
     */
    default Optional<Permissions> getPermissions() {
        return Optional.ofNullable(getCapability(PERMISSIONS_OPTION))
                .map(v -> new Permissions(String.valueOf(v)));
    }
}
