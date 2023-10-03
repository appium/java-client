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
import io.appium.java_client.imagecomparison.BaseComparisonOptions;
import io.appium.java_client.imagecomparison.ComparisonMode;
import io.appium.java_client.screenrecording.BaseStartScreenRecordingOptions;
import io.appium.java_client.screenrecording.BaseStopScreenRecordingOptions;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.http.HttpMethod;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * The repository of mobile commands defined in the Mobile JSON
 * wire protocol.
 * Most of these commands are platform-specific obsolete things and should eventually be replaced with
 * calls to corresponding `mobile:` extensions, so we don't abuse non-w3c APIs
 */
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "checkstyle:ConstantName"})
public class MobileCommand {
    //General
    @Deprecated
    protected static final String RESET;
    @Deprecated
    protected static final String GET_STRINGS;
    @Deprecated
    public static final String SET_VALUE;
    @Deprecated
    protected static final String PULL_FILE;
    @Deprecated
    protected static final String PULL_FOLDER;
    @Deprecated
    public static final String RUN_APP_IN_BACKGROUND;
    @Deprecated
    protected static final String PERFORM_TOUCH_ACTION;
    @Deprecated
    protected static final String PERFORM_MULTI_TOUCH;
    @Deprecated
    public static final String LAUNCH_APP;
    @Deprecated
    public static final String CLOSE_APP;
    @Deprecated
    protected static final String GET_DEVICE_TIME;
    @Deprecated
    protected static final String GET_SESSION;
    protected static final String LOG_EVENT;
    protected static final String GET_EVENTS;

    //region Applications Management
    @Deprecated
    protected static final String IS_APP_INSTALLED;
    @Deprecated
    protected static final String INSTALL_APP;
    @Deprecated
    protected static final String ACTIVATE_APP;
    @Deprecated
    protected static final String QUERY_APP_STATE;
    @Deprecated
    protected static final String TERMINATE_APP;
    @Deprecated
    protected static final String REMOVE_APP;
    //endregion

    //region Clipboard
    @Deprecated
    public static final String GET_CLIPBOARD;
    @Deprecated
    public static final String SET_CLIPBOARD;
    //endregion

    @Deprecated
    protected static final String GET_PERFORMANCE_DATA;
    @Deprecated
    protected static final String GET_SUPPORTED_PERFORMANCE_DATA_TYPES;

    @Deprecated
    public static final String START_RECORDING_SCREEN;
    @Deprecated
    public static final String STOP_RECORDING_SCREEN;

    @Deprecated
    protected static final String HIDE_KEYBOARD;
    @Deprecated
    protected static final String LOCK;
    //iOS
    @Deprecated
    protected static final String SHAKE;
    @Deprecated
    protected static final String TOUCH_ID;
    @Deprecated
    protected static final String TOUCH_ID_ENROLLMENT;
    //Android
    @Deprecated
    public static final String CURRENT_ACTIVITY;
    @Deprecated
    protected static final String END_TEST_COVERAGE;
    @Deprecated
    protected static final String GET_DISPLAY_DENSITY;
    @Deprecated
    protected static final String GET_NETWORK_CONNECTION;
    @Deprecated
    protected static final String GET_SYSTEM_BARS;
    @Deprecated
    protected static final String IS_KEYBOARD_SHOWN;
    @Deprecated
    protected static final String IS_LOCKED;
    @Deprecated
    public static final String LONG_PRESS_KEY_CODE;
    @Deprecated
    protected static final String FINGER_PRINT;
    @Deprecated
    protected static final String OPEN_NOTIFICATIONS;
    @Deprecated
    public static final String PRESS_KEY_CODE;
    @Deprecated
    protected static final String PUSH_FILE;
    @Deprecated
    protected static final String SET_NETWORK_CONNECTION;
    @Deprecated
    protected static final String START_ACTIVITY;
    @Deprecated
    protected static final String TOGGLE_LOCATION_SERVICES;
    @Deprecated
    protected static final String UNLOCK;
    @Deprecated
    public static final String REPLACE_VALUE;
    protected static final String GET_SETTINGS;
    @Deprecated
    protected static final String SET_SETTINGS;
    @Deprecated
    public static final String GET_CURRENT_PACKAGE;
    @Deprecated
    public static final String SEND_SMS;
    @Deprecated
    public static final String GSM_CALL;
    @Deprecated
    public static final String GSM_SIGNAL;
    @Deprecated
    public static final String GSM_VOICE;
    @Deprecated
    public static final String NETWORK_SPEED;
    @Deprecated
    public static final String POWER_CAPACITY;
    @Deprecated
    public static final String POWER_AC_STATE;
    @Deprecated
    protected static final String TOGGLE_WIFI;
    @Deprecated
    protected static final String TOGGLE_AIRPLANE_MODE;
    @Deprecated
    protected static final String TOGGLE_DATA;
    protected static final String COMPARE_IMAGES;
    protected static final String EXECUTE_DRIVER_SCRIPT;
    @Deprecated
    protected static final String GET_ALLSESSION;
    protected static final String EXECUTE_GOOGLE_CDP_COMMAND;

