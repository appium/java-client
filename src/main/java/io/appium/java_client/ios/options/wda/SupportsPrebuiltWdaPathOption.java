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

public interface SupportsPrebuiltWdaPathOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String PREBUILT_WDA_PATH_OPTION = "prebuiltWDAPath";

    /**
     * The full path to the prebuilt WebDriverAgent-Runner application
     * package to be installed if appium:usePreinstalledWDA capability
     * is enabled. The package's bundle identifier could be customized via
     * appium:updatedWDABundleId capability.
     *
     * @param path The full path to the bundle .app file on the server file system.
     * @return self instance for chaining.
     */
    default T setPrebuiltWdaPath(String path) {
        return amend(PREBUILT_WDA_PATH_OPTION, path);
    }

    /**
     * Get prebuilt WebDriverAgent path.
     *
     * @return The full path to the bundle .app file on the server file system.
     */
    default Optional<String> getPrebuiltWdaPath() {
        return Optional.ofNullable((String) getCapability(PREBUILT_WDA_PATH_OPTION));
    }
}
