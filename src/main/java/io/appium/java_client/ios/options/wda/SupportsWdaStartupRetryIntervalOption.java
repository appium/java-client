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

public interface SupportsWdaStartupRetryIntervalOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String WDA_STARTUP_RETRY_INTERVAL_OPTION = "wdaStartupRetryInterval";

    /**
     * Time interval to wait between tries to build and launch WebDriverAgent.
     * Defaults to 10000ms.
     *
     * @param interval Interval value.
     * @return self instance for chaining.
     */
    default T setWdaStartupRetryInterval(Duration interval) {
        return amend(WDA_STARTUP_RETRY_INTERVAL_OPTION, interval.toMillis());
    }

    /**
     * Get the interval to wait between tries to build and launch WebDriverAgent.
     *
     * @return Interval value.
     */
    default Optional<Duration> getWdaStartupRetryInterval() {
        return Optional.ofNullable(
                CapabilityHelpers.toDuration(getCapability(WDA_STARTUP_RETRY_INTERVAL_OPTION))
        );
    }
}
