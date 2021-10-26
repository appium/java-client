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

public interface SupportsChromeLoggingPrefsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String CHROME_LOGGING_PREFS_OPTION = "chromeLoggingPrefs";

    /**
     * Chrome logging preferences mapping. Basically the same as
     * [goog:loggingPrefs](https://newbedev.com/
     * getting-console-log-output-from-chrome-with-selenium-python-api-bindings).
     * It is set to {"browser": "ALL"} by default.
     *
     * @param opts Chrome logging preferences.
     * @return self instance for chaining.
     */
    default T setChromeLoggingPrefs(Map<String, Object> opts) {
        return amend(CHROME_LOGGING_PREFS_OPTION, opts);
    }

    /**
     * Get chrome logging preferences.
     *
     * @return Chrome logging preferences.
     */
    default Optional<Map<String, Object>> getChromeLoggingPrefs() {
        //noinspection unchecked
        return Optional.ofNullable((Map<String, Object>) getCapability(CHROME_LOGGING_PREFS_OPTION));
    }
}
