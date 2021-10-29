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

package io.appium.java_client.android.options.app;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsAppWaitForLaunchOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String APP_WAIT_FOR_LAUNCH_OPTION = "appWaitForLaunch";

    /**
     * Whether to block until the app under test returns the control to the
     * caller after its activity has been started by Activity Manager
     * (true, the default value) or to continue the test without waiting for that (false).
     *
     * @param value Set to false if you don't want to wait for the app to finish its launch.
     * @return self instance for chaining.
     */
    default T setAppWaitForLaunch(boolean value) {
        return amend(APP_WAIT_FOR_LAUNCH_OPTION, value);
    }

    /**
     * Get whether to block until the app under test returns the control to the
     * caller after its activity has been started by Activity Manager.
     *
     * @return True if the driver should block or false otherwise.
     */
    default Optional<Boolean> doesAppWaitForLaunch() {
        return Optional.ofNullable(toSafeBoolean(getCapability(APP_WAIT_FOR_LAUNCH_OPTION)));
    }
}
