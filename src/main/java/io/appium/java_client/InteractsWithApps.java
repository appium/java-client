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

import static io.appium.java_client.MobileCommand.CLOSE_APP;
import static io.appium.java_client.MobileCommand.INSTALL_APP;
import static io.appium.java_client.MobileCommand.IS_APP_INSTALLED;
import static io.appium.java_client.MobileCommand.LAUNCH_APP;
import static io.appium.java_client.MobileCommand.REMOVE_APP;
import static io.appium.java_client.MobileCommand.RESET;
import static io.appium.java_client.MobileCommand.RUN_APP_IN_BACKGROUND;
import static io.appium.java_client.MobileCommand.prepareArguments;

import com.google.common.collect.ImmutableMap;

import java.time.Duration;
import java.util.AbstractMap;

public interface InteractsWithApps extends ExecutesMethod {
    /**
     * Launch the app which was provided in the capabilities at session creation.
     */
    default void launchApp() {
        execute(LAUNCH_APP);
    }

    /**
     * Install an app on the mobile device.
     *
     * @param appPath path to app to install.
     */
    default void installApp(String appPath) {
        execute(INSTALL_APP, ImmutableMap.of("appPath", appPath));
    }

    /**
     * Checks if an app is installed on the device.
     *
     * @param bundleId bundleId of the app.
     * @return True if app is installed, false otherwise.
     */
    default boolean isAppInstalled(String bundleId) {
        return CommandExecutionHelper.execute(this,
                new AbstractMap.SimpleEntry<>(IS_APP_INSTALLED, prepareArguments("bundleId", bundleId)));
    }

    /**
     * Reset the currently running app for this session.
     */
    default void resetApp() {
        execute(RESET);
    }

    /**
     * Runs the current app as a background app for the time
     * requested. This is a synchronous method, it returns after the back has
     * been returned to the foreground.
     *
     * @param duration The time to run App in background. Minimum time resolution is one second
     */
    default void runAppInBackground(Duration duration) {
        execute(RUN_APP_IN_BACKGROUND, ImmutableMap.of("seconds", duration.getSeconds()));
    }

    /**
     * Remove the specified app from the device (uninstall).
     *
     * @param bundleId the bunble identifier (or app id) of the app to remove.
     */
    default void removeApp(String bundleId) {
        execute(REMOVE_APP, ImmutableMap.of("bundleId", bundleId));
    }

    /**
     * Close the app which was provided in the capabilities at session creation.
     */
    default void closeApp() {
        execute(CLOSE_APP);
    }

}
