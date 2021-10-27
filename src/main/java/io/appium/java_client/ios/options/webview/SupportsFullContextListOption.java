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

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsFullContextListOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String FULL_CONTEXT_LIST_OPTION = "fullContextList";

    /**
     * Enforces to return the detailed information on contexts for the get available
     * context command.
     *
     * @return self instance for chaining.
     */
    default T fullContextList() {
        return amend(FULL_CONTEXT_LIST_OPTION, true);
    }

    /**
     * Sets to return the detailed information on contexts for the get available
     * context command. If this capability is enabled, then each item in the returned
     * contexts list would additionally include WebView title, full URL and the bundle
     * identifier. Defaults to false.
     *
     * @param value Whether to return the detailed info on available context command.
     * @return self instance for chaining.
     */
    default T setFullContextList(boolean value) {
        return amend(FULL_CONTEXT_LIST_OPTION, value);
    }

    /**
     * Get whether to return the detailed information on contexts for the get available
     * context command.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesFullContextList() {
        return Optional.ofNullable(toSafeBoolean(getCapability(FULL_CONTEXT_LIST_OPTION)));
    }
}
