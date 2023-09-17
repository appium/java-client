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

package io.appium.java_client;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.UnsupportedCommandException;

import java.time.Duration;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.appium.java_client.MobileCommand.getIsDeviceLockedCommand;
import static io.appium.java_client.MobileCommand.lockDeviceCommand;
import static io.appium.java_client.MobileCommand.unlockDeviceCommand;

public interface LocksDevice extends ExecutesMethod, CanRememberExtensionPresence {

    /**
     * This method locks a device. It will return silently if the device
     * is already locked.
     */
    default void lockDevice() {
        lockDevice(Duration.ofSeconds(0));
    }

    /**
     * Lock the device (bring it to the lock screen) for a given number of
     * seconds or forever (until the command for unlocking is called). The call
     * is ignored if the device has been already locked.
     *
     * @param duration for how long to lock the screen. Minimum time resolution is one second.
     *                 A negative/zero value will lock the device and return immediately.
     */
    default void lockDevice(Duration duration) {
        final String extName = "mobile: lock";
        try {
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, ImmutableMap.of(
                    "seconds", duration.getSeconds()
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(markExtensionAbsence(extName), lockDeviceCommand(duration));
        }
    }

    /**
     * Unlock the device if it is locked. This method will return silently if the device
     * is not locked.
     */
    default void unlockDevice() {
        final String extName = "mobile: unlock";
        try {
            //noinspection ConstantConditions
            if (!Boolean.parseBoolean(CommandExecutionHelper.executeScript(assertExtensionExists(extName), "mobile: isLocked"))) {
                return;
            }
            CommandExecutionHelper.executeScript(this, extName);
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(markExtensionAbsence(extName), unlockDeviceCommand());
        }
    }

    /**
     * Check if the device is locked.
     *
     * @return true if the device is locked or false otherwise.
     */
    default boolean isDeviceLocked() {
        final String extName = "mobile: isLocked";
        try {
            return checkNotNull(
                    CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName)
            );
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            return checkNotNull(
                    CommandExecutionHelper.execute(markExtensionAbsence(extName), getIsDeviceLockedCommand())
            );
        }
    }
}
