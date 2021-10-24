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

public interface SupportsOptionalIntentArgumentsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String OPTIONAL_INTENT_ARGUMENTS_OPTION = "optionalIntentArguments";

    /**
     * Set an optional intent arguments to be applied when
     * starting the given appActivity by Activity Manager.
     *
     * @param arguments Intent arguments string.
     * @return self instance for chaining.
     */
    default T setOptionalIntentArguments(String arguments) {
        return amend(OPTIONAL_INTENT_ARGUMENTS_OPTION, arguments);
    }

    /**
     * Get intent arguments to be applied when
     * starting the given appActivity by Activity Manager.
     *
     * @return Intent arguments string.
     */
    default Optional<String> getOptionalIntentArguments() {
        return Optional.ofNullable((String) getCapability(OPTIONAL_INTENT_ARGUMENTS_OPTION));
    }
}
