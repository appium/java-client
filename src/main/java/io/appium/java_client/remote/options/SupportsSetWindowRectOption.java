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

package io.appium.java_client.remote.options;

import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsSetWindowRectOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SET_WINDOW_RECT_OPTION = "setWindowRect";

    /**
     * Indicates whether the remote end supports all
     * of the resizing and repositioning commands.
     *
     * @param bool True or false.
     * @return self instance for chaining.
     */
    default T setWindowRect(boolean bool) {
        return amend(SET_WINDOW_RECT_OPTION, bool);
    }

    /**
     * Get whether the remote end supports all
     * of the resizing and repositioning commands.
     *
     * @return true or false.
     */
    default Optional<Boolean> doesSetWindowRect() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SET_WINDOW_RECT_OPTION)));
    }
}