    public static final Map<String, CommandInfo> commandRepository;

    static {
        RESET = "reset";
        GET_STRINGS = "getStrings";
        SET_VALUE = "setValue";
        PULL_FILE = "pullFile";
        PULL_FOLDER = "pullFolder";
        RUN_APP_IN_BACKGROUND = "runAppInBackground";
        PERFORM_TOUCH_ACTION = "performTouchAction";
        PERFORM_MULTI_TOUCH = "performMultiTouch";
        LAUNCH_APP = "launchApp";
        CLOSE_APP = "closeApp";
        GET_DEVICE_TIME = "getDeviceTime";
        GET_SESSION = "getSession";
        LOG_EVENT = "logCustomEvent";
        GET_EVENTS = "getLogEvents";

        //region Applications Management
        IS_APP_INSTALLED = "isAppInstalled";
        QUERY_APP_STATE = "queryAppState";
        TERMINATE_APP = "terminateApp";
        ACTIVATE_APP = "activateApp";
        REMOVE_APP = "removeApp";
        INSTALL_APP = "installApp";
        //endregion

        //region Clipboard
        SET_CLIPBOARD = "setClipboard";
        GET_CLIPBOARD = "getClipboard";
        //endregion

        GET_PERFORMANCE_DATA = "getPerformanceData";
        GET_SUPPORTED_PERFORMANCE_DATA_TYPES = "getSuppportedPerformanceDataTypes";

        START_RECORDING_SCREEN = "startRecordingScreen";
        STOP_RECORDING_SCREEN = "stopRecordingScreen";

        HIDE_KEYBOARD = "hideKeyboard";
        LOCK = "lock";
        SHAKE = "shake";
        TOUCH_ID = "touchId";
        TOUCH_ID_ENROLLMENT = "toggleEnrollTouchId";

        CURRENT_ACTIVITY = "currentActivity";
        END_TEST_COVERAGE = "endTestCoverage";
        GET_DISPLAY_DENSITY = "getDisplayDensity";
        GET_NETWORK_CONNECTION = "getNetworkConnection";
        GET_SYSTEM_BARS = "getSystemBars";
        IS_KEYBOARD_SHOWN = "isKeyboardShown";
        IS_LOCKED = "isLocked";
        LONG_PRESS_KEY_CODE = "longPressKeyCode";
        FINGER_PRINT = "fingerPrint";
        OPEN_NOTIFICATIONS = "openNotifications";
        PRESS_KEY_CODE = "pressKeyCode";
        PUSH_FILE = "pushFile";
        SET_NETWORK_CONNECTION = "setNetworkConnection";
        START_ACTIVITY = "startActivity";
        TOGGLE_LOCATION_SERVICES = "toggleLocationServices";
        UNLOCK = "unlock";
        REPLACE_VALUE = "replaceValue";
        GET_SETTINGS = "getSettings";
        SET_SETTINGS = "setSettings";
        GET_CURRENT_PACKAGE = "getCurrentPackage";
        SEND_SMS = "sendSMS";
        GSM_CALL = "gsmCall";
        GSM_SIGNAL = "gsmSignal";
        GSM_VOICE = "gsmVoice";
        NETWORK_SPEED = "networkSpeed";
        POWER_CAPACITY = "powerCapacity";
        POWER_AC_STATE = "powerAC";
        TOGGLE_WIFI = "toggleWiFi";
        TOGGLE_AIRPLANE_MODE = "toggleFlightMode";
        TOGGLE_DATA = "toggleData";
        COMPARE_IMAGES = "compareImages";
        EXECUTE_DRIVER_SCRIPT = "executeDriverScript";
        GET_ALLSESSION = "getAllSessions";
        EXECUTE_GOOGLE_CDP_COMMAND = "executeCdp";

        commandRepository = new HashMap<>();
        commandRepository.put(RESET, postC("/session/:sessionId/appium/app/reset"));
        commandRepository.put(GET_STRINGS, postC("/session/:sessionId/appium/app/strings"));
        commandRepository.put(SET_VALUE, postC("/session/:sessionId/appium/element/:id/value"));
        commandRepository.put(PULL_FILE, postC("/session/:sessionId/appium/device/pull_file"));
        commandRepository.put(PULL_FOLDER, postC("/session/:sessionId/appium/device/pull_folder"));
        commandRepository.put(HIDE_KEYBOARD, postC("/session/:sessionId/appium/device/hide_keyboard"));
        commandRepository.put(RUN_APP_IN_BACKGROUND, postC("/session/:sessionId/appium/app/background"));
        commandRepository.put(PERFORM_TOUCH_ACTION, postC("/session/:sessionId/touch/perform"));
        commandRepository.put(PERFORM_MULTI_TOUCH, postC("/session/:sessionId/touch/multi/perform"));
        commandRepository.put(LAUNCH_APP, postC("/session/:sessionId/appium/app/launch"));
        commandRepository.put(CLOSE_APP, postC("/session/:sessionId/appium/app/close"));
        commandRepository.put(LOCK, postC("/session/:sessionId/appium/device/lock"));
        commandRepository.put(GET_SETTINGS, getC("/session/:sessionId/appium/settings"));
        commandRepository.put(SET_SETTINGS, postC("/session/:sessionId/appium/settings"));
        commandRepository.put(GET_DEVICE_TIME, getC("/session/:sessionId/appium/device/system_time"));
        commandRepository.put(GET_SESSION, getC("/session/:sessionId/"));
        commandRepository.put(GET_SUPPORTED_PERFORMANCE_DATA_TYPES,
                postC("/session/:sessionId/appium/performanceData/types"));
        commandRepository.put(GET_PERFORMANCE_DATA,
                postC("/session/:sessionId/appium/getPerformanceData"));
        commandRepository.put(START_RECORDING_SCREEN,
                postC("/session/:sessionId/appium/start_recording_screen"));
        commandRepository.put(STOP_RECORDING_SCREEN,
                postC("/session/:sessionId/appium/stop_recording_screen"));
        commandRepository.put(GET_EVENTS,
                postC("/session/:sessionId/appium/events"));
        commandRepository.put(LOG_EVENT,
                postC("/session/:sessionId/appium/log_event"));

        //region Applications Management
        commandRepository.put(IS_APP_INSTALLED, postC("/session/:sessionId/appium/device/app_installed"));
        commandRepository.put(INSTALL_APP, postC("/session/:sessionId/appium/device/install_app"));
        commandRepository.put(ACTIVATE_APP, postC("/session/:sessionId/appium/device/activate_app"));
        commandRepository.put(REMOVE_APP, postC("/session/:sessionId/appium/device/remove_app"));
        commandRepository.put(TERMINATE_APP, postC("/session/:sessionId/appium/device/terminate_app"));
        commandRepository.put(QUERY_APP_STATE, postC("/session/:sessionId/appium/device/app_state"));
        //endregion

        //region Clipboard
        commandRepository.put(GET_CLIPBOARD, postC("/session/:sessionId/appium/device/get_clipboard"));
        commandRepository.put(SET_CLIPBOARD, postC("/session/:sessionId/appium/device/set_clipboard"));
        //endregion

        //iOS
        commandRepository.put(SHAKE, postC("/session/:sessionId/appium/device/shake"));
        commandRepository.put(TOUCH_ID, postC("/session/:sessionId/appium/simulator/touch_id"));
        commandRepository.put(TOUCH_ID_ENROLLMENT,
                postC("/session/:sessionId/appium/simulator/toggle_touch_id_enrollment"));
        //Android
        commandRepository.put(CURRENT_ACTIVITY,
                getC("/session/:sessionId/appium/device/current_activity"));
        commandRepository.put(END_TEST_COVERAGE,
                postC("/session/:sessionId/appium/app/end_test_coverage"));
        commandRepository.put(GET_DISPLAY_DENSITY, getC("/session/:sessionId/appium/device/display_density"));
        commandRepository.put(GET_NETWORK_CONNECTION, getC("/session/:sessionId/network_connection"));
        commandRepository.put(GET_SYSTEM_BARS, getC("/session/:sessionId/appium/device/system_bars"));
        commandRepository.put(IS_KEYBOARD_SHOWN, getC("/session/:sessionId/appium/device/is_keyboard_shown"));
        commandRepository.put(IS_LOCKED, postC("/session/:sessionId/appium/device/is_locked"));
        commandRepository.put(LONG_PRESS_KEY_CODE,
                postC("/session/:sessionId/appium/device/long_press_keycode"));
        commandRepository.put(FINGER_PRINT, postC("/session/:sessionId/appium/device/finger_print"));
        commandRepository.put(OPEN_NOTIFICATIONS,
                postC("/session/:sessionId/appium/device/open_notifications"));
        commandRepository.put(PRESS_KEY_CODE,
                postC("/session/:sessionId/appium/device/press_keycode"));
        commandRepository.put(PUSH_FILE, postC("/session/:sessionId/appium/device/push_file"));
        commandRepository.put(SET_NETWORK_CONNECTION,
                postC("/session/:sessionId/network_connection"));
        commandRepository.put(START_ACTIVITY,
                postC("/session/:sessionId/appium/device/start_activity"));
        commandRepository.put(TOGGLE_LOCATION_SERVICES,
                postC("/session/:sessionId/appium/device/toggle_location_services"));
        commandRepository.put(UNLOCK, postC("/session/:sessionId/appium/device/unlock"));
        commandRepository.put(REPLACE_VALUE, postC("/session/:sessionId/appium/element/:id/replace_value"));
        commandRepository.put(GET_CURRENT_PACKAGE, getC("/session/:sessionId/appium/device/current_package"));
        commandRepository.put(SEND_SMS, postC("/session/:sessionId/appium/device/send_sms"));
        commandRepository.put(GSM_CALL, postC("/session/:sessionId/appium/device/gsm_call"));
        commandRepository.put(GSM_SIGNAL, postC("/session/:sessionId/appium/device/gsm_signal"));
        commandRepository.put(GSM_VOICE, postC("/session/:sessionId/appium/device/gsm_voice"));
        commandRepository.put(NETWORK_SPEED, postC("/session/:sessionId/appium/device/network_speed"));
        commandRepository.put(POWER_CAPACITY, postC("/session/:sessionId/appium/device/power_capacity"));
        commandRepository.put(POWER_AC_STATE, postC("/session/:sessionId/appium/device/power_ac"));
        commandRepository.put(TOGGLE_WIFI, postC("/session/:sessionId/appium/device/toggle_wifi"));
        commandRepository.put(TOGGLE_AIRPLANE_MODE, postC("/session/:sessionId/appium/device/toggle_airplane_mode"));
        commandRepository.put(TOGGLE_DATA, postC("/session/:sessionId/appium/device/toggle_data"));
        commandRepository.put(COMPARE_IMAGES, postC("/session/:sessionId/appium/compare_images"));
        commandRepository.put(EXECUTE_DRIVER_SCRIPT, postC("/session/:sessionId/appium/execute_driver"));
        commandRepository.put(GET_ALLSESSION, getC("/sessions"));
        commandRepository.put(EXECUTE_GOOGLE_CDP_COMMAND, postC("/session/:sessionId/goog/cdp/execute"));
    }

