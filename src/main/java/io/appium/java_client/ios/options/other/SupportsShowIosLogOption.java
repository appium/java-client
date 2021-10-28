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

public interface SupportsShowIosLogOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SHOW_IOS_LOG_OPTION = "showIOSLog";

    /**
     * Enforces showing any logs captured from a device in the appium logs.
     *
     * @return self instance for chaining.
     */
    default T showIosLog() {
        return amend(SHOW_IOS_LOG_OPTION, true);
    }

    /**
     * Whether to show any logs captured from a device in the appium logs.
     * Default false.
     *
     * @param value Whether to show any logs captured from a device in the appium logs.
     * @return self instance for chaining.
     */
    default T setShowIosLog(boolean value) {
        return amend(SHOW_IOS_LOG_OPTION, value);
    }

    /**
     * Get whether to show any logs captured from a device in the appium logs.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesShowIosLog() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SHOW_IOS_LOG_OPTION)));
    }
}
