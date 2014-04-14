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


import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.*;

import java.net.URL;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.appium.java_client.MobileCommand.*;

public class AppiumDriver extends RemoteWebDriver implements MobileDriver, ContextAware, FindsByIosUIAutomation,
  FindsByAndroidUIAutomator {

  private final MobileErrorHandler errorHandler = new MobileErrorHandler();

  public AppiumDriver(URL remoteAddress, Capabilities desiredCapabilities){

    super(remoteAddress, desiredCapabilities);

    ImmutableMap.Builder<String, CommandInfo> builder = ImmutableMap.builder();
    builder
            .put(RESET, postC("/session/:sessionId/appium/app/reset"))
            .put(GET_STRINGS, getC("/session/:sessionId/appium/app/strings"))
            .put(KEY_EVENT, postC("/session/:sessionId/appium/device/keyevent"))
            ;
    ImmutableMap<String, CommandInfo> mobileCommands = builder.build();

    HttpCommandExecutor mobileExecutor = new HttpCommandExecutor(mobileCommands, remoteAddress);
    super.setCommandExecutor(mobileExecutor);

  }

  @Override
  protected Response execute(String driverCommand, Map<String, ?> parameters) {
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


  public void resetApp() {
    execute(MobileCommand.RESET);
  }

  public String getAppStrings() {
    Response response = execute(GET_STRINGS);
    return response.getValue().toString();
  }

  public void sendKeyEvent(int key) {
    sendKeyEvent(key, null);
  }

  public void sendKeyEvent(int key, Integer metastate) {
    ImmutableMap.Builder builder = ImmutableMap.builder();
    builder.put("keycode", key);
    if (metastate != null) { builder.put("metastate", metastate); }
    ImmutableMap<String, Integer> parameters = builder.build();
    execute(KEY_EVENT, parameters);
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