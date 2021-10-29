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

package io.appium.java_client.ios.options.app;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsLocalizableStringsDirOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String LOCALIZABLE_STRINGS_DIR_OPTION = "localizableStringsDir";

    /**
     * Where to look for localizable strings in the application bundle.
     * Defaults to en.lproj.
     *
     * @param folder The resource folder name where the main locale strings are stored.
     * @return self instance for chaining.
     */
    default T setLocalizableStringsDir(String folder) {
        return amend(LOCALIZABLE_STRINGS_DIR_OPTION, folder);
    }

    /**
     * Get the resource folder name where the main locale strings are stored.
     *
     * @return Folder name.
     */
    default Optional<String> getLocalizableStringsDir() {
        return Optional.ofNullable((String) getCapability(LOCALIZABLE_STRINGS_DIR_OPTION));
    }
}
