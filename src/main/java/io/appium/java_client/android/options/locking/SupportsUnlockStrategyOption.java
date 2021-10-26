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

package io.appium.java_client.android.options.locking;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsUnlockStrategyOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String UNLOCK_STRATEGY_OPTION = "unlockStrategy";

    /**
     * Either 'locksettings' (default) or 'uiautomator'.
     * Setting it to 'uiautomator' will enforce the driver to avoid using special
     * ADB shortcuts in order to speed up the unlock procedure.
     *
     * @param strategy The unlock strategy.
     * @return self instance for chaining.
     */
    default T setUnlockStrategy(String strategy) {
        return amend(UNLOCK_STRATEGY_OPTION, strategy);
    }

    /**
     * Get the strategy key.
     *
     * @return Unlock strategy.
     */
    default Optional<String> getUnlockStrategy() {
        return Optional.ofNullable((String) getCapability(UNLOCK_STRATEGY_OPTION));
    }
}
