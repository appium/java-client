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

package io.appium.java_client.mac.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toInteger;

public interface SupportsSystemPortOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SYSTEM_PORT_OPTION = "systemPort";

    /**
     * The number of the port for the internal server to listen on.
     * If not provided then Mac2Driver will use the default port 10100.
     *
     * @param port port number in range 0..65535
     * @return self instance for chaining.
     */
    default T setSystemPort(int port) {
        return amend(SYSTEM_PORT_OPTION, port);
    }

    /**
     * Get the number of the port for the internal server to listen on.
     *
     * @return System port value.
     */
    default Optional<Integer> getSystemPort() {
        return Optional.ofNullable(toInteger(getCapability(SYSTEM_PORT_OPTION)));
    }
}
