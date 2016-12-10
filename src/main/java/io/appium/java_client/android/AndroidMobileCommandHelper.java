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

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.MobileCommand;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.internal.HasIdentity;

import java.util.AbstractMap;
import java.util.Map;

/**
 * This util class helps to prepare parameters of Android-specific JSONWP
 * commands.
 */
public class AndroidMobileCommandHelper extends MobileCommand {

    /**
     * This method forms a {@link java.util.Map} of parameters for the
     * getting of the current activity.
     *
     * @return a key-value pair. The key is the command name. The value is a
     * {@link java.util.Map} command arguments.
     */
    public static Map.Entry<String, Map<String, ?>> currentActivityCommand() {
        return new AbstractMap.SimpleEntry<>(
                CURRENT_ACTIVITY, ImmutableMap.<String, Object>of());
    }

    /**
     * This method forms a {@link java.util.Map} of parameters for the
     * ending of the test coverage.
     *
     * @param intent intent to broadcast.
     * @param path   path to .ec file.
     * @return a key-value pair. The key is the command name. The value is a
     * {@link java.util.Map} command arguments.
     */
    public static Map.Entry<String, Map<String, ?>> endTestCoverageCommand(String intent,
        String path) {
        String[] parameters = new String[] {"intent", "path"};
        Object[] values = new Object[] {intent, path};
        return new AbstractMap.SimpleEntry<>(
                END_TEST_COVERAGE, prepareArguments(parameters, values));
    }

    /**
     * This method forms a {@link java.util.Map} of parameters to
     * Retrieve the display density of the Android device.
     *
     * @return a key-value pair. The key is the command name. The value is a
     * {@link java.util.Map} command arguments.
     */
    public static Map.Entry<String, Map<String, ?>> getDisplayDensityCommand() {
        return new AbstractMap.SimpleEntry<>(
            GET_DISPLAY_DENSITY, ImmutableMap.<String, Object>of());
    }

    /**
     * This method forms a {@link java.util.Map} of parameters for the
     * getting of a network connection value.
     *
     * @return a key-value pair. The key is the command name. The value is a
     * {@link java.util.Map} command arguments.
     */
    public static Map.Entry<String, Map<String, ?>> getNetworkConnectionCommand() {
        return new AbstractMap.SimpleEntry<>(
                GET_NETWORK_CONNECTION, ImmutableMap.<String, Object>of());
    }

    /**
     * This method forms a {@link java.util.Map} of parameters to
     * Retrieve visibility and bounds information of the status and navigation bars.
     *
     * @return a key-value pair. The key is the command name. The value is a
     * {@link java.util.Map} command arguments.
     */
    public static Map.Entry<String, Map<String, ?>> getSystemBarsCommand() {
        return new AbstractMap.SimpleEntry<>(
            GET_SYSTEM_BARS, ImmutableMap.<String, Object>of());
    }

    /**
     * This method forms a {@link java.util.Map} of parameters for the
     * checking of the keyboard state (is it shown or not).
     *
     * @return a key-value pair. The key is the command name. The value is a
     * {@link java.util.Map} command arguments.
     */
    public static Map.Entry<String, Map<String, ?>> isKeyboardShownCommand() {
        return new AbstractMap.SimpleEntry<>(
            IS_KEYBOARD_SHOWN, ImmutableMap.<String, Object>of());
    }

    /**
     * This method forms a {@link java.util.Map} of parameters for the
     * checking of the device state (is it locked or not).
     *
     * @return a key-value pair. The key is the command name. The value is a
     * {@link java.util.Map} command arguments.
     */
    public static Map.Entry<String, Map<String, ?>> isLockedCommand() {
        return new AbstractMap.SimpleEntry<>(
                IS_LOCKED, ImmutableMap.<String, Object>of());
    }


