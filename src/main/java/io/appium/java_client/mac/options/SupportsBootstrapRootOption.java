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

package io.appium.java_client.mac.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsBootstrapRootOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String BOOTSTRAP_ROOT_OPTION = "bootstrapRoot";

    /**
     * The full path to WebDriverAgentMac root folder where Xcode project
     * of the server sources lives. By default, this project is located in
     * the same folder where the corresponding driver Node.js module lives.
     *
     * @param path The full path to WebDriverAgentMac root folder.
     * @return self instance for chaining.
     */
    default T setBootstrapRoot(String path) {
        return amend(BOOTSTRAP_ROOT_OPTION, path);
    }

    /**
     * Get the full path to WebDriverAgentMac root folder where Xcode project
     * of the server sources lives.
     *
     * @return The full path to WebDriverAgentMac root folder.
     */
    default Optional<String> getBootstrapRoot() {
        return Optional.ofNullable((String) getCapability(BOOTSTRAP_ROOT_OPTION));
    }
}
