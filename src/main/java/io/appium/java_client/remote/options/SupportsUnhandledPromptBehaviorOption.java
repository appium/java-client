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

package io.appium.java_client.remote.options;

import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsUnhandledPromptBehaviorOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String UNHANDLED_PROMPT_BEHAVIOR_OPTION = "unhandledPromptBehavior";

    /**
     * Defines the current session’s page load strategy.
     *
     * @param strategy Page load strategy.
     * @return self instance for chaining.
     */
    default T setUnhandledPromptBehavior(UnhandledPromptBehavior strategy) {
        return amend(UNHANDLED_PROMPT_BEHAVIOR_OPTION, strategy.toString());
    }

    /**
     * Get the current session’s page load strategy.
     *
     * @return Page load strategy.
     */
    default Optional<UnhandledPromptBehavior> getUnhandledPromptBehavior() {
        return Optional.ofNullable(getCapability(UNHANDLED_PROMPT_BEHAVIOR_OPTION))
                .map(String::valueOf)
                .map(UnhandledPromptBehavior::fromString);
    }
}
