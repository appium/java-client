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

public interface SupportsSafariIgnoreWebHostnamesOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SAFARI_IGNORE_WEB_HOSTNAMES_OPTION = "safariIgnoreWebHostnames";

    /**
     * Provide a list of hostnames (comma-separated) that the Safari automation
     * tools should ignore. This is to provide a workaround to prevent a webkit
     * bug where the web context is unintentionally changed to a 3rd party website
     * and the test gets stuck. The common culprits are search engines (yahoo, bing,
     * google) and about:blank.
     *
     * @param hostnames E.g. 'www.yahoo.com, www.bing.com, www.google.com, about:blank'.
     * @return self instance for chaining.
     */
    default T setSafariIgnoreWebHostnames(String hostnames) {
        return amend(SAFARI_IGNORE_WEB_HOSTNAMES_OPTION, hostnames);
    }

    /**
     * Get the comma-separated list of host names to be ignored.
     *
     * @return XCTest result bundle path.
     */
    default Optional<String> getSafariIgnoreWebHostnames() {
        return Optional.ofNullable((String) getCapability(SAFARI_IGNORE_WEB_HOSTNAMES_OPTION));
    }
}
