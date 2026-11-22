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

/**
 * Provides getters and setters for the {@code skipSettingsAppReinstall} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsSkipSettingsAppReinstallOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code skipSettingsAppReinstall} capability.
     */
    String SKIP_SETTINGS_APP_REINSTALL_OPTION = "skipSettingsAppReinstall";

    /**
     * Skips reinstalling the Appium Settings helper application on session start.
     *
     * @return self instance for chaining.
     */
    default T skipSettingsAppReinstall() {
        return amend(SKIP_SETTINGS_APP_REINSTALL_OPTION, true);
    }

    /**
     * Whether to skip installing or reinstalling the Appium Settings helper application
     * upon session startup.
     *
     * @param value True to skip Appium Settings reinstallation.
     * @return self instance for chaining.
     */
    default T setSkipSettingsAppReinstall(boolean value) {
        return amend(SKIP_SETTINGS_APP_REINSTALL_OPTION, value);
    }

    /**
     * Get whether reinstalling the Appium Settings helper application is skipped.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesSkipSettingsAppReinstall() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SKIP_SETTINGS_APP_REINSTALL_OPTION)));
    }
}
