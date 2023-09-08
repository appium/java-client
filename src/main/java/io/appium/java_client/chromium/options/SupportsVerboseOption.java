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

public interface SupportsVerboseOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String VERBOSE = "verbose";

    /**
     * Set to true to add the --verbose flag when starting WebDriver.
     * Unless the verbose preference has been user-set, the capability
     * is not present because the default value is false.
     *
     * @param verbose flag for --verbose.
     * @return self instance for chaining.
     */
    default T setVerbose(boolean verbose) {
        return amend(VERBOSE, verbose);
    }

    /**
     * Get the verbose flag.
     *
     * @return verbose flag.
     */
    default Optional<Boolean> isVerbose() {
        return Optional.ofNullable(toSafeBoolean(getCapability(VERBOSE)));
    }
}
