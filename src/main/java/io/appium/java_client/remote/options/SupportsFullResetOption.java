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

package io.appium.java_client.remote.options;

import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsFullResetOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String FULL_RESET_OPTION = "fullReset";

    /**
     * Set the app to do a full reset.
     *
     * @return self instance for chaining.
     */
    default T setFullReset() {
        return setFullReset(true);
    }

    /**
     * Set whether the app does a full reset.
     *
     * @param bool is whether the app does a full reset.
     * @return self instance for chaining.
     */
    default T setFullReset(boolean bool) {
        return amend(FULL_RESET_OPTION, bool);
    }

    /**
     * Get whether the app does a full reset.
     *
     * @return true if the app does a full reset.
     */
    default Optional<Boolean> doesFullReset() {
        return Optional.ofNullable(toSafeBoolean(getCapability(FULL_RESET_OPTION)));
    }
}
