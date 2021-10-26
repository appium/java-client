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

public interface SupportsWdaLocalPortOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String WDA_LOCAL_PORT_OPTION = "wdaLocalPort";

    /**
     * This value, if specified, will be used to forward traffic from
     * Mac host to real ios devices over USB.
     * Default value is the same as the port number used by WDA on
     * the device under test (8100).
     *
     * @param port port number in range 0..65535
     * @return self instance for chaining.
     */
    default T setWdaLocalPort(int port) {
        return amend(WDA_LOCAL_PORT_OPTION, port);
    }

    /**
     * Get the local port number where the WDA traffic is being forwarded.
     *
     * @return The port number.
     */
    default Optional<Integer> getWdaLocalPort() {
        return Optional.ofNullable(toInteger(getCapability(WDA_LOCAL_PORT_OPTION)));
    }
}
