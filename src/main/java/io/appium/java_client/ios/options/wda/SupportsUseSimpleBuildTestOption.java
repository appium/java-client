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

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsUseSimpleBuildTestOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String USE_SIMPLE_BUILD_TEST_OPTION = "useSimpleBuildTest";

    /**
     * Enforce usage of simple build test.
     *
     * @return self instance for chaining.
     */
    default T useSimpleBuildTest() {
        return amend(USE_SIMPLE_BUILD_TEST_OPTION, true);
    }

    /**
     * Build with build and run test with test in xcodebuild for all Xcode versions if
     * this is true, or build with build-for-testing and run tests with
     * test-without-building for over Xcode 8 if this is false. Defaults to false.
     *
     * @param value Whether to use simple build test.
     * @return self instance for chaining.
     */
    default T setUseSimpleBuildTest(boolean value) {
        return amend(USE_SIMPLE_BUILD_TEST_OPTION, value);
    }

    /**
     * Get whether to use simple build test.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesUseSimpleBuildTest() {
        return Optional.ofNullable(toSafeBoolean(getCapability(USE_SIMPLE_BUILD_TEST_OPTION)));
    }
}
