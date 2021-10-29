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

package io.appium.java_client.android.options.context;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toInteger;

public interface SupportsWebviewDevtoolsPortOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String WEBVIEW_DEVTOOLS_PORT_OPTION = "webviewDevtoolsPort";

    /**
     * The local port number to use for devtools communication. By default, the first
     * free port from 10900..11000 range is selected. Consider setting the custom
     * value if you are running parallel tests.
     *
     * @param port port number in range 0..65535
     * @return self instance for chaining.
     */
    default T setWebviewDevtoolsPort(int port) {
        return amend(WEBVIEW_DEVTOOLS_PORT_OPTION, port);
    }

    /**
     * Get the local port number to use for devtools communication.
     *
     * @return Port number.
     */
    default Optional<Integer> getWebviewDevtoolsPort() {
        return Optional.ofNullable(toInteger(getCapability(WEBVIEW_DEVTOOLS_PORT_OPTION)));
    }
}
