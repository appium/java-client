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

package io.appium.java_client.chromium.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsAutodownloadOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String AUTODOWNLOAD_ENABLED = "autodownloadEnabled";

    /**
     * Set to false for disabling automatic downloading of Chrome drivers.
     * Unless disable build check preference has been user-set, the capability
     * is present because the default value is true.
     *
     * @param autodownloadEnabled flag.
     * @return self instance for chaining.
     */
    default T setAutodownloadEnabled(boolean autodownloadEnabled) {
        return amend(AUTODOWNLOAD_ENABLED, autodownloadEnabled);
    }

    /**
     * Get the auto download flag.
     *
     * @return auto download flag.
     */
    default Optional<Boolean> isAutodownloadEnabled() {
        return Optional.ofNullable(toSafeBoolean(getCapability(AUTODOWNLOAD_ENABLED)));
    }
}