    /**
     * This methods forms GET commands.
     *
     * @param url is the command URL
     * @return an instance of {@link org.openqa.selenium.remote.CommandInfo}
     */
    public static AppiumCommandInfo getC(String url) {
        return new AppiumCommandInfo(url, HttpMethod.GET);
    }

    /**
     * This methods forms POST commands.
     *
     * @param url is the command URL
     * @return an instance of {@link org.openqa.selenium.remote.CommandInfo}
     */
    public static AppiumCommandInfo postC(String url) {
        return new AppiumCommandInfo(url, HttpMethod.POST);
    }

    /**
     * This methods forms DELETE commands.
     *
     * @param url is the command URL
     * @return an instance of {@link org.openqa.selenium.remote.CommandInfo}
     */
    public static AppiumCommandInfo deleteC(String url) {
        return new AppiumCommandInfo(url, HttpMethod.DELETE);
    }

    /**
     * This method forms a {@link Map} of parameters for the keyboard hiding.
     *
     * @param keyName The button pressed by the mobile driver to attempt hiding the
     *                keyboard.
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     * @deprecated This helper is deprecated and will be removed in future versions.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> hideKeyboardCommand(String keyName) {
        return new AbstractMap.SimpleEntry<>(
                HIDE_KEYBOARD, prepareArguments("keyName", keyName));
    }

    /**
     * This method forms a {@link Map} of parameters for the keyboard hiding.
     *
     * @param strategy HideKeyboardStrategy.
     * @param keyName  a String, representing the text displayed on the button of the
     *                 keyboard you want to press. For example: "Done".
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     * @deprecated This helper is deprecated and will be removed in future versions.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> hideKeyboardCommand(String strategy,
                                                                        String keyName) {
        String[] parameters = new String[]{"strategy", "key"};
        Object[] values = new Object[]{strategy, keyName};
        return new AbstractMap.SimpleEntry<>(
                HIDE_KEYBOARD, prepareArguments(parameters, values));
    }

    /**
     * Prepares single argument.
     *
     * @param param is a parameter name.
     * @param value is the parameter value.
     * @return built {@link ImmutableMap}.
     */
    public static ImmutableMap<String, Object> prepareArguments(String param,
                                                                Object value) {
        ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put(param, value);
        return builder.build();
    }

