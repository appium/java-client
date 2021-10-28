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

package io.appium.java_client.ios.options.other;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsUseJsonSourceOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String USE_JSON_SOURCE_OPTION = "useJSONSource";

    /**
     * Enforces getting JSON source from WDA and transform it to XML on the Appium
     * server side.
     *
     * @return self instance for chaining.
     */
    default T useJSONSource() {
        return amend(USE_JSON_SOURCE_OPTION, true);
    }

    /**
     * Get JSON source from WDA and transform it to XML on the Appium server side.
     * Defaults to false.
     *
     * @param value Whether to get JSON source from WDA and transform it to XML.
     * @return self instance for chaining.
     */
    default T setUseJSONSource(boolean value) {
        return amend(USE_JSON_SOURCE_OPTION, value);
    }

    /**
     * Get whether to get JSON source from WDA and transform it to XML.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesUseJSONSource() {
        return Optional.ofNullable(toSafeBoolean(getCapability(USE_JSON_SOURCE_OPTION)));
    }
}
