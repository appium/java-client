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

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Capabilities;

public interface SupportsEnablePerformanceLoggingOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability {

    /**
     * Set the app to enable performance logging.
     *
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#ENABLE_PERFORMANCE_LOGGING
     */
    default T setEnablePerformanceLogging() {
        return setEnablePerformanceLogging(true);
    }

    /**
     * Set whether the app logs performance.
     *
     * @param bool is whether the app logs performance.
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#ENABLE_PERFORMANCE_LOGGING
     */
    default T setEnablePerformanceLogging(boolean bool) {
        setCapability(MobileCapabilityType.ENABLE_PERFORMANCE_LOGGING, bool);
        //noinspection unchecked
        return (T) this;
    }

    /**
     * Get the app logs performance.
     *
     * @return true if the app logs performance.
     * @see MobileCapabilityType#ENABLE_PERFORMANCE_LOGGING
     */
    default boolean isEnablePerformanceLogging() {
        return (boolean) getCapability(MobileCapabilityType.ENABLE_PERFORMANCE_LOGGING);
    }
}
