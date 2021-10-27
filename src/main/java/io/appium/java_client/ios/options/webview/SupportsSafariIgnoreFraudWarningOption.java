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

public interface SupportsSafariIgnoreFraudWarningOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SAFARI_IGNORE_FRAUD_WARNING_OPTION = "safariIgnoreFraudWarning";

    /**
     * Enforces to prevent Safari from showing a fraudulent website warning.
     *
     * @return self instance for chaining.
     */
    default T safariIgnoreFraudWarning() {
        return amend(SAFARI_IGNORE_FRAUD_WARNING_OPTION, true);
    }

    /**
     * Prevent Safari from showing a fraudulent website warning.
     * Default keeps current sim setting..
     *
     * @param value Whether to prevent Safari from showing a fraudulent website warning.
     * @return self instance for chaining.
     */
    default T setSafariIgnoreFraudWarning(boolean value) {
        return amend(SAFARI_IGNORE_FRAUD_WARNING_OPTION, value);
    }

    /**
     * Get whether to prevent Safari from showing a fraudulent website warning.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesSafariIgnoreFraudWarning() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SAFARI_IGNORE_FRAUD_WARNING_OPTION)));
    }
}
