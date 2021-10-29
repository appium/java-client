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

public interface SupportsResultBundlePathOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String RESULT_BUNDLE_PATH_OPTION = "resultBundlePath";

    /**
     * Specify the path to the result bundle path as xcodebuild argument for
     * WebDriverAgent build under a security flag. WebDriverAgent process must
     * start/stop every time to pick up changed value of this property.
     * Specifying useNewWDA to true may help there. Please read man xcodebuild
     * for more details.
     *
     * @param path The path where the resulting XCTest bundle should be stored.
     * @return self instance for chaining.
     */
    default T setResultBundlePath(String path) {
        return amend(RESULT_BUNDLE_PATH_OPTION, path);
    }

    /**
     * Get the path where the resulting XCTest bundle should be stored.
     *
     * @return XCTest result bundle path.
     */
    default Optional<String> getResultBundlePath() {
        return Optional.ofNullable((String) getCapability(RESULT_BUNDLE_PATH_OPTION));
    }
}
