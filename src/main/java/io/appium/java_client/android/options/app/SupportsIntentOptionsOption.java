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

import java.util.Map;
import java.util.Optional;

public interface SupportsIntentOptionsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String INTENT_OPTIONS_OPTION = "intentOptions";

    /**
     * The mapping of custom options for the intent that is going to be passed
     * to the main app activity. Check
     * https://github.com/appium/appium-espresso-driver#intent-options
     * for more details.
     *
     * @param options Intent options.
     * @return self instance for chaining.
     */
    default T setIntentOptions(IntentOptions options) {
        return amend(INTENT_OPTIONS_OPTION, options);
    }

    /**
     * Get intent options.
     *
     * @return Intent options.
     */
    default Optional<IntentOptions> getIntentOptions() {
        //noinspection unchecked
        return Optional.ofNullable(getCapability(INTENT_OPTIONS_OPTION))
                .map((v) -> new IntentOptions((Map<String, Object>) v));
    }
}
