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

public interface SupportsCreateSessionTimeoutOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String CREATE_SESSION_TIMEOUT_OPTION = "createSessionTimeout";

    /**
     * Set the timeout used to retry Appium Windows Driver session startup.
     * This capability could be used as a workaround for the long startup times
     * of UWP applications (aka Failed to locate opened application window
     * with appId: TestCompany.my_app4!App, and processId: 8480). Default value is 20000ms.
     *
     * @param timeout The timeout value.
     * @return self instance for chaining.
     */
    default T setCreateSessionTimeout(Duration timeout) {
        return amend(CREATE_SESSION_TIMEOUT_OPTION, timeout.toMillis());
    }

    /**
     * Get the timeout used to retry Appium Windows Driver session startup.
     *
     * @return The timeout value.
     */
    default Optional<Duration> getCreateSessionTimeout() {
        return Optional.ofNullable(
                toDuration(getCapability(CREATE_SESSION_TIMEOUT_OPTION))
        );
    }
}
