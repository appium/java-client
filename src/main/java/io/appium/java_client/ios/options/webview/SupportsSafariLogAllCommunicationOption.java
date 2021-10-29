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

package io.appium.java_client.ios.options.webview;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsSafariLogAllCommunicationOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SAFARI_LOG_ALL_COMMUNICATION_OPTION = "safariLogAllCommunication";

    /**
     * Enforces logging of plists sent to and received from the Web Inspector.
     *
     * @return self instance for chaining.
     */
    default T safariLogAllCommunication() {
        return amend(SAFARI_LOG_ALL_COMMUNICATION_OPTION, true);
    }

    /**
     * Log all plists sent to and received from the Web Inspector, as plain text.
     * For some operations this can be a lot of data, so it is recommended to
     * be used only when necessary. Defaults to false.
     *
     * @param value Whether to log all internal web debugger communication.
     * @return self instance for chaining.
     */
    default T setSafariLogAllCommunication(boolean value) {
        return amend(SAFARI_LOG_ALL_COMMUNICATION_OPTION, value);
    }

    /**
     * Get whether to log of plists sent to and received from the Web Inspector.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesSafariLogAllCommunication() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SAFARI_LOG_ALL_COMMUNICATION_OPTION)));
    }
}
