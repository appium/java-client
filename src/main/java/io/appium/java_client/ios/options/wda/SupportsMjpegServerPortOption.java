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

public interface SupportsMjpegServerPortOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String MJPEG_SERVER_PORT_OPTION = "mjpegServerPort";

    /**
     * The port number on which WDA broadcasts screenshots stream encoded into MJPEG
     * format from the device under test. It might be necessary to change this value
     * if the default port is busy because of other tests running in parallel.
     * Default value: 9100.
     *
     * @param port port number in range 0..65535
     * @return self instance for chaining.
     */
    default T setMjpegServerPort(int port) {
        return amend(MJPEG_SERVER_PORT_OPTION, port);
    }

    /**
     * Get the port number on which WDA broadcasts screenshots stream encoded into MJPEG
     * format from the device under test.
     *
     * @return The port number.
     */
    default Optional<Integer> getMjpegServerPort() {
        return Optional.ofNullable(toInteger(getCapability(MJPEG_SERVER_PORT_OPTION)));
    }
}
