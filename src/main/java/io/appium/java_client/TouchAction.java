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

import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.HasIdentity;

import java.time.Duration;

/**
 * Used for Webdriver 3 touch actions
 * See the Webriver 3 spec
 * https://dvcs.w3.org/hg/webdriver/raw-file/default/webdriver-spec.html
 * The flow is to chain individual touch actions into an entire gesture. e.g.
 * TouchAction action = new TouchAction(performsTouchActions);
 * action.press(element).waitAction(300).moveTo(element1).release().perform();
 * Calling perform() sends the action command to the Mobile Driver. Otherwise,
 * more and more actions can be chained.
 */
public class TouchAction implements PerformsActions<TouchAction> {

    protected ImmutableList.Builder<ActionParameter> parameterBuilder;
    private PerformsTouchActions performsTouchActions;

    public TouchAction(PerformsTouchActions performsTouchActions) {
        this.performsTouchActions = performsTouchActions;
        parameterBuilder = ImmutableList.builder();
    }

    /**
     * Press on the center of an element.
     *
     * @param el element to press on.
     * @return this TouchAction, for chaining.
     */
    public TouchAction press(WebElement el) {
        ActionParameter action = new ActionParameter("press", (HasIdentity) el);
        parameterBuilder.add(action);
        return this;
    }

    /**
     * Press on an absolute position on the screen.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @return this TouchAction, for chaining.
     */
    public TouchAction press(int x, int y) {
        ActionParameter action = new ActionParameter("press");
        action.addParameter("x", x);
        action.addParameter("y", y);
        parameterBuilder.add(action);
        return this;
    }

    /**
     * Press on an element, offset from upper left corner by a number of pixels.
     *
     * @param el element to press on.
     * @param x  x offset.
     * @param y  y offset.
     * @return this TouchAction, for chaining.
     */
    public TouchAction press(WebElement el, int x, int y) {
        ActionParameter action = new ActionParameter("press", (HasIdentity) el);
        action.addParameter("x", x);
        action.addParameter("y", y);
        parameterBuilder.add(action);
        return this;
    }

    /**
     * Remove the current touching implement from the screen (withdraw your touch).
     *
     * @return this TouchAction, for chaining.
     */
    public TouchAction release() {
        ActionParameter action = new ActionParameter("release");
        parameterBuilder.add(action);
        return this;
    }

    /**
     * Move current touch to center of an element.
     *
     * @param el element to move to.
     * @return this TouchAction, for chaining.
     */
    public TouchAction moveTo(WebElement el) {
        ActionParameter action = new ActionParameter("moveTo", (HasIdentity) el);
        parameterBuilder.add(action);
        return this;
    }

    /**
     * Move current touch to a new position relative to the current position on
     * the screen. If the current position of this TouchAction is (xOld, yOld),
     * then this method will move the TouchAction to (xOld + x, yOld + y).
     *
     * @param x change in x coordinate to move through.
     * @param y change in y coordinate to move through.
     * @return this TouchAction, for chaining.
     */
    public TouchAction moveTo(int x, int y) {
        ActionParameter action = new ActionParameter("moveTo");
        action.addParameter("x", x);
        action.addParameter("y", y);
        parameterBuilder.add(action);
        return this;
    }

    /**
     * Move current touch to an element, offset from upper left corner.
     *
     * @param el element to move current touch to.
     * @param x  x offset.
     * @param y  y offset.
     * @return this TouchAction, for chaining.
     */
    public TouchAction moveTo(WebElement el, int x, int y) {
        ActionParameter action = new ActionParameter("moveTo", (HasIdentity) el);
        action.addParameter("x", x);
        action.addParameter("y", y);
        parameterBuilder.add(action);
        return this;
    }

    /**
     * Tap the center of an element.
     *
     * @param el element to tap.
     * @return this TouchAction, for chaining.
     */
    public TouchAction tap(WebElement el) {
        ActionParameter action = new ActionParameter("tap", (HasIdentity) el);
        parameterBuilder.add(action);
        return this;
    }

    /**
     * Tap an absolute position on the screen.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @return this TouchAction, for chaining.
     */
    public TouchAction tap(int x, int y) {
        ActionParameter action = new ActionParameter("tap");
        action.addParameter("x", x);
        action.addParameter("y", y);
        parameterBuilder.add(action);
        return this;
    }

    /**
     * Tap an element, offset from upper left corner.
     *
     * @param el element to tap.
     * @param x  x offset.
     * @param y  y offset.
     * @return this TouchAction, for chaining.
     */
    public TouchAction tap(WebElement el, int x, int y) {
        ActionParameter action = new ActionParameter("tap", (HasIdentity) el);
        action.addParameter("x", x);
        action.addParameter("y", y);
        parameterBuilder.add(action);
        return this;
    }

    /**
     * A wait action, used as a NOP in multi-chaining.
     *
     * @return this TouchAction, for chaining.
     */
    public TouchAction waitAction() {
        ActionParameter action = new ActionParameter("wait");
        parameterBuilder.add(action);
        return this;
    }

