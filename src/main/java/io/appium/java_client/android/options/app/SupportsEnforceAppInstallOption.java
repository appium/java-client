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

public interface SupportsEnforceAppInstallOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String ENFORCE_APP_INSTALL_OPTION = "enforceAppInstall";

    /**
     * Sets the application under test is always reinstalled even if a newer version
     * of it already exists on the device under test.
     *
     * @return self instance for chaining.
     */
    default T enforceAppInstall() {
        return amend(ENFORCE_APP_INSTALL_OPTION, true);
    }

    /**
     * Allows setting whether the application under test is always reinstalled even
     * if a newer version of it already exists on the device under test. false by default.
     *
     * @param value True to allow test packages installation.
     * @return self instance for chaining.
     */
    default T setEnforceAppInstall(boolean value) {
        return amend(ENFORCE_APP_INSTALL_OPTION, value);
    }

    /**
     * Get whether the application under test is always reinstalled even
     * if a newer version of it already exists on the device under test.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesEnforceAppInstall() {
        return Optional.ofNullable(toSafeBoolean(getCapability(ENFORCE_APP_INSTALL_OPTION)));
    }
}
