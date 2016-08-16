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

import java.util.HashMap;
import java.util.Map;

/**
 * The repository of mobile commands defined in the Mobile JSON
 * wire protocol.
 */
public class MobileCommand {
    //General
    protected static final String RESET = "reset";
    protected static final String GET_STRINGS = "getStrings";
    protected static final String SET_VALUE = "setValue";
    protected static final String PULL_FILE = "pullFile";
    protected static final String PULL_FOLDER = "pullFolder";
    protected static final String HIDE_KEYBOARD = "hideKeyboard";
    protected static final String RUN_APP_IN_BACKGROUND = "runAppInBackground";
    protected static final String PERFORM_TOUCH_ACTION = "performTouchAction";
    protected static final String PERFORM_MULTI_TOUCH = "performMultiTouch";
    protected static final String IS_APP_INSTALLED = "isAppInstalled";
    protected static final String INSTALL_APP = "installApp";
    protected static final String REMOVE_APP = "removeApp";
    protected static final String LAUNCH_APP = "launchApp";
    protected static final String CLOSE_APP = "closeApp";
    protected static final String LOCK = "lock";
    protected static final String COMPLEX_FIND = "complexFind";
    protected static final String GET_SETTINGS = "getSettings";
    protected static final String SET_SETTINGS = "setSettings";
    protected static final String GET_DEVICE_TIME = "getDeviceTime";
    protected static final String GET_SESSION = "getSession";
    //iOS
    protected static final String SHAKE = "shake";
    //Android
    protected static final String CURRENT_ACTIVITY = "currentActivity";
    protected static final String END_TEST_COVERAGE = "endTestCoverage";
    protected static final String GET_NETWORK_CONNECTION = "getNetworkConnection";
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
        result.put(COMPLEX_FIND, postC("/session/:sessionId/appium/app/complex_find"));
        result.put(GET_SETTINGS, getC("/session/:sessionId/appium/settings"));
        result.put(SET_SETTINGS, postC("/session/:sessionId/appium/settings"));
        result.put(GET_DEVICE_TIME, getC("/session/:sessionId/appium/device/system_time"));
        result.put(GET_SESSION,getC("/session/:sessionId/"));
        //iOS
        result.put(SHAKE, postC("/session/:sessionId/appium/device/shake"));
        //Android
        result.put(CURRENT_ACTIVITY,
            getC("/session/:sessionId/appium/device/current_activity"));
        result.put(END_TEST_COVERAGE,
            postC("/session/:sessionId/appium/app/end_test_coverage"));
        result.put(GET_NETWORK_CONNECTION, getC("/session/:sessionId/network_connection"));
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
}
