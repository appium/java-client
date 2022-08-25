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

package io.appium.java_client;

/**
 * Manage Appium Client options.
 */

public class AppiumClientConfig {
    private boolean directConnect = false;

    public AppiumClientConfig() {}

    /**
     * @return the instance of AppiumClientConfig.
     */
    public static AppiumClientConfig defaultConfig() {
        return new AppiumClientConfig();
    }

    /**
     * Whether enable directConnect feature described in
     * https://appiumpro.com/editions/86-connecting-directly-to-appium-hosts-in-distributed-environments
     *
     * @param directConnect if enable the directConnect feature
     * @return A self reference
     */
    public AppiumClientConfig directConnect(boolean directConnect) {
        this.directConnect = directConnect;
        return this;
    }

    /**
     * Whether enable directConnect feature is enabled.
     *
     * @return If the directConnect is enabled.Defaults false.
     */
    public boolean isDirectConnectEnabled() { return this.directConnect; }
}
