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

public interface SupportsShowXcodeLogOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SHOW_XCODE_LOG_OPTION = "showXcodeLog";

    /**
     * Enforce to display the output of the Xcode command used to run the tests
     * in Appium logs.
     *
     * @return self instance for chaining.
     */
    default T showXcodeLog() {
        return amend(SHOW_XCODE_LOG_OPTION, true);
    }

    /**
     * Whether to display the output of the Xcode command used to run the tests in
     * Appium logs. If this is true, there will be lots of extra logging at startup.
     * Defaults to false.
     *
     * @param value Whether to display the output of the Xcode command used to run the tests.
     * @return self instance for chaining.
     */
    default T setShowXcodeLog(boolean value) {
        return amend(SHOW_XCODE_LOG_OPTION, value);
    }

    /**
     * Get whether to display the output of the Xcode command used to run the tests.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesShowXcodeLog() {
        return Optional.ofNullable(toSafeBoolean(getCapability(SHOW_XCODE_LOG_OPTION)));
    }
}