    /**
     * Waits for specified amount of time to pass before continue to next touch action.
     *
     * @param duration of the wait action. Minimum time reolution unit is one millisecond.
     * @return this TouchAction, for chaining.
     */
    public TouchAction waitAction(Duration duration) {
        ActionParameter action = new ActionParameter("wait");
        action.addParameter("ms", duration.toMillis());
        parameterBuilder.add(action);
        return this;
    }

    /**
     * Press and hold the at the center of an element until the contextmenu event has fired.
     *
     * @param el element to long-press.
     * @return this TouchAction, for chaining.
     */
    public TouchAction longPress(WebElement el) {
        ActionParameter action = new ActionParameter("longPress", (HasIdentity) el);
        parameterBuilder.add(action);
        return this;
    }

    /**
     * Press and hold the at the center of an element until the contextmenu event has fired.
     *
     * @param el       element to long-press.
     * @param duration of the long-press. Minimum time resolution unit is one millisecond.
     * @return this TouchAction, for chaining.
     */
    public TouchAction longPress(WebElement el, Duration duration) {
        ActionParameter action = new ActionParameter("longPress", (HasIdentity) el);
        action.addParameter("duration", duration.toMillis());
        parameterBuilder.add(action);
        return this;
    }

    /**
     * Press and hold the at an absolute position on the screen
     * until the contextmenu event has fired.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @return this TouchAction, for chaining.
     */
    public TouchAction longPress(int x, int y) {
        ActionParameter action = new ActionParameter("longPress");
        action.addParameter("x", x);
        action.addParameter("y", y);
        parameterBuilder.add(action);
        return this;
    }

    /**
     * Press and hold the at an absolute position on the screen until the
     * contextmenu event has fired.
     *
     * @param x        x coordinate.
     * @param y        y coordinate.
     * @param duration of the long-press. Minimum time resolution unit is one millisecond.
     * @return this TouchAction, for chaining.
     */
    public TouchAction longPress(int x, int y, Duration duration) {
        ActionParameter action = new ActionParameter("longPress");
        action.addParameter("x", x);
        action.addParameter("y", y);
        action.addParameter("duration", duration.toMillis());
        parameterBuilder.add(action);
        return this;
    }


    /**
     * Press and hold the at an elements upper-left corner, offset by the given amount,
     * until the contextmenu event has fired.
     *
     * @param el element to long-press.
     * @param x  x offset.
     * @param y  y offset.
     * @return this TouchAction, for chaining.
     */
    public TouchAction longPress(WebElement el, int x, int y) {
        ActionParameter action = new ActionParameter("longPress", (HasIdentity) el);
        action.addParameter("x", x);
        action.addParameter("y", y);
        parameterBuilder.add(action);
        return this;
    }

    /**
     * Press and hold the at an elements upper-left corner, offset by the
     * given amount, until the contextmenu event has fired.
     *
     * @param el       element to long-press.
     * @param x        x offset.
     * @param y        y offset.
     * @param duration of the long-press. Minimum time resolution unit is one millisecond.
     * @return this TouchAction, for chaining.
     */
    public TouchAction longPress(WebElement el, int x, int y, Duration duration) {
        ActionParameter action = new ActionParameter("longPress", (HasIdentity) el);
        action.addParameter("x", x);
        action.addParameter("y", y);
        action.addParameter("duration", duration.toMillis());
        parameterBuilder.add(action);
        return this;
    }

    /**
     * Cancel this action, if it was partially completed by the performsTouchActions.
     */
    public void cancel() {
        ActionParameter action = new ActionParameter("cancel");
        parameterBuilder.add(action);
        this.perform();
    }

    /**
     * Perform this chain of actions on the performsTouchActions.
     *
     * @return this TouchAction, for possible segmented-touches.
     */
    public TouchAction perform() {
        performsTouchActions.performTouchAction(this);
        return this;
    }

    /**
     * Get the mjsonwp parameters for this Action.
     *
     * @return A map of parameters for this touch action to pass as part of mjsonwp.
     */
    protected ImmutableMap<String, ImmutableList<Object>> getParameters() {

        ImmutableList.Builder<Object> parameters = ImmutableList.builder();
        ImmutableList<ActionParameter> actionList = parameterBuilder.build();

        actionList.forEach(action -> parameters.add(action.getParameterMap()));
        return ImmutableMap.of("actions", parameters.build());
    }

    /**
     * Clears all the existing action parameters and resets the instance to the initial state.
     *
     * @return this TouchAction, for possible segmented-touches.
     */
    protected TouchAction clearParameters() {
        parameterBuilder = ImmutableList.builder();
        return this;
    }

    /**
     * Just holds values to eventually return the parameters required for the mjsonwp.
     */
    protected class ActionParameter {
        private String actionName;
        private ImmutableMap.Builder<String, Object> optionsBuilder;

        public ActionParameter(String actionName) {
            this.actionName = actionName;
            optionsBuilder = ImmutableMap.builder();
        }

        public ActionParameter(String actionName, HasIdentity el) {
            this.actionName = actionName;
            optionsBuilder = ImmutableMap.builder();
            addParameter("element", el.getId());
        }

        public ImmutableMap<String, Object> getParameterMap() {
            ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
            builder.put("action", actionName).put("options", optionsBuilder.build());
            return builder.build();
        }

        public void addParameter(String name, Object value) {
            optionsBuilder.put(name, value);
        }
    }
}
