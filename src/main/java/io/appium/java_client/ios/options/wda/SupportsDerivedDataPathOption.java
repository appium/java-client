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

public interface SupportsDerivedDataPathOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String DERIVED_DATA_PATH_OPTION = "derivedDataPath";

    /**
     * Use along with usePrebuiltWDA capability and choose where to search for the existing WDA app. If the capability
     * is not set then Xcode will store the derived data in the default root taken from preferences.
     * It also makes sense to choose different folders for parallel WDA sessions.
     *
     * @param path Derived data folder path.
     * @return self instance for chaining.
     */
    default T setDerivedDataPath(String path) {
        return amend(DERIVED_DATA_PATH_OPTION, path);
    }

    /**
     * Get the path to the derived data WDA folder.
     *
     * @return Derived data folder path.
     */
    default Optional<String> getDerivedDataPath() {
        return Optional.ofNullable((String) getCapability(DERIVED_DATA_PATH_OPTION));
    }
}
