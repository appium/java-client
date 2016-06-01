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

import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.http.HttpMethod;

import java.util.Map;

/**
 * The repository of mobile commands defined in the Mobile JSON
 * wire protocol.
 */
public class MobileCommand {

    public static final String RESET = "reset";
    public static final String GET_STRINGS = "getStrings";
    public static final String PRESS_KEY_CODE = "pressKeyCode";
    public static final String LONG_PRESS_KEY_CODE = "longPressKeyCode";
    public static final String CURRENT_ACTIVITY = "currentActivity";
    public static final String SET_VALUE = "setValue";
    public static final String REPLACE_VALUE = "replaceValue";
    public static final String PULL_FILE = "pullFile";
    public static final String PUSH_FILE = "pushFile";
    public static final String PULL_FOLDER = "pullFolder";
    public static final String HIDE_KEYBOARD = "hideKeyboard";
    public static final String RUN_APP_IN_BACKGROUND = "runAppInBackground";
    public static final String PERFORM_TOUCH_ACTION = "performTouchAction";
    public static final String PERFORM_MULTI_TOUCH = "performMultiTouch";
    public static final String IS_APP_INSTALLED = "isAppInstalled";
    public static final String INSTALL_APP = "installApp";
    public static final String REMOVE_APP = "removeApp";
    public static final String LAUNCH_APP = "launchApp";
    public static final String CLOSE_APP = "closeApp";
    public static final String END_TEST_COVERAGE = "endTestCoverage";
    public static final String LOCK = "lock";
    public static final String IS_LOCKED = "isLocked";
    public static final String SHAKE = "shake";
    public static final String COMPLEX_FIND = "complexFind";
    public static final String OPEN_NOTIFICATIONS = "openNotifications";
    public static final String GET_NETWORK_CONNECTION = "getNetworkConnection";
    public static final String SET_NETWORK_CONNECTION = "setNetworkConnection";
    public static final String GET_SETTINGS = "getSettings";
    public static final String SET_SETTINGS = "setSettings";
    public static final String START_ACTIVITY = "startActivity";
    public static final String TOGGLE_LOCATION_SERVICES = "toggleLocationServices";
    public static final String GET_DEVICE_TIME = "getDeviceTime";
    public static final String UNLOCK = "unlock";
    public static final  Map<String, CommandInfo> commandRepository = getMobileCommands();

    public static CommandInfo getC(String url) {
        return new CommandInfo(url, HttpMethod.GET);
    }

    public static CommandInfo postC(String url) {
        return new CommandInfo(url, HttpMethod.POST);
    }

    protected static ImmutableMap.Builder<String, CommandInfo> getCommandsBuilder() {

        ImmutableMap.Builder<String, CommandInfo> builder = ImmutableMap.builder();
        builder.put(RESET, postC("/session/:sessionId/appium/app/reset"))
            .put(GET_STRINGS, postC("/session/:sessionId/appium/app/strings"))
            .put(PRESS_KEY_CODE, postC("/session/:sessionId/appium/device/press_keycode"))
            .put(LONG_PRESS_KEY_CODE, postC("/session/:sessionId/appium/device/long_press_keycode"))
            .put(CURRENT_ACTIVITY, getC("/session/:sessionId/appium/device/current_activity"))
            .put(SET_VALUE, postC("/session/:sessionId/appium/element/:id/value"))
            .put(REPLACE_VALUE, postC("/session/:sessionId/appium/element/:id/replace_value"))
            .put(PULL_FILE, postC("/session/:sessionId/appium/device/pull_file"))
            .put(PULL_FOLDER, postC("/session/:sessionId/appium/device/pull_folder"))
            .put(HIDE_KEYBOARD, postC("/session/:sessionId/appium/device/hide_keyboard"))
            .put(PUSH_FILE, postC("/session/:sessionId/appium/device/push_file"))
            .put(RUN_APP_IN_BACKGROUND, postC("/session/:sessionId/appium/app/background"))
            .put(PERFORM_TOUCH_ACTION, postC("/session/:sessionId/touch/perform"))
            .put(PERFORM_MULTI_TOUCH, postC("/session/:sessionId/touch/multi/perform"))
            .put(IS_APP_INSTALLED, postC("/session/:sessionId/appium/device/app_installed"))
            .put(INSTALL_APP, postC("/session/:sessionId/appium/device/install_app"))
            .put(REMOVE_APP, postC("/session/:sessionId/appium/device/remove_app"))
            .put(LAUNCH_APP, postC("/session/:sessionId/appium/app/launch"))
            .put(CLOSE_APP, postC("/session/:sessionId/appium/app/close"))
            .put(END_TEST_COVERAGE, postC("/session/:sessionId/appium/app/end_test_coverage"))
            .put(LOCK, postC("/session/:sessionId/appium/device/lock"))
            .put(IS_LOCKED, postC("/session/:sessionId/appium/device/is_locked"))
            .put(SHAKE, postC("/session/:sessionId/appium/device/shake"))
            .put(COMPLEX_FIND, postC("/session/:sessionId/appium/app/complex_find"))
            .put(OPEN_NOTIFICATIONS, postC("/session/:sessionId/appium/device/open_notifications"))
            .put(GET_NETWORK_CONNECTION, getC("/session/:sessionId/network_connection"))
            .put(SET_NETWORK_CONNECTION, postC("/session/:sessionId/network_connection"))
            .put(GET_SETTINGS, getC("/session/:sessionId/appium/settings"))
            .put(SET_SETTINGS, postC("/session/:sessionId/appium/settings"))
            .put(START_ACTIVITY, postC("/session/:sessionId/appium/device/start_activity"))
            .put(TOGGLE_LOCATION_SERVICES,
                postC("/session/:sessionId/appium/device/toggle_location_services"))
            .put(GET_DEVICE_TIME, getC("/session/:sessionId/appium/device/system_time"))
            .put(UNLOCK, postC("/session/:sessionId/appium/device/unlock"));

        return builder;
    }
    
    public static Map<String, CommandInfo> getMobileCommands() {
        if (commandRepository != null) {
            return commandRepository;
        }
        
    	return getCommandsBuilder().build();
    }
}
