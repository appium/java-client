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

/**
 * Used for Webdriver 3 multi-touch gestures
 * See the Webriver 3 spec https://dvcs.w3.org/hg/webdriver/raw-file/default/webdriver-spec.html
 *
 * The MultiTouchAction object is a collection of TouchAction objects
 *   (remember that TouchAction objects are in turn, a chain of individual actions)
 *
 * Add multiple TouchAction objects using the add() method.
 * When perform() method is called, all actions are sent to the driver.
 *
 * The driver performs the first step of each TouchAction object simultaneously as a multi-touch "execution group".
 *   Conceptually, the number of TouchAction objects added to the MultiTouchAction is equal to the number of "fingers" or
 *   other appendages or tools touching the screen at the same time as part of this multi-gesture.
 * Then the driver performs the second step of each TouchAction object and another "execution group", and the third, and so on.
 *
 * Using a waitAction() action within a TouchAction takes up one of the slots in an "execution group", so these can be used to
 *   sync up complex actions.
 *
 * Calling perform() sends the action command to the Mobile Driver. Otherwise, more and more actions can be chained.
 */
public class MultiTouchAction {

  private MobileDriver driver;
  ImmutableList.Builder actions;

  public MultiTouchAction(MobileDriver driver) {
    this.driver = driver;
    actions = ImmutableList.builder();
  }

  /**
   * Add a TouchAction to this multi-touch gesture
   * @param action TouchAction to add to this gesture
   * @return This MultiTouchAction, for chaining
   */
  public MultiTouchAction add(TouchAction action) {
    actions.add(action);

    return this;
  }

  /**
   * Perform the multi-touch action on the mobile driver.
   */
  public void perform() {
    driver.performMultiTouchAction(this);
  }

  protected ImmutableMap getParameters() {
    ImmutableList.Builder listOfActionChains = ImmutableList.builder();
    ImmutableList<TouchAction> touchActions = actions.build();

    for (TouchAction action : touchActions) {
      listOfActionChains.add(action.getParameters().get("actions"));
    }
    return ImmutableMap.of("actions", listOfActionChains.build());
  }
}
