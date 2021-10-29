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

import java.util.Optional;

public interface SupportsPlatformVersionOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String PLATFORM_VERSION_OPTION = "platformVersion";

    /**
     * Set the version of the platform.
     *
     * @param version is the platform version.
     * @return self instance for chaining.
     */
    default T setPlatformVersion(String version) {
        return amend(PLATFORM_VERSION_OPTION, version);
    }

    /**
     * Get the version of the platform.
     *
     * @return String representing the platform version.
     */
    default Optional<String> getPlatformVersion() {
        return Optional.ofNullable((String) getCapability(PLATFORM_VERSION_OPTION));
    }
}
