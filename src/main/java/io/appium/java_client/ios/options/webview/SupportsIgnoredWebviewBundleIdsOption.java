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

package io.appium.java_client.ios.options.webview;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.List;
import java.util.Optional;

/**
 * Provides getters and setters for the {@code ignoredWebviewBundleIds} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsIgnoredWebviewBundleIdsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code ignoredWebviewBundleIds} capability.
     */
    String IGNORED_WEBVIEW_BUNDLE_IDS_OPTION = "ignoredWebviewBundleIds";

    /**
     * Array of bundle identifiers to exclude from webview context detection, such as
     * {@code com.apple.amsengagementd}. Defaults to an empty list.
     *
     * @param identifiers Bundle identifiers list to ignore.
     * @return self instance for chaining.
     */
    default T setIgnoredWebviewBundleIds(List<String> identifiers) {
        return amend(IGNORED_WEBVIEW_BUNDLE_IDS_OPTION, identifiers);
    }

    /**
     * Get the list of bundle identifiers excluded from webview context detection.
     *
     * @return Bundle identifiers list.
     */
    @SuppressWarnings("unchecked")
    default Optional<List<String>> getIgnoredWebviewBundleIds() {
        return Optional.ofNullable(
                (List<String>) getCapability(IGNORED_WEBVIEW_BUNDLE_IDS_OPTION)
        );
    }
}
