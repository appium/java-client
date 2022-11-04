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

package io.appium.java_client.remote.options;

import org.openqa.selenium.Capabilities;

public interface SupportsBrowserVersionOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String BROWSER_VERSION_OPTION = "browserVersion";

    /**
     * Provide the version number of the browser to automate if there are multiple
     * versions installed on the same machine where the driver is running.
     *
     * @param version Browser version to use.
     * @return self instance for chaining.
     */
    default T setBrowserVersion(String version) {
        return amend(BROWSER_VERSION_OPTION, version);
    }
}
