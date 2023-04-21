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

package io.appium.java_client.ios;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;

public interface PerformsTouchID extends ExecutesMethod {

    /**
     * Simulate touchId event on iOS Simulator. Check the documentation on 'mobile: sendBiometricMatch'
     * extension for more details.
     *
     * @param match If true, simulates a successful fingerprint scan. If false, simulates a failed fingerprint scan.
     */
    default void performTouchID(boolean match) {
        CommandExecutionHelper.executeScript(this, "mobile: sendBiometricMatch", ImmutableMap.of(
                "type", "touchId",
                "match", match
        ));
    }

    /**
     * Enrolls touchId in iOS Simulator. Check the documentation on 'mobile: enrollBiometric'
     * extension for more details.
     *
     * @param enabled Whether to enable or disable Touch ID Enrollment. The actual state of the feature
     *                will only be changed if the current value is different from the previous one.
     *                Multiple calls of the method with the same argument value have no effect.
     */
    default void toggleTouchIDEnrollment(boolean enabled) {
        CommandExecutionHelper.executeScript(this, "mobile: enrollBiometric", ImmutableMap.of(
                "isEnabled", enabled
        ));
    }
}
