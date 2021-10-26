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

import java.util.Map;
import java.util.Optional;

public interface SupportsChromeOptionsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String CHROME_OPTIONS_OPTION = "chromeOptions";

    /**
     * A mapping, that allows to customize chromedriver options.
     * See https://chromedriver.chromium.org/capabilities for the list
     * of available entries.
     *
     * @param opts Chrome options.
     * @return self instance for chaining.
     */
    default T setChromeOptions(Map<String, Object> opts) {
        return amend(CHROME_OPTIONS_OPTION, opts);
    }

    /**
     * Get chrome options.
     *
     * @return Chrome options.
     */
    default Optional<Map<String, Object>> getChromeOptions() {
        //noinspection unchecked
        return Optional.ofNullable((Map<String, Object>) getCapability(CHROME_OPTIONS_OPTION));
    }
}
