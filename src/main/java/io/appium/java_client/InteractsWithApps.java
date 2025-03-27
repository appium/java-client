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
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.UnsupportedCommandException;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static io.appium.java_client.MobileCommand.ACTIVATE_APP;
import static io.appium.java_client.MobileCommand.INSTALL_APP;
import static io.appium.java_client.MobileCommand.IS_APP_INSTALLED;
import static io.appium.java_client.MobileCommand.QUERY_APP_STATE;
import static io.appium.java_client.MobileCommand.REMOVE_APP;
import static io.appium.java_client.MobileCommand.RUN_APP_IN_BACKGROUND;
import static io.appium.java_client.MobileCommand.TERMINATE_APP;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

@SuppressWarnings({"rawtypes", "unchecked"})
public interface InteractsWithApps extends ExecutesMethod, CanRememberExtensionPresence {

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
        try {
            var args = new HashMap<String, Object>();
            args.put("app", appPath);
            args.put("appPath", appPath);
            ofNullable(options).map(BaseOptions::build).ifPresent(args::putAll);
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, args);
        } catch (UnsupportedCommandException | InvalidArgumentException e) {
            // TODO: Remove the fallback
            var args = new HashMap<String, Object>();
            args.put("appPath", appPath);
            ofNullable(options).map(BaseOptions::build).ifPresent(opts -> args.put("options", opts));
            CommandExecutionHelper.execute(markExtensionAbsence(extName), Map.entry(INSTALL_APP, args));
        }
    }

    /**
     * Checks if an app is installed on the device.
     *
     * @param bundleId bundleId of the app.
     * @return True if app is installed, false otherwise.
     */
    default boolean isAppInstalled(String bundleId) {
        final String extName = "mobile: isAppInstalled";
        try {
            return requireNonNull(
                    CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, Map.of(
                            "bundleId", bundleId,
                            "appId", bundleId
                    ))
            );
        } catch (UnsupportedCommandException | InvalidArgumentException e) {
            // TODO: Remove the fallback
            return requireNonNull(
                    CommandExecutionHelper.execute(
                            markExtensionAbsence(extName),
                            Map.entry(IS_APP_INSTALLED, Map.of("bundleId", bundleId))
                    )
            );
        }
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
        try {
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, Map.of(
                    "seconds", duration.toMillis() / 1000.0
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(
                    markExtensionAbsence(extName),
                    Map.entry(RUN_APP_IN_BACKGROUND, Map.of("seconds", duration.toMillis() / 1000.0))
            );
        }
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
        try {
            var args = new HashMap<String, Object>();
            args.put("bundleId", bundleId);
            args.put("appId", bundleId);
            ofNullable(options).map(BaseOptions::build).ifPresent(args::putAll);
            return requireNonNull(
                    CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, args)
            );
        } catch (UnsupportedCommandException | InvalidArgumentException e) {
            // TODO: Remove the fallback
            var args = new HashMap<String, Object>();
            args.put("bundleId", bundleId);
            ofNullable(options).map(BaseOptions::build).ifPresent(opts -> args.put("options", opts));
            //noinspection RedundantCast
            return requireNonNull(
                    (Boolean) CommandExecutionHelper.execute(
                            markExtensionAbsence(extName),
                            Map.entry(REMOVE_APP, args)
                    )
            );
        }
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
        try {
            var args = new HashMap<String, Object>();
            args.put("bundleId", bundleId);
            args.put("appId", bundleId);
            ofNullable(options).map(BaseOptions::build).ifPresent(args::putAll);
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, args);
        } catch (UnsupportedCommandException | InvalidArgumentException e) {
            // TODO: Remove the fallback
            var args = new HashMap<String, Object>();
            args.put("bundleId", bundleId);
            ofNullable(options).map(BaseOptions::build).ifPresent(opts -> args.put("options", opts));
            CommandExecutionHelper.execute(markExtensionAbsence(extName), Map.entry(ACTIVATE_APP, args));
        }
    }

    /**
     * Queries the state of an application.
     *
     * @param bundleId the bundle identifier (or app id) of the app to query the state of.
     * @return one of possible {@link ApplicationState} values,
     */
    default ApplicationState queryAppState(String bundleId) {
        final String extName = "mobile: queryAppState";
        try {
            return ApplicationState.ofCode(
                    requireNonNull(
                            CommandExecutionHelper.executeScript(
                                    assertExtensionExists(extName),
                                    extName, Map.of(
                                            "bundleId", bundleId,
                                            "appId", bundleId
                                    )
                            )
                    )
            );
        } catch (UnsupportedCommandException | InvalidArgumentException e) {
            // TODO: Remove the fallback
            return ApplicationState.ofCode(
                    requireNonNull(
                        CommandExecutionHelper.execute(
                                markExtensionAbsence(extName),
                                Map.entry(QUERY_APP_STATE, Map.of("bundleId", bundleId))
                        )
                    )
            );
        }
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
        try {
            var args = new HashMap<String, Object>();
            args.put("bundleId", bundleId);
            args.put("appId", bundleId);
            ofNullable(options).map(BaseOptions::build).ifPresent(args::putAll);
            return requireNonNull(
                    CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, args)
            );
        } catch (UnsupportedCommandException | InvalidArgumentException e) {
            // TODO: Remove the fallback
            var args = new HashMap<String, Object>();
            args.put("bundleId", bundleId);
            ofNullable(options).map(BaseOptions::build).ifPresent(opts -> args.put("options", opts));
            //noinspection RedundantCast
            return requireNonNull(
                    (Boolean) CommandExecutionHelper.execute(
                            markExtensionAbsence(extName), Map.entry(TERMINATE_APP, args)
                    )
            );
        }
    }
}
