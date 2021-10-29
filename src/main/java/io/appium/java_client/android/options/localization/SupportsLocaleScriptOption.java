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

package io.appium.java_client.android.options.localization;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsLocaleScriptOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String LOCALE_SCRIPT_OPTION = "localeScript";

    /**
     * Set canonical name of the locale to be set for the app under test,
     * for example zh-Hans-CN.
     * See https://developer.android.com/reference/java/util/Locale.html for more details.
     *
     * @param localeScript is the language abbreviation.
     * @return this MobileOptions, for chaining.
     */
    default T setLocaleScript(String localeScript) {
        return amend(LOCALE_SCRIPT_OPTION, localeScript);
    }

    /**
     * Get canonical name of the locale to be set for the app under test.
     *
     * @return Locale script value.
     */
    default Optional<String> getLocaleScript() {
        return Optional.ofNullable((String) getCapability(LOCALE_SCRIPT_OPTION));
    }
}
