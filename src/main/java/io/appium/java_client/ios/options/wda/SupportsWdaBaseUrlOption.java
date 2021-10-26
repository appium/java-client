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

public interface SupportsWdaBaseUrlOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String WDA_BASE_URL_OPTION = "wdaBaseUrl";

    /**
     * This value, if specified, will be used as a prefix to build a custom
     * WebDriverAgent url. It is different from webDriverAgentUrl, because
     * if the latter is set then it expects WebDriverAgent to be already
     * listening and skips the building phase. Defaults to http://localhost.
     *
     * @param url The URL prefix.
     * @return self instance for chaining.
     */
    default T setWdaBaseUrl(URL url) {
        return amend(WDA_BASE_URL_OPTION, url.toString());
    }

    /**
     * This value, if specified, will be used as a prefix to build a custom
     * WebDriverAgent url. It is different from webDriverAgentUrl, because
     * if the latter is set then it expects WebDriverAgent to be already
     * listening and skips the building phase. Defaults to http://localhost.
     *
     * @param url The URL prefix.
     * @return self instance for chaining.
     */
    default T setWdaBaseUrl(String url) {
        return amend(WDA_BASE_URL_OPTION, url);
    }

    /**
     * Get a prefix to build a custom WebDriverAgent URL.
     *
     * @return The URL prefix.
     */
    default Optional<URL> getWdaBaseUrl() {
        return Optional.ofNullable(getCapability(WDA_BASE_URL_OPTION))
                .map(CapabilityHelpers::toUrl);
    }
}
