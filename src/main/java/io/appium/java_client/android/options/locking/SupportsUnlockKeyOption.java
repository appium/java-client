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

public interface SupportsUnlockKeyOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String UNLOCK_KEY_OPTION = "unlockKey";

    /**
     * Allows to set an unlock key.
     * Read [Unlock tutorial](https://github.com/appium/appium-android-driver/blob/master/docs/UNLOCK.md)
     * for more details.
     *
     * @param unlockKey The unlock key.
     * @return self instance for chaining.
     */
    default T setUnlockKey(String unlockKey) {
        return amend(UNLOCK_KEY_OPTION, unlockKey);
    }

    /**
     * Get the unlock key.
     *
     * @return Unlock key.
     */
    default Optional<String> getUnlockKey() {
        return Optional.ofNullable((String) getCapability(UNLOCK_KEY_OPTION));
    }
}
