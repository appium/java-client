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

public interface SupportsMockLocationAppOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String MOCK_LOCATION_APP_OPTION = "mockLocationApp";

    /**
     * Sets the package identifier of the app, which is used as a system mock location
     * provider since Appium 1.18.0+. This capability has no effect on emulators.
     * If the value is set to null or an empty string, then Appium will skip the mocked
     * location provider setup procedure. Defaults to Appium Setting package
     * identifier (io.appium.settings).
     *
     * @param appIdentifier The identifier of the mock location provider app.
     * @return self instance for chaining.
     */
    default T setMockLocationApp(String appIdentifier) {
        return amend(MOCK_LOCATION_APP_OPTION, appIdentifier);
    }

    /**
     * Get the identifier of the app, which is used as a system mock location provider.
     *
     * @return App identifier.
     */
    default Optional<String> getMockLocationApp() {
        return Optional.ofNullable((String) getCapability(MOCK_LOCATION_APP_OPTION));
    }
}
