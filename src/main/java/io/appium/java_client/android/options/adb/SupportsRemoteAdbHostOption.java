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

package io.appium.java_client.android.options.adb;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsRemoteAdbHostOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String REMOTE_ADB_HOST_OPTION = "remoteAdbHost";

    /**
     * Address of the host where ADB is running (the value of -H ADB command line option).
     * Localhost by default.
     *
     * @param host The name host where ADB server is running.
     * @return self instance for chaining.
     */
    default T setRemoteAdbHost(String host) {
        return amend(REMOTE_ADB_HOST_OPTION, host);
    }

    /**
     * Get the address of the host where ADB is running.
     *
     * @return Host name.
     */
    default Optional<String> getRemoteAdbHost() {
        return Optional.ofNullable((String) getCapability(REMOTE_ADB_HOST_OPTION));
    }
}
