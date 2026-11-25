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

package io.appium.java_client.ios.options.app;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

/**
 * Provides getters and setters for the {@code initialDeeplinkUrl} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsInitialDeeplinkUrlOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code initialDeeplinkUrl} capability.
     */
    String INITIAL_DEEPLINK_URL_OPTION = "initialDeeplinkUrl";

    /**
     * A deeplink URL used to run either the application assigned to {@code bundleId},
     * or the default application assigned to handle that deeplink scheme. When combined
     * with Safari, {@code safariInitialUrl} is ignored. Requires iOS 16.4+.
     *
     * @param url Deeplink URL string.
     * @return self instance for chaining.
     */
    default T setInitialDeeplinkUrl(String url) {
        return amend(INITIAL_DEEPLINK_URL_OPTION, url);
    }

    /**
     * Get the initial deeplink URL.
     *
     * @return Deeplink URL string.
     */
    default Optional<String> getInitialDeeplinkUrl() {
        return Optional.ofNullable((String) getCapability(INITIAL_DEEPLINK_URL_OPTION));
    }
}
