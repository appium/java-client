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

public interface SupportsChromedriverExecutableDirOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String CHROMEDRIVER_EXECUTABLE_DIR_OPTION = "chromedriverExecutableDir";

    /**
     * Full path to the folder where chromedriver executables are located.
     * This folder is used then to store the downloaded chromedriver executables
     * if automatic download is enabled. Read [Automatic Chromedriver
     * Discovery](https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/
     * web/chromedriver.md#automatic-discovery-of-compatible-chromedriver)
     * article for more details.
     *
     * @param path Path to chromedriver executable.
     * @return self instance for chaining.
     */
    default T setChromedriverExecutableDir(String path) {
        return amend(CHROMEDRIVER_EXECUTABLE_DIR_OPTION, path);
    }

    /**
     * Get full path to the folder where chromedriver executables are located.
     *
     * @return Path to chromedriver executable dir.
     */
    default Optional<String> getChromedriverExecutableDir() {
        return Optional.ofNullable((String) getCapability(CHROMEDRIVER_EXECUTABLE_DIR_OPTION));
    }
}
