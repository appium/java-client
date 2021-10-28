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

public interface SupportsIncludeSafariInWebviewsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String INCLUDE_SAFARI_IN_WEBVIEWS_OPTION = "includeSafariInWebviews";

    /**
     * Enforces Safari web views to be added to the list of contexts available
     * during a native/webview app test.
     *
     * @return self instance for chaining.
     */
    default T includeSafariInWebviews() {
        return amend(INCLUDE_SAFARI_IN_WEBVIEWS_OPTION, true);
    }

    /**
     * Add Safari web contexts to the list of contexts available during a
     * native/webview app test. This is useful if the test opens Safari and
     * needs to be able to interact with it. Defaults to false.
     *
     * @param value Whether to add Safari to the list of contexts available during a native/webview app test.
     * @return self instance for chaining.
     */
    default T setIncludeSafariInWebviews(boolean value) {
        return amend(INCLUDE_SAFARI_IN_WEBVIEWS_OPTION, value);
    }

    /**
     * Get whether to add Safari web views to the list of contexts available
     * during a native/webview app test.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesIncludeSafariInWebviews() {
        return Optional.ofNullable(toSafeBoolean(getCapability(INCLUDE_SAFARI_IN_WEBVIEWS_OPTION)));
    }
}
