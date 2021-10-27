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

public interface SupportsNativeWebTapOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String NATIVE_WEB_TAP_OPTION = "nativeWebTap";

    /**
     * Enforces native non-javascript-based taps in web context mode.
     *
     * @return self instance for chaining.
     */
    default T nativeWebTap() {
        return amend(NATIVE_WEB_TAP_OPTION, true);
    }

    /**
     * Enable native, non-javascript-based taps being in web context mode. Defaults
     * to false. Warning: sometimes the preciseness of native taps could be broken,
     * because there is no reliable way to map web element coordinates to native ones.
     *
     * @param value Whether to enable native taps in web view mode.
     * @return self instance for chaining.
     */
    default T setNativeWebTap(boolean value) {
        return amend(NATIVE_WEB_TAP_OPTION, value);
    }

    /**
     * Get whether to enable native taps in web view mode.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesNativeWebTap() {
        return Optional.ofNullable(toSafeBoolean(getCapability(NATIVE_WEB_TAP_OPTION)));
    }
}
