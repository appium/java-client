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

public interface SupportsAppDurationOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String APP_WAIT_DURATION_OPTION = "appWaitDuration";

    /**
     * Maximum amount of time to wait until the application under test is started
     * (e.g. an activity returns the control to the caller). 20000 ms by default.
     *
     * @param appWaitDuration Package identifier to wait for.
     * @return self instance for chaining.
     */
    default T setAppWaitDuration(Duration appWaitDuration) {
        return amend(APP_WAIT_DURATION_OPTION, appWaitDuration.toMillis());
    }

    /**
     * Get the identifier of the app package to wait for.
     *
     * @return Package identifier.
     */
    default Optional<Duration> getAppWaitDuration() {
        Long value = toSafeLong(getCapability(APP_WAIT_DURATION_OPTION));
        return Optional.ofNullable(value == null ? null : Duration.ofMillis(value));
    }
}
