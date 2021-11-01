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

package io.appium.java_client.gecko.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toInteger;

public interface SupportsMarionettePortOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String MARIONETTE_PORT_OPTION = "marionettePort";

    /**
     * Selects the port for Geckodriver’s connection to the Marionette
     * remote protocol. The existing Firefox instance must have Marionette
     * enabled. To enable the remote protocol in Firefox, you can pass the
     * -marionette flag. Unless the marionette.port preference has been
     * user-set, Marionette will listen on port 2828, which is the default
     * value for this capability.
     *
     * @param port port number in range 0..65535
     * @return self instance for chaining.
     */
    default T setMarionettePort(int port) {
        return amend(MARIONETTE_PORT_OPTION, port);
    }

    /**
     * Get the number of the port for the Marionette server to listen on.
     *
     * @return Marionette port value.
     */
    default Optional<Integer> getMarionettePort() {
        return Optional.ofNullable(toInteger(getCapability(MARIONETTE_PORT_OPTION)));
    }
}
