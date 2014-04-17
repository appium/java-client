/*
 +Copyright 2014 Appium contributors
 +Copyright 2014 Software Freedom Conservancy
 +
 +Licensed under the Apache License, Version 2.0 (the "License");
 +you may not use this file except in compliance with the License.
 +You may obtain a copy of the License at
 +
 +     http://www.apache.org/licenses/LICENSE-2.0
 +
 +Unless required by applicable law or agreed to in writing, software
 +distributed under the License is distributed on an "AS IS" BASIS,
 +WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 +See the License for the specific language governing permissions and
 +limitations under the License.
 + */

package io.appium.java_client;

/**
 * An empty interface defining constants for the mobile commands defined in the Mobile JSON
 * wire protocol.
 * 
 */
public interface MobileCommand {

  String RESET = "reset";
  String GET_STRINGS = "getStrings";
  String KEY_EVENT = "keyEvent";
  String CURRENT_ACTIVITY = "currentActivity";
  String SET_VALUE = "setValue";
  String PULL_FILE = "pullFile";
  String PUSH_FILE = "pushFile";
  String HIDE_KEYBOARD = "hideKeyboard";
  String RUN_APP_IN_BACKGROUND = "runAppInBackground";
  String PERFORM_TOUCH_ACTION = "performTouchAction";
  String PERFORM_MULTI_TOUCH = "performMultiTouch";


}
