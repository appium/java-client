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
     * @return self instance for chaining
     */
    default HasAndroidSettings ignoreUnimportantViews(Boolean compress) {
        setSetting(Setting.IGNORE_UNIMPORTANT_VIEWS, compress);
        return this;
    }

    /**
     * invoke {@code setWaitForIdleTimeout} in {@code com.android.uiautomator.core.Configurator}.
     *
     * @param timeout A negative value would reset to its default value. Minimum time unit
     *                resolution is one millisecond
     * @return self instance for chaining
     */
    default HasAndroidSettings configuratorSetWaitForIdleTimeout(Duration timeout) {
        setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, timeout.toMillis());
        return this;
    }

    /**
     * invoke {@code setWaitForSelectorTimeout} in {@code com.android.uiautomator.core.Configurator}.
     *
     * @param timeout A negative value would reset to its default value. Minimum time unit
     *                resolution is one millisecond
     * @return self instance for chaining
     */
    default HasAndroidSettings configuratorSetWaitForSelectorTimeout(Duration timeout) {
        setSetting(Setting.WAIT_FOR_SELECTOR_TIMEOUT, timeout.toMillis());
        return this;
    }

    /**
     * invoke {@code setScrollAcknowledgmentTimeout} in {@code com.android.uiautomator.core.Configurator}.
     *
     * @param timeout A negative value would reset to its default value. Minimum time unit
     *                resolution is one millisecond
     * @return self instance for chaining
     */
    default HasAndroidSettings configuratorSetScrollAcknowledgmentTimeout(Duration timeout) {
        setSetting(Setting.WAIT_SCROLL_ACKNOWLEDGMENT_TIMEOUT, timeout.toMillis());
        return this;
    }

    /**
     * invoke {@code configuratorSetKeyInjectionDelay} in {@code com.android.uiautomator.core.Configurator}.
     *
     * @param delay A negative value would reset to its default value. Minimum time unit
     *              resolution is one millisecond
     * @return self instance for chaining
     */
    default HasAndroidSettings configuratorSetKeyInjectionDelay(Duration delay) {
        setSetting(Setting.KEY_INJECTION_DELAY, delay.toMillis());
        return this;
    }

    /**
     * invoke {@code setActionAcknowledgmentTimeout} in {@code com.android.uiautomator.core.Configurator}.
     *
     * @param timeout A negative value would reset to its default value. Minimum time unit
     *                resolution is one millisecond
     * @return self instance for chaining
     */
    default HasAndroidSettings configuratorSetActionAcknowledgmentTimeout(Duration timeout) {
        setSetting(Setting.WAIT_ACTION_ACKNOWLEDGMENT_TIMEOUT, timeout.toMillis());
        return this;
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
        setSetting(Setting.NORMALIZE_TAG_NAMES, enabled);
        return this;
    }

    /**
     * Whether to return compact (standards-compliant) & faster responses in find element/s
     * (the default setting). If set to false then the response will also contains other
     * available element attributes.
     *
     * @param enabled Either true or false. The default value if true.
     * @return self instance for chaining
     */
    default HasAndroidSettings setShouldUseCompactResponses(boolean enabled) {
        setSetting(Setting.SHOULD_USE_COMPACT_RESPONSES, enabled);
        return this;
    }

    /**
     * Which attributes should be returned if compact responses are disabled.
     * It works only if shouldUseCompactResponses is false. Defaults to "type,label" string.
     *
     * @param attrNames The comma-separated list of fields to return with each element.
     * @return self instance for chaining
     */
    default HasAndroidSettings setElementResponseAttributes(String attrNames) {
        setSetting(Setting.ELEMENT_RESPONSE_ATTRIBUTES, attrNames);
        return this;
    }

    /**
     * Set whether the source output/xpath search should consider all elements, visible and invisible.
     * Disabling this setting speeds up source and xml search. Works for UIAutomator2 only.
     *
     * @param enabled Either true or false. The default value if false.
     * @return self instance for chaining
     */
    default HasAndroidSettings allowInvisibleElements(boolean enabled) {
        setSetting(Setting.ALLOW_INVISIBLE_ELEMENTS, enabled);
        return this;
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
        setSetting(Setting.ENABLE_NOTIFICATION_LISTENER, enabled);
        return this;
    }
}
