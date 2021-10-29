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

package io.appium.java_client.ios.options.wda;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsAllowProvisioningDeviceRegistrationOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String ALLOW_PROVISIONING_DEVICE_REGISTRATION_OPTION = "allowProvisioningDeviceRegistration";

    /**
     * Allows xcodebuild to register your destination device on the developer portal.
     *
     * @return self instance for chaining.
     */
    default T allowProvisioningDeviceRegistration() {
        return amend(ALLOW_PROVISIONING_DEVICE_REGISTRATION_OPTION, true);
    }

    /**
     * Allow xcodebuild to register your destination device on the developer portal
     * if necessary. Requires a developer account to have been added in Xcode's Accounts
     * preference pane. Defaults to false.
     *
     * @param value Whether to allow xcodebuild to register your destination device on the developer portal.
     * @return self instance for chaining.
     */
    default T setAllowProvisioningDeviceRegistration(boolean value) {
        return amend(ALLOW_PROVISIONING_DEVICE_REGISTRATION_OPTION, value);
    }

    /**
     * Get whether to allow xcodebuild to register your destination device on the developer portal.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesAllowProvisioningDeviceRegistration() {
        return Optional.ofNullable(toSafeBoolean(getCapability(ALLOW_PROVISIONING_DEVICE_REGISTRATION_OPTION)));
    }
}
