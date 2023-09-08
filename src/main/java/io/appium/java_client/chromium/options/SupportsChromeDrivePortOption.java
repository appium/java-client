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

package io.appium.java_client.chromium.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toInteger;

public interface SupportsChromeDrivePortOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String CHROME_DRIVER_PORT = "chromedriverPort";

    /**
     * The port to start WebDriver processes on. Unless the chrome drive port preference
     * has been user-set, it will listen on port 9515, which is the default
     * value for this capability.
     *
     * @param port port number in range 0..65535.
     * @return self instance for chaining.
     */
    default T setChromeDriverPort(int port) {
        return amend(CHROME_DRIVER_PORT, port);
    }

    /**
     * Get the number of the port for the chrome driver to listen on.
     *
     * @return Chrome driver port value.
     */
    default Optional<Integer> getChromeDriverPort() {
        return Optional.ofNullable(toInteger(getCapability(CHROME_DRIVER_PORT)));
    }
}
