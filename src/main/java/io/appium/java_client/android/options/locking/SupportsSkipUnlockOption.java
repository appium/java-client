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

package io.appium.java_client.android.options.locking;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsSkipUnlockOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SKIP_UNLOCK_OPTION = "skipUnlock";

    /**
     * Skip the check for lock screen presence.
     *
     * @return self instance for chaining.
     */
    default T skipUnlock() {
        return amend(SKIP_UNLOCK_OPTION, true);
    }

    /**
     * Whether to skip the check for lock screen presence (true). By default,
     * UiAutomator2 driver tries to detect if the device's screen is locked
     * before starting the test and to unlock that (which sometimes might be unstable).
     * Note, that this operation takes some time, so it is highly recommended to set
     * this capability to true and disable screen locking on devices under test.
     *
     * @param value Set it to true in order to skip screen unlock checks.
     * @return self instance for chaining.
     */
    default T setSkipUnlock(boolean value) {
        return amend(SKIP_UNLOCK_OPTION, value);
    }

    /**
     * Get whether to skip the check for lock screen presence.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesSkipUnlock() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SKIP_UNLOCK_OPTION)));
    }
}
