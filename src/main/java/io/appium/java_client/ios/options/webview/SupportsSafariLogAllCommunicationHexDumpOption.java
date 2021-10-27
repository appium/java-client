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

public interface SupportsSafariLogAllCommunicationHexDumpOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SAFARI_LOG_ALL_COMMUNICATION_HEX_DUMP_OPTION = "safariLogAllCommunicationHexDump";

    /**
     * Enforces logging of plists sent to and received from the Web Inspector
     * in hex dump format.
     *
     * @return self instance for chaining.
     */
    default T safariLogAllCommunicationHexDump() {
        return amend(SAFARI_LOG_ALL_COMMUNICATION_HEX_DUMP_OPTION, true);
    }

    /**
     * Log all communication sent to and received from the Web Inspector, as raw
     * hex dump and printable characters. This logging is done before any data
     * manipulation, and so can elucidate some communication issues. Like
     * appium:safariLogAllCommunication, this can produce a lot of data in some cases,
     * so it is recommended to be used only when necessary. Defaults to false.
     *
     * @param value Whether to log all internal web debugger communication in hex dump format.
     * @return self instance for chaining.
     */
    default T setSafariLogAllCommunicationHexDump(boolean value) {
        return amend(SAFARI_LOG_ALL_COMMUNICATION_HEX_DUMP_OPTION, value);
    }

    /**
     * Get whether to log of plists sent to and received from the Web Inspector
     * in hex dump format.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesSafariLogAllCommunicationHexDump() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SAFARI_LOG_ALL_COMMUNICATION_HEX_DUMP_OPTION)));
    }
}
