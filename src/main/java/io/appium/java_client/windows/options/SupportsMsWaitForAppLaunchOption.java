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

package io.appium.java_client.windows.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.time.Duration;
import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toDuration;

public interface SupportsMsWaitForAppLaunchOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String MS_WAIT_FOR_APP_LAUNCH_OPTION = "ms:waitForAppLaunch";

    /**
     * Similar to createSessionTimeout, but is
     * applied on the server side. Enables Appium Windows Driver to wait for
     * a defined amount of time after an app launch is initiated prior to
     * attaching to the application session. The limit for this is 50 seconds.
     *
     * @param timeout The timeout value.
     * @return self instance for chaining.
     */
    default T setWaitForAppLaunch(Duration timeout) {
        return amend(MS_WAIT_FOR_APP_LAUNCH_OPTION, timeout.getSeconds());
    }

    /**
     * Get the timeout used to retry Appium Windows Driver session startup.
     *
     * @return The timeout value.
     */
    default Optional<Duration> doesWaitForAppLaunch() {
        return Optional.ofNullable(
                toDuration(getCapability(MS_WAIT_FOR_APP_LAUNCH_OPTION), Duration::ofSeconds)
        );
    }
}
