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

package io.appium.java_client.mac.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsBundleIdOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String BUNDLE_ID_OPTION = "bundleId";

    /**
     * The bundle identifier of the application to automate, for example
     * com.apple.TextEdit. This is an optional capability. If it is not provided
     * then the session will be started without an application under test
     * (actually, it will be Finder). If the application with the given
     * identifier is not installed then an error will be thrown on session
     * startup. If the application is already running then it will be moved to
     * the foreground.
     *
     * @param identifier A valid application bundle identifier.
     * @return self instance for chaining.
     */
    default T setBundleId(String identifier) {
        return amend(BUNDLE_ID_OPTION, identifier);
    }

    /**
     * Get the bundle identifier of the application to automate.
     *
     * @return Application bundle identifier.
     */
    default Optional<String> getBundleId() {
        return Optional.ofNullable((String) getCapability(BUNDLE_ID_OPTION));
    }
}
