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

package io.appium.java_client.android.options.avd;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Map;
import java.util.Optional;

public interface SupportsAvdEnvOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String AVD_ENV_OPTION = "avdEnv";

    /**
     * Set the mapping of emulator environment variables.
     *
     * @param env Emulator environment variables mapping.
     * @return self instance for chaining.
     */
    default T setAvdEnv(Map<String, Object> env) {
        return amend(AVD_ENV_OPTION, env);
    }

    /**
     * Get the mapping of emulator environment variables.
     *
     * @return Emulator environment variables mapping.
     */
    default Optional<Map<String, Object>> getAvdEnv() {
        //noinspection unchecked
        return Optional.ofNullable(getCapability(AVD_ENV_OPTION))
                .map(v -> (Map<String, Object>) v);
    }
}
