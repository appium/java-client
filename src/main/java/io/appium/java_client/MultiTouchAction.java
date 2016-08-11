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


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

/**
 * Used for Webdriver 3 multi-touch gestures
 * See the Webriver 3 spec https://dvcs.w3.org/hg/webdriver/raw-file/default/webdriver-spec.html
 * The MultiTouchAction object is a collection of TouchAction objects
 * (remember that TouchAction objects are in turn, a chain of individual actions)
 * Add multiple TouchAction objects using the add() method.
 * When perform() method is called, all actions are sent to the driver.
 * The driver performs the first step of each TouchAction object simultaneously as a multi-touch
 * "execution group". Conceptually, the number of TouchAction objects added to the MultiTouchAction
 * is equal to the number of "fingers" or  other appendages or tools touching the screen at the
 * same time as part of this multi-gesture. Then the driver performs the second step of each
 * TouchAction object and another "execution group", and the third, and so on.
 * Using a waitAction() action within a TouchAction takes up one of the slots in an
 * "execution group", so these can be used to sync up complex actions.
 * Calling perform() sends the action command to the Mobile Driver. Otherwise, more and
 * more actions can be chained.
 */
@SuppressWarnings({"rawtypes", "unchecked"}) public class MultiTouchAction {

    ImmutableList.Builder<TouchAction> actions;
    private MobileDriver driver;

    public MultiTouchAction(MobileDriver driver) {
        this.driver = driver;
        actions = ImmutableList.builder();
    }

    /**
     * Add a TouchAction to this multi-touch gesture.
     *
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
        int size = actions.build().size();
        if (size > 1) {
            driver.performMultiTouchAction(this);
        } else if (size == 1) {
            //android doesn't like having multi-touch actions with only a single TouchAction...
            driver.performTouchAction(actions.build().get(0));
        } else {
            throw new MissingParameterException(
                "MultiTouch action must have at least one TouchAction "
                    + "added before it can be performed");
        }

    }

    protected ImmutableMap getParameters() {
        ImmutableList.Builder<Object> listOfActionChains = ImmutableList.builder();
        ImmutableList<TouchAction> touchActions = actions.build();

        for (TouchAction action : touchActions) {
            listOfActionChains.add(action.getParameters().get("actions"));
        }
        return ImmutableMap.of("actions", listOfActionChains.build());
    }

    /**
     * @see TouchableElement#pinch(WebElement).
     */
    public MultiTouchAction pinch(WebElement el) {
        MultiTouchAction multiTouch = new MultiTouchAction(driver);

        Dimension dimensions = el.getSize();
        Point upperLeft = el.getLocation();
        Point center = new Point(upperLeft.getX() + dimensions.getWidth() / 2,
            upperLeft.getY() + dimensions.getHeight() / 2);
        int yOffset = center.getY() - upperLeft.getY();

        TouchAction action0 =
            new TouchAction(driver).press(el, center.getX(), center.getY() - yOffset).moveTo(el)
                .release();
        TouchAction action1 =
            new TouchAction(driver).press(el, center.getX(), center.getY() + yOffset).moveTo(el)
                .release();

        return multiTouch.add(action0).add(action1);
    }

    /**
     * @see TouchableElement#pinch(int, int).
     */
    public MultiTouchAction pinch(int x, int y) {
        MultiTouchAction multiTouch = new MultiTouchAction(driver);

        int scrHeight = driver.manage().window().getSize().getHeight();
        int yOffset = 100;

        if (y - 100 < 0) {
            yOffset = y;
        } else if (y + 100 > scrHeight) {
            yOffset = scrHeight - y;
        }

        TouchAction action0 = new TouchAction(driver).press(x, y - yOffset).moveTo(x, y).release();
        TouchAction action1 = new TouchAction(driver).press(x, y + yOffset).moveTo(x, y).release();

        return multiTouch.add(action0).add(action1);
    }

    /**
     * @see TouchableElement#zoom(WebElement).
     */
    public MultiTouchAction zoom(WebElement el) {
        MultiTouchAction multiTouch = new MultiTouchAction(driver);

        Dimension dimensions = el.getSize();
        Point upperLeft = el.getLocation();
        Point center = new Point(upperLeft.getX() + dimensions.getWidth() / 2,
            upperLeft.getY() + dimensions.getHeight() / 2);
        int yOffset = center.getY() - upperLeft.getY();

        TouchAction action0 = new TouchAction(driver).press(center.getX(), center.getY())
            .moveTo(el, center.getX(), center.getY() - yOffset).release();
        TouchAction action1 = new TouchAction(driver).press(center.getX(), center.getY())
            .moveTo(el, center.getX(), center.getY() + yOffset).release();

        return multiTouch.add(action0).add(action1);
    }

    /**
     * @see TouchableElement#zoom(int, int).
     */
    public MultiTouchAction zoom(int x, int y) {
        MultiTouchAction multiTouch = new MultiTouchAction(driver);

        int scrHeight = driver.manage().window().getSize().getHeight();
        int yOffset = 100;

        if (y - 100 < 0) {
            yOffset = y;
        } else if (y + 100 > scrHeight) {
            yOffset = scrHeight - y;
        }

        TouchAction action0 = new TouchAction(driver).press(x, y).moveTo(0, -yOffset).release();
        TouchAction action1 = new TouchAction(driver).press(x, y).moveTo(0, yOffset).release();

        return multiTouch.add(action0).add(action1);
    }
}
