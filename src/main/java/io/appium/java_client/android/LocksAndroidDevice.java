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

package io.appium.java_client.android;

import static io.appium.java_client.MobileCommand.lockDeviceCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.isLockedCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.unlockCommand;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;

public interface LocksAndroidDevice extends ExecutesMethod {
    /**
     * Check if the device is locked.
     *
     * @return true if device is locked. False otherwise
     */
    default boolean isLocked() {
        return CommandExecutionHelper.execute(this, isLockedCommand());
    }

    /**
     * This method locks a device.
     */
    default void lockDevice() {
        CommandExecutionHelper.execute(this, lockDeviceCommand(0));
    }

    /**
     * This method unlocks a device.
     */
    default void unlockDevice() {
        CommandExecutionHelper.execute(this, unlockCommand());
    }
}
