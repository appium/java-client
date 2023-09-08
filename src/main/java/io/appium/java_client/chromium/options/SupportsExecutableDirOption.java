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

public interface SupportsExecutableDirOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String EXECUTABLE_DIR = "executableDir";

    /**
     * A directory within which is found any number of WebDriver binaries.
     * If set, the driver will search this directory for WebDrivers of the
     * appropriate version to use for your browser.
     *
     * @param directory of WebDriver binaries.
     * @return self instance for chaining.
     */
    default T setExecutableDir(String directory) {
        return amend(EXECUTABLE_DIR, directory);
    }

    /**
     * Get a directory within which is found any number of WebDriver binaries.
     *
     * @return executable directory of a Driver binary.
     */
    default Optional<String> getExecutableDir() {
        return Optional.ofNullable((String) getCapability(EXECUTABLE_DIR));
    }
}
