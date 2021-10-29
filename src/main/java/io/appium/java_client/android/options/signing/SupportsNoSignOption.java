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

package io.appium.java_client.android.options.signing;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsNoSignOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String NO_SIGN_OPTION = "noSign";

    /**
     * Skips application signing.
     *
     * @return self instance for chaining.
     */
    default T noSign() {
        return amend(NO_SIGN_OPTION, true);
    }

    /**
     * Set it to true in order to skip application signing. By default,
     * all apps are always signed with the default Appium debug signature
     * if they don't have any. This capability cancels all the signing checks
     * and makes the driver to use the application package as is. This
     * capability does not affect .apks packages as these are expected to be
     * already signed.
     *
     * @param value Whether to skip package signing.
     * @return self instance for chaining.
     */
    default T setNoSign(boolean value) {
        return amend(NO_SIGN_OPTION, value);
    }

    /**
     * Get whether to skip package signing.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesNoSign() {
        return Optional.ofNullable(toSafeBoolean(getCapability(NO_SIGN_OPTION)));
    }
}
