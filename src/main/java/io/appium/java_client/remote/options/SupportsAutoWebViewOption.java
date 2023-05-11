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

package io.appium.java_client.remote.options;

import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsAutoWebViewOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String AUTO_WEB_VIEW_OPTION = "autoWebview";

    /**
     * Set the app to move directly into Webview context.
     *
     * @return self instance for chaining.
     */
    default T autoWebview() {
        return setAutoWebview(true);
    }

    /**
     * Set whether the app moves directly into Webview context.
     *
     * @param bool is whether the app moves directly into Webview context.
     * @return self instance for chaining.
     */
    default T setAutoWebview(boolean bool) {
        return amend(AUTO_WEB_VIEW_OPTION, bool);
    }

    /**
     * Get whether the app moves directly into Webview context.
     *
     * @return true if app moves directly into Webview context.
     */
    default Optional<Boolean> doesAutoWebview() {
        return Optional.ofNullable(toSafeBoolean(getCapability(AUTO_WEB_VIEW_OPTION)));
    }
}
