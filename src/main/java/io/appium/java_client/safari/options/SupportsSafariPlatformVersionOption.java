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

package io.appium.java_client.safari.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsSafariPlatformVersionOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SAFARI_PLATFORM_VERSION_OPTION = "safari:platformVersion";

    /**
     * safaridriver will only create a session using hosts whose OS
     * version matches the value of safari:platformVersion. OS version
     * numbers are prefix-matched. For example, if the value of safari:platformVersion
     * is '12', this will allow hosts with an OS version of '12.0' or '12.1' but not '10.12'.
     *
     * @param version is the platform version.
     * @return self instance for chaining.
     */
    default T setSafariPlatformVersion(String version) {
        return amend(SAFARI_PLATFORM_VERSION_OPTION, version);
    }

    /**
     * Get the version of the platform.
     *
     * @return String representing the platform version.
     */
    default Optional<String> getSafariPlatformVersion() {
        return Optional.ofNullable((String) getCapability(SAFARI_PLATFORM_VERSION_OPTION));
    }
}
