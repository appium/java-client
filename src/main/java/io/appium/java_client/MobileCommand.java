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
    protected static final String RESET;
    protected static final String GET_STRINGS;
    protected static final String SET_VALUE;
    protected static final String PULL_FILE;
    protected static final String PULL_FOLDER;
    protected static final String RUN_APP_IN_BACKGROUND;
    protected static final String PERFORM_TOUCH_ACTION;
    protected static final String PERFORM_MULTI_TOUCH;
    protected static final String IS_APP_INSTALLED;
    protected static final String INSTALL_APP;
    protected static final String REMOVE_APP;
    protected static final String LAUNCH_APP;
    protected static final String CLOSE_APP;
    protected static final String GET_DEVICE_TIME;
    protected static final String GET_SESSION;

    protected static final String HIDE_KEYBOARD;
    protected static final String LOCK;
    //iOS
    protected static final String SHAKE;
    protected static final String TOUCH_ID;
    //Android
    protected static final String CURRENT_ACTIVITY;
    protected static final String END_TEST_COVERAGE;
    protected static final String GET_DISPLAY_DENSITY;
    protected static final String GET_NETWORK_CONNECTION;
    protected static final String GET_SYSTEM_BARS;
    protected static final String IS_KEYBOARD_SHOWN;
    protected static final String IS_LOCKED;
    protected static final String LONG_PRESS_KEY_CODE;
    protected static final String OPEN_NOTIFICATIONS;
    protected static final String PRESS_KEY_CODE;
    protected static final String PUSH_FILE;
    protected static final String SET_NETWORK_CONNECTION;
    protected static final String START_ACTIVITY;
    protected static final String TOGGLE_LOCATION_SERVICES;
    protected static final String UNLOCK;
    protected static final String REPLACE_VALUE;
    protected static final String GET_SETTINGS;
    protected static final String SET_SETTINGS;

    public static final  Map<String, CommandInfo> commandRepository;

    static {
        RESET = "reset";
        GET_STRINGS = "getStrings";
        SET_VALUE = "setValue";
        PULL_FILE = "pullFile";
        PULL_FOLDER = "pullFolder";
        RUN_APP_IN_BACKGROUND = "runAppInBackground";
        PERFORM_TOUCH_ACTION = "performTouchAction";
        PERFORM_MULTI_TOUCH = "performMultiTouch";
        IS_APP_INSTALLED = "isAppInstalled";
        INSTALL_APP = "installApp";
        REMOVE_APP = "removeApp";
        LAUNCH_APP = "launchApp";
        CLOSE_APP = "closeApp";
        GET_DEVICE_TIME = "getDeviceTime";
        GET_SESSION = "getSession";

        HIDE_KEYBOARD = "hideKeyboard";
        LOCK = "lock";
        SHAKE = "shake";
        TOUCH_ID = "touchId";

        CURRENT_ACTIVITY = "currentActivity";
        END_TEST_COVERAGE = "endTestCoverage";
        GET_DISPLAY_DENSITY = "getDisplayDensity";
        GET_NETWORK_CONNECTION = "getNetworkConnection";
        GET_SYSTEM_BARS = "getSystemBars";
        IS_KEYBOARD_SHOWN = "isKeyboardShown";
        IS_LOCKED = "isLocked";
        LONG_PRESS_KEY_CODE = "longPressKeyCode";
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
        commandRepository.put(IS_APP_INSTALLED, postC("/session/:sessionId/appium/device/app_installed"));
        commandRepository.put(INSTALL_APP, postC("/session/:sessionId/appium/device/install_app"));
        commandRepository.put(REMOVE_APP, postC("/session/:sessionId/appium/device/remove_app"));
        commandRepository.put(LAUNCH_APP, postC("/session/:sessionId/appium/app/launch"));
        commandRepository.put(CLOSE_APP, postC("/session/:sessionId/appium/app/close"));
        commandRepository.put(LOCK, postC("/session/:sessionId/appium/device/lock"));
        commandRepository.put(GET_SETTINGS, getC("/session/:sessionId/appium/settings"));
        commandRepository.put(SET_SETTINGS, postC("/session/:sessionId/appium/settings"));
        commandRepository.put(GET_DEVICE_TIME, getC("/session/:sessionId/appium/device/system_time"));
        commandRepository.put(GET_SESSION,getC("/session/:sessionId/"));
        //iOS
        commandRepository.put(SHAKE, postC("/session/:sessionId/appium/device/shake"));
        commandRepository.put(TOUCH_ID, postC("/session/:sessionId/appium/simulator/touch_id"));
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
        commandRepository. put(REPLACE_VALUE, postC("/session/:sessionId/appium/element/:id/replace_value"));
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
