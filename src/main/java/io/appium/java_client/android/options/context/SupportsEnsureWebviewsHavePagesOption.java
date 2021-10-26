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

package io.appium.java_client.android.options.context;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsEnsureWebviewsHavePagesOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String ENSURE_WEBVIEWS_HAVE_PAGES_OPTION = "ensureWebviewsHavePages";

    /**
     * Set to skip web views that have no pages from being shown in getContexts output.
     *
     * @return self instance for chaining.
     */
    default T ensureWebviewsHavePages() {
        return amend(ENSURE_WEBVIEWS_HAVE_PAGES_OPTION, true);
    }

    /**
     * Whether to skip web views that have no pages from being shown in getContexts
     * output. The driver uses devtools connection to retrieve the information about
     * existing pages. true by default since Appium 1.19.0, false if lower than 1.19.0.
     *
     * @param value Whether to ensure if web views have pages.
     * @return self instance for chaining.
     */
    default T setEnsureWebviewsHavePages(boolean value) {
        return amend(ENSURE_WEBVIEWS_HAVE_PAGES_OPTION, value);
    }

    /**
     * Get whether to ensure if web views have pages.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesEnsureWebviewsHavePages() {
        return Optional.ofNullable(toSafeBoolean(getCapability(ENSURE_WEBVIEWS_HAVE_PAGES_OPTION)));
    }
}
