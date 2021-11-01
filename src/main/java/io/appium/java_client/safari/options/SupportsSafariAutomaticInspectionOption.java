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

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsSafariAutomaticInspectionOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SAFARI_AUTOMATIC_INSPECTION_OPTION = "safari:automaticInspection";

    /**
     * Enforces safaridriver to use Automatic Inspection.
     *
     * @return self instance for chaining.
     */
    default T safariAutomaticInspection() {
        return amend(SAFARI_AUTOMATIC_INSPECTION_OPTION, true);
    }

    /**
     * This capability instructs Safari to preload the Web Inspector and JavaScript
     * debugger in the background prior to returning a newly-created window.
     * To pause the test's execution in JavaScript and bring up Web Inspector's
     * Debugger tab, you can simply evaluate a debugger; statement in the test page.
     *
     * @param bool Whether to use automatic inspection.
     * @return self instance for chaining.
     */
    default T setSafariAutomaticInspection(boolean bool) {
        return amend(SAFARI_AUTOMATIC_INSPECTION_OPTION, bool);
    }

    /**
     * Get whether to use automatic inspection.
     *
     * @return true or false.
     */
    default Optional<Boolean> doesAafariAutomaticInspection() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SAFARI_AUTOMATIC_INSPECTION_OPTION)));
    }
}
