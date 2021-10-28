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

import static io.appium.java_client.internal.CapabilityHelpers.toInteger;

public interface SupportsSafariWebInspectorMaxFrameLengthOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SAFARI_WEB_INSPECTOR_MAX_FRAME_LENGTH_OPTION = "safariWebInspectorMaxFrameLength";

    /**
     * The maximum size in bytes of a single data frame for the Web Inspector.
     * Too high values could introduce slowness and/or memory leaks.
     * Too low values could introduce possible buffer overflow exceptions.
     * Defaults to 20MB (20*1024*1024).
     *
     * @param length Max size of a single data frame.
     * @return self instance for chaining.
     */
    default T setSafariWebInspectorMaxFrameLength(int length) {
        return amend(SAFARI_WEB_INSPECTOR_MAX_FRAME_LENGTH_OPTION, length);
    }

    /**
     * Get the maximum size in bytes of a single data frame for the Web Inspector.
     *
     * @return Size in bytes.
     */
    default Optional<Integer> getSafariWebInspectorMaxFrameLength() {
        return Optional.ofNullable(toInteger(getCapability(SAFARI_WEB_INSPECTOR_MAX_FRAME_LENGTH_OPTION)));
    }
}
