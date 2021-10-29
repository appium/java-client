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

public interface SupportsSystemHostOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SYSTEM_HOST_OPTION = "systemHost";

    /**
     * The name of the host for the internal server to listen on.
     * If not provided then Mac2Driver will use the default host
     * address 127.0.0.1. You could set it to 0.0.0.0 to make the
     * server listening on all available network interfaces.
     * It is also possible to set the particular interface name, for example en1.
     *
     * @param host Host name.
     * @return self instance for chaining.
     */
    default T setSystemHost(String host) {
        return amend(SYSTEM_HOST_OPTION, host);
    }

    /**
     * Get the name of the host for the internal server to listen on.
     *
     * @return System host value.
     */
    default Optional<String> getSystemHost() {
        return Optional.ofNullable((String) getCapability(SYSTEM_HOST_OPTION));
    }
}
