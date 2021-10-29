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

package io.appium.java_client.ios.options.simulator;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsKeepKeyChainsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String KEEP_KEY_CHAINS_OPTION = "keepKeyChains";

    /**
     * Enforce preservation of Simulator keychains folder after full reset.
     *
     * @return self instance for chaining.
     */
    default T keepKeyChains() {
        return amend(KEEP_KEY_CHAINS_OPTION, true);
    }

    /**
     * Set the capability to true in order to preserve Simulator keychains folder after
     * full reset. This feature has no effect on real devices. Defaults to false.
     *
     * @param value Whether to preserve Simulator keychains after full reset.
     * @return self instance for chaining.
     */
    default T setKeepKeyChains(boolean value) {
        return amend(KEEP_KEY_CHAINS_OPTION, value);
    }

    /**
     * Get whether to preserve Simulator keychains after full reset.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesKeepKeyChains() {
        return Optional.ofNullable(toSafeBoolean(getCapability(KEEP_KEY_CHAINS_OPTION)));
    }
}
