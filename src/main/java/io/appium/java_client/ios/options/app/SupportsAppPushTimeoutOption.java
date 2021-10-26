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

package io.appium.java_client.ios.options.app;

import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.time.Duration;
import java.util.Optional;

public interface SupportsAppPushTimeoutOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String APP_PUSH_TIMEOUT_OPTION = "appPushTimeout";

    /**
     * The timeout for application upload.
     * Works for real devices only. The default value is 30000ms.
     *
     * @param timeout App push timeout.
     * @return self instance for chaining.
     */
    default T setAppPushTimeout(Duration timeout) {
        return amend(APP_PUSH_TIMEOUT_OPTION, timeout.toMillis());
    }

    /**
     * Get maximum timeout for application upload.
     *
     * @return Timeout value.
     */
    default Optional<Duration> getAppPushTimeout() {
        return Optional.ofNullable(
                CapabilityHelpers.toDuration(getCapability(APP_PUSH_TIMEOUT_OPTION))
        );
    }
}
