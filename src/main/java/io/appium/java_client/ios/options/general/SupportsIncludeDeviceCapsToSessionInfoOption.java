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

package io.appium.java_client.ios.options.general;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsIncludeDeviceCapsToSessionInfoOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String INCLUDE_DEVICE_CAPS_TO_SESSION_INFO_OPTION = "includeDeviceCapsToSessionInfo";

    /**
     * Whether to include screen information as the result of Get Session Capabilities.
     * It includes pixelRatio, statBarHeight and viewportRect, but
     * it causes an extra API call to WDA which may increase the response time.
     * Defaults to true.
     *
     * @param value Whether to include screen information as the result of Get Session Capabilities.
     * @return self instance for chaining.
     */
    default T setIncludeDeviceCapsToSessionInfo(boolean value) {
        return amend(INCLUDE_DEVICE_CAPS_TO_SESSION_INFO_OPTION, value);
    }

    /**
     * Get whether to include screen information as the result of Get Session Capabilities.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesIncludeDeviceCapsToSessionInfo() {
        return Optional.ofNullable(toSafeBoolean(getCapability(INCLUDE_DEVICE_CAPS_TO_SESSION_INFO_OPTION)));
    }
}
