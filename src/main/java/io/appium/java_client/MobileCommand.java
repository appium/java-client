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

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.http.HttpMethod;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * The repository of mobile commands defined in the Mobile JSON
 * wire protocol.
 */
public class MobileCommand {
    //General
    static final String RESET = "reset";
    static final String GET_STRINGS = "getStrings";
    static final String SET_VALUE = "setValue";
    static final String PULL_FILE = "pullFile";
    static final String PULL_FOLDER = "pullFolder";
    protected static final String HIDE_KEYBOARD = "hideKeyboard";
    static final String RUN_APP_IN_BACKGROUND = "runAppInBackground";
    static final String PERFORM_TOUCH_ACTION = "performTouchAction";
    static final String PERFORM_MULTI_TOUCH = "performMultiTouch";
    static final String IS_APP_INSTALLED = "isAppInstalled";
    static final String INSTALL_APP = "installApp";
    static final String REMOVE_APP = "removeApp";
    static final String LAUNCH_APP = "launchApp";
    static final String CLOSE_APP = "closeApp";
    protected static final String LOCK = "lock";
    static final String GET_DEVICE_TIME = "getDeviceTime";
    static final String GET_SESSION = "getSession";
    //iOS
    protected static final String SHAKE = "shake";
    protected static final String TOUCH_ID = "touchId";
    //Android
    protected static final String CURRENT_ACTIVITY = "currentActivity";
    protected static final String END_TEST_COVERAGE = "endTestCoverage";
    protected static final String GET_DISPLAY_DENSITY = "getDisplayDensity";
    protected static final String GET_NETWORK_CONNECTION = "getNetworkConnection";
    protected static final String GET_SYSTEM_BARS = "getSystemBars";
    protected static final String IS_KEYBOARD_SHOWN = "isKeyboardShown";
    protected static final String IS_LOCKED = "isLocked";
    protected static final String LONG_PRESS_KEY_CODE = "longPressKeyCode";
    protected static final String OPEN_NOTIFICATIONS = "openNotifications";
    protected static final String PRESS_KEY_CODE = "pressKeyCode";
    protected static final String PUSH_FILE = "pushFile";
    protected static final String SET_NETWORK_CONNECTION = "setNetworkConnection";
    protected static final String START_ACTIVITY = "startActivity";
    protected static final String TOGGLE_LOCATION_SERVICES = "toggleLocationServices";
    protected static final String UNLOCK = "unlock";
    protected static final String REPLACE_VALUE = "replaceValue";
    protected static final String GET_SETTINGS = "getSettings";
    protected static final String SET_SETTINGS = "setSettings";

    public static final  Map<String, CommandInfo> commandRepository = createCommandRepository();

