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

package io.appium.java_client.android.options.server;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.internal.Either;

import java.util.Optional;

public interface SupportsEspressoBuildConfigOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String ESPRESSO_BUILD_CONFIG_OPTION = "espressoBuildConfig";

    /**
     * This config allows to customize several important properties of
     * Espresso server. Refer to
     * https://github.com/appium/appium-espresso-driver#espresso-build-config
     * for more information on how to properly construct such config.
     *
     * @param configPath The path to the config file on the server file system.
     * @return self instance for chaining.
     */
    default T setEspressoBuildConfig(String configPath) {
        return amend(ESPRESSO_BUILD_CONFIG_OPTION, configPath);
    }

    /**
     * This config allows to customize several important properties of
     * Espresso server. Refer to
     * https://github.com/appium/appium-espresso-driver#espresso-build-config
     * for more information on how to properly construct such config.
     *
     * @param config Config instance.
     * @return self instance for chaining.
     */
    default T setEspressoBuildConfig(EspressoBuildConfig config) {
        return amend(ESPRESSO_BUILD_CONFIG_OPTION, config.toJson().toString());
    }

    /**
     * Get the Espresso build config.
     *
     * @return System port value
     */
    default Optional<Either<EspressoBuildConfig, String>> getEspressoBuildConfig() {
        return Optional.ofNullable(getCapability(ESPRESSO_BUILD_CONFIG_OPTION))
                .map(String::valueOf)
                .map((v) -> v.trim().startsWith("{")
                        ? Either.left(new EspressoBuildConfig(v))
                        : Either.right(v)
                );
    }
}
