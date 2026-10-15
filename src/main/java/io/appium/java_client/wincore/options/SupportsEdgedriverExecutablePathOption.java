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

package io.appium.java_client.wincore.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

/**
 * Provides getters and setters for the {@code edgedriverExecutablePath} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsEdgedriverExecutablePathOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code edgedriverExecutablePath} capability.
     */
    String EDGEDRIVER_EXECUTABLE_PATH_OPTION = "edgedriverExecutablePath";

    /**
     * Full path to the local msedgedriver binary on the server file system.
     *
     * @param value The capability value.
     * @return self instance for chaining.
     */
    default T setEdgedriverExecutablePath(String value) {
        return amend(EDGEDRIVER_EXECUTABLE_PATH_OPTION, value);
    }

    /**
     * Get the path to the local msedgedriver binary on the server file system.
     *
     * @return The capability value.
     */
    default Optional<String> getEdgedriverExecutablePath() {
        return Optional.ofNullable((String) getCapability(EDGEDRIVER_EXECUTABLE_PATH_OPTION));
    }
}
