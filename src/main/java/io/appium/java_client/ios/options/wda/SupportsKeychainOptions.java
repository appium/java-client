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

package io.appium.java_client.ios.options.wda;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsKeychainOptions<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String KEYCHAIN_PATH_OPTION = "keychainPath";
    String KEYCHAIN_PASSWORD_OPTION = "keychainPassword";

    /**
     * Provides details to access custom keychain, which
     * contains the private development key exported from the system keychain.
     *
     * @param keychain Keychain access properties.
     * @return self instance for chaining.
     */
    default T setKeychain(Keychain keychain) {
        return amend(KEYCHAIN_PATH_OPTION, keychain.getPath())
                .amend(KEYCHAIN_PASSWORD_OPTION, keychain.getPassword());
    }

    /**
     * Get details to access custom keychain.
     *
     * @return Keychain access properties
     */
    default Optional<Keychain> getKeychain() {
        String path = (String) getCapability(KEYCHAIN_PATH_OPTION);
        String password = (String) getCapability(KEYCHAIN_PASSWORD_OPTION);
        return path == null || password == null
            ? Optional.empty()
            : Optional.of(new Keychain(path, password));
    }
}
