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

package io.appium.java_client.android.options.context;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

/**
 * Provides getters and setters for the {@code chromedriverGrantPermissions} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsChromedriverGrantPermissionsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code chromedriverGrantPermissions} capability.
     */
    String CHROMEDRIVER_GRANT_PERMISSIONS_OPTION = "chromedriverGrantPermissions";

    /**
     * Automatically grants requested runtime permissions for Chrome or the webview package.
     *
     * @return self instance for chaining.
     */
    default T chromedriverGrantPermissions() {
        return amend(CHROMEDRIVER_GRANT_PERMISSIONS_OPTION, true);
    }

    /**
     * Whether to automatically grant requested runtime permissions for the Chrome
     * or webview package.
     *
     * @param value True to auto-grant Chrome or webview runtime permissions.
     * @return self instance for chaining.
     */
    default T setChromedriverGrantPermissions(boolean value) {
        return amend(CHROMEDRIVER_GRANT_PERMISSIONS_OPTION, value);
    }

    /**
     * Get whether runtime permissions are granted automatically for Chrome or the webview package.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesChromedriverGrantPermissions() {
        return Optional.ofNullable(toSafeBoolean(getCapability(CHROMEDRIVER_GRANT_PERMISSIONS_OPTION)));
    }
}
