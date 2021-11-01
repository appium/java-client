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

public interface SupportsAppTopLevelWindowOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String APP_TOP_LEVEL_WINDOW_OPTION = "appTopLevelWindow";

    /**
     * Set the hexadecimal handle of an existing application top level
     * window to attach to, for example 0x12345 (should be of string type).
     * Either this capability or app one must be provided on session startup.
     *
     * @param identifier E.g. "0x12345".
     * @return self instance for chaining.
     */
    default T setAppTopLevelWindow(String identifier) {
        return amend(APP_TOP_LEVEL_WINDOW_OPTION, identifier);
    }

    /**
     * Get the hexadecimal handle of an existing application top level
     * window to attach to.
     *
     * @return Top level window handle.
     */
    default Optional<String> getAppTopLevelWindow() {
        return Optional.ofNullable((String) getCapability(APP_TOP_LEVEL_WINDOW_OPTION));
    }
}
