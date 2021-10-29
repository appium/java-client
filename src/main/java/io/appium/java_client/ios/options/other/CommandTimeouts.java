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

import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.remote.options.BaseMapOptionData;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

public class CommandTimeouts extends BaseMapOptionData<CommandTimeouts> {
    public static final String DEFAULT_COMMAND = "default";

    public CommandTimeouts() {
    }

    public CommandTimeouts(Map<String, Object> timeouts) {
        super(timeouts);
    }

    public CommandTimeouts(String json) {
        super(json);
    }

    /**
     * Sets the timeout for the particular Appium command that
     * is proxied to WDA.
     * Command names you can find in logs, look for
     * "Executing command 'command_name'" records.
     * Timeout value is expected to  contain max milliseconds to wait for
     * the given WDA command to be executed before terminating the session forcefully.
     *
     * @param commandName The command name.
     * @param timeout Command timeout.
     * @return self instance for chaining.
     */
    public CommandTimeouts withCommandTimeout(String commandName, Duration timeout) {
        return assignOptionValue(commandName, timeout.toMillis());
    }

    /**
     * Sets the default timeout for all Appium commands that
     * are proxied to WDA.
     *
     * @param timeout Commands timeout.
     * @return self instance for chaining.
     */
    public CommandTimeouts withDefaultCommandTimeout(Duration timeout) {
        return withCommandTimeout(DEFAULT_COMMAND, timeout);
    }

    /**
     * Get the command timeout.
     *
     * @param commandName The command name
     * @return Timeout value.
     */
    public Optional<Duration> getCommandTimeout(String commandName) {
        Optional<Object> result = getOptionValue(commandName);
        return result.map(CapabilityHelpers::toDuration);
    }
}
