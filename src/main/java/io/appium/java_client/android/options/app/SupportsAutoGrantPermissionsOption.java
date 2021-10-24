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

package io.appium.java_client.android.options.app;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsAutoGrantPermissionsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String AUTO_GRANT_PERMISSIONS_OPTION = "autoGrantPermissions";

    /**
     * Enables granting of all the requested application permissions
     * automatically when a test starts.
     *
     * @return self instance for chaining.
     */
    default T setAutoGrantPermissions() {
        return amend(AUTO_GRANT_PERMISSIONS_OPTION, true);
    }


    /**
     * Whether to grant all the requested application permissions
     * automatically when a test starts(true). false by default.
     *
     * @param value Whether to enable or disable automatic permissions granting.
     * @return self instance for chaining.
     */
    default T setAutoGrantPermissions(boolean value) {
        return amend(AUTO_GRANT_PERMISSIONS_OPTION, value);
    }

    /**
     * Get whether to grant all the requested application permissions
     * automatically when a test starts.
     *
     * @return True if the permissions should be granted.
     */
    default Optional<Boolean> doesAutoGrantPermissions() {
        return Optional.ofNullable(toSafeBoolean(getCapability(AUTO_GRANT_PERMISSIONS_OPTION)));
    }
}
