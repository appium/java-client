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

public class KeystoreConfig {
    private final String path;
    private final String password;
    private final String keyAlias;
    private final String keyPassword;

    /**
     * Defines the configuration of a custom keystore.
     *
     * @param path The full path to the keystore file on the server filesystem.
     * @param password The password to the keystore file provided in path.
     * @param keyAlias The alias of the key in the keystore file provided in path.
     * @param keyPassword The password of the key in the keystore file provided in path.
     */
    public KeystoreConfig(String path, String password,
                          String keyAlias, String keyPassword) {
        this.path = path;
        this.password = password;
        this.keyAlias = keyAlias;
        this.keyPassword = keyPassword;
    }

    public String getPath() {
        return path;
    }

    public String getPassword() {
        return password;
    }

    public String getKeyAlias() {
        return keyAlias;
    }

    public String getKeyPassword() {
        return keyPassword;
    }
}
