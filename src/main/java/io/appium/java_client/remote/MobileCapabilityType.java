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

package io.appium.java_client.remote;

import org.openqa.selenium.remote.CapabilityType;

public interface MobileCapabilityType extends CapabilityType {
  
  String AUTOMATION_NAME = "automationName";

  String PLATFORM_NAME = "platformName";
  String PLATFORM_VERSION = "platformVersion";

  String DEVICE_NAME = "deviceName";

  String NEW_COMMAND_TIMEOUT = "newCommandTimeout";
  String DEVICE_READY_TIMEOUT = "deviceReadyTimeout";
  String LAUNCH_TIMEOUT = "launchTimeout";

  String APP = "app";
  String APP_PACKAGE = "appPackage";
  String APP_ACTIVITY = "appActivity";
  String APP_WAIT_ACTIVITY = "appWaitActivity";
  String APP_WAIT_PACKAGE = "appWaitPackage";
  String SELENDROID_PORT  = "selendroidPort";
  String UDID = "udid";

  //Sauce-specific
  String APPIUM_VERSION = "appiumVersion";
}
