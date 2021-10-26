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

package io.appium.java_client.ios.options.app;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsBundleIdOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String BUNDLE_ID_OPTION = "bundleId";

    /**
     * Bundle identifier of the app under test, for example com.mycompany.myapp.
     * The capability value is calculated automatically if app is provided.
     * If neither app nor bundleId capability is provided then XCUITest driver
     * starts from the Home screen.
     *
     * @param identifier App identifier.
     * @return self instance for chaining.
     */
    default T setBundleId(String identifier) {
        return amend(BUNDLE_ID_OPTION, identifier);
    }

    /**
     * Get the app bundle identifier.
     *
     * @return Identifier value.
     */
    default Optional<String> getBundleId() {
        return Optional.ofNullable((String) getCapability(BUNDLE_ID_OPTION));
    }
}
