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

import java.time.Duration;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * The interface for the drivers that can lock and unlock the device.
 */
public interface LocksDevice extends ExecutesMethod {

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
        CommandExecutionHelper.executeScript(this, extName, Map.of(
                "seconds", duration.getSeconds()
        ));
    }

    /**
     * Unlock the device if it is locked. This method will return silently if the device
     * is not locked.
     */
    default void unlockDevice() {
        final String extName = "mobile: unlock";
        //noinspection ConstantConditions
        if (!(Boolean) CommandExecutionHelper.executeScript(this, "mobile: isLocked")) {
            return;
        }
        CommandExecutionHelper.executeScript(this, extName);
    }

    /**
     * Check if the device is locked.
     *
     * @return true if the device is locked or false otherwise.
     */
    default boolean isDeviceLocked() {
        final String extName = "mobile: isLocked";
        return requireNonNull(
                CommandExecutionHelper.executeScript(this, extName)
        );
    }
}
