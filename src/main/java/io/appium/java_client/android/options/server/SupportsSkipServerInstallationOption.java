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

package io.appium.java_client.android.options.server;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsSkipServerInstallationOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SKIP_SERVER_INSTALLATION_OPTION = "skipServerInstallation";

    /**
     * Enables skipping of the UiAutomator2 Server component installation
     * on the device under test and all the related checks.
     * This could help to speed up the session startup if you know for sure the
     * correct server version is installed on the device.
     * In case the server is not installed or an incorrect version of it is installed
     * then you may get an unexpected error later.
     *
     * @return self instance for chaining.
     */
    default T setSkipServerInstallation() {
        return amend(SKIP_SERVER_INSTALLATION_OPTION, true);
    }

    /**
     * Set whether to skip the UiAutomator2 Server component installation
     * on the device under test and all the related checks.
     * This could help to speed up the session startup if you know for sure the
     * correct server version is installed on the device.
     * In case the server is not installed or an incorrect version of it is installed
     * then you may get an unexpected error later.
     *
     * @param value True to skip the server installation.
     * @return self instance for chaining.
     */
    default T setSkipServerInstallation(boolean value) {
        return amend(SKIP_SERVER_INSTALLATION_OPTION, value);
    }

    /**
     * Get whether to skip the UiAutomator2 Server component installation
     * on the device under test and all the related checks.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesSkipServerInstallation() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SKIP_SERVER_INSTALLATION_OPTION)));
    }
}
