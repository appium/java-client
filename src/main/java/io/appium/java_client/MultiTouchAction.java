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
 * When perform() method is called, all actions are sent to the performsTouchActions.
 * The performsTouchActions performs the first step of each TouchAction object simultaneously as a multi-touch
 * "execution group". Conceptually, the number of TouchAction objects added to the MultiTouchAction
 * is equal to the number of "fingers" or  other appendages or tools touching the screen at the
 * same time as part of this multi-gesture. Then the performsTouchActions performs the second step of each
 * TouchAction object and another "execution group", and the third, and so on.
 * Using a waitAction() action within a TouchAction takes up one of the slots in an
 * "execution group", so these can be used to sync up complex actions.
 * Calling perform() sends the action command to the Mobile Driver. Otherwise, more and
 * more actions can be chained.
 */
public class MultiTouchAction {

    private ImmutableList.Builder<TouchAction> actions;
    private PerformsTouchActions performsTouchActions;

    public MultiTouchAction(PerformsTouchActions performsTouchActions) {
        this.performsTouchActions = performsTouchActions;
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
     * Perform the multi-touch action on the mobile performsTouchActions.
     */
    public void perform() {
        int size = actions.build().size();
        if (size > 1) {
            performsTouchActions.performMultiTouchAction(this);
        } else if (size == 1) {
            //android doesn't like having multi-touch actions with only a single TouchAction...
            performsTouchActions.performTouchAction(actions.build().get(0));
        } else {
            throw new MissingParameterException(
                "MultiTouch action must have at least one TouchAction "
                    + "added before it can be performed");
        }

    }

    protected ImmutableMap<String, ImmutableList<Object>> getParameters() {
        ImmutableList.Builder<Object> listOfActionChains = ImmutableList.builder();
        ImmutableList<TouchAction> touchActions = actions.build();

        touchActions.forEach(action -> {
            listOfActionChains.add(action.getParameters().get("actions"));
        });
        return ImmutableMap.of("actions", listOfActionChains.build());
    }

    /**
     * Creates few combined touch actions which performs the pinching on the given elements.
     */
    public MultiTouchAction pinch(WebElement el) {
        Dimension dimensions = el.getSize();
        Point upperLeft = el.getLocation();
        Point center = new Point(upperLeft.getX() + dimensions.getWidth() / 2,
            upperLeft.getY() + dimensions.getHeight() / 2);
        int yOffset = center.getY() - upperLeft.getY();

        TouchAction action0 =
            new TouchAction(performsTouchActions).press(el, center.getX(), center.getY() - yOffset).moveTo(el)
                .release();
        TouchAction action1 =
            new TouchAction(performsTouchActions).press(el, center.getX(), center.getY() + yOffset).moveTo(el)
                .release();

        this.add(action0).add(action1);
        return this;
    }

    /**
     * Creates few combined touch actions which performs the pinching by given coordinates and offsets.
     * @param x is a x-coordinate to perform the pinching to
     * @param y is an y-coordinate to perform the pinching to
     * @param xOffset is an +/- offset from the given x-coordinate to perform the pinching to
     * @param yOffset is an +/- offset from the given y-coordinate to perform the pinching to
     * @return the self-reference
     */
    public MultiTouchAction pinch(int x, int y, int xOffset, int yOffset) {
        TouchAction action0 = new TouchAction(performsTouchActions).press(x + xOffset, y - yOffset)
                .moveTo(- xOffset, yOffset).release();
        TouchAction action1 = new TouchAction(performsTouchActions).press(x - xOffset, y + yOffset)
                .moveTo(xOffset, -yOffset).release();

        this.add(action0).add(action1);
        return this;
    }

    /**
     * Creates few combined touch actions which performs the vertical pinching by given coordinates and y-Offset.
     * @param x is a x-coordinate to perform the pinching to
     * @param y is an y-coordinate to perform the pinching to
     * @param yOffset is an +/- offset from the given y-coordinate to perform the pinching to
     * @return the self-reference
     */
    public MultiTouchAction pinch(int x, int y, int yOffset) {
        return pinch(x, y, 0, yOffset);
    }

    /**
     * Creates few combined touch actions which performs the zooming on the given element.
     */
    public MultiTouchAction zoom(WebElement el) {
        Dimension dimensions = el.getSize();
        Point upperLeft = el.getLocation();
        Point center = new Point(upperLeft.getX() + dimensions.getWidth() / 2,
            upperLeft.getY() + dimensions.getHeight() / 2);
        int yOffset = center.getY() - upperLeft.getY();

        TouchAction action0 = new TouchAction(performsTouchActions).press(center.getX(), center.getY())
            .moveTo(el, center.getX(), center.getY() - yOffset).release();
        TouchAction action1 = new TouchAction(performsTouchActions).press(center.getX(), center.getY())
            .moveTo(el, center.getX(), center.getY() + yOffset).release();

        this.add(action0).add(action1);
        return this;
    }

    /**
     * Creates few combined touch actions which performs the zooming by given coordinates and x/y-Offset value.
     * @param x is a x-coordinate to perform the zooming from
     * @param y is a y-coordinate to perform the zooming from
     * @param xOffset is an +/- offset from the given x-coordinate to perform the zooming from
     * @param yOffset is an +/- offset from the given y-coordinate to perform the zooming from
     * @return the self-reference
     */
    public MultiTouchAction zoom(int x, int y, int xOffset, int yOffset) {
        TouchAction action0 = new TouchAction(performsTouchActions).press(x, y)
                .moveTo(xOffset, -yOffset).release();
        TouchAction action1 = new TouchAction(performsTouchActions).press(x, y)
                .moveTo(-xOffset, yOffset).release();

        return this.add(action0).add(action1);
    }

    /**
     * Creates few combined touch actions which performs the vertical zooming by given coordinates and x/y-Offset value.
     * @param x is a x-coordinate to perform the zooming from
     * @param y is a y-coordinate to perform the zooming from
     * @param yOffset is an +/- offset from the given y-coordinate to perform the zooming from
     * @return the self-reference
     */
    public MultiTouchAction zoom(int x, int y, int yOffset) {
        return zoom(x, y, 0, yOffset);
    }

    /**
     * Creates the tapping by few finders using given coordinates.
     *
     * @param fingers is a count of fingers to tap
     * @param x coordinate
     * @param y coordinate
     * @param duration is a time for the tapping in milliseconds
     * @return the self-reference
     */
    public MultiTouchAction tap(int fingers, int x, int y, int duration) {
        for (int i = 0; i < fingers; i++) {
            TouchAction tap = new TouchAction(performsTouchActions);
            this.add(tap.press(x, y).waitAction(duration).release());
        }
        return this;
    }

    /**
     * Creates the tapping by few finders on the element.
     *
     * @param fingers is a count of fingers to tap
     * @param element to be tapped
     * @param duration is a time for the tapping in milliseconds
     * @return the self-reference
     */
    public MultiTouchAction tap(int fingers, WebElement element, int duration) {
        for (int i = 0; i < fingers; i++) {
            TouchAction tap = new TouchAction(performsTouchActions);
            this.add(tap.press(element).waitAction(duration).release());
        }
        return this;
    }
}
