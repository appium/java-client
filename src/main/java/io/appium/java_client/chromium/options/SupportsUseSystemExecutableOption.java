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

public interface SupportsUseSystemExecutableOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String USE_SYSTEM_EXECUTABLE = "useSystemExecutable";

    /**
     * Set to true to use the version of WebDriver bundled with this driver,
     * rather than attempting to download a new one based on the version of the
     * browser under test.
     * Unless disable build check preference has been user-set, the capability
     * is not present because the default value is false.
     *
     * @param useSystemExecutable flag.
     * @return self instance for chaining.
     */
    default T setUseSystemExecutable(boolean useSystemExecutable) {
        return amend(USE_SYSTEM_EXECUTABLE, useSystemExecutable);
    }

    /**
     * Get the use system executable flag.
     *
     * @return use system executable flag.
     */
    default Optional<Boolean> isUseSystemExecutable() {
        return Optional.ofNullable(toSafeBoolean(getCapability(USE_SYSTEM_EXECUTABLE)));
    }
}
