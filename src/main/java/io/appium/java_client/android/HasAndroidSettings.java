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

public interface HasAndroidSettings extends HasSettings {
    /**
     * Set the `ignoreUnimportantViews` setting. *Android-only method*.
     * Sets whether Android devices should use `setCompressedLayoutHeirarchy()`
     * which ignores all views which are marked IMPORTANT_FOR_ACCESSIBILITY_NO
     * or IMPORTANT_FOR_ACCESSIBILITY_AUTO (and have been deemed not important
     * by the system), in an attempt to make things less confusing or faster.
     *
     * @param compress ignores unimportant views if true, doesn't ignore otherwise.
     * @return self instance for chaining
     */
    default HasAndroidSettings ignoreUnimportantViews(Boolean compress) {
        return (HasAndroidSettings) setSetting(Setting.IGNORE_UNIMPORTANT_VIEWS, compress);
    }

    /**
     * invoke {@code setWaitForIdleTimeout} in {@code com.android.uiautomator.core.Configurator}.
     *
     * @param timeout A negative value would reset to its default value. Minimum time unit
     *                resolution is one millisecond
     * @return self instance for chaining
     */
    default HasAndroidSettings configuratorSetWaitForIdleTimeout(Duration timeout) {
        return (HasAndroidSettings) setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, timeout.toMillis());
    }

    /**
     * invoke {@code setWaitForSelectorTimeout} in {@code com.android.uiautomator.core.Configurator}.
     *
     * @param timeout A negative value would reset to its default value. Minimum time unit
     *                resolution is one millisecond
     * @return self instance for chaining
     */
    default HasAndroidSettings configuratorSetWaitForSelectorTimeout(Duration timeout) {
        return (HasAndroidSettings) setSetting(Setting.WAIT_FOR_SELECTOR_TIMEOUT, timeout.toMillis());
    }

    /**
     * invoke {@code setScrollAcknowledgmentTimeout} in {@code com.android.uiautomator.core.Configurator}.
     *
     * @param timeout A negative value would reset to its default value. Minimum time unit
     *                resolution is one millisecond
     * @return self instance for chaining
     */
    default HasAndroidSettings configuratorSetScrollAcknowledgmentTimeout(Duration timeout) {
        return (HasAndroidSettings) setSetting(Setting.WAIT_SCROLL_ACKNOWLEDGMENT_TIMEOUT, timeout.toMillis());
    }

    /**
     * invoke {@code configuratorSetKeyInjectionDelay} in {@code com.android.uiautomator.core.Configurator}.
     *
     * @param delay A negative value would reset to its default value. Minimum time unit
     *              resolution is one millisecond
     * @return self instance for chaining
     */
    default HasAndroidSettings configuratorSetKeyInjectionDelay(Duration delay) {
        return (HasAndroidSettings) setSetting(Setting.KEY_INJECTION_DELAY, delay.toMillis());
    }

    /**
     * invoke {@code setActionAcknowledgmentTimeout} in {@code com.android.uiautomator.core.Configurator}.
     *
     * @param timeout A negative value would reset to its default value. Minimum time unit
     *                resolution is one millisecond
     * @return self instance for chaining
     */
    default HasAndroidSettings configuratorSetActionAcknowledgmentTimeout(Duration timeout) {
        return (HasAndroidSettings) setSetting(Setting.WAIT_ACTION_ACKNOWLEDGMENT_TIMEOUT, timeout.toMillis());
    }

    /**
     * Setting this value to true will enforce source tree dumper
     * to transliterate all class names used as XML tags to the limited
     * set of ASCII characters supported by Apache Harmony
     * lib and used by default in Android to avoid possible
     * XML parsing exceptions caused by XPath lookup.
     * The Unicode to ASCII transliteration is based on
     * JUnidecode library (https://github.com/gcardone/junidecode).
     * Works for UIAutomator2 only.
     *
     * @param enabled Either true or false. The default value if false.
     * @return self instance for chaining
     */
    default HasAndroidSettings normalizeTagNames(boolean enabled) {
        return (HasAndroidSettings) setSetting(Setting.NORMALIZE_TAG_NAMES, enabled);
    }

    /**
     * Whether to return compact (standards-compliant) and faster responses in find element/s
     * (the default setting). If set to false then the response may also contain other
     * available element attributes.
     *
     * @param enabled Either true or false. The default value if true.
     * @return self instance for chaining
     */
    default HasAndroidSettings setShouldUseCompactResponses(boolean enabled) {
        return (HasAndroidSettings) setSetting(Setting.SHOULD_USE_COMPACT_RESPONSES, enabled);
    }

    /**
     * Which attributes should be returned if compact responses are disabled.
     * It works only if shouldUseCompactResponses is false. Defaults to "" (empty string).
     *
     * @param attrNames The comma-separated list of fields to return with each element.
     * @return self instance for chaining
     */
    default HasAndroidSettings setElementResponseAttributes(String attrNames) {
        return (HasAndroidSettings) setSetting(Setting.ELEMENT_RESPONSE_ATTRIBUTES, attrNames);
    }

    /**
     * Set whether the source output/xpath search should consider all elements, visible and invisible.
     * Disabling this setting speeds up source and xml search. Works for UIAutomator2 only.
     *
     * @param enabled Either true or false. The default value if false.
     * @return self instance for chaining
     */
    default HasAndroidSettings allowInvisibleElements(boolean enabled) {
        return (HasAndroidSettings) setSetting(Setting.ALLOW_INVISIBLE_ELEMENTS, enabled);
    }

    /**
     * Whether to enable or disable the notification listener.
     * No toast notifications are going to be added into page source output if
     * this setting is disabled.
     * Works for UIAutomator2 only.
     *
     * @param enabled Either true or false. The default value if true.
     * @return self instance for chaining
     */
    default HasAndroidSettings enableNotificationListener(boolean enabled) {
        return (HasAndroidSettings) setSetting(Setting.ENABLE_NOTIFICATION_LISTENER, enabled);
    }

    /**
     * Whether to enable or disable shutdown the server through
     * the broadcast receiver on ACTION_POWER_DISCONNECTED.
     *
     * @param enabled Either true or false. The default value if true.
     * @return self instance for chaining
     */
    default HasAndroidSettings shutdownOnPowerDisconnect(boolean enabled) {
        return (HasAndroidSettings) setSetting(Setting.SHUTDOWN_ON_POWER_DISCONNECT, enabled);
    }

    /**
     * Turn on or off the tracking of scroll events as they happen.
     * If {@code true}, a field {@code lastScrollData} is added to the results of
     * {@code getSession}, which can then be used to check on scroll progress.
     * Turning this feature off significantly increases touch action performance.
     *
     * @param enabled Either true or false. The default value if true.
     * @return self instance for chaining
     */
    default HasAndroidSettings setTrackScrollEvents(boolean enabled) {
        return (HasAndroidSettings) setSetting(Setting.TRACK_SCROLL_EVENTS, enabled);
    }
}
