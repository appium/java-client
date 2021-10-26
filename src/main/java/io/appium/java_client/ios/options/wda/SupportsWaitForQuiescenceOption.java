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

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsWaitForQuiescenceOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String WAIT_FOR_QUIESCENCE_OPTION = "waitForQuiescence";

    /**
     * It allows to turn on/off waiting for application quiescence in WebDriverAgent,
     * while performing queries. The default value is true. You can avoid this kind
     * of issues if you turn it off. Consider using waitForIdleTimeout capability
     * instead for this purpose since Appium 1.20.0.
     *
     * @param value Whether to wait for application quiescence.
     * @return self instance for chaining.
     */
    default T setWaitForQuiescence(boolean value) {
        return amend(WAIT_FOR_QUIESCENCE_OPTION, value);
    }

    /**
     * Get whether to wait for application quiescence.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesWaitForQuiescence() {
        return Optional.ofNullable(toSafeBoolean(getCapability(WAIT_FOR_QUIESCENCE_OPTION)));
    }
}
