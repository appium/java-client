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
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.UnsupportedCommandException;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.appium.java_client.MobileCommand.ACTIVATE_APP;
import static io.appium.java_client.MobileCommand.INSTALL_APP;
import static io.appium.java_client.MobileCommand.IS_APP_INSTALLED;
import static io.appium.java_client.MobileCommand.QUERY_APP_STATE;
import static io.appium.java_client.MobileCommand.REMOVE_APP;
import static io.appium.java_client.MobileCommand.RUN_APP_IN_BACKGROUND;
import static io.appium.java_client.MobileCommand.TERMINATE_APP;

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
            Map<String, Object> args = ImmutableMap.<String, Object>builder()
                .put("app", appPath)
                .put("appPath", appPath)
                .putAll(Optional.ofNullable(options).map(BaseOptions::build).orElseGet(Collections::emptyMap))
                .build();
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, args);
        } catch (UnsupportedCommandException | InvalidArgumentException e) {
            // TODO: Remove the fallback
            Map args = ImmutableMap.builder()
                    .put("appPath", appPath)
                    .putAll(Optional.ofNullable(options).map(
                            opts -> ImmutableMap.of("options", opts.build())
                    ).orElseGet(ImmutableMap::of))
                    .build();
            CommandExecutionHelper.execute(
                    markExtensionAbsence(extName), new AbstractMap.SimpleEntry<>(INSTALL_APP, args)
            );
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
            return checkNotNull(
                    CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, ImmutableMap.of(
                            "bundleId", bundleId,
                            "appId", bundleId
                    ))
            );
        } catch (UnsupportedCommandException | InvalidArgumentException e) {
            // TODO: Remove the fallback
            return checkNotNull(
                    CommandExecutionHelper.execute(
                            markExtensionAbsence(extName),
                            new AbstractMap.SimpleEntry<>(IS_APP_INSTALLED, ImmutableMap.of("bundleId", bundleId))
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
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, ImmutableMap.of(
                    "seconds", duration.toMillis() / 1000.0
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(
                    markExtensionAbsence(extName),
                    new AbstractMap.SimpleEntry<>(RUN_APP_IN_BACKGROUND, ImmutableMap.of(
                            "seconds", duration.toMillis() / 1000.0)
                    )
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
            Map<String, Object> args = ImmutableMap.<String, Object>builder()
                    .put("bundleId", bundleId)
                    .put("appId", bundleId)
                    .putAll(Optional.ofNullable(options).map(BaseOptions::build).orElseGet(Collections::emptyMap))
                    .build();
            return checkNotNull(
                    CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, args)
            );
        } catch (UnsupportedCommandException | InvalidArgumentException e) {
            // TODO: Remove the fallback
            Map args = ImmutableMap.builder()
                    .put("bundleId", bundleId)
                    .putAll(Optional.ofNullable(options).map(
                            opts -> ImmutableMap.of("options", opts.build())
                    ).orElseGet(ImmutableMap::of))
                    .build();
            //noinspection RedundantCast
            return checkNotNull(
                    (Boolean) CommandExecutionHelper.execute(
                            markExtensionAbsence(extName),
                            new AbstractMap.SimpleEntry<>(REMOVE_APP, args)
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
            Map<String, Object> args = ImmutableMap.<String, Object>builder()
                    .put("bundleId", bundleId)
                    .put("appId", bundleId)
                    .putAll(Optional.ofNullable(options).map(BaseOptions::build).orElseGet(Collections::emptyMap))
                    .build();
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, args);
        } catch (UnsupportedCommandException | InvalidArgumentException e) {
            // TODO: Remove the fallback
            Map args = ImmutableMap.builder()
                    .put("bundleId", bundleId)
                    .putAll(Optional.ofNullable(options).map(
                            opts -> ImmutableMap.of("options", opts.build())
                    ).orElseGet(ImmutableMap::of))
                    .build();
            CommandExecutionHelper.execute(
                    markExtensionAbsence(extName), new AbstractMap.SimpleEntry<>(ACTIVATE_APP, args)
            );
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
                    checkNotNull(
                            CommandExecutionHelper.executeScript(
                                    assertExtensionExists(extName),
                                    extName, ImmutableMap.of(
                                            "bundleId", bundleId,
                                            "appId", bundleId
                                    )
                            )
                    )
            );
        } catch (UnsupportedCommandException | InvalidArgumentException e) {
            // TODO: Remove the fallback
            return ApplicationState.ofCode(
                    checkNotNull(
                        CommandExecutionHelper.execute(
                                markExtensionAbsence(extName),
                                new AbstractMap.SimpleEntry<>(QUERY_APP_STATE, ImmutableMap.of("bundleId", bundleId))
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
            Map<String, Object> args = ImmutableMap.<String, Object>builder()
                    .put("bundleId", bundleId)
                    .put("appId", bundleId)
                    .putAll(Optional.ofNullable(options).map(BaseOptions::build).orElseGet(Collections::emptyMap))
                    .build();
            return checkNotNull(
                    CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, args)
            );
        } catch (UnsupportedCommandException | InvalidArgumentException e) {
            // TODO: Remove the fallback
            Map args = ImmutableMap.builder()
                    .put("bundleId", bundleId)
                    .putAll(Optional.ofNullable(options).map(
                            opts -> ImmutableMap.of("options", opts.build())
                    ).orElseGet(ImmutableMap::of))
                    .build();
            //noinspection RedundantCast
            return checkNotNull(
                    (Boolean) CommandExecutionHelper.execute(
                            markExtensionAbsence(extName), new AbstractMap.SimpleEntry<>(TERMINATE_APP, args)
                    )
            );
        }
    }
}
