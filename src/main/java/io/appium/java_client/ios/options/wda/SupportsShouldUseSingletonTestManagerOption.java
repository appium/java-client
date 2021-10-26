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

public interface SupportsShouldUseSingletonTestManagerOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SHOULD_USE_SINGLETON_TEST_MANAGER_OPTION = "shouldUseSingletonTestManager";

    /**
     * Enforce usage of the default proxy for test management within WebDriverAgent.
     *
     * @return self instance for chaining.
     */
    default T shouldUseSingletonTestManager() {
        return amend(SHOULD_USE_SINGLETON_TEST_MANAGER_OPTION, true);
    }

    /**
     * Use default proxy for test management within WebDriverAgent. Setting this to false
     * sometimes helps with socket hangup problems. Defaults to true.
     *
     * @param value Whether to use the default proxy for test management within WebDriverAgent.
     * @return self instance for chaining.
     */
    default T setShouldUseSingletonTestManager(boolean value) {
        return amend(SHOULD_USE_SINGLETON_TEST_MANAGER_OPTION, value);
    }

    /**
     * Get whether to use the default proxy for test management within WebDriverAgent.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesUseSingletonTestManager() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SHOULD_USE_SINGLETON_TEST_MANAGER_OPTION)));
    }
}
