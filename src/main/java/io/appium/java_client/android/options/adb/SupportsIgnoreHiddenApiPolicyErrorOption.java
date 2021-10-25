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

package io.appium.java_client.android.options.adb;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsIgnoreHiddenApiPolicyErrorOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String IGNORE_HIDDEN_API_POLICY_ERROR_OPTION = "ignoreHiddenApiPolicyError";

    /**
     * Prevents the driver from ever killing the ADB server explicitl.
     *
     * @return self instance for chaining.
     */
    default T ignoreHiddenApiPolicyError() {
        return amend(IGNORE_HIDDEN_API_POLICY_ERROR_OPTION, true);
    }

    /**
     * Being set to true ignores a failure while changing hidden API access policies.
     * Could be useful on some devices, where access to these policies has been locked by its vendor.
     * false by default.
     *
     * @param value Whether to ignore a failure while changing hidden API access policies.
     * @return self instance for chaining.
     */
    default T setIgnoreHiddenApiPolicyError(boolean value) {
        return amend(IGNORE_HIDDEN_API_POLICY_ERROR_OPTION, value);
    }

    /**
     * Get whether to ignore a failure while changing hidden API access policies.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesIgnoreHiddenApiPolicyError() {
        return Optional.ofNullable(toSafeBoolean(getCapability(IGNORE_HIDDEN_API_POLICY_ERROR_OPTION)));
    }
}
