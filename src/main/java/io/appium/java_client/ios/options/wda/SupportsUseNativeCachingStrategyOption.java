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

package io.appium.java_client.ios.options.wda;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsUseNativeCachingStrategyOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String USE_NATIVE_CACHING_STRATEGY_OPTION = "useNativeCachingStrategy";

    /**
     * Set this capability to false in order to use the custom elements caching
     * strategy. This might help to avoid stale element exception on property
     * change. By default, the native XCTest cache resolution is used (true)
     * for all native locators (e.g. all, but xpath).
     *
     * @param value Whether to use the native caching strategy.
     * @return self instance for chaining.
     */
    default T setUseNativeCachingStrategy(boolean value) {
        return amend(USE_NATIVE_CACHING_STRATEGY_OPTION, value);
    }

    /**
     * Get whether to use the native caching strategy.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesUseNativeCachingStrategy() {
        return Optional.ofNullable(toSafeBoolean(getCapability(USE_NATIVE_CACHING_STRATEGY_OPTION)));
    }
}
