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

import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.appmanagement.BaseActivateApplicationOptions;
import io.appium.java_client.appmanagement.BaseInstallApplicationOptions;
import io.appium.java_client.appmanagement.BaseOptions;
import io.appium.java_client.appmanagement.BaseRemoveApplicationOptions;
import io.appium.java_client.appmanagement.BaseTerminateApplicationOptions;
import org.jspecify.annotations.Nullable;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

/**
 * The interface for the drivers that can install, launch and manage the apps on the device.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
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
        final String extName = "mobile: installApp";
        var args = new HashMap<String, Object>();
        args.put("app", appPath);
        args.put("appPath", appPath);
        ofNullable(options).map(BaseOptions::build).ifPresent(args::putAll);
        CommandExecutionHelper.executeScript(this, extName, args);
    }

    /**
     * Checks if an app is installed on the device.
     *
     * @param bundleId bundleId of the app.
     * @return True if app is installed, false otherwise.
     */
    default boolean isAppInstalled(String bundleId) {
        final String extName = "mobile: isAppInstalled";
        return requireNonNull(
                CommandExecutionHelper.executeScript(this, extName, Map.of(
                        "bundleId", bundleId,
                        "appId", bundleId
                ))
        );
    }

    /**
     * Runs the current app in the background for the time
     * requested. This is a synchronous method, it blocks while the
     * application is in background.
     *
     * @param duration The time to run App in background. Minimum time resolution unit is one millisecond.
     *                 Passing a negative value will switch to Home screen and return immediately.
     */
    default void runAppInBackground(Duration duration) {
        final String extName = "mobile: backgroundApp";
        CommandExecutionHelper.executeScript(this, extName, Map.of(
                "seconds", duration.toMillis() / 1000.0
        ));
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
        final String extName = "mobile: removeApp";
        var args = new HashMap<String, Object>();
        args.put("bundleId", bundleId);
        args.put("appId", bundleId);
        ofNullable(options).map(BaseOptions::build).ifPresent(args::putAll);
        return requireNonNull(
                CommandExecutionHelper.executeScript(this, extName, args)
        );
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
        final String extName = "mobile: activateApp";
        var args = new HashMap<String, Object>();
        args.put("bundleId", bundleId);
        args.put("appId", bundleId);
        ofNullable(options).map(BaseOptions::build).ifPresent(args::putAll);
        CommandExecutionHelper.executeScript(this, extName, args);
    }

    /**
     * Queries the state of an application.
     *
     * @param bundleId the bundle identifier (or app id) of the app to query the state of.
     * @return one of possible {@link ApplicationState} values,
     */
    default ApplicationState queryAppState(String bundleId) {
        final String extName = "mobile: queryAppState";
        return ApplicationState.ofCode(
                requireNonNull(
                        CommandExecutionHelper.executeScript(
                                this,
                                extName, Map.of(
                                        "bundleId", bundleId,
                                        "appId", bundleId
                                )
                        )
                )
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
        final String extName = "mobile: terminateApp";
        var args = new HashMap<String, Object>();
        args.put("bundleId", bundleId);
        args.put("appId", bundleId);
        ofNullable(options).map(BaseOptions::build).ifPresent(args::putAll);
        return requireNonNull(
                CommandExecutionHelper.executeScript(this, extName, args)
        );
    }
}
