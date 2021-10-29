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

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsDisableAutomaticScreenshotsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String DISABLE_AUTOMATIC_SCREENSHOTS_OPTION = "disableAutomaticScreenshots";

    /**
     * Disable automatic screenshots taken by XCTest at every interaction.
     * Default is up to WebDriverAgent's config to decide, which currently
     * defaults to true.
     *
     * @param value Whether to disable automatic XCTest screenshots.
     * @return self instance for chaining.
     */
    default T setDisableAutomaticScreenshots(boolean value) {
        return amend(DISABLE_AUTOMATIC_SCREENSHOTS_OPTION, value);
    }

    /**
     * Get whether to disable automatic XCTest screenshots.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesDisableAutomaticScreenshots() {
        return Optional.ofNullable(toSafeBoolean(getCapability(DISABLE_AUTOMATIC_SCREENSHOTS_OPTION)));
    }
}
