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

package io.appium.java_client.android.options.avd;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsAvdOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String AVD_OPTION = "avd";

    /**
     * The name of Android emulator to run the test on.
     * The names of currently installed emulators could be listed using
     * avdmanager list avd command. If the emulator with the given name
     * is not running then it is going to be started before a test.
     *
     * @param avd The emulator name to use.
     * @return self instance for chaining.
     */
    default T setAvd(String avd) {
        return amend(AVD_OPTION, avd);
    }

    /**
     * Get the name of Android emulator to run the test on.
     *
     * @return Emulator name.
     */
    default Optional<String> getAvd() {
        return Optional.ofNullable((String) getCapability(AVD_OPTION));
    }
}
