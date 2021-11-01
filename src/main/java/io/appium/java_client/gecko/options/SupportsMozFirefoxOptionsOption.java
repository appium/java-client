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

package io.appium.java_client.gecko.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Map;
import java.util.Optional;

public interface SupportsMozFirefoxOptionsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String MOZ_FIREFOX_OPTIONS_OPTION = "moz:firefoxOptions";

    /**
     * See https://developer.mozilla.org/en-US/docs/Web/WebDriver/Capabilities/firefoxOptions.
     *
     * @param options Firefox options mapping.
     * @return self instance for chaining.
     */
    default T setMozFirefoxOptions(Map<String, Object> options) {
        return amend(MOZ_FIREFOX_OPTIONS_OPTION, options);
    }

    /**
     * Get Firefox options mapping.
     *
     * @return Firefox options mapping.
     */
    default Optional<Map<String, Object>> getMozFirefoxOptions() {
        //noinspection unchecked
        return Optional.ofNullable((Map<String, Object>) getCapability(MOZ_FIREFOX_OPTIONS_OPTION));
    }
}
