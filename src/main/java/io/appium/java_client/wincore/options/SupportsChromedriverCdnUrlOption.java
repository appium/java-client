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

package io.appium.java_client.wincore.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

/**
 * Provides getters and setters for the {@code chromedriverCdnUrl} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsChromedriverCdnUrlOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code chromedriverCdnUrl} capability.
     */
    String CHROMEDRIVER_CDN_URL_OPTION = "chromedriverCdnUrl";

    /**
     * The custom CDN URL to download chromedriver binaries from.
     *
     * @param value The capability value.
     * @return self instance for chaining.
     */
    default T setChromedriverCdnUrl(String value) {
        return amend(CHROMEDRIVER_CDN_URL_OPTION, value);
    }

    /**
     * Get the custom CDN URL to download chromedriver binaries from.
     *
     * @return The capability value.
     */
    default Optional<String> getChromedriverCdnUrl() {
        return Optional.ofNullable((String) getCapability(CHROMEDRIVER_CDN_URL_OPTION));
    }
}
