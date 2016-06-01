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

/**
 * An empty interface defining constants for the mobile commands defined in the Mobile JSON
 * wire protocol.
 * 
 */
public interface MobileCommand {

  String RESET = "reset";
  String GET_STRINGS = "getStrings";
  String PRESS_KEY_CODE = "pressKeyCode";
  String LONG_PRESS_KEY_CODE = "longPressKeyCode";
  String CURRENT_ACTIVITY = "currentActivity";
  String SET_VALUE = "setValue";
  String REPLACE_VALUE = "replaceValue";
  String PULL_FILE = "pullFile";
  String PUSH_FILE = "pushFile";
  String PULL_FOLDER = "pullFolder";
  String HIDE_KEYBOARD = "hideKeyboard";
  String RUN_APP_IN_BACKGROUND = "runAppInBackground";
  String PERFORM_TOUCH_ACTION = "performTouchAction";
  String PERFORM_MULTI_TOUCH = "performMultiTouch";
  String IS_APP_INSTALLED = "isAppInstalled";
  String INSTALL_APP = "installApp";
  String REMOVE_APP = "removeApp";
  String LAUNCH_APP = "launchApp";
  String CLOSE_APP = "closeApp";
  String END_TEST_COVERAGE = "endTestCoverage";
  String LOCK = "lock";
  String IS_LOCKED = "isLocked";
  String SHAKE = "shake";
  String COMPLEX_FIND = "complexFind";
  String OPEN_NOTIFICATIONS = "openNotifications";
  String GET_NETWORK_CONNECTION = "getNetworkConnection";
  String SET_NETWORK_CONNECTION = "setNetworkConnection";
  String GET_SETTINGS = "getSettings";
  String SET_SETTINGS = "setSettings";
  String START_ACTIVITY = "startActivity";
  String TOGGLE_LOCATION_SERVICES = "toggleLocationServices";
  String STOP_APP = "stopApp";
  String REPLACE_APP = "replaceApp";
  String REMOVE_FILE = "removeFile";
  String BROADCAST_INTENT = "broadcastIntent";
  String LIST_FILES = "listFiles";
  String IS_SOFT_KEYBOARD_PRESENT = "isSoftKeyboardPresent";
  String CLEAR_DATA = "clearData";
  String SWIPE_UP_HOME_BUTTON = "swipeUpHomeButton";
  String ADB_SWIPE = "adbSwipe";
  String ADB_TAP = "adbTap";
  String ADB_INPUT_TEXT = "adbInputText";
  String GET_NAVIGATION_BAR_REGION = "getNavigationBarRegion";
  String GET_DISPLAY_METRICS = "getDisplayMetrics";
  String GET_DATE = "getDate";
  String GET_DATE_STRING = "getDateString";
  String SET_DATE = "setDate";
  String HAS_ROOT = "hasRoot";
  String GET_VERSIONS = "getVersions";
  String REMOVE_FOLDER = "removeFolder";
  String LIST_FOLDER = "listFolder";
  String CP = "cp";
  String GET_OWNER = "getOwner";
  String SET_OWNER = "setOwner";
}
