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

package io.appium.java_client.android.options.avd;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsIsHeadlessOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String IS_HEADLESS_OPTION = "isHeadless";

    /**
     * Set emulator to start in headless mode (e.g. no UI is shown).
     * It is only applied if the emulator is not running before the test starts.
     *
     * @return self instance for chaining.
     */
    default T headless() {
        return amend(IS_HEADLESS_OPTION, true);
    }

    /**
     * If set to true then emulator starts in headless mode (e.g. no UI is shown).
     * It is only applied if the emulator is not running before the test starts.
     * false by default.
     *
     * @param value Whether to enable or disable headless mode.
     * @return self instance for chaining.
     */
    default T setIsHeadless(boolean value) {
        return amend(IS_HEADLESS_OPTION, value);
    }

    /**
     * Get whether the emulator starts in headless mode.
     *
     * @return True or false.
     */
    default Optional<Boolean> isHeadless() {
        return Optional.ofNullable(toSafeBoolean(getCapability(IS_HEADLESS_OPTION)));
    }
}
