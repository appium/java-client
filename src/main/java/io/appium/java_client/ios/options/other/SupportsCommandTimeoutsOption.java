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
import org.openqa.selenium.internal.Either;

import java.time.Duration;
import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toDuration;

public interface SupportsCommandTimeoutsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String COMMAND_TIMEOUTS_OPTION = "commandTimeouts";

    /**
     * Custom timeout(s) in milliseconds for WDA backend commands execution.
     * This might be useful if WDA backend freezes unexpectedly or requires too
     * much time to fail and blocks automated test execution.
     *
     * @param timeouts Command timeouts.
     * @return self instance for chaining.
     */
    default T setCommandTimeouts(CommandTimeouts timeouts) {
        return amend(COMMAND_TIMEOUTS_OPTION, timeouts.toString());
    }

    /**
     * Custom timeout for all WDA backend commands execution.
     * This might be useful if WDA backend freezes unexpectedly or requires too
     * much time to fail and blocks automated test execution.
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
     * @return Either a global timeout duration or detailed command timeouts.
     */
    default Optional<Either<CommandTimeouts, Duration>> getCommandTimeouts() {
        return Optional.ofNullable(getCapability(COMMAND_TIMEOUTS_OPTION))
                .map(String::valueOf)
                .map((v) ->  v.trim().startsWith("{")
                        ? Either.left(new CommandTimeouts(v))
                        : Either.right(toDuration(v))
                );
    }
}