    private static Map<String, CommandInfo> createCommandRepository() {
        HashMap<String, CommandInfo> result = new HashMap<String, CommandInfo>();
        result.put(RESET, postC("/session/:sessionId/appium/app/reset"));
        result.put(GET_STRINGS, postC("/session/:sessionId/appium/app/strings"));
        result.put(SET_VALUE, postC("/session/:sessionId/appium/element/:id/value"));
        result.put(PULL_FILE, postC("/session/:sessionId/appium/device/pull_file"));
        result.put(PULL_FOLDER, postC("/session/:sessionId/appium/device/pull_folder"));
        result.put(HIDE_KEYBOARD, postC("/session/:sessionId/appium/device/hide_keyboard"));
        result.put(RUN_APP_IN_BACKGROUND, postC("/session/:sessionId/appium/app/background"));
        result.put(PERFORM_TOUCH_ACTION, postC("/session/:sessionId/touch/perform"));
        result.put(PERFORM_MULTI_TOUCH, postC("/session/:sessionId/touch/multi/perform"));
        result.put(IS_APP_INSTALLED, postC("/session/:sessionId/appium/device/app_installed"));
        result.put(INSTALL_APP, postC("/session/:sessionId/appium/device/install_app"));
        result.put(REMOVE_APP, postC("/session/:sessionId/appium/device/remove_app"));
        result.put(LAUNCH_APP, postC("/session/:sessionId/appium/app/launch"));
        result.put(CLOSE_APP, postC("/session/:sessionId/appium/app/close"));
        result.put(LOCK, postC("/session/:sessionId/appium/device/lock"));
        result.put(GET_SETTINGS, getC("/session/:sessionId/appium/settings"));
        result.put(SET_SETTINGS, postC("/session/:sessionId/appium/settings"));
        result.put(GET_DEVICE_TIME, getC("/session/:sessionId/appium/device/system_time"));
        result.put(GET_SESSION,getC("/session/:sessionId/"));
        //iOS
        result.put(SHAKE, postC("/session/:sessionId/appium/device/shake"));
        result.put(TOUCH_ID, postC("/session/:sessionId/appium/simulator/touch_id"));
        //Android
        result.put(CURRENT_ACTIVITY,
            getC("/session/:sessionId/appium/device/current_activity"));
        result.put(END_TEST_COVERAGE,
            postC("/session/:sessionId/appium/app/end_test_coverage"));
        result.put(GET_DISPLAY_DENSITY, getC("/session/:sessionId/appium/device/display_density"));
        result.put(GET_NETWORK_CONNECTION, getC("/session/:sessionId/network_connection"));
        result.put(GET_SYSTEM_BARS, getC("/session/:sessionId/appium/device/system_bars"));
        result.put(IS_KEYBOARD_SHOWN, getC("/session/:sessionId/appium/device/is_keyboard_shown"));
        result.put(IS_LOCKED, postC("/session/:sessionId/appium/device/is_locked"));
        result.put(LONG_PRESS_KEY_CODE,
            postC("/session/:sessionId/appium/device/long_press_keycode"));
        result.put(OPEN_NOTIFICATIONS,
            postC("/session/:sessionId/appium/device/open_notifications"));
        result.put(PRESS_KEY_CODE,
            postC("/session/:sessionId/appium/device/press_keycode"));
        result.put(PUSH_FILE, postC("/session/:sessionId/appium/device/push_file"));
        result.put(SET_NETWORK_CONNECTION,
            postC("/session/:sessionId/network_connection"));
        result.put(START_ACTIVITY,
            postC("/session/:sessionId/appium/device/start_activity"));
        result.put(TOGGLE_LOCATION_SERVICES,
            postC("/session/:sessionId/appium/device/toggle_location_services"));
        result.put(UNLOCK, postC("/session/:sessionId/appium/device/unlock"));
        result.put(REPLACE_VALUE, postC("/session/:sessionId/appium/element/:id/replace_value"));
        return result;
    }

    /**
     * This methods forms GET commands.
     *
     * @param url is the command URL
     * @return an instance of {@link org.openqa.selenium.remote.CommandInfo}
     */
    public static CommandInfo getC(String url) {
        return new CommandInfo(url, HttpMethod.GET);
    }

    /**
     * This methods forms POST commands.
     *
     * @param url is the command URL
     * @return an instance of {@link org.openqa.selenium.remote.CommandInfo}
     */
    public static CommandInfo postC(String url) {
        return new CommandInfo(url, HttpMethod.POST);
    }

    /**
     * This methods forms DELETE commands.
     *
     * @param url is the command URL
     * @return an instance of {@link org.openqa.selenium.remote.CommandInfo}
     */
    public static CommandInfo deleteC(String url) {
        return new CommandInfo(url, HttpMethod.DELETE);
    }

    /**
     * This method forms a {@link java.util.Map} of parameters for the
     * keyboard hiding.
     *
     * @param keyName The button pressed by the mobile driver to attempt hiding the
     *                keyboard.
     * @return a key-value pair. The key is the command name. The value is a
     * {@link java.util.Map} command arguments.
     */
    public static Map.Entry<String, Map<String, ?>> hideKeyboardCommand(String keyName) {
        return new AbstractMap.SimpleEntry<>(
                HIDE_KEYBOARD, prepareArguments("keyName", keyName));
    }

