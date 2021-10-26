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

public interface SupportsScreenshotQualityOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SCREENSHOT_QUALITY_OPTION = "screenshotQuality";

    /**
     * Changes the quality of phone display screenshots following
     * xctest/xctimagequality Default value is 1. 0 is the highest and
     * 2 is the lowest quality. You can also change it via settings
     * command. 0 might cause OutOfMemory crash on high-resolution
     * devices like iPad Pro.
     *
     * @param quality Quality value in range 0..2.
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
