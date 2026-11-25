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

/**
 * Provides getters and setters for the {@code prebuildWDA} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsPrebuildWdaOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code prebuildWDA} capability.
     */
    String PREBUILD_WDA_OPTION = "prebuildWDA";

    /**
     * Enables prebuilding the WebDriverAgentRunner application before running WebDriverAgent.
     *
     * @return self instance for chaining.
     */
    default T prebuildWda() {
        return amend(PREBUILD_WDA_OPTION, true);
    }

    /**
     * Enables or disables prebuilding the WebDriverAgentRunner application first and then
     * running the session as with {@code usePrebuiltWDA} enabled. Defaults to {@code false}.
     *
     * @param value Whether to prebuild WebDriverAgent.
     * @return self instance for chaining.
     */
    default T setPrebuildWda(boolean value) {
        return amend(PREBUILD_WDA_OPTION, value);
    }

    /**
     * Get whether to prebuild WebDriverAgent before running the session.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesPrebuildWda() {
        return Optional.ofNullable(toSafeBoolean(getCapability(PREBUILD_WDA_OPTION)));
    }
}
