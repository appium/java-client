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

import static io.appium.java_client.android.AndroidMobileCommandHelper.getSettingsCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.setSettingsCommand;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;

import org.openqa.selenium.remote.Response;

import java.time.Duration;
import java.util.Map;

interface HasSettings extends ExecutesMethod {
    /**
     * Set a setting for this test session It's probably better to use a
     * convenience function, rather than use this function directly. Try finding
     * the method for the specific setting you want to change.
     *
     * @param setting Setting you wish to set.
     * @param value   value of the setting.
     */
    default void setSetting(Setting setting, Object value) {
        CommandExecutionHelper.execute(this, setSettingsCommand(setting, value));
    }

    /**
     * Get settings stored for this test session It's probably better to use a
     * convenience function, rather than use this function directly. Try finding
     * the method for the specific setting you want to read.
     *
     * @return JsonObject, a straight-up hash of settings.
     */
    @SuppressWarnings("unchecked")
    default Map<String, Object> getSettings() {
        Map.Entry<String, Map<String, ?>> keyValuePair = getSettingsCommand();
        Response response = execute(keyValuePair.getKey(), keyValuePair.getValue());

        return ImmutableMap.<String, Object>builder()
                .putAll(Map.class.cast(response.getValue())).build();
    }

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
     * invoke {@code setWaitForIdleTimeout} in {@code com.android.uiautomator.core.Configurator}
     * This method is deprecated. Please use {@link #configuratorSetWaitForIdleTimeout(Duration)} instead.
     *
     * @param timeout in milliseconds. A negative value would reset to its default value
     */
    @Deprecated
    default void configuratorSetWaitForIdleTimeout(int timeout) {
        configuratorSetWaitForIdleTimeout(Duration.ofMillis(timeout));
    }

    /**
     * invoke {@code setWaitForIdleTimeout} in {@code com.android.uiautomator.core.Configurator}
     *
     * @param timeout A negative value would reset to its default value. Minimum time unit
     *                resolution is one millisecond
     */
    default void configuratorSetWaitForIdleTimeout(Duration timeout) {
        setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, timeout.toMillis());
    }

    /**
     * invoke {@code setWaitForSelectorTimeout} in {@code com.android.uiautomator.core.Configurator}
     * This method is deprecated. Please use {@link #configuratorSetWaitForSelectorTimeout(Duration)} instead.
     *
     * @param timeout in milliseconds. A negative value would reset to its default value
     */
    @Deprecated
    default void configuratorSetWaitForSelectorTimeout(int timeout) {
        configuratorSetWaitForSelectorTimeout(Duration.ofMillis(timeout));
    }

    /**
     * invoke {@code setWaitForSelectorTimeout} in {@code com.android.uiautomator.core.Configurator}
     *
     * @param timeout A negative value would reset to its default value. Minimum time unit
     *                resolution is one millisecond
     */
    default void configuratorSetWaitForSelectorTimeout(Duration timeout) {
        setSetting(Setting.WAIT_FOR_SELECTOR_TIMEOUT, timeout.toMillis());
    }

    /**
     * invoke {@code setScrollAcknowledgmentTimeout} in {@code com.android.uiautomator.core.Configurator}
     * This method is deprecated. Please use {@link #configuratorSetScrollAcknowledgmentTimeout(Duration)} instead.
     *
     * @param timeout in milliseconds. A negative value would reset to its default value
     */
    @Deprecated
    default void configuratorSetScrollAcknowledgmentTimeout(int timeout) {
        configuratorSetScrollAcknowledgmentTimeout(Duration.ofMillis(timeout));
    }

    /**
     * invoke {@code setScrollAcknowledgmentTimeout} in {@code com.android.uiautomator.core.Configurator}
     *
     * @param timeout A negative value would reset to its default value. Minimum time unit
     *                resolution is one millisecond
     */
    default void configuratorSetScrollAcknowledgmentTimeout(Duration timeout) {
        setSetting(Setting.WAIT_SCROLL_ACKNOWLEDGMENT_TIMEOUT, timeout.toMillis());
    }

    /**
     * invoke {@code configuratorSetKeyInjectionDelay} in {@code com.android.uiautomator.core.Configurator}
     * This method is deprecated. Please use {@link #configuratorSetKeyInjectionDelay(Duration)} instead.
     *
     * @param delay in milliseconds. A negative value would reset to its default value
     */
    @Deprecated
    default void configuratorSetKeyInjectionDelay(int delay) {
        configuratorSetKeyInjectionDelay(Duration.ofMillis(delay));
    }

    /**
     * invoke {@code configuratorSetKeyInjectionDelay} in {@code com.android.uiautomator.core.Configurator}
     *
     * @param delay A negative value would reset to its default value. Minimum time unit
     *              resolution is one millisecond
     */
    default void configuratorSetKeyInjectionDelay(Duration delay) {
        setSetting(Setting.KEY_INJECTION_DELAY, delay.toMillis());
    }

    /**
     * invoke {@code setActionAcknowledgmentTimeout} in {@code com.android.uiautomator.core.Configurator}
     * This method is deprecated. Please use {@link #configuratorSetActionAcknowledgmentTimeout(Duration)} instead.
     *
     * @param timeout in milliseconds. A negative value would reset to its default value
     */
    @Deprecated
    default void configuratorSetActionAcknowledgmentTimeout(int timeout) {
        configuratorSetActionAcknowledgmentTimeout(Duration.ofMillis(timeout));
    }

    /**
     * invoke {@code setActionAcknowledgmentTimeout} in {@code com.android.uiautomator.core.Configurator}
     *
     * @param timeout A negative value would reset to its default value. Minimum time unit
     *                resolution is one millisecond
     */
    default void configuratorSetActionAcknowledgmentTimeout(Duration timeout) {
        setSetting(Setting.WAIT_ACTION_ACKNOWLEDGMENT_TIMEOUT, timeout.toMillis());
    }
}
