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

package io.appium.java_client.ios;

import io.appium.java_client.HasSettings;
import io.appium.java_client.Setting;

interface HasIOSSettings extends HasSettings {
    /**
     * Set the `nativeWebTap` setting. *iOS-only method*.
     * Sets whether Safari/webviews should convert element taps into x/y taps.
     *
     * @param enabled turns nativeWebTap on if true, off if false
     * @return self instance for chaining
     */
    default HasIOSSettings nativeWebTap(Boolean enabled) {
        setSetting(Setting.NATIVE_WEB_TAP, enabled);
        return this;
    }

    /**
     * Whether to return compact (standards-compliant) and faster responses from find element/s
     * (the default setting). If set to false then the response may also contain other
     * available element attributes.
     *
     * @param enabled Either true or false. The default value if true.
     * @return self instance for chaining
     */
    default HasIOSSettings setShouldUseCompactResponses(boolean enabled) {
        setSetting(Setting.SHOULD_USE_COMPACT_RESPONSES, enabled);
        return this;
    }

    /**
     * Which attributes should be returned if compact responses are disabled.
     * It works only if shouldUseCompactResponses is set to false. Defaults to an empty string.
     *
     * @param attrNames The comma-separated list of fields to return with each element.
     * @return self instance for chaining
     */
    default HasIOSSettings setElementResponseAttributes(String attrNames) {
        setSetting(Setting.ELEMENT_RESPONSE_ATTRIBUTES, attrNames);
        return this;
    }

    /**
     * The quality of the screenshots generated by the screenshots broadcaster,
     * The value of 0 represents the maximum compression
     * (or lowest quality) while the value of 100 represents the least compression (or best quality).
     *
     * @param quality An integer in range 0..100. The default value is 25.
     * @return self instance for chaining
     */
    default HasIOSSettings setMjpegServerScreenshotQuality(int quality) {
        setSetting(Setting.MJPEG_SERVER_SCREENSHOT_QUALITY, quality);
        return this;
    }

    /**
     * The frame rate at which the background screenshots broadcaster should broadcast screenshots in range 1..60.
     * The default value is 10 (Frames Per Second).
     * Setting zero value will cause the frame rate to be at its maximum possible value.
     *
     * @param framerate An integer in range 1..60. The default value is 10.
     * @return self instance for chaining
     */
    default HasIOSSettings setMjpegServerFramerate(int framerate) {
        setSetting(Setting.MJPEG_SERVER_FRAMERATE, framerate);
        return this;
    }

    /**
     * Changes the quality of phone display screenshots according to XCTest/XCTImageQuality enum.
     * Sometimes setting this value to the maximum possible quality may crash XCTest because of
     * lack of the memory (lossless screenshot require more space).
     *
     * @param quality An integer in range 0..2. The default value is 1.
     * @return self instance for chaining
     */
    default HasIOSSettings setScreenshotQuality(int quality) {
        setSetting(Setting.SCREENSHOT_QUALITY, quality);
        return this;
    }
}
