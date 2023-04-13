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
import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.appmanagement.BaseActivateApplicationOptions;
import io.appium.java_client.appmanagement.BaseInstallApplicationOptions;
import io.appium.java_client.appmanagement.BaseOptions;
import io.appium.java_client.appmanagement.BaseRemoveApplicationOptions;
import io.appium.java_client.appmanagement.BaseTerminateApplicationOptions;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.appium.java_client.MobileCommand.RUN_APP_IN_BACKGROUND;

@SuppressWarnings("rawtypes")
public interface InteractsWithApps extends ExecutesMethod {

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
     * @param options Set of the corresponding installation options for
     *                the particular platform.
     */
    default void installApp(String appPath, @Nullable BaseInstallApplicationOptions options) {
        Map<String, Object> args = new HashMap<>();
        args.put("app", appPath);
        args.put("appPath", appPath);
        Optional.ofNullable(options).map(BaseOptions::build).ifPresent(args::putAll);
        CommandExecutionHelper.executeScript(this, "mobile: installApp", args);
    }

    /**
     * Checks if an app is installed on the device.
     *
     * @param bundleId bundleId of the app.
     * @return True if app is installed, false otherwise.
     */
    default boolean isAppInstalled(String bundleId) {
        return CommandExecutionHelper.executeScript(this, "mobile: isAppInstalled", ImmutableMap.of(
                "bundleId", bundleId,
                "appId", bundleId
        ));
    }

    /**
     * Runs the current app as a background app for the time
     * requested. This is a synchronous method, it blocks while the
     * application is in background.
     *
     * @param duration The time to run App in background. Minimum time resolution is one millisecond.
     *                 Passing zero or a negative value will switch to Home screen and return immediately.
     */
    default void runAppInBackground(Duration duration) {
        execute(RUN_APP_IN_BACKGROUND, ImmutableMap.of("seconds", duration.toMillis() / 1000.0));
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
        Map<String, Object> args = new HashMap<>();
        args.put("bundleId", bundleId);
        args.put("appId", bundleId);
        Optional.ofNullable(options).map(BaseOptions::build).ifPresent(args::putAll);
        return CommandExecutionHelper.executeScript(this, "mobile: removeApp", args);
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
        Map<String, Object> args = new HashMap<>();
        args.put("bundleId", bundleId);
        args.put("appId", bundleId);
        Optional.ofNullable(options).map(BaseOptions::build).ifPresent(args::putAll);
        CommandExecutionHelper.executeScript(this, "mobile: activateApp", args);
    }

    /**
     * Queries the state of an application.
     *
     * @param bundleId the bundle identifier (or app id) of the app to query the state of.
     * @return one of possible {@link ApplicationState} values,
     */
    default ApplicationState queryAppState(String bundleId) {
        return ApplicationState.ofCode(
                CommandExecutionHelper.executeScript(this, "mobile: queryAppState", ImmutableMap.of(
                "bundleId", bundleId,
                "appId", bundleId
                ))
        );
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
        Map<String, Object> args = new HashMap<>();
        args.put("bundleId", bundleId);
        args.put("appId", bundleId);
        Optional.ofNullable(options).map(BaseOptions::build).ifPresent(args::putAll);
        return CommandExecutionHelper.executeScript(this, "mobile: terminateApp", args);
    }
}
