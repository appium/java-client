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

public interface SupportsAllowTestPackagesOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String ALLOW_TEST_PACKAGES_OPTION = "allowTestPackages";

    /**
     * Enables usage of packages built with the test flag for
     * the automated testing (literally adds -t flag to the adb install command).
     *
     * @return self instance for chaining.
     */
    default T allowTestPackages() {
        return amend(ALLOW_TEST_PACKAGES_OPTION, true);
    }

    /**
     * If set to true then it would be possible to use packages built with the test flag for
     * the automated testing (literally adds -t flag to the adb install command). false by default.
     *
     * @param value True to allow test packages installation.
     * @return self instance for chaining.
     */
    default T setAllowTestPackages(boolean value) {
        return amend(ALLOW_TEST_PACKAGES_OPTION, value);
    }

    /**
     * Get whether it is possible to use packages built with the test flag for
     * the automated testing (literally adds -t flag to the adb install command).
     *
     * @return True or false.
     */
    default Optional<Boolean> doesAllowTestPackages() {
        return Optional.ofNullable(toSafeBoolean(getCapability(ALLOW_TEST_PACKAGES_OPTION)));
    }
}
