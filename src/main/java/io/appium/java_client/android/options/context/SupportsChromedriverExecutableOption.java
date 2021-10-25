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

public interface SupportsChromedriverExecutableOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String CHROMEDRIVER_EXECUTABLE_OPTION = "chromedriverExecutable";

    /**
     * Full path to the chromedriver executable on the server file system.
     *
     * @param path Path to chromedriver executable.
     * @return self instance for chaining.
     */
    default T setChromedriverExecutable(String path) {
        return amend(CHROMEDRIVER_EXECUTABLE_OPTION, path);
    }

    /**
     * Get the path to the chromedriver executable on the server file system..
     *
     * @return Path to chromedriver executable.
     */
    default Optional<String> getChromedriverExecutable() {
        return Optional.ofNullable((String) getCapability(CHROMEDRIVER_EXECUTABLE_OPTION));
    }
}
