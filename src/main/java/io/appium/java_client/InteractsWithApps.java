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

import static io.appium.java_client.MobileCommand.ACTIVATE_APP;
import static io.appium.java_client.MobileCommand.CLOSE_APP;
import static io.appium.java_client.MobileCommand.INSTALL_APP;
import static io.appium.java_client.MobileCommand.IS_APP_INSTALLED;
import static io.appium.java_client.MobileCommand.LAUNCH_APP;
import static io.appium.java_client.MobileCommand.QUERY_APP_STATE;
import static io.appium.java_client.MobileCommand.REMOVE_APP;
import static io.appium.java_client.MobileCommand.RESET;
import static io.appium.java_client.MobileCommand.RUN_APP_IN_BACKGROUND;
import static io.appium.java_client.MobileCommand.TERMINATE_APP;
import static io.appium.java_client.MobileCommand.prepareArguments;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.appmanagement.BaseActivateApplicationOptions;
import io.appium.java_client.appmanagement.BaseInstallApplicationOptions;
import io.appium.java_client.appmanagement.BaseRemoveApplicationOptions;
import io.appium.java_client.appmanagement.BaseTerminateApplicationOptions;

import java.time.Duration;
import java.util.AbstractMap;
import javax.annotation.Nullable;

public interface InteractsWithApps extends ExecutesMethod {

    /**
     * Launches the app, which was provided in the capabilities at session creation,
     * and (re)starts the session.
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
        installApp(appPath, null);
    }

    /**
     * Install an app on the mobile device.
     *
     * @param appPath path to app to install or a remote URL.
     * @param options Set of the corresponding instllation options for
     *                the particular platform.
     */
    default void installApp(String appPath, @Nullable BaseInstallApplicationOptions options) {
        String[] parameters = options == null ? new String[]{"appPath"} :
                new String[]{"appPath", "options"};
        Object[] values = options == null ? new Object[]{appPath} :
                new Object[]{appPath, options.build()};
        CommandExecutionHelper.execute(this,
                new AbstractMap.SimpleEntry<>(INSTALL_APP, prepareArguments(parameters, values)));
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
     * Resets the currently running app together with the session.
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
     * @param bundleId the bundle identifier (or app id) of the app to remove.
     * @return true if the uninstall was successful.
     */
    default boolean removeApp(String bundleId) {
        return removeApp(bundleId, null);
    }

    /**
     * Remove the specified app from the device (uninstall).
     *
     * @param bundleId the bundle identifier (or app id) of the app to remove.
     * @param options  the set of uninstall options supported by the
     *                 particular platform.
     * @return true if the uninstall was successful.
     */
    default boolean removeApp(String bundleId, @Nullable BaseRemoveApplicationOptions options) {
        String[] parameters = options == null ? new String[]{"bundleId"} :
                new String[]{"bundleId", "options"};
        Object[] values = options == null ? new Object[]{bundleId} :
                new Object[]{bundleId, options.build()};
        return CommandExecutionHelper.execute(this,
                new AbstractMap.SimpleEntry<>(REMOVE_APP, prepareArguments(parameters, values)));
    }

    /**
     * Close the app which was provided in the capabilities at session creation
     * and quits the session.
     */
    default void closeApp() {
        execute(CLOSE_APP);
    }

    /**
     * Activates the given app if it installed, but not running or if it is running in the
     * background.
     *
     * @param bundleId the bundle identifier (or app id) of the app to activate.
     */
    default void activateApp(String bundleId) {
        activateApp(bundleId, null);
    }

    /**
     * Activates the given app if it installed, but not running or if it is running in the
     * background.
     *
     * @param bundleId the bundle identifier (or app id) of the app to activate.
     * @param options  the set of activation options supported by the
     *                 particular platform.
     */
    default void activateApp(String bundleId, @Nullable BaseActivateApplicationOptions options) {
        String[] parameters = options == null ? new String[]{"bundleId"} :
                new String[]{"bundleId", "options"};
        Object[] values = options == null ? new Object[]{bundleId} :
                new Object[]{bundleId, options.build()};
        CommandExecutionHelper.execute(this,
                new AbstractMap.SimpleEntry<>(ACTIVATE_APP, prepareArguments(parameters, values)));
    }

    /**
     * Queries the state of an application.
     *
     * @param bundleId the bundle identifier (or app id) of the app to query the state of.
     * @return one of possible {@link ApplicationState} values,
     */
    default ApplicationState queryAppState(String bundleId) {
        return ApplicationState.ofCode(CommandExecutionHelper.execute(this,
                new AbstractMap.SimpleEntry<>(QUERY_APP_STATE, ImmutableMap.of("bundleId", bundleId))));
    }

    /**
     * Terminate the particular application if it is running.
     *
     * @param bundleId the bundle identifier (or app id) of the app to be terminated.
     * @return true if the app was running before and has been successfully stopped.
     */
    default boolean terminateApp(String bundleId) {
        return terminateApp(bundleId, null);
    }

    /**
     * Terminate the particular application if it is running.
     *
     * @param bundleId the bundle identifier (or app id) of the app to be terminated.
     * @param options  the set of termination options supported by the
     *                 particular platform.
     * @return true if the app was running before and has been successfully stopped.
     */
    default boolean terminateApp(String bundleId, @Nullable BaseTerminateApplicationOptions options) {
        String[] parameters = options == null ? new String[]{"bundleId"} :
                new String[]{"bundleId", "options"};
        Object[] values = options == null ? new Object[]{bundleId} :
                new Object[]{bundleId, options.build()};
        return CommandExecutionHelper.execute(this,
                new AbstractMap.SimpleEntry<>(TERMINATE_APP, prepareArguments(parameters, values)));
    }
}
