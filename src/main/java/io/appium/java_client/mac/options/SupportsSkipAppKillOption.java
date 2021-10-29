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

package io.appium.java_client.mac.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsSkipAppKillOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SKIP_APP_KILL_OPTION = "skipAppKill";

    /**
     * Enforces skipping the termination of the application under test
     * when the testing session quits.
     *
     * @return self instance for chaining.
     */
    default T skipAppKill() {
        return amend(SKIP_APP_KILL_OPTION, true);
    }

    /**
     * Set whether to skip the termination of the application under test
     * when the testing session quits. false by default. This capability
     * is only going to be applied if bundleId is set.
     *
     * @param value True to skip the termination of the application under test.
     * @return self instance for chaining.
     */
    default T setSkipAppKill(boolean value) {
        return amend(SKIP_APP_KILL_OPTION, value);
    }

    /**
     * Get whether to skip the termination of the application under test.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesSkipAppKill() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SKIP_APP_KILL_OPTION)));
    }
}
