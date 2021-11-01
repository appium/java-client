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

public interface SupportsSafariPlatformBuildVersionOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SAFARI_PLATFORM_BUILD_VERSION_OPTION = "safari:platformBuildVersion";

    /**
     * safaridriver will only create a session using hosts whose OS build
     * version matches the value of safari:platformBuildVersion. Example
     * of a macOS build version is '18E193'. On macOS, the OS build version
     * can be determined by running the sw_vers(1) utility.
     *
     * @param version is the platform build version.
     * @return self instance for chaining.
     */
    default T setSafariPlatformBuildVersion(String version) {
        return amend(SAFARI_PLATFORM_BUILD_VERSION_OPTION, version);
    }

    /**
     * Get the build version of the platform.
     *
     * @return String representing the platform build version.
     */
    default Optional<String> getSafariPlatformBuildVersion() {
        return Optional.ofNullable((String) getCapability(SAFARI_PLATFORM_BUILD_VERSION_OPTION));
    }
}
