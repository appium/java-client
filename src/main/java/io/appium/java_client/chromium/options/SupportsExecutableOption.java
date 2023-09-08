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

public interface SupportsExecutableOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String EXECUTABLE = "executable";

    /**
     * The absolute path to a WebDriver binary executable.
     * If set, the driver will use that path instead of its own WebDriver.
     *
     * @param path absolute of a WebDriver.
     * @return self instance for chaining.
     */
    default T setExecutable(String path) {
        return amend(EXECUTABLE, path);
    }

    /**
     * Get the absolute path to a WebDriver binary executable.
     *
     * @return executable absolute path.
     */
    default Optional<String> getExecutable() {
        return Optional.ofNullable((String) getCapability(EXECUTABLE));
    }
}