    /**
     * It is deprecated. Please use {@link MobileCommand#pressKeyCodeCommand(int)} instead.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> pressKeyCodeCommand(int key) {
        return new AbstractMap.SimpleEntry<>(
                PRESS_KEY_CODE, prepareArguments("keycode", key));
    }

    /**
     * It is deprecated. Please use {@link MobileCommand#pressKeyCodeCommand(int, Integer)} instead.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> pressKeyCodeCommand(int key,
        Integer metastate) {
        String[] parameters = new String[] {"keycode", "metastate"};
        Object[] values = new Object[] {key, metastate};
        return new AbstractMap.SimpleEntry<>(
                PRESS_KEY_CODE, prepareArguments(parameters, values));
    }

    /**
     * It is deprecated. Please use {@link MobileCommand#longPressKeyCodeCommand(int)} instead.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> longPressKeyCodeCommand(int key) {
        return new AbstractMap.SimpleEntry<>(
                LONG_PRESS_KEY_CODE, prepareArguments("keycode", key));
    }

    /**
     * It is deprecated. Please use {@link MobileCommand#longPressKeyCodeCommand(int, Integer)} instead.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> longPressKeyCodeCommand(int key,
        Integer metastate) {
        String[] parameters = new String[] {"keycode", "metastate"};
        Object[] values = new Object[] {key, metastate};
        return new AbstractMap.SimpleEntry<>(
                LONG_PRESS_KEY_CODE, prepareArguments(parameters, values));
    }

    /**
     * This method forms a {@link java.util.Map} of parameters for the
     * notification opening.
     *
     * @return a key-value pair. The key is the command name. The value is a
     * {@link java.util.Map} command arguments.
     */
    public static Map.Entry<String, Map<String, ?>> openNotificationsCommand() {
        return new AbstractMap.SimpleEntry<>(
                OPEN_NOTIFICATIONS, ImmutableMap.<String, Object>of());
    }

    /**
     * This method forms a {@link java.util.Map} of parameters for the
     * file pushing
     *
     * @param remotePath Path to file to write data to on remote device
     * @param base64Data Base64 encoded byte array of data to write to remote device
     * @return a key-value pair. The key is the command name. The value is a
     * {@link java.util.Map} command arguments.
     */
    public static Map.Entry<String, Map<String, ?>>  pushFileCommandCommand(String remotePath,
        byte[] base64Data) {
        String[] parameters = new String[] {"path", "data"};
        Object[] values = new Object[] {remotePath, base64Data};
        return new AbstractMap.SimpleEntry<>(PUSH_FILE, prepareArguments(parameters, values));
    }

    /**
     * This method forms a {@link java.util.Map} of parameters for the
     * setting of device network connection.
     *
     * @param connection The bitmask of the desired connection
     * @return a key-value pair. The key is the command name. The value is a
     * {@link java.util.Map} command arguments.
     */
    public static Map.Entry<String, Map<String, ?>> setConnectionCommand(Connection connection) {
        String[] parameters = new String[] {"name", "parameters"};
        Object[] values =
            new Object[] {"network_connection", ImmutableMap.of("type", connection.bitMask)};
        return new AbstractMap.SimpleEntry<>(
                SET_NETWORK_CONNECTION, prepareArguments(parameters, values));
    }

