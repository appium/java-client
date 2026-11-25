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

/**
 * Provides getters and setters for the {@code wdaRemotePort} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsWdaRemotePortOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code wdaRemotePort} capability.
     */
    String WDA_REMOTE_PORT_OPTION = "wdaRemotePort";

    /**
     * Port number to start the WebDriverAgent HTTP server on a real device.
     * Only relevant for real devices, and can hint the remote port when
     * {@code webDriverAgentUrl} is provided. Defaults to 8100.
     *
     * @param port Port number in range 0..65535.
     * @return self instance for chaining.
     */
    default T setWdaRemotePort(int port) {
        return amend(WDA_REMOTE_PORT_OPTION, port);
    }

    /**
     * Get the remote port number where the WebDriverAgent HTTP server listens.
     *
     * @return The port number.
     */
    default Optional<Integer> getWdaRemotePort() {
        return Optional.ofNullable(toInteger(getCapability(WDA_REMOTE_PORT_OPTION)));
    }
}