    /**
     * Prepares collection of arguments.
     *
     * @param params is the array with parameter names.
     * @param values is the array with parameter values.
     * @return built {@link ImmutableMap}.
     */
    public static ImmutableMap<String, Object> prepareArguments(String[] params,
                                                                Object[] values) {
        ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        for (int i = 0; i < params.length; i++) {
            if (!StringUtils.isBlank(params[i]) && values[i] != null) {
                builder.put(params[i], values[i]);
            }
        }
        return builder.build();
    }

    /**
     * This method forms a {@link Map} of parameters for the key event invocation.
     *
     * @param key code for the key pressed on the device.
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     * @deprecated This helper is deprecated and will be removed in future versions.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> pressKeyCodeCommand(int key) {
        return new AbstractMap.SimpleEntry<>(
                PRESS_KEY_CODE, prepareArguments("keycode", key));
    }

    /**
     * This method forms a {@link Map} of parameters for the key event invocation.
     *
     * @param key       code for the key pressed on the Android device.
     * @param metastate metastate for the keypress.
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     * @deprecated This helper is deprecated and will be removed in future versions.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> pressKeyCodeCommand(int key,
                                                                        Integer metastate) {
        String[] parameters = new String[]{"keycode", "metastate"};
        Object[] values = new Object[]{key, metastate};
        return new AbstractMap.SimpleEntry<>(
                PRESS_KEY_CODE, prepareArguments(parameters, values));
    }

    /**
     * This method forms a {@link Map} of parameters for the long key event invocation.
     *
     * @param key code for the long key pressed on the device.
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     * @deprecated This helper is deprecated and will be removed in future versions.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> longPressKeyCodeCommand(int key) {
        return new AbstractMap.SimpleEntry<>(
                LONG_PRESS_KEY_CODE, prepareArguments("keycode", key));
    }

    /**
     * This method forms a {@link Map} of parameters for the long key event invocation.
     *
     * @param key       code for the long key pressed on the Android device.
     * @param metastate metastate for the long key press.
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     * @deprecated This helper is deprecated and will be removed in future versions.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> longPressKeyCodeCommand(int key,
                                                                            Integer metastate) {
        String[] parameters = new String[]{"keycode", "metastate"};
        Object[] values = new Object[]{key, metastate};
        return new AbstractMap.SimpleEntry<>(
                LONG_PRESS_KEY_CODE, prepareArguments(parameters, values));
    }

    /**
     * This method forms a {@link Map} of parameters for the device locking.
     *
     * @param duration for how long to lock the screen for. Minimum time resolution is one second
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     * @deprecated This helper is deprecated and will be removed in future versions.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> lockDeviceCommand(Duration duration) {
        return new AbstractMap.SimpleEntry<>(
                LOCK, prepareArguments("seconds", duration.getSeconds()));
    }

    /**
     * This method forms a {@link Map} of parameters for the device unlocking.
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     * @deprecated This helper is deprecated and will be removed in future versions.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> unlockDeviceCommand() {
        return new AbstractMap.SimpleEntry<>(UNLOCK, ImmutableMap.of());
    }

    /**
     * This method forms a {@link Map} of parameters for the device locked query.
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     * @deprecated This helper is deprecated and will be removed in future versions.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> getIsDeviceLockedCommand() {
        return new AbstractMap.SimpleEntry<>(IS_LOCKED, ImmutableMap.of());
    }

    public static Map.Entry<String, Map<String, ?>> getSettingsCommand() {
        return new AbstractMap.SimpleEntry<>(GET_SETTINGS, ImmutableMap.of());
    }

    public static Map.Entry<String, Map<String, ?>> setSettingsCommand(String setting, Object value) {
        return setSettingsCommand(prepareArguments(setting, value));
    }

    public static Map.Entry<String, Map<String, ?>> setSettingsCommand(Map<String, Object> settings) {
        return new AbstractMap.SimpleEntry<>(SET_SETTINGS, prepareArguments("settings", settings));
    }

    /**
     * This method forms a {@link Map} of parameters for the file pushing.
     *
     * @param remotePath Path to file to write data to on remote device
     * @param base64Data Base64 encoded byte array of data to write to remote device
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     * @deprecated This helper is deprecated and will be removed in future versions.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> pushFileCommand(String remotePath, byte[] base64Data) {
        String[] parameters = new String[]{"path", "data"};
        Object[] values = new Object[]{remotePath, new String(base64Data, StandardCharsets.UTF_8)};
        return new AbstractMap.SimpleEntry<>(PUSH_FILE, prepareArguments(parameters, values));
    }

    public static Map.Entry<String, Map<String, ?>> startRecordingScreenCommand(BaseStartScreenRecordingOptions opts) {
        return new AbstractMap.SimpleEntry<>(START_RECORDING_SCREEN,
                prepareArguments("options", opts.build()));
    }

    public static Map.Entry<String, Map<String, ?>> stopRecordingScreenCommand(BaseStopScreenRecordingOptions opts) {
        return new AbstractMap.SimpleEntry<>(STOP_RECORDING_SCREEN,
                prepareArguments("options", opts.build()));
    }

    /**
     * Forms a {@link Map} of parameters for images comparison.
     *
     * @param mode one of possible comparison modes
     * @param img1Data base64-encoded data of the first image
     * @param img2Data base64-encoded data of the second image
     * @param options comparison options
     * @return key-value pairs
     */
    public static Map.Entry<String, Map<String, ?>> compareImagesCommand(ComparisonMode mode,
                                                                         byte[] img1Data, byte[] img2Data,
                                                                         @Nullable BaseComparisonOptions options) {
        String[] parameters = options == null
                ? new String[]{"mode", "firstImage", "secondImage"}
                : new String[]{"mode", "firstImage", "secondImage", "options"};
        Object[] values = options == null
                ? new Object[]{mode.toString(), new String(img1Data, StandardCharsets.UTF_8),
                               new String(img2Data, StandardCharsets.UTF_8)}
                : new Object[]{mode.toString(), new String(img1Data, StandardCharsets.UTF_8),
                               new String(img2Data, StandardCharsets.UTF_8), options.build()};
        return new AbstractMap.SimpleEntry<>(COMPARE_IMAGES, prepareArguments(parameters, values));
    }

    /**
     * This method forms a {@link Map} of parameters for the checking of the keyboard state (is it shown or not).
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     * @deprecated This helper is deprecated and will be removed in future versions.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> isKeyboardShownCommand() {
        return new AbstractMap.SimpleEntry<>(IS_KEYBOARD_SHOWN, ImmutableMap.of());
    }
}
