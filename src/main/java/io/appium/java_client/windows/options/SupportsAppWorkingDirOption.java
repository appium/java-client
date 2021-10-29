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

package io.appium.java_client.windows.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsAppWorkingDirOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String APP_WORKING_DIR_OPTION = "appWorkingDir";

    /**
     * Full path to the folder, which is going to be set as the working
     * dir for the application under test. This is only applicable for classic apps.
     *
     * @param path Existing folder path on the server file system.
     * @return self instance for chaining.
     */
    default T setAppWorkingDir(String path) {
        return amend(APP_WORKING_DIR_OPTION, path);
    }

    /**
     * Get the full path to the folder, which is going to be set as the working
     * dir for the application under test.
     *
     * @return Folder path on the server file system.
     */
    default Optional<String> getAppWorkingDir() {
        return Optional.ofNullable((String) getCapability(APP_WORKING_DIR_OPTION));
    }
}