    /**
     * This method forms a {@link java.util.Map} of parameters for the
     * activity starting.
     *
     * @param appPackage      The package containing the activity. [Required]
     * @param appActivity     The activity to start. [Required]
     * @param appWaitPackage  Automation will begin after this package starts. [Optional]
     * @param appWaitActivity Automation will begin after this activity starts. [Optional]
     * @param intentAction  Intent action which will be used to start activity [Optional]
     * @param intentCategory  Intent category which will be used to start activity [Optional]
     * @param intentFlags  Flags that will be used to start activity [Optional]
     * @param optionalIntentArguments Additional intent arguments that will be used to
     *                                start activity [Optional]
     * @return a key-value pair. The key is the command name. The value is a
     * {@link java.util.Map} command arguments.
     * @throws IllegalArgumentException when any required argument is empty
     */
    public static Map.Entry<String, Map<String, ?>> startActivityCommand(String appPackage,
        String appActivity, String appWaitPackage, String appWaitActivity,
        String intentAction, String intentCategory, String intentFlags,
        String optionalIntentArguments, boolean stopApp) throws IllegalArgumentException {

        checkArgument((!StringUtils.isBlank(appPackage)
                && !StringUtils.isBlank(appActivity)),
            String.format("'%s' and '%s' are required.", "appPackage", "appActivity"));

        String targetWaitPackage = !StringUtils.isBlank(appWaitPackage) ? appWaitPackage : "";
        String targetWaitActivity = !StringUtils.isBlank(appWaitActivity) ? appWaitActivity : "";
        String targetIntentAction = !StringUtils.isBlank(intentAction) ? intentAction : "";
        String targetIntentCategory = !StringUtils.isBlank(intentCategory) ? intentCategory : "";
        String targetIntentFlags = !StringUtils.isBlank(intentFlags) ? intentFlags : "";
        String targetOptionalIntentArguments = !StringUtils.isBlank(optionalIntentArguments)
            ? optionalIntentArguments : "";

        ImmutableMap<String, ?> parameters = ImmutableMap
            .<String, Object>builder().put("appPackage", appPackage)
            .put("appActivity", appActivity)
            .put("appWaitPackage", targetWaitPackage)
            .put("appWaitActivity", targetWaitActivity)
            .put("dontStopAppOnReset", !stopApp)
            .put("intentAction", targetIntentAction)
            .put("intentCategory", targetIntentCategory)
            .put("intentFlags", targetIntentFlags)
            .put("optionalIntentArguments", targetOptionalIntentArguments)
            .build();
        return new AbstractMap.SimpleEntry<>(START_ACTIVITY, parameters);
    }

    /**
     * This method forms a {@link java.util.Map} of parameters for the
     * toggling of  location services.
     *
     * @return a key-value pair. The key is the command name. The value is a
     * {@link java.util.Map} command arguments.
     */
    public static Map.Entry<String, Map<String, ?>> toggleLocationServicesCommand() {
        return new AbstractMap.SimpleEntry<>(TOGGLE_LOCATION_SERVICES, ImmutableMap.<String, Object>of());
    }

    /**
     * This method forms a {@link java.util.Map} of parameters for the
     * device unlocking.
     *
     * @return a key-value pair. The key is the command name. The value is a
     * {@link java.util.Map} command arguments.
     */
    public static Map.Entry<String, Map<String, ?>> unlockCommand() {
        return new AbstractMap.SimpleEntry<>(UNLOCK, ImmutableMap.<String, Object>of());
    }

    /**
     * This method was moved to {@link MobileCommand#hideKeyboardCommand(String, String)}.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>>  lockDeviceCommand() {
        return new AbstractMap.SimpleEntry<>(LOCK, prepareArguments("seconds", 0));
    }

    /**
     * This method forms a {@link java.util.Map} of parameters for the element
     * value replacement. It is used against input elements
     *
     * @param hasIdentityObject an instance which contains an element ID
     * @param value a new value
     * @return a key-value pair. The key is the command name. The value is a
     * {@link java.util.Map} command arguments.
     */
    public static Map.Entry<String, Map<String, ?>>  replaceElementValueCommand(
        HasIdentity hasIdentityObject, String value) {
        String[] parameters = new String[] {"id", "value"};
        Object[] values =
            new Object[] {hasIdentityObject.getId(), value};

        return new AbstractMap.SimpleEntry<>(
                REPLACE_VALUE, prepareArguments(parameters, values));
    }

    public static Map.Entry<String, Map<String, ?>> getSettingsCommand() {
        return new AbstractMap.SimpleEntry<>(GET_SETTINGS, ImmutableMap.<String, Object>of());
    }

    public static Map.Entry<String, Map<String, ?>> setSettingsCommand(Setting setting, Object value) {
        return new AbstractMap.SimpleEntry<>(SET_SETTINGS, prepareArguments("settings",
                prepareArguments(setting.toString(), value)));
    }
}
