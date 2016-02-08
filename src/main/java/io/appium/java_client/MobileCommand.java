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
 * 
 */
public class MobileCommand {

  public final static String RESET = "reset";
  public final static String GET_STRINGS = "getStrings";
  public final static String PRESS_KEY_CODE = "pressKeyCode";
  public final static String LONG_PRESS_KEY_CODE = "longPressKeyCode";
  public final static String CURRENT_ACTIVITY = "currentActivity";
  public final static String SET_VALUE = "setValue";
  public final static String REPLACE_VALUE = "replaceValue";
  public final static String PULL_FILE = "pullFile";
  public final static String PUSH_FILE = "pushFile";
  public final static String PULL_FOLDER = "pullFolder";
  public final static String HIDE_KEYBOARD = "hideKeyboard";
  public final static String RUN_APP_IN_BACKGROUND = "runAppInBackground";
  public final static String PERFORM_TOUCH_ACTION = "performTouchAction";
  public final static String PERFORM_MULTI_TOUCH = "performMultiTouch";
  public final static String IS_APP_INSTALLED = "isAppInstalled";
  public final static String INSTALL_APP = "installApp";
  public final static String REMOVE_APP = "removeApp";
  public final static String LAUNCH_APP = "launchApp";
  public final static String CLOSE_APP = "closeApp";
  public final static String END_TEST_COVERAGE = "endTestCoverage";
  public final static String LOCK = "lock";
  public final static String IS_LOCKED = "isLocked";
  public final static String SHAKE = "shake";
  public final static String COMPLEX_FIND = "complexFind";
  public final static String OPEN_NOTIFICATIONS = "openNotifications";
  public final static String GET_NETWORK_CONNECTION = "getNetworkConnection";
  public final static String SET_NETWORK_CONNECTION = "setNetworkConnection";
  public final static String GET_SETTINGS = "getSettings";
  public final static String SET_SETTINGS = "setSettings";
  public final static String START_ACTIVITY = "startActivity";
  public final static String TOGGLE_LOCATION_SERVICES = "toggleLocationServices";
  public final static String GET_DEVICE_TIME = "getDeviceTime";

  static CommandInfo getC(String url) {
    return new CommandInfo(url, HttpMethod.GET);
  }

  static CommandInfo postC(String url) {
    return new CommandInfo(url, HttpMethod.POST);
  }

  static final Map<String, CommandInfo> commandRepository = getMobileCommands();

  private static Map<String, CommandInfo> getMobileCommands(){
    if (commandRepository != null) {
      return commandRepository;
    }

    ImmutableMap.Builder<String, CommandInfo> builder = ImmutableMap
            .builder();
    builder.put(RESET, postC("/session/:sessionId/appium/app/reset"))
            .put(GET_STRINGS,
                    postC("/session/:sessionId/appium/app/strings"))
            .put(PRESS_KEY_CODE,
                    postC("/session/:sessionId/appium/device/press_keycode"))
            .put(LONG_PRESS_KEY_CODE,
                    postC("/session/:sessionId/appium/device/long_press_keycode"))
            .put(CURRENT_ACTIVITY,
                    getC("/session/:sessionId/appium/device/current_activity"))
            .put(SET_VALUE,
                    postC("/session/:sessionId/appium/element/:id/value"))
            .put(REPLACE_VALUE,
                    postC("/session/:sessionId/appium/element/:id/replace_value"))
            .put(PULL_FILE,
                    postC("/session/:sessionId/appium/device/pull_file"))
            .put(PULL_FOLDER,
                    postC("/session/:sessionId/appium/device/pull_folder"))
            .put(HIDE_KEYBOARD,
                    postC("/session/:sessionId/appium/device/hide_keyboard"))
            .put(PUSH_FILE,
                    postC("/session/:sessionId/appium/device/push_file"))
            .put(RUN_APP_IN_BACKGROUND,
                    postC("/session/:sessionId/appium/app/background"))
            .put(PERFORM_TOUCH_ACTION,
                    postC("/session/:sessionId/touch/perform"))
            .put(PERFORM_MULTI_TOUCH,
                    postC("/session/:sessionId/touch/multi/perform"))
            .put(IS_APP_INSTALLED,
                    postC("/session/:sessionId/appium/device/app_installed"))
            .put(INSTALL_APP,
                    postC("/session/:sessionId/appium/device/install_app"))
            .put(REMOVE_APP,
                    postC("/session/:sessionId/appium/device/remove_app"))
            .put(LAUNCH_APP, postC("/session/:sessionId/appium/app/launch"))
            .put(CLOSE_APP, postC("/session/:sessionId/appium/app/close"))
            .put(END_TEST_COVERAGE,
                    postC("/session/:sessionId/appium/app/end_test_coverage"))
            .put(LOCK, postC("/session/:sessionId/appium/device/lock"))
            .put(IS_LOCKED,
                    postC("/session/:sessionId/appium/device/is_locked"))
            .put(SHAKE, postC("/session/:sessionId/appium/device/shake"))
            .put(COMPLEX_FIND,
                    postC("/session/:sessionId/appium/app/complex_find"))
            .put(OPEN_NOTIFICATIONS,
                    postC("/session/:sessionId/appium/device/open_notifications"))
            .put(GET_NETWORK_CONNECTION,
                    getC("/session/:sessionId/network_connection"))
            .put(SET_NETWORK_CONNECTION,
                    postC("/session/:sessionId/network_connection"))
            .put(GET_SETTINGS, getC("/session/:sessionId/appium/settings"))
            .put(SET_SETTINGS, postC("/session/:sessionId/appium/settings"))
            .put(START_ACTIVITY,
                    postC("/session/:sessionId/appium/device/start_activity"))
            .put(TOGGLE_LOCATION_SERVICES, postC("/session/:sessionId/appium/device/toggle_location_services"))
            .put(GET_DEVICE_TIME,getC("/session/:sessionId/appium/device/system_time"));

    return builder.build();
  }
}
