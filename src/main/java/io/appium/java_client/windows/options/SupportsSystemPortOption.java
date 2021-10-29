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

package io.appium.java_client.windows.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toInteger;

public interface SupportsSystemPortOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SYSTEM_PORT_OPTION = "systemPort";

    /**
     * The port number to execute Appium Windows Driver server listener on,
     * for example 5556. The port must not be occupied. The default starting port
     * number for a new Appium Windows Driver session is 4724. If this port is
     * already busy then the next free port will be automatically selected.
     *
     * @param port port number in range 0..65535
     * @return self instance for chaining.
     */
    default T setSystemPort(int port) {
        return amend(SYSTEM_PORT_OPTION, port);
    }

    /**
     * Get the port number to execute Appium Windows Driver server listener on.
     *
     * @return System port value.
     */
    default Optional<Integer> getSystemPort() {
        return Optional.ofNullable(toInteger(getCapability(SYSTEM_PORT_OPTION)));
    }
}
