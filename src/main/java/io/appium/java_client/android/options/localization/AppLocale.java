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

import io.appium.java_client.android.options.BaseMapOptionData;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AppLocale extends BaseMapOptionData<AppLocale> {
    public AppLocale() {
        super();
    }

    public AppLocale(Map<String, Object> options) {
        super(options);
    }

    /**
     * Language identifier. See
     * https://github.com/libyal/libfwnt/wiki/Language-Code-identifiers#language-identifiers
     * for the list of available values.
     *
     * @param lang Language identifier, for example "zh".
     * @return self instance for chaining.
     */
    public AppLocale withLanguage(String lang) {
        return assignOptionValue("language", lang);
    }

    /**
     * Get the language identifier.
     *
     * @return Language identifier.
     */
    public Optional<String> getLanguage() {
        return getOptionValue("language");
    }

    /**
     * Allows to set a country identifier.
     *
     * @param country Country identifier, for example "CN".
     * @return self instance for chaining.
     */
    public AppLocale withCountry(String country) {
        return assignOptionValue("country", country);
    }

    /**
     * Get the country identifier.
     *
     * @return Country identifier.
     */
    public Optional<String> getCountry() {
        return getOptionValue("country");
    }

    /**
     * Allows to set an optional language variant value.
     *
     * @param variant Language variant, for example "Hans".
     * @return self instance for chaining.
     */
    public AppLocale withVariant(String variant) {
        return assignOptionValue("variant", variant);
    }

    /**
     * Get the language variant.
     *
     * @return Language variant.
     */
    public Optional<String> getVariant() {
        return getOptionValue("variant");
    }
}
