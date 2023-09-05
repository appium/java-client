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

public interface SupportsBuildCheckOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String DISABLE_BUILD_CHECK = "disableBuildCheck";

    /**
     * Set to true to add the --disable-build-check flag when starting WebDriver.
     * Unless disable build check preference has been user-set, the capability
     * is not present because the default value is false.
     *
     * @param BuildCheckDisabled flag for --disable-build-check.
     * @return self instance for chaining.
     */
    default T setBuildCheckDisabled(boolean BuildCheckDisabled) {
        return amend(DISABLE_BUILD_CHECK, BuildCheckDisabled);
    }

    /**
     * Get disable build check flag.
     *
     * @return disable build check flag.
     */
    default Optional<Boolean> isBuildCheckDisabled() {
        return Optional.ofNullable(toSafeBoolean(getCapability(DISABLE_BUILD_CHECK)));
    }
}
