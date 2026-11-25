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

/**
 * Provides getters and setters for the {@code wdaBindingIP} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsWdaBindingIpOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code wdaBindingIP} capability.
     */
    String WDA_BINDING_IP_OPTION = "wdaBindingIP";

    /**
     * IP address for the WebDriverAgent HTTP server to bind on, such as {@code 127.0.0.1}.
     * Only relevant for simulators on macOS.
     *
     * @param ip IP address string.
     * @return self instance for chaining.
     */
    default T setWdaBindingIp(String ip) {
        return amend(WDA_BINDING_IP_OPTION, ip);
    }

    /**
     * Get the IP address for the WebDriverAgent HTTP server to bind on.
     *
     * @return IP address string.
     */
    default Optional<String> getWdaBindingIp() {
        return Optional.ofNullable((String) getCapability(WDA_BINDING_IP_OPTION));
    }
}
