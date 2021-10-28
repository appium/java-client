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

import com.google.gson.JsonObject;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.time.Duration;
import java.util.Optional;

public interface SupportsCommandTimeoutsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String COMMAND_TIMEOUTS_OPTION = "commandTimeouts";

    /**
     * Custom timeout(s) in milliseconds for WDA backend commands execution.
     * This might be useful if WDA backend freezes unexpectedly or requires too
     * much time to fail and blocks automated test execution. The value is expected
     * to be of type string and can either contain max milliseconds to wait for
     * each WDA command to be executed before terminating the session forcefully
     * or a valid JSON string, where keys are internal Appium command names (you
     * can find these in logs, look for "Executing command 'command_name'" records)
     * and values are timeouts in milliseconds. You can also set the 'default' key
     * to assign the timeout for all other commands not explicitly enumerated as
     * JSON keys.
     *
     * @param timeouts E.g. '{"findElement": 40000, "findElements": 40000}'.
     * @return self instance for chaining.
     */
    default T setCommandTimeouts(JsonObject timeouts) {
        return amend(COMMAND_TIMEOUTS_OPTION, timeouts.toString());
    }

    /**
     * Custom timeout(s) in milliseconds for WDA backend commands execution.
     * This might be useful if WDA backend freezes unexpectedly or requires too
     * much time to fail and blocks automated test execution. The value is expected
     * to be of type string and can either contain max milliseconds to wait for
     * each WDA command to be executed before terminating the session forcefully
     * or a valid JSON string, where keys are internal Appium command names (you
     * can find these in logs, look for "Executing command 'command_name'" records)
     * and values are timeouts in milliseconds. You can also set the 'default' key
     * to assign the timeout for all other commands not explicitly enumerated as
     * JSON keys.
     *
     * @param timeout The timeout value for all commands.
     * @return self instance for chaining.
     */
    default T setCommandTimeouts(Duration timeout) {
        return amend(COMMAND_TIMEOUTS_OPTION, String.valueOf(timeout.toMillis()));
    }

    /**
     * Get custom timeout(s) in milliseconds for WDA backend commands execution.
     *
     * @return Command timeouts.
     */
    default Optional<String> getCommandTimeouts() {
        return Optional.ofNullable(
                (String) getCapability(COMMAND_TIMEOUTS_OPTION)
        );
    }
}
