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

/**
 * Provides getters and setters for the {@code appLaunchStateTimeoutSec} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsAppLaunchStateTimeoutSecOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code appLaunchStateTimeoutSec} capability.
     */
    String APP_LAUNCH_STATE_TIMEOUT_SEC_OPTION = "appLaunchStateTimeoutSec";

    /**
     * Sets the timeout in float seconds for the application state change on session startup
     * in the exclusive range (0, 240). Defaults to 60 seconds in XCTest. Has no effect if
     * the application is not (re)started at session startup.
     *
     * @param timeout Launch state timeout duration.
     * @return self instance for chaining.
     */
    default T setAppLaunchStateTimeoutSec(Duration timeout) {
        return amend(APP_LAUNCH_STATE_TIMEOUT_SEC_OPTION, timeout.toMillis() / 1000.0);
    }

    /**
     * Get the application launch state timeout.
     *
     * @return Launch state timeout duration.
     */
    default Optional<Duration> getAppLaunchStateTimeoutSec() {
        return Optional.ofNullable(getCapability(APP_LAUNCH_STATE_TIMEOUT_SEC_OPTION))
                .map(CapabilityHelpers::toDouble)
                .map(d -> toDuration(Math.round(d * 1000.0)));
    }
}
