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

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

/**
 * Support for the {@code enablePerformanceLogging} capability: whether performance logging is enabled.
  *
  * @param <T> the concrete options type, returned for chaining
 */
public interface SupportsEnablePerformanceLoggingOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code enablePerformanceLogging} capability.
     */
    String ENABLE_PERFORMANCE_LOGGING_OPTION = "enablePerformanceLogging";

    /**
     * Set the app to enable performance logging. Safari performance logging on iOS,
     * Chrome performance logging on Android (deprecated there, use {@code chromeLoggingPrefs}).
     *
     * @return self instance for chaining.
     */
    default T enablePerformanceLogging() {
        return setEnablePerformanceLogging(true);
    }

    /**
     * Set whether performance logging is enabled. Safari performance logging on iOS,
     * Chrome performance logging on Android (deprecated there, use {@code chromeLoggingPrefs}).
     *
     * @param bool is whether performance logging is enabled.
     * @return self instance for chaining.
     */
    default T setEnablePerformanceLogging(boolean bool) {
        return amend(ENABLE_PERFORMANCE_LOGGING_OPTION, bool);
    }

    /**
     * Get whether performance logging is enabled.
     *
     * @return true if performance logging is enabled.
     */
    default Optional<Boolean> isEnablePerformanceLogging() {
        return Optional.ofNullable(toSafeBoolean(getCapability(ENABLE_PERFORMANCE_LOGGING_OPTION)));
    }
}
