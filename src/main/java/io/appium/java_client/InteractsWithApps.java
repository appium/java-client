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

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.appmanagement.BaseActivateApplicationOptions;
import io.appium.java_client.appmanagement.BaseInstallApplicationOptions;
import io.appium.java_client.appmanagement.BaseOptions;
import io.appium.java_client.appmanagement.BaseRemoveApplicationOptions;
import io.appium.java_client.appmanagement.BaseTerminateApplicationOptions;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnsupportedCommandException;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.appium.java_client.MobileCommand.RUN_APP_IN_BACKGROUND;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

@SuppressWarnings("rawtypes")
public interface InteractsWithApps extends ExecutesMethod, HasCapabilities {

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
        List<String> argNames = ImmutableList.of("app", "appPath");
        String platformName = (String) getCapabilities().getCapability(PLATFORM_NAME);
        if (platformName != null) {
            if (platformName.equalsIgnoreCase(Platform.IOS.name())) {
                argNames = ImmutableList.of("app");
            } else if (platformName.equalsIgnoreCase(Platform.ANDROID.name())) {
                argNames = ImmutableList.of("appPath");
            }
        }
        //noinspection unchecked
        Map<String, Object> args = ImmutableMap.<String, Object>builder()
            .putAll(argNames.stream().collect(Collectors.toMap(Functions.identity(), (ign) -> appPath)))
            .putAll(Optional.ofNullable(options).map(BaseOptions::build).orElseGet(Collections::emptyMap))
            .build();
        CommandExecutionHelper.executeScript(this, "mobile: installApp", args);
    }

    /**
     * Checks if an app is installed on the device.
     *
     * @param bundleId bundleId of the app.
     * @return True if app is installed, false otherwise.
     */
    default boolean isAppInstalled(String bundleId) {
        List<String> argNames = ImmutableList.of("bundleId", "appId");
        String platformName = (String) getCapabilities().getCapability(PLATFORM_NAME);
        if (platformName != null) {
            if (platformName.equalsIgnoreCase(Platform.IOS.name())) {
                argNames = ImmutableList.of("bundleId");
            } else if (platformName.equalsIgnoreCase(Platform.ANDROID.name())) {
                argNames = ImmutableList.of("appId");
            }
        }
        Map<String, Object> args = argNames.stream().collect(Collectors.toMap(
                Functions.identity(), (ign) -> bundleId
        ));
        return checkNotNull(
            CommandExecutionHelper.executeScript(this, "mobile: isAppInstalled", args)
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
        try {
            CommandExecutionHelper.executeScript(this, "mobile: backgroundApp", ImmutableMap.of(
                    "seconds", duration.toMillis() / 1000.0
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            execute(RUN_APP_IN_BACKGROUND, ImmutableMap.of("seconds", duration.toMillis() / 1000.0));
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
        List<String> argNames = ImmutableList.of("bundleId", "appId");
        String platformName = (String) getCapabilities().getCapability(PLATFORM_NAME);
        if (platformName != null) {
            if (platformName.equalsIgnoreCase(Platform.IOS.name())) {
                argNames = ImmutableList.of("bundleId");
            } else if (platformName.equalsIgnoreCase(Platform.ANDROID.name())) {
                argNames = ImmutableList.of("appId");
            }
        }
        //noinspection unchecked
        Map<String, Object> args = ImmutableMap.<String, Object>builder()
                .putAll(argNames.stream().collect(Collectors.toMap(Functions.identity(), (ign) -> bundleId)))
                .putAll(Optional.ofNullable(options).map(BaseOptions::build).orElseGet(Collections::emptyMap))
                .build();
        return checkNotNull(
                CommandExecutionHelper.executeScript(this, "mobile: removeApp", args)
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
        List<String> argNames = ImmutableList.of("bundleId", "appId");
        String platformName = (String) getCapabilities().getCapability(PLATFORM_NAME);
        if (platformName != null) {
            if (platformName.equalsIgnoreCase(Platform.IOS.name())) {
                argNames = ImmutableList.of("bundleId");
            } else if (platformName.equalsIgnoreCase(Platform.ANDROID.name())) {
                argNames = ImmutableList.of("appId");
            }
        }
        //noinspection unchecked
        Map<String, Object> args = ImmutableMap.<String, Object>builder()
                .putAll(argNames.stream().collect(Collectors.toMap(Functions.identity(), (ign) -> bundleId)))
                .putAll(Optional.ofNullable(options).map(BaseOptions::build).orElseGet(Collections::emptyMap))
                .build();
        CommandExecutionHelper.executeScript(this, "mobile: activateApp", args);
    }

    /**
     * Queries the state of an application.
     *
     * @param bundleId the bundle identifier (or app id) of the app to query the state of.
     * @return one of possible {@link ApplicationState} values,
     */
    default ApplicationState queryAppState(String bundleId) {
        List<String> argNames = ImmutableList.of("bundleId", "appId");
        String platformName = (String) getCapabilities().getCapability(PLATFORM_NAME);
        if (platformName != null) {
            if (platformName.equalsIgnoreCase(Platform.IOS.name())) {
                argNames = ImmutableList.of("bundleId");
            } else if (platformName.equalsIgnoreCase(Platform.ANDROID.name())) {
                argNames = ImmutableList.of("appId");
            }
        }
        Map<String, Object> args = argNames.stream().collect(Collectors.toMap(Functions.identity(), (ign) -> bundleId));
        return ApplicationState.ofCode(
            checkNotNull(
                CommandExecutionHelper.executeScript(this, "mobile: queryAppState", args)
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
        List<String> argNames = ImmutableList.of("bundleId", "appId");
        String platformName = (String) getCapabilities().getCapability(PLATFORM_NAME);
        if (platformName != null) {
            if (platformName.equalsIgnoreCase(Platform.IOS.name())) {
                argNames = ImmutableList.of("bundleId");
            } else if (platformName.equalsIgnoreCase(Platform.ANDROID.name())) {
                argNames = ImmutableList.of("appId");
            }
        }
        //noinspection unchecked
        Map<String, Object> args = ImmutableMap.<String, Object>builder()
                .putAll(argNames.stream().collect(Collectors.toMap(Functions.identity(), (ign) -> bundleId)))
                .putAll(Optional.ofNullable(options).map(BaseOptions::build).orElseGet(Collections::emptyMap))
                .build();
        return checkNotNull(
                CommandExecutionHelper.executeScript(this, "mobile: terminateApp", args)
        );
    }
}
