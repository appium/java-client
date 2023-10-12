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

public interface SupportsShowChromedriverLogOption<T extends BaseOptions<T>>
        extends Capabilities, CanSetCapability<T> {
    String SHOW_CHROMEDRIVER_LOG_OPTION = "showChromedriverLog";

    /**
     * Enforces all the output from chromedriver binary to be
     * forwarded to the Appium server log.
     *
     * @return self instance for chaining.
     */
    default T showChromedriverLog() {
        return amend(SHOW_CHROMEDRIVER_LOG_OPTION, true);
    }

    /**
     * If set to true then all the output from chromedriver binary will be
     * forwarded to the Appium server log. false by default.
     *
     * @param value Whether to forward chromedriver output to the Appium server log.
     * @return self instance for chaining.
     */
    default T setShowChromedriverLog(boolean value) {
        return amend(SHOW_CHROMEDRIVER_LOG_OPTION, value);
    }

    /**
     * Get whether to forward chromedriver output to the Appium server log.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesShowChromedriverLog() {
        return Optional.ofNullable(
                toSafeBoolean(getCapability(SHOW_CHROMEDRIVER_LOG_OPTION))
        );
    }
}
