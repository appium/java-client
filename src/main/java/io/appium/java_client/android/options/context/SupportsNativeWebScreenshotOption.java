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

package io.appium.java_client.android.options.context;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsNativeWebScreenshotOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String NATIVE_WEB_SCREENSHOT_OPTION = "nativeWebScreenshot";

    /**
     * Enforce to use screenshoting endpoint provided by UiAutomator framework
     * rather than the one provided by chromedriver.
     *
     * @return self instance for chaining.
     */
    default T nativeWebScreenshot() {
        return amend(NATIVE_WEB_SCREENSHOT_OPTION, true);
    }

    /**
     * Whether to use screenshoting endpoint provided by UiAutomator framework (true)
     * rather than the one provided by chromedriver (false, the default value).
     * Use it when you experience issues with the latter.
     *
     * @param value Whether to use native screenshots in web view context.
     * @return self instance for chaining.
     */
    default T setNativeWebScreenshot(boolean value) {
        return amend(NATIVE_WEB_SCREENSHOT_OPTION, value);
    }

    /**
     * Get whether to use native screenshots in web view context.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesNativeWebScreenshot() {
        return Optional.ofNullable(toSafeBoolean(getCapability(NATIVE_WEB_SCREENSHOT_OPTION)));
    }
}
