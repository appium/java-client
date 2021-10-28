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

package io.appium.java_client.ios.options.other;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsLaunchWithIdbOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String LAUNCH_WITH_IDB_OPTION = "launchWithIDB";

    /**
     * Enforces launching of WebDriverAgentRunner with idb instead of xcodebuild.
     *
     * @return self instance for chaining.
     */
    default T launchWithIdb() {
        return amend(LAUNCH_WITH_IDB_OPTION, true);
    }

    /**
     * Launch WebDriverAgentRunner with idb instead of xcodebuild. This could save
     * a significant amount of time by skipping the xcodebuild process, although the
     * idb might not be very reliable, especially with fresh Xcode SDKs. Check
     * the idb repository for more details on possible compatibility issues.
     * Defaults to false.
     *
     * @param value Whether to launch WebDriverAgentRunner with idb instead of xcodebuild.
     * @return self instance for chaining.
     */
    default T setLaunchWithIdb(boolean value) {
        return amend(LAUNCH_WITH_IDB_OPTION, value);
    }

    /**
     * Get whether to launch WebDriverAgentRunner with idb instead of xcodebuild
     *
     * @return True or false.
     */
    default Optional<Boolean> doesLaunchWithIdb() {
        return Optional.ofNullable(toSafeBoolean(getCapability(LAUNCH_WITH_IDB_OPTION)));
    }
}
