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

package io.appium.java_client.ios.options.webview;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsAbsoluteWebLocationsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String ABSOLUTE_WEB_LOCATIONS_OPTION = "absoluteWebLocations";

    /**
     * Enforces Get Element Location to return coordinates
     * relative to the page origin for web view elements.
     *
     * @return self instance for chaining.
     */
    default T absoluteWebLocations() {
        return amend(ABSOLUTE_WEB_LOCATIONS_OPTION, true);
    }

    /**
     * This capability will direct the Get Element Location command, when used
     * within webviews, to return coordinates which are relative to the origin of
     * the page, rather than relative to the current scroll offset. This capability
     * has no effect outside of webviews. Default false.
     *
     * @param value Whether to return coordinates relative to the page origin for web view elements.
     * @return self instance for chaining.
     */
    default T setAbsoluteWebLocations(boolean value) {
        return amend(ABSOLUTE_WEB_LOCATIONS_OPTION, value);
    }

    /**
     * Get whether Get Element Location returns coordinates
     * relative to the page origin for web view elements.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesAbsoluteWebLocations() {
        return Optional.ofNullable(toSafeBoolean(getCapability(ABSOLUTE_WEB_LOCATIONS_OPTION)));
    }
}
