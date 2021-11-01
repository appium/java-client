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

import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.net.URL;
import java.util.Optional;

public interface SupportsWebDriverAgentMacUrlOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String WEB_DRIVER_AGENT_MAC_URL_OPTION = "webDriverAgentMacUrl";

    /**
     * Set the URL Appium will connect to an existing WebDriverAgentMac
     * instance at this URL instead of starting a new one.
     *
     * @param url E.g. "http://192.168.10.1:10101"
     * @return self instance for chaining.
     */
    default T setWebDriverAgentMacUrl(URL url) {
        return amend(WEB_DRIVER_AGENT_MAC_URL_OPTION, url.toString());
    }

    /**
     * Set the URL Appium will connect to an existing WebDriverAgentMac
     * instance at this URL instead of starting a new one.
     *
     * @param url E.g. "http://192.168.10.1:10101"
     * @return self instance for chaining.
     */
    default T setWebDriverAgentMacUrl(String url) {
        return amend(WEB_DRIVER_AGENT_MAC_URL_OPTION, url);
    }

    /**
     * Get the URL Appium will connect to an existing WebDriverAgentMac
     * instance.
     *
     * @return Server URL.
     */
    default Optional<URL> getWebDriverAgentMacUrl() {
        return Optional.ofNullable(getCapability(WEB_DRIVER_AGENT_MAC_URL_OPTION))
                .map(CapabilityHelpers::toUrl);
    }
}
