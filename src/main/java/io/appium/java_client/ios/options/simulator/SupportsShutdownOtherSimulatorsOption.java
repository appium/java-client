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

package io.appium.java_client.ios.options.simulator;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsShutdownOtherSimulatorsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SHUTDOWN_OTHER_SIMULATORS_OPTION = "shutdownOtherSimulators";

    /**
     * Enforce shutdown of other booted simulators except of the current one.
     *
     * @return self instance for chaining.
     */
    default T shutdownOtherSimulators() {
        return amend(SHUTDOWN_OTHER_SIMULATORS_OPTION, true);
    }

    /**
     * If this capability set to true and the current device under test is an iOS
     * Simulator then Appium will try to shutdown all the other running Simulators
     * before to start a new session. This might be useful while executing webview
     * tests on different devices, since only one device can be debugged remotely
     * at once due to an Apple bug. The capability only has an effect if
     * --relaxed-security command line argument is provided to the server.
     * Defaults to false.
     *
     * @param value Whether shutdown of other booted simulators except of the current one.
     * @return self instance for chaining.
     */
    default T setShutdownOtherSimulators(boolean value) {
        return amend(SHUTDOWN_OTHER_SIMULATORS_OPTION, value);
    }

    /**
     * Get whether to shutdown of other booted simulators except of the current one.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesShutdownOtherSimulators() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SHUTDOWN_OTHER_SIMULATORS_OPTION)));
    }
}
