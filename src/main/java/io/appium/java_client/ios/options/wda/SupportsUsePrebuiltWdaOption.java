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

public interface SupportsUsePrebuiltWdaOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String USE_PREBUILT_WDA_OPTION = "usePrebuiltWDA";

    /**
     * Enforce to skip the build phase of running the WDA app.
     *
     * @return self instance for chaining.
     */
    default T usePrebuiltWda() {
        return amend(USE_PREBUILT_WDA_OPTION, true);
    }

    /**
     * Skips the build phase of running the WDA app. Building is then the responsibility
     * of the user. Only works for Xcode 8+. Defaults to false.
     *
     * @param value Whether to skip the build phase of running the WDA app.
     * @return self instance for chaining.
     */
    default T setUsePrebuiltWda(boolean value) {
        return amend(USE_PREBUILT_WDA_OPTION, value);
    }

    /**
     * Get whether to skip the build phase of running the WDA app.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesUsePrebuiltWda() {
        return Optional.ofNullable(toSafeBoolean(getCapability(USE_PREBUILT_WDA_OPTION)));
    }
}
