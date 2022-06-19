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

package io.appium.java_client.mac.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Map;
import java.util.Optional;

public interface SupportsEnvironmentOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String ENVIRONMENT_OPTION = "environment";

    /**
     * A dictionary of environment variables (name-&gt;value) that are going to be passed
     * to the application under test on top of environment variables inherited from
     * the parent process. This capability is only going to be applied if the application
     * is not running on session startup.
     *
     * @param env E.g. ["--help"].
     * @return self instance for chaining.
     */
    default T setEnvironment(Map<String, String> env) {
        return amend(ENVIRONMENT_OPTION, env);
    }

    /**
     * Get the application environment variables mapping.
     *
     * @return Application environment mapping.
     */
    default Optional<Map<String, String>> getEnvironment() {
        //noinspection unchecked
        return Optional.ofNullable((Map<String, String>) getCapability(ENVIRONMENT_OPTION));
    }
}
