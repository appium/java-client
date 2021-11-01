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

package io.appium.java_client.safari.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Map;
import java.util.Optional;

public interface SupportsWebkitWebrtcOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String WEBKIT_WEB_RTC_OPTION = "webkit:WebRTC";

    /**
     * This capability allows a test to temporarily change Safari's policies
     * for WebRTC and Media Capture.
     *
     * @param webrtcData WebRTC policies.
     * @return self instance for chaining.
     */
    default T setWebkitWebrtc(WebrtcData webrtcData) {
        return amend(WEBKIT_WEB_RTC_OPTION, webrtcData.toMap());
    }

    /**
     * Get WebRTC policies.
     *
     * @return WebRTC policies.
     */
    default Optional<WebrtcData> getWebkitWebrtc() {
        //noinspection unchecked
        return Optional.ofNullable((Map<String, Object>) getCapability(WEBKIT_WEB_RTC_OPTION))
                .map(WebrtcData::new);
    }
}
