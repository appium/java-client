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

package io.appium.java_client.ios.options.webview;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toInteger;

public interface SupportsWebviewConnectRetriesOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String WEBVIEW_CONNECT_RETRIES_OPTION = "webviewConnectRetries";

    /**
     * Number of times to send connection message to remote debugger,
     * to get a webview. Default: 8.
     *
     * @param retries Max retries count.
     * @return self instance for chaining.
     */
    default T setWebviewConnectRetries(int retries) {
        return amend(WEBVIEW_CONNECT_RETRIES_OPTION, retries);
    }

    /**
     * Get the number of retries to send connection message to remote debugger,
     * to get a webview.
     *
     * @return Max retries count.
     */
    default Optional<Integer> getWebviewConnectRetries() {
        return Optional.ofNullable(toInteger(getCapability(WEBVIEW_CONNECT_RETRIES_OPTION)));
    }
}
