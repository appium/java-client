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
}
