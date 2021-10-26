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

import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.time.Duration;
import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toDuration;

public interface SupportsWaitForIdleTimeoutOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String WAIT_FOR_IDLE_TIMEOUT_OPTION = "waitForIdleTimeout";

    /**
     * The time to wait until the application under test is idling.
     * XCTest requires the app's main thread to be idling in order to execute any action on it,
     * so WDA might not even start/freeze if the app under test is constantly hogging the main
     * thread. The default value is 10 (seconds). Setting it to zero disables idling checks completely
     * (not recommended) and has the same effect as setting waitForQuiescence to false.
     * Available since Appium 1.20.0.
     *
     * @param timeout Idle timeout.
     * @return self instance for chaining.
     */
    default T setWaitForIdleTimeout(Duration timeout) {
        return amend(WAIT_FOR_IDLE_TIMEOUT_OPTION, timeout.toMillis() / 1000.0);
    }

    /**
     * Get the maximum timeout to wait until WDA responds to HTTP requests.
     *
     * @return Timeout value.
     */
    default Optional<Duration> getWaitForIdleTimeout() {
        return Optional.ofNullable(getCapability(WAIT_FOR_IDLE_TIMEOUT_OPTION))
                .map(CapabilityHelpers::toDouble)
                .map((d) -> toDuration((long) (d * 1000.0)));
    }
}
