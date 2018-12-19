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

package io.appium.java_client.android;

import io.appium.java_client.HasSettings;
import io.appium.java_client.Setting;

import java.time.Duration;

interface HasAndroidSettings extends HasSettings {
    /**
     * Set the `ignoreUnimportantViews` setting. *Android-only method*.
     * Sets whether Android devices should use `setCompressedLayoutHeirarchy()`
     * which ignores all views which are marked IMPORTANT_FOR_ACCESSIBILITY_NO
     * or IMPORTANT_FOR_ACCESSIBILITY_AUTO (and have been deemed not important
     * by the system), in an attempt to make things less confusing or faster.
     *
     * @param compress ignores unimportant views if true, doesn't ignore otherwise.
     */
    default void ignoreUnimportantViews(Boolean compress) {
        setSetting(Setting.IGNORE_UNIMPORTANT_VIEWS, compress);
    }

    /**
     * invoke {@code setWaitForIdleTimeout} in {@code com.android.uiautomator.core.Configurator}.
     *
     * @param timeout A negative value would reset to its default value. Minimum time unit
     *                resolution is one millisecond
     */
    default void configuratorSetWaitForIdleTimeout(Duration timeout) {
        setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, timeout.toMillis());
    }

    /**
     * invoke {@code setWaitForSelectorTimeout} in {@code com.android.uiautomator.core.Configurator}.
     *
     * @param timeout A negative value would reset to its default value. Minimum time unit
     *                resolution is one millisecond
     */
    default void configuratorSetWaitForSelectorTimeout(Duration timeout) {
        setSetting(Setting.WAIT_FOR_SELECTOR_TIMEOUT, timeout.toMillis());
    }

    /**
     * invoke {@code setScrollAcknowledgmentTimeout} in {@code com.android.uiautomator.core.Configurator}.
     *
     * @param timeout A negative value would reset to its default value. Minimum time unit
     *                resolution is one millisecond
     */
    default void configuratorSetScrollAcknowledgmentTimeout(Duration timeout) {
        setSetting(Setting.WAIT_SCROLL_ACKNOWLEDGMENT_TIMEOUT, timeout.toMillis());
    }

    /**
     * invoke {@code configuratorSetKeyInjectionDelay} in {@code com.android.uiautomator.core.Configurator}.
     *
     * @param delay A negative value would reset to its default value. Minimum time unit
     *              resolution is one millisecond
     */
    default void configuratorSetKeyInjectionDelay(Duration delay) {
        setSetting(Setting.KEY_INJECTION_DELAY, delay.toMillis());
    }

    /**
     * invoke {@code setActionAcknowledgmentTimeout} in {@code com.android.uiautomator.core.Configurator}.
     *
     * @param timeout A negative value would reset to its default value. Minimum time unit
     *                resolution is one millisecond
     */
    default void configuratorSetActionAcknowledgmentTimeout(Duration timeout) {
        setSetting(Setting.WAIT_ACTION_ACKNOWLEDGMENT_TIMEOUT, timeout.toMillis());
    }

    /**
     * Setting this value to true will enforce source tree dumper
     * to transliterate all class names used as XML tags to the limited
     * set of ASCII characters supported by Apache Harmony
     * lib and used by default in Android to avoid possible
     * XML parsing exceptions caused by XPath lookup.
     * The Unicode to ASCII transliteration is based on
     * JUnidecode library (https://github.com/gcardone/junidecode).
     *
     * @param enabled Either true or false. The default value if false.
     */
    default void normalizeTagNames(boolean enabled) {
        setSetting(Setting.NORMALIZE_TAG_NAMES, enabled);
    }
}
