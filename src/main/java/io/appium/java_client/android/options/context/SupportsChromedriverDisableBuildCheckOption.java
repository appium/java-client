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

public interface SupportsChromedriverDisableBuildCheckOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String CHROMEDRIVER_DISABLE_BUILD_CHECK_OPTION = "chromedriverDisableBuildCheck";

    /**
     * Disables the compatibility validation between the current chromedriver
     * and the destination browser/web view.
     *
     * @return self instance for chaining.
     */
    default T chromedriverDisableBuildCheck() {
        return amend(CHROMEDRIVER_DISABLE_BUILD_CHECK_OPTION, true);
    }

    /**
     * Being set to true disables the compatibility validation between the current
     * chromedriver and the destination browser/web view. Use it with care.
     * false by default.
     *
     * @param value Whether to enable the validation.
     * @return self instance for chaining.
     */
    default T setChromedriverDisableBuildCheck(boolean value) {
        return amend(CHROMEDRIVER_DISABLE_BUILD_CHECK_OPTION, value);
    }

    /**
     * Get whether to disable the compatibility validation between the current
     * chromedriver and the destination browser/web view.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesChromedriverDisableBuildCheck() {
        return Optional.ofNullable(toSafeBoolean(getCapability(CHROMEDRIVER_DISABLE_BUILD_CHECK_OPTION)));
    }
}
