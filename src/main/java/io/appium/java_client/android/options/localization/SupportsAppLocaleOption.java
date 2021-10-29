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

public interface SupportsAppLocaleOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String APP_LOCALE_OPTION = "appLocale";

    /**
     * Sets the locale for the app under test. The main difference between this option
     * and the above ones is that this option only changes the locale for the application
     * under test and does not affect other parts of the system. Also, it only uses
     * public APIs for its purpose. See
     * https://github.com/libyal/libfwnt/wiki/Language-Code-identifiers to get the
     * list of available language abbreviations.
     * Example: {"language": "zh", "country": "CN", "variant": "Hans"}.
     *
     * @param locale App locale data.
     * @return this MobileOptions, for chaining.
     */
    default T setAppLocale(AppLocale locale) {
        return amend(APP_LOCALE_OPTION, locale);
    }

    /**
     * Get the locale for the app under test.
     *
     * @return App locale data.
     */
    default Optional<String> getAppLocale() {
        return Optional.ofNullable((String) getCapability(APP_LOCALE_OPTION));
    }
}
