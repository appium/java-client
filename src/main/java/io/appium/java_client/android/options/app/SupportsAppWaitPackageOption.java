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

package io.appium.java_client.android.options.app;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsAppWaitPackageOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String APP_WAIT_PACKAGE_OPTION = "appWaitPackage";

    /**
     * Identifier of the package that the driver should wait for
     * (not necessarily the main one).
     * If not provided then defaults to appium:appPackage.
     *
     * @param appWaitPackage Package identifier to wait for.
     * @return self instance for chaining.
     */
    default T setAppWaitPackage(String appWaitPackage) {
        return amend(APP_WAIT_PACKAGE_OPTION, appWaitPackage);
    }

    /**
     * Get the identifier of the app package to wait for.
     *
     * @return Package identifier.
     */
    default Optional<String> getAppWaitPackage() {
        return Optional.ofNullable((String) getCapability(APP_WAIT_PACKAGE_OPTION));
    }
}
