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

import java.time.Duration;
import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeDuration;

public interface SupportsNewCommandTimeoutOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String NEW_COMMAND_TIMEOUT_OPTION = "newCommandTimeout";

    /**
     * Set the timeout for new commands.
     *
     * @param duration is the allowed time before seeing a new command.
     * @return self instance for chaining.
     */
    default T setNewCommandTimeout(Duration duration) {
        return amend(NEW_COMMAND_TIMEOUT_OPTION, duration.getSeconds());
    }

    /**
     * Get the timeout for new commands.
     *
     * @return allowed time before seeing a new command.
     */
    default Optional<Duration> getNewCommandTimeout() {
        return Optional.ofNullable(
                toSafeDuration(getCapability(NEW_COMMAND_TIMEOUT_OPTION), Duration::ofSeconds)
        );
    }
}
