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

public interface SupportsNativeWebTapStrictOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String NATIVE_WEB_TAP_STRICT_OPTION = "nativeWebTapStrict";

    /**
     * Enforce native taps to be done by XCUITest driver rather than WebDriverAgent.
     *
     * @return self instance for chaining.
     */
    default T nativeWebTapStrict() {
        return amend(NATIVE_WEB_TAP_STRICT_OPTION, true);
    }

    /**
     * Configure native taps to be done by XCUITest driver rather than WebDriverAgent.
     * Only applicable if nativeWebTap is enabled. false by default.
     *
     * @param value Whether native taps are done by XCUITest driver rather than WebDriverAgent.
     * @return self instance for chaining.
     */
    default T setNativeWebTapStrict(boolean value) {
        return amend(NATIVE_WEB_TAP_STRICT_OPTION, value);
    }

    /**
     * Get whether native taps are done by XCUITest driver rather than WebDriverAgent.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesNativeWebTapStrict() {
        return Optional.ofNullable(toSafeBoolean(getCapability(NATIVE_WEB_TAP_STRICT_OPTION)));
    }
}
