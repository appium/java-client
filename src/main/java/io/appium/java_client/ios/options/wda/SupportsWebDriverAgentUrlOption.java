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

import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.net.URL;
import java.util.Optional;

public interface SupportsWebDriverAgentUrlOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String WEB_DRIVER_AGENT_URL_OPTION = "webDriverAgentUrl";

    /**
     * If provided, Appium will connect to an existing WebDriverAgent
     * instance at this URL instead of starting a new one.
     *
     * @param url The URL where WDA is listening.
     * @return self instance for chaining.
     */
    default T setWebDriverAgentUrl(URL url) {
        return amend(WEB_DRIVER_AGENT_URL_OPTION, url.toString());
    }

    /**
     * If provided, Appium will connect to an existing WebDriverAgent
     * instance at this URL instead of starting a new one.
     *
     * @param url The URL where WDA is listening.
     * @return self instance for chaining.
     */
    default T setWebDriverAgentUrl(String url) {
        return amend(WEB_DRIVER_AGENT_URL_OPTION, url);
    }

    /**
     * Get the WDA URL.
     *
     * @return The URL where WDA is listening.
     */
    default Optional<URL> getWebDriverAgentUrl() {
        return Optional.ofNullable(getCapability(WEB_DRIVER_AGENT_URL_OPTION))
                .map(CapabilityHelpers::toUrl);
    }
}
