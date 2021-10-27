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

public interface SupportsAdditionalWebviewBundleIdsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String ADDITIONAL_WEBVIEW_BUNDLE_IDS_OPTION = "additionalWebviewBundleIds";

    /**
     * Array of possible bundle identifiers for webviews. This is sometimes
     * necessary if the Web Inspector is found to be returning a modified
     * bundle identifier for the app. Defaults to [].
     *
     * @param identifiers Identifiers list.
     * @return self instance for chaining.
     */
    default T setAdditionalWebviewBundleIds(List<String> identifiers) {
        return amend(ADDITIONAL_WEBVIEW_BUNDLE_IDS_OPTION, identifiers);
    }

    /**
     * Get the array of possible bundle identifiers for webviews.
     *
     * @return Identifier list.
     */
    default Optional<List<String>> getAdditionalWebviewBundleIds() {
        //noinspection unchecked
        return Optional.ofNullable(
                (List<String>) getCapability(ADDITIONAL_WEBVIEW_BUNDLE_IDS_OPTION)
        );
    }
}