    /**
     * This method forms a {@link java.util.Map} of parameters for the
     * keyboard hiding.
     *
     * @param strategy HideKeyboardStrategy.
     * @param keyName  a String, representing the text displayed on the button of the
     *                 keyboard you want to press. For example: "Done".
     * @return a key-value pair. The key is the command name. The value is a
     * {@link java.util.Map} command arguments.
     */
    public static Map.Entry<String, Map<String, ?>> hideKeyboardCommand(String strategy,
                                                                        String keyName) {
        String[] parameters = new String[] {"strategy", "key"};
        Object[] values = new Object[] {strategy, keyName};
        return new AbstractMap.SimpleEntry<>(
                HIDE_KEYBOARD, prepareArguments(parameters, values));
    }

    /**
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
     * @param params is the array with parameter names.
     * @param values is the array with parameter values.
     * @return built {@link ImmutableMap}.
     */
    public static ImmutableMap<String, Object> prepareArguments(String[] params,
                                                                   Object[] values) {
        ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        for (int i = 0; i < params.length; i++) {
            if (!StringUtils.isBlank(params[i]) && (values[i] != null)) {
                builder.put(params[i], values[i]);
            }
        }
        return builder.build();
    }

    /**
     * This method forms a {@link java.util.Map} of parameters for the
     * key event invocation.
     *
     * @param key code for the key pressed on the device.
     * @return a key-value pair. The key is the command name. The value is a
     * {@link java.util.Map} command arguments.
     */
    public static Map.Entry<String, Map<String, ?>> pressKeyCodeCommand(int key) {
        return new AbstractMap.SimpleEntry<>(
                PRESS_KEY_CODE, prepareArguments("keycode", key));
    }

    /**
     * This method forms a {@link java.util.Map} of parameters for the
     * key event invocation.
     *
     * @param key       code for the key pressed on the Android device.
     * @param metastate metastate for the keypress.
     * @return a key-value pair. The key is the command name. The value is a
     * {@link java.util.Map} command arguments.
     */
    public static Map.Entry<String, Map<String, ?>> pressKeyCodeCommand(int key,
                                                                        Integer metastate) {
        String[] parameters = new String[] {"keycode", "metastate"};
        Object[] values = new Object[] {key, metastate};
        return new AbstractMap.SimpleEntry<>(
                PRESS_KEY_CODE, prepareArguments(parameters, values));
    }

    /**
     * This method forms a {@link java.util.Map} of parameters for the
     * long key event invocation.
     *
     * @param key code for the long key pressed on the device.
     * @return a key-value pair. The key is the command name. The value is a
     * {@link java.util.Map} command arguments.
     */
    public static Map.Entry<String, Map<String, ?>> longPressKeyCodeCommand(int key) {
        return new AbstractMap.SimpleEntry<>(
                LONG_PRESS_KEY_CODE, prepareArguments("keycode", key));
    }

    /**
     * This method forms a {@link java.util.Map} of parameters for the
     * long key event invocation.
     *
     * @param key       code for the long key pressed on the Android device.
     * @param metastate metastate for the long key press.
     * @return a key-value pair. The key is the command name. The value is a
     * {@link java.util.Map} command arguments.
     */
    public static Map.Entry<String, Map<String, ?>> longPressKeyCodeCommand(int key,
                                                                            Integer metastate) {
        String[] parameters = new String[] {"keycode", "metastate"};
        Object[] values = new Object[] {key, metastate};
        return new AbstractMap.SimpleEntry<>(
                LONG_PRESS_KEY_CODE, prepareArguments(parameters, values));
    }

    /**
     * This method forms a {@link java.util.Map} of parameters for the
     * device locking.
     *
     * @param seconds seconds number of seconds to lock the screen for
     * @return  a key-value pair. The key is the command name. The value is a
     * {@link java.util.Map} command arguments.
     */
    public static Map.Entry<String, Map<String, ?>>  lockDeviceCommand(int seconds) {
        return new AbstractMap.SimpleEntry<>(
                LOCK, prepareArguments("seconds", seconds));
    }
}
