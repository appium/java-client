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

public interface SupportsSafariInitialUrlOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SAFARI_INITIAL_URL_OPTION = "safariInitialUrl";

    /**
     * Set initial safari url, default is a local welcome page.
     *
     * @param url Initial safari url.
     * @return self instance for chaining.
     */
    default T setSafariInitialUrl(String url) {
        return amend(SAFARI_INITIAL_URL_OPTION, url);
    }

    /**
     * Get the initial safari url.
     *
     * @return Initial safari url.
     */
    default Optional<String> getSafariInitialUrl() {
        return Optional.ofNullable((String) getCapability(SAFARI_INITIAL_URL_OPTION));
    }
}
