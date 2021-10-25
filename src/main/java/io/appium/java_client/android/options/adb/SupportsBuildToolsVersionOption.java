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

package io.appium.java_client.android.options.adb;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsBuildToolsVersionOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String BUILD_TOOLS_VERSION_OPTION = "buildToolsVersion";

    /**
     * The version of Android build tools to use. By default, UiAutomator2
     * driver uses the most recent version of build tools installed on
     * the machine, but sometimes it might be necessary to give it a hint
     * (let say if there is a known bug in the most recent tools version).
     * Example: 28.0.3
     *
     * @param version The build tools version to use.
     * @return self instance for chaining.
     */
    default T setBuildToolsVersion(String version) {
        return amend(BUILD_TOOLS_VERSION_OPTION, version);
    }

    /**
     * Get the version of Android build tools to use.
     *
     * @return Build tools version.
     */
    default Optional<String> getBuildToolsVersion() {
        return Optional.ofNullable((String) getCapability(BUILD_TOOLS_VERSION_OPTION));
    }
}
