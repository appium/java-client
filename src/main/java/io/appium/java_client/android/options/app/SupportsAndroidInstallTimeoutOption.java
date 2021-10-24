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

package io.appium.java_client.android.options.app;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.time.Duration;
import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeLong;

public interface SupportsAndroidInstallTimeoutOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String ANDROID_INSTALL_TIMEOUT_OPTION = "androidInstallTimeout";

    /**
     * Maximum amount of time to wait until the application under test is installed.
     * 90000 ms by default
     *
     * @param installTimeout App install timeout.
     * @return self instance for chaining.
     */
    default T setAndroidInstallTimeout(Duration installTimeout) {
        return amend(ANDROID_INSTALL_TIMEOUT_OPTION, installTimeout.toMillis());
    }

    /**
     * Get maximum amount of time to wait until the application under test is installed.
     *
     * @return Timeout value.
     */
    default Optional<Duration> getAndroidInstallTimeout() {
        Long value = toSafeLong(getCapability(ANDROID_INSTALL_TIMEOUT_OPTION));
        return Optional.ofNullable(value == null ? null : Duration.ofMillis(value));
    }
}
