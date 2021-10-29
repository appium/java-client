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

public interface SupportsIntentCategoryOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String INTENT_CATEGORY_OPTION = "intentCategory";

    /**
     * Set an optional intent category to be applied when
     * starting the given appActivity by Activity Manager.
     *
     * @param intentCategory Intent category class name.
     * @return self instance for chaining.
     */
    default T setIntentCategory(String intentCategory) {
        return amend(INTENT_CATEGORY_OPTION, intentCategory);
    }

    /**
     * Get intent category to be applied when
     * starting the given appActivity by Activity Manager.
     *
     * @return Intent category class name.
     */
    default Optional<String> getIntentCategory() {
        return Optional.ofNullable((String) getCapability(INTENT_CATEGORY_OPTION));
    }
}
