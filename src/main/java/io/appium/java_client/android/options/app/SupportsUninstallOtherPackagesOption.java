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

public interface SupportsUninstallOtherPackagesOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String UNINSTALL_OTHER_PACKAGES_OPTION = "uninstallOtherPackages";

    /**
     * Allows to set one or more comma-separated package
     * identifiers to be uninstalled from the device before a test starts.
     *
     * @param packages one or more comma-separated package identifiers to uninstall.
     * @return self instance for chaining.
     */
    default T setUninstallOtherPackages(String packages) {
        return amend(UNINSTALL_OTHER_PACKAGES_OPTION, packages);
    }

    /**
     * Get identifiers of packages to be uninstalled from the device before a test starts.
     *
     * @return Comma-separated package identifiers.
     */
    default Optional<String> getUninstallOtherPackages() {
        return Optional.ofNullable((String) getCapability(UNINSTALL_OTHER_PACKAGES_OPTION));
    }
}
