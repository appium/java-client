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

import static io.appium.java_client.internal.CapabilityHelpers.toInteger;

/**
 * Provides getters and setters for the {@code screenshotQuality} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsScreenshotQualityOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code screenshotQuality} capability.
     */
    String SCREENSHOT_QUALITY_OPTION = "screenshotQuality";

    /**
     * Changes the quality of phone display screenshots. Supported values:
     * 0 - lossless PNG, 1 - high-quality JPEG, 2 - low-quality JPEG,
     * 3 - lossless HEIC (falls back to PNG if hardware HEIC encoding is not
     * supported). The default is defined by the driver/WebDriverAgent version.
     * You can also change it via settings command.
     *
     * @param quality Quality value in range 0..3.
     * @return self instance for chaining.
     */
    default T setScreenshotQuality(int quality) {
        return amend(SCREENSHOT_QUALITY_OPTION, quality);
    }

    /**
     * Get the screenshot quality value.
     *
     * @return The screenshot quality value.
     */
    default Optional<Integer> getScreenshotQuality() {
        return Optional.ofNullable(toInteger(getCapability(SCREENSHOT_QUALITY_OPTION)));
    }
}
