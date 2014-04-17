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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.*;

import javax.xml.bind.DatatypeConverter;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.appium.java_client.MobileCommand.*;

public class AppiumDriver extends RemoteWebDriver implements MobileDriver, ContextAware, FindsByIosUIAutomation,
        FindsByAndroidUIAutomator, FindsByAccessibilityId {

  private final MobileErrorHandler errorHandler = new MobileErrorHandler();

  public AppiumDriver(URL remoteAddress, Capabilities desiredCapabilities){

    super(remoteAddress, desiredCapabilities);

    ImmutableMap.Builder<String, CommandInfo> builder = ImmutableMap.builder();
    builder
            .put(RESET, postC("/session/:sessionId/appium/app/reset"))
            .put(GET_STRINGS, getC("/session/:sessionId/appium/app/strings"))
            .put(KEY_EVENT, postC("/session/:sessionId/appium/device/keyevent"))
            .put(CURRENT_ACTIVITY, getC("/session/:sessionId/appium/device/current_activity"))
            .put(SET_VALUE, postC("/session/:sessionId/appium/element/:id/value"))
            .put(PULL_FILE, postC("/session/:sessionId/appium/device/pull_file"))
            .put(HIDE_KEYBOARD, postC("/session/:sessionId/appium/device/hide_keyboard"))
            .put(PUSH_FILE, postC("/session/:sessionId/appium/device/push_file"))
            .put(RUN_APP_IN_BACKGROUND, postC("/session/:sessionId/appium/app/background"))
            .put(PERFORM_TOUCH_ACTION, postC("/session/:sessionId/touch/perform"))
            ;
    ImmutableMap<String, CommandInfo> mobileCommands = builder.build();

    HttpCommandExecutor mobileExecutor = new HttpCommandExecutor(mobileCommands, remoteAddress);
    super.setCommandExecutor(mobileExecutor);

  }

  @Override
  public Response execute(String driverCommand, Map<String, ?> parameters) {
    try {
      return super.execute(driverCommand, parameters);
    } catch (WebDriverException ex) {
      errorHandler.throwIfMobileError(ex);
    }

    throw new RuntimeException("An WebDriver error should have been thrown, if you're reading this, the problem is " +
            "definitely in the Appium Driver");
  }

  @Override
  protected Response execute(String command) {
    return execute(command, ImmutableMap.<String, Object>of());
  }


  /**
   * Reset the currently running app for this session
   */
  public void resetApp() {
    execute(MobileCommand.RESET);
  }

  /**
   * Get all defined Strings from an Android app
   *
   * @return a string of all the localized strings defined in the app
   */
  public String getAppStrings() {
    Response response = execute(GET_STRINGS);
    return response.getValue().toString();
  }

  /**
   * Send a key event to the device
   *
   * @param key code for the key pressed on the device
   */
  public void sendKeyEvent(int key) {
    sendKeyEvent(key, null);
  }

  /**
   * Send a key event along with an Android metastate to an Android device
   * Metastates are things like *shift* to get uppercase characters
   *
   * @param key code for the key pressed on the Android device
   * @param metastate metastate for the keypress
   */
  public void sendKeyEvent(int key, Integer metastate) {
    ImmutableMap.Builder builder = ImmutableMap.builder();
    builder.put("keycode", key);
    if (metastate != null) { builder.put("metastate", metastate); }
    ImmutableMap<String, Integer> parameters = builder.build();
    execute(KEY_EVENT, parameters);
  }

  /**
   * Get the current activity being run on the mobile device
   */
  public String currentActivity() {
    Response response = execute(CURRENT_ACTIVITY);
    return response.getValue().toString();
  }

  /**
   *
   * @param remotePath On Android and iOS, this is either the path to the file (relative to the root of the app's file system).
   *                   On iOS only, if path starts with /AppName.app, which will be replaced with the application's .app directory
   * @return A byte array of Base64 encoded data.
   */
  public byte[] pullFile(String remotePath) {
    Response response = execute(PULL_FILE, ImmutableMap.of("path", remotePath));
    String base64String = response.getValue().toString();

    return DatatypeConverter.parseBase64Binary(base64String);
  }

  /**
   * Save base64 encoded data as a file on the remote mobile device.
   * This is an Android only method.
   * @param remotePath Path to file to write data to on remote device
   * @param base64Data Base64 encoded byte array of data to write to remote device
   */
  public void pushFile(String remotePath, byte[] base64Data) {
    ImmutableMap.Builder builder = ImmutableMap.builder();
    builder.put("path", remotePath).put("data", base64Data);
    execute(PUSH_FILE, builder.build());
  }

  /**
   * Hides the keyboard if it is showing.
   * This is an iOS only command.
   */
  public void hideKeyboard() {
    execute(HIDE_KEYBOARD);
  }

  /**
   * Hides the keyboard by pressing the button specified by keyName if it is showing.
   * This is an iOS only command.
   * @param keyName The button pressed by the mobile driver to attempt hiding the keyboard
   */
  public void hideKeyboard(String keyName) {
    execute(HIDE_KEYBOARD, ImmutableMap.of("keyName", keyName));
  }

  /**
   * Runs the current app as a background app for the number of seconds requested.
   * This is a synchronous method, it returns after the back has been returned to the foreground.
   * @param seconds Number of seconds to run App in background
   */
  public void runAppInBackground(int seconds) {
    execute(RUN_APP_IN_BACKGROUND, ImmutableMap.of("seconds", seconds));
  }

  public TouchAction performTouchAction(TouchAction touchAction) {
    ImmutableMap<String, ImmutableList> parameters = touchAction.getParameters();
    touchAction.clearParameters();
    execute(PERFORM_TOUCH_ACTION, parameters);

    return touchAction;
  }

  @Override
  public WebDriver context(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Must supply a context name");
    }


    execute(DriverCommand.SWITCH_TO_CONTEXT, ImmutableMap.of("name", name));
    return AppiumDriver.this;
  }

  @Override
  public Set<String> getContextHandles() {
    Response response = execute(DriverCommand.GET_CONTEXT_HANDLES);
    Object value = response.getValue();
    try {
      List<String> returnedValues = (List<String>)value;
      return new LinkedHashSet<String>(returnedValues);
    } catch (ClassCastException ex) {
      throw new WebDriverException("Returned value cannot be converted to List<String>: " + value, ex);
    }
  }

  @Override
  public String getContext() {
    String contextName = String.valueOf(execute(DriverCommand.GET_CURRENT_CONTEXT_HANDLE).getValue());
    if (contextName.equals("null")) {
      return null;
    }
    return contextName;
  }

  @Override
  public WebElement findElementByIosUIAutomation(String using) {
    return findElement("-ios uiautomation", using);
  }

  @Override
  public List<WebElement> findElementsByIosUIAutomation(String using) {
    return findElements("-ios uiautomation", using);
  }

  @Override
  public WebElement findElementByAndroidUIAutomator(String using) {
    return findElement("-android uiautomator", using);
  }

  @Override
  public List<WebElement> findElementsByAndroidUIAutomator(String using) {
    return findElements("-android uiautomator", using);
  }

  @Override
  public WebElement findElementByAccessibilityId(String using) {
    return findElement("accessibility id", using);
  }

  @Override
  public List<WebElement> findElementsByAccessibilityId(String using) {
    return findElements("accessibility id", using);
  }

  private static CommandInfo getC(String url) {
    return new CommandInfo(url, HttpVerb.GET);
  }

  private static CommandInfo postC(String url) {
    return new CommandInfo(url, HttpVerb.POST);
  }

  private static CommandInfo deleteC(String url) {
    return new CommandInfo(url, HttpVerb.DELETE);
  }
}