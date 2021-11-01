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

public interface SupportsKeystoreOptions<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String USE_KEYSTORE_OPTION = "useKeystore";
    String KEYSTORE_PATH_OPTION = "keystorePath";
    String KEYSTORE_PASSWORD_OPTION = "keystorePassword";
    String KEY_ALIAS_OPTION = "keyAlias";
    String KEY_PASSWORD_OPTION = "keyPassword";

    /**
     * Use a custom keystore to sign the app under test.
     *
     * @param keystoreConfig The keystore config to use.
     * @return self instance for chaining.
     */
    default T setKeystoreConfig(KeystoreConfig keystoreConfig) {
        return amend(USE_KEYSTORE_OPTION, true)
                .amend(KEYSTORE_PATH_OPTION, keystoreConfig.getPath())
                .amend(KEYSTORE_PASSWORD_OPTION, keystoreConfig.getPassword())
                .amend(KEY_ALIAS_OPTION, keystoreConfig.getKeyAlias())
                .amend(KEY_PASSWORD_OPTION, keystoreConfig.getKeyPassword());
    }

    /**
     * Get whether to use a custom keystore.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesUseKeystore() {
        return Optional.ofNullable(toSafeBoolean(getCapability(USE_KEYSTORE_OPTION)));
    }

    /**
     * Get the custom keystore config.
     *
     * @return The keystore config.
     */
    default Optional<KeystoreConfig> getKeystoreConfig() {
        if (!doesUseKeystore().orElse(false)) {
            return Optional.empty();
        }
        return Optional.of(new KeystoreConfig(
                (String) getCapability(KEYSTORE_PATH_OPTION),
                (String) getCapability(KEYSTORE_PASSWORD_OPTION),
                (String) getCapability(KEY_ALIAS_OPTION),
                (String) getCapability(KEY_PASSWORD_OPTION)
        ));
    }
}
