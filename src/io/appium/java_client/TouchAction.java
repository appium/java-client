package io.appium.java_client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

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
public class TouchAction {

  private MobileDriver driver;
  ImmutableList.Builder parameterBuilder;

  public TouchAction(MobileDriver driver) {
    this.driver = driver;
    parameterBuilder = ImmutableList.builder();
  }

  public TouchAction press(WebElement el) {
    ActionParameter action = new ActionParameter("press", (RemoteWebElement)el);
    parameterBuilder.add(action);
    return this;
  }

  public TouchAction press(WebElement el, int x, int y) {
    ActionParameter action = new ActionParameter("press", (RemoteWebElement)el);
    action.addParameter("x", x);
    action.addParameter("y", y);
    parameterBuilder.add(action);
    return this;
  }

  public TouchAction release() {
    ActionParameter action = new ActionParameter("release");
    parameterBuilder.add(action);
    return this;
  }

  public TouchAction moveTo(WebElement el) {
    ActionParameter action = new ActionParameter("moveTo", (RemoteWebElement)el);
    parameterBuilder.add(action);
    return this;
  }

  public TouchAction moveTo(WebElement el, int x, int y) {
    ActionParameter action = new ActionParameter("moveTo", (RemoteWebElement)el);
    action.addParameter("x", x);
    action.addParameter("y", y);
    parameterBuilder.add(action);
    return this;
  }

  public TouchAction tap(WebElement el) {
    ActionParameter action = new ActionParameter("tap", (RemoteWebElement)el);
    parameterBuilder.add(action);
    return this;
  }

  public TouchAction waitAction() {
    ActionParameter action = new ActionParameter("wait");
    parameterBuilder.add(action);
    return this;
  }

  public TouchAction waitAction(int ms) {
    ActionParameter action = new ActionParameter("wait");
    action.addParameter("ms", ms);
    parameterBuilder.add(action);
    return this;
  }

  public TouchAction longPress(WebElement el) {
    ActionParameter action = new ActionParameter("longPress", (RemoteWebElement)el);
    parameterBuilder.add(action);
    return this;
  }

  public TouchAction longPress(WebElement el, int x, int y) {
    ActionParameter action = new ActionParameter("longPress", (RemoteWebElement)el);
    action.addParameter("x", x);
    action.addParameter("y", y);
    parameterBuilder.add(action);
    return this;
  }

  public void cancel() {
    ActionParameter action = new ActionParameter("wait");
    parameterBuilder.add(action);
    this.perform();
  }

  public TouchAction perform() {
    driver.performTouchAction(this);
    return this;
  }




  /**
   * Get the mjsonwp parameters for this Action
   * @return A map of parameters for this touch action to pass as part of mjsonwp
   */
  protected ImmutableMap<String, ImmutableList> getParameters() {

    ImmutableList.Builder parameters = ImmutableList.builder();
    ImmutableList<ActionParameter> actionList = parameterBuilder.build();
    for (ActionParameter action : actionList){
      parameters.add(action.getParameterMap());
    }
    return ImmutableMap.of("actions", parameters.build());
  }

  protected void clearParameters() {
    parameterBuilder = ImmutableList.builder();
  }

  private class ActionParameter {
    private String actionName;
    private ImmutableMap.Builder optionsBuilder;

    public ActionParameter(String actionName) {
      this.actionName = actionName;
      optionsBuilder = ImmutableMap.builder();
    }

    public ActionParameter(String actionName, RemoteWebElement el) {
      this.actionName = actionName;
      optionsBuilder = ImmutableMap.builder();
      addParameter("element", el.getId());
    }

    public ImmutableMap<String, Object> getParameterMap() {
      ImmutableMap.Builder builder = ImmutableMap.builder();
      builder.put("action", actionName).put("options", optionsBuilder.build());
      return builder.build();
    }

    public void addParameter(String name, Object value) {
      optionsBuilder.put(name, value);
    }
  }
}
