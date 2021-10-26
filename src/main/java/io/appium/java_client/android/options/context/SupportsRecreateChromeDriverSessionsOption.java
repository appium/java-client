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

package io.appium.java_client.android.options.context;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsRecreateChromeDriverSessionsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String RECREATE_CHROME_DRIVER_SESSIONS = "recreateChromeDriverSessions";

    /**
     * Enforce chromedriver sessions to be killed and then recreated instead
     * of just suspending it on context switch.
     *
     * @return self instance for chaining.
     */
    default T recreateChromeDriverSessions() {
        return amend(RECREATE_CHROME_DRIVER_SESSIONS, true);
    }

    /**
     * If this capability is set to true then chromedriver session is always going
     * to be killed and then recreated instead of just suspending it on context
     * switching. false by default.
     *
     * @param value Whether to recreate chromedriver sessions.
     * @return self instance for chaining.
     */
    default T setRecreateChromeDriverSessions(boolean value) {
        return amend(RECREATE_CHROME_DRIVER_SESSIONS, value);
    }

    /**
     * Get whether chromedriver sessions should be killed and then recreated instead
     * of just suspending it on context switch.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesRecreateChromeDriverSessions() {
        return Optional.ofNullable(toSafeBoolean(getCapability(RECREATE_CHROME_DRIVER_SESSIONS)));
    }
}
