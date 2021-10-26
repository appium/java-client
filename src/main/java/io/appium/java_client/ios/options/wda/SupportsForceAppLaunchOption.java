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

public interface SupportsForceAppLaunchOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String FORCE_APP_LAUNCH_OPTION = "forceAppLaunch";

    /**
     * Specify if the app should be forcefully restarted if it is already
     * running on session startup. This capability only has an effect if an
     * application identifier has been passed to the test session (either
     * explicitly, by setting bundleId, or implicitly, by providing app).
     * Default is true unless noReset capability is set to true.
     *
     * @param value Whether to enforce app restart on session startup.
     * @return self instance for chaining.
     */
    default T setForceAppLaunch(boolean value) {
        return amend(FORCE_APP_LAUNCH_OPTION, value);
    }

    /**
     * Get whether to enforce app restart on session startup.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesForceAppLaunch() {
        return Optional.ofNullable(toSafeBoolean(getCapability(FORCE_APP_LAUNCH_OPTION)));
    }
}
