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

package io.appium.java_client.android.options.app;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsIntentFlagsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String INTENT_FLAGS_OPTION = "intentFlags";

    /**
     * Set an optional intent flags to be applied when
     * starting the given appActivity by Activity Manager.
     *
     * @param intentFlags Intent flags hexadecimal string.
     * @return self instance for chaining.
     */
    default T setIntentFlags(String intentFlags) {
        return amend(INTENT_FLAGS_OPTION, intentFlags);
    }

    /**
     * Get intent flags to be applied when
     * starting the given appActivity by Activity Manager.
     *
     * @return Intent flags string.
     */
    default Optional<String> getIntentFlags() {
        return Optional.ofNullable((String) getCapability(INTENT_FLAGS_OPTION));
    }
}
