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

package io.appium.java_client.android.options.other;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toInteger;

public interface SupportsUserProfileOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String USER_PROFILE_OPTION = "userProfile";

    /**
     * Integer identifier of a user profile. By default, the app under test is
     * installed for the currently active user, but in case it is necessary to
     * test how the app performs while being installed for a user profile,
     * which is different from the current one, then this capability might
     * come in handy.
     *
     * @param profileId User profile identifier.
     * @return self instance for chaining.
     */
    default T setUserProfile(int profileId) {
        return amend(USER_PROFILE_OPTION, profileId);
    }

    /**
     * Get the integer identifier of a user profile.
     *
     * @return User profile id.
     */
    default Optional<Integer> getUserProfile() {
        return Optional.ofNullable(toInteger(getCapability(USER_PROFILE_OPTION)));
    }
}
