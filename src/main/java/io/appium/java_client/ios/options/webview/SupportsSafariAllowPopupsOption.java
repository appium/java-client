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

public interface SupportsSafariAllowPopupsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SAFARI_ALLOW_POPUPS_OPTION = "safariAllowPopups";

    /**
     * Enforces to allow javascript to open popups in Safari.
     *
     * @return self instance for chaining.
     */
    default T safariAllowPopups() {
        return amend(SAFARI_ALLOW_POPUPS_OPTION, true);
    }

    /**
     * Allow javascript to open new windows in Safari. Default keeps current sim setting.
     *
     * @param value Whether to allow javascript to open popups in Safari.
     * @return self instance for chaining.
     */
    default T setSafariAllowPopups(boolean value) {
        return amend(SAFARI_ALLOW_POPUPS_OPTION, value);
    }

    /**
     * Get whether to allow javascript to open new windows in Safari.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesSafariAllowPopups() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SAFARI_ALLOW_POPUPS_OPTION)));
    }
}
