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

public interface SupportsResetOnSessionStartOnlyOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String RESET_ON_SESSION_START_ONLY_OPTION = "resetOnSessionStartOnly";

    /**
     * Whether to perform reset on test session finish (false) or not (true).
     * Keeping this variable set to true and Simulator running (the default
     * behaviour since version 1.6.4) may significantly shorten the duration of
     * test session initialization.
     *
     * @param value Whether to perform reset on test session finish (false) or not (true)..
     * @return self instance for chaining.
     */
    default T setResetOnSessionStartOnly(boolean value) {
        return amend(RESET_ON_SESSION_START_ONLY_OPTION, value);
    }

    /**
     * Get whether to perform reset on test session finish (false) or not (true).
     *
     * @return True or false.
     */
    default Optional<Boolean> doesResetOnSessionStartOnly() {
        return Optional.ofNullable(toSafeBoolean(getCapability(RESET_ON_SESSION_START_ONLY_OPTION)));
    }
}
