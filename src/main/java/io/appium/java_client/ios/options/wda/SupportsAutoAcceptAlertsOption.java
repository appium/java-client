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

package io.appium.java_client.ios.options.wda;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsAutoAcceptAlertsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String AUTO_ACCEPT_ALERTS_OPTION = "autoAcceptAlerts";

    /**
     * Enforce to accept all alerts automatically.
     *
     * @return self instance for chaining.
     */
    default T autoAcceptAlerts() {
        return amend(AUTO_ACCEPT_ALERTS_OPTION, true);
    }

    /**
     * Accept all iOS alerts automatically if they pop up. This includes privacy
     * access permission alerts (e.g., location, contacts, photos). Default is false.
     *
     * @param value Whether to accepts alerts automatically.
     * @return self instance for chaining.
     */
    default T setAutoAcceptAlerts(boolean value) {
        return amend(AUTO_ACCEPT_ALERTS_OPTION, value);
    }

    /**
     * Get whether to accept all alerts automatically.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesAutoAcceptAlerts() {
        return Optional.ofNullable(toSafeBoolean(getCapability(AUTO_ACCEPT_ALERTS_OPTION)));
    }
}
