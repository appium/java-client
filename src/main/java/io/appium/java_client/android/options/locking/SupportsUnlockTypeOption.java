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

public interface SupportsUnlockTypeOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String UNLOCK_TYPE_OPTION = "unlockType";

    /**
     * Set one of the possible types of Android lock screens to unlock.
     * Read the [Unlock tutorial](https://github.com/appium/appium-android-driver/blob/master/docs/UNLOCK.md)
     * for more details.
     *
     * @param unlockType One of possible unlock types.
     * @return self instance for chaining.
     */
    default T setUnlockType(String unlockType) {
        return amend(UNLOCK_TYPE_OPTION, unlockType);
    }

    /**
     * Get the unlock type.
     *
     * @return Unlock type.
     */
    default Optional<String> getUnlockType() {
        return Optional.ofNullable((String) getCapability(UNLOCK_TYPE_OPTION));
    }
}
