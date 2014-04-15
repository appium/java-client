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


import org.openqa.selenium.WebDriver;

public interface MobileDriver extends WebDriver {

  /**
   * Reset the currently running app for this session
   */
  void resetApp();

  /**
   * Get all defined Strings from an Android app
   */
  String getAppStrings();

  /**
   * Send a key event to the device
   *
   * @param key code for the key pressed on the device
   */
  void sendKeyEvent(int key);

  /**
   * Send a key event along with an Android metastate to an Android device
   * Metastates are things like *shift* to get uppercase characters
   *
   * @param key code for the key pressed on the Android device
   * @param metastate metastate for the keypress
   */
  void sendKeyEvent(int key, Integer metastate);

}
