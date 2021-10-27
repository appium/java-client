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

package io.appium.java_client.ios.options.simulator;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsKeychainsExcludePatternsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String KEYCHAINS_EXCLUDE_PATTERNS_OPTION = "keychainsExcludePatterns";

    /**
     * This capability accepts comma-separated path patterns,
     * which are going to be excluded from keychains restore while
     * full reset is being performed on Simulator. It might be
     * useful if you want to exclude only particular keychain types
     * from being restored, like the applications keychain. This
     * feature has no effect on real devices. E.g. "*keychain*.db*"
     * to exclude applications keychain from being restored
     *
     * @param patterns Comma-separated list of file exclude patterns.
     * @return self instance for chaining.
     */
    default T setKeychainsExcludePatterns(String patterns) {
        return amend(KEYCHAINS_EXCLUDE_PATTERNS_OPTION, patterns);
    }

    /**
     * Get keychains exclude patterns.
     *
     * @return Exclude patterns.
     */
    default Optional<String> getKeychainsExcludePatterns() {
        return Optional.ofNullable((String) getCapability(KEYCHAINS_EXCLUDE_PATTERNS_OPTION));
    }
}
