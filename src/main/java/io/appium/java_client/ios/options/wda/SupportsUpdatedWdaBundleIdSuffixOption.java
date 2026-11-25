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

package io.appium.java_client.ios.options.wda;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

/**
 * Provides getters and setters for the {@code updatedWDABundleIdSuffix} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsUpdatedWdaBundleIdSuffixOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code updatedWDABundleIdSuffix} capability.
     */
    String UPDATED_WDA_BUNDLE_ID_SUFFIX_OPTION = "updatedWDABundleIdSuffix";

    /**
     * Sets the suffix appended to {@code updatedWDABundleId} when {@code usePreinstalledWDA}
     * is enabled (available since {@code appium-xcuitest-driver} 7.6.0). Pass an empty
     * string {@code ""} to omit the suffix. Defaults to {@code .xctrunner}.
     *
     * @param suffix Bundle identifier suffix.
     * @return self instance for chaining.
     */
    default T setUpdatedWdaBundleIdSuffix(String suffix) {
        return amend(UPDATED_WDA_BUNDLE_ID_SUFFIX_OPTION, suffix);
    }

    /**
     * Get the bundle identifier suffix for {@code updatedWDABundleId}.
     *
     * @return Bundle identifier suffix.
     */
    default Optional<String> getUpdatedWdaBundleIdSuffix() {
        return Optional.ofNullable((String) getCapability(UPDATED_WDA_BUNDLE_ID_SUFFIX_OPTION));
    }
}
