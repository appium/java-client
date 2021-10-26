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

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toInteger;

public interface SupportsWdaStartupRetriesOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String WDA_STARTUP_RETRIES_OPTION = "wdaStartupRetries";

    /**
     * Number of times to try to build and launch WebDriverAgent onto the device.
     * Defaults to 2.
     *
     * @param count Retries count.
     * @return self instance for chaining.
     */
    default T setWdaStartupRetries(int count) {
        return amend(WDA_STARTUP_RETRIES_OPTION, count);
    }

    /**
     * Get number of retries before to fail WDA deployment.
     *
     * @return Retries count.
     */
    default Optional<Integer> getWdaStartupRetries() {
        return Optional.ofNullable(toInteger(getCapability(WDA_STARTUP_RETRIES_OPTION)));
    }
}
