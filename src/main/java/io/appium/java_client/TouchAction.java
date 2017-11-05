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

import static com.google.common.base.Preconditions.checkNotNull;
import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.PositionOffsetOption.positionOffsetOption;
import static io.appium.java_client.touch.WebElementOption.elementOption;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import io.appium.java_client.touch.ActionOptions;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.PositionOffsetOption;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.WebElementOption;
import org.openqa.selenium.WebElement;

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
public class TouchAction<T extends TouchAction<T>> implements PerformsActions<T> {

    protected ImmutableList.Builder<ActionParameter> parameterBuilder;
    private PerformsTouchActions performsTouchActions;

    public TouchAction(PerformsTouchActions performsTouchActions) {
        this.performsTouchActions = performsTouchActions;
        parameterBuilder = ImmutableList.builder();
    }

    /**
     * Press action on the screen.
     *
     * @param pressOptions see {@link WebElementOption} and {@link PositionOffsetOption}.
     * @return this TouchAction, for chaining.
     */
    public <S extends PositionOffsetOption> T press(S pressOptions) {
        parameterBuilder.add(new ActionParameter("press", pressOptions));
        //noinspection unchecked
        return (T) this;
    }

    /**
     * Press on the center of an element.
     *
     * @param el element to press on.
     * @return this TouchAction, for chaining.
     * @deprecated use {@link #press(PositionOffsetOption)} instead
     */
    @Deprecated
    public T press(WebElement el) {
        return press(elementOption(el));
    }

    /**
     * Press on an absolute position on the screen.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @return this TouchAction, for chaining.
     * @deprecated use {@link #press(PositionOffsetOption)} instead
     */
    @Deprecated
    public T press(int x, int y) {
        return (T) press(positionOffsetOption(x, y));
    }

    /**
     * Press on an element, offset from upper left corner by a number of pixels.
     *
     * @param el element to press on.
     * @param x  x offset.
     * @param y  y offset.
     * @return this TouchAction, for chaining.
     * @deprecated use {@link #press(PositionOffsetOption)} instead
     */
    @Deprecated
    public T press(WebElement el, int x, int y) {
        return press(elementOption(el, x, y));
    }

    /**
     * Remove the current touching implement from the screen (withdraw your touch).
     *
     * @return this TouchAction, for chaining.
     */
    public T release() {
        ActionParameter action = new ActionParameter("release");
        parameterBuilder.add(action);
        //noinspection unchecked
        return (T) this;
    }

    /**
     * Moves current touch to a new position.
     *
     * @param  moveToOptions see {@link WebElementOption} and {@link PositionOffsetOption}.
     * @return this TouchAction, for chaining.
     */
    public <S extends PositionOffsetOption> T moveTo(S moveToOptions) {
        ActionParameter action = new ActionParameter("moveTo", moveToOptions);
        parameterBuilder.add(action);
        //noinspection unchecked
        return (T) this;
    }

    /**
     * Move current touch to center of an element.
     *
     * @param el element to move to.
     * @return this TouchAction, for chaining.
     * @deprecated {@link #moveTo(PositionOffsetOption)} instead
     */
    @Deprecated
    public T moveTo(WebElement el) {
        return moveTo(elementOption(el));
    }

    /**
     * Move current touch to a new position relative to the current position on
     * the screen. If the current position of this TouchAction is (xOld, yOld),
     * then this method will move the TouchAction to (xOld + x, yOld + y).
     *
     * @param x change in x coordinate to move through.
     * @param y change in y coordinate to move through.
     * @return this TouchAction, for chaining.
     * @deprecated {@link #moveTo(PositionOffsetOption)} instead
     */
    @Deprecated
    public T moveTo(int x, int y) {
        return (T) moveTo(positionOffsetOption(x, y));
    }

    /**
     * Move current touch to an element, offset from upper left corner.
     *
     * @param el element to move current touch to.
     * @param x  x offset.
     * @param y  y offset.
     * @return this TouchAction, for chaining.
     * @deprecated {@link #moveTo(PositionOffsetOption)} instead
     */
    @Deprecated
    public T moveTo(WebElement el, int x, int y) {
        return moveTo(elementOption(el, x, y));
    }

    /**
     * Tap on an element.
     *
     * @param tapOptions see {@link TapOptions}.
     * @return this TouchAction, for chaining.
     */
    public TouchAction tap(TapOptions tapOptions) {
        ActionParameter action = new ActionParameter("tap", tapOptions);
        parameterBuilder.add(action);
        return this;
    }

    /**
     * Tap on a position.
     *
     * @param tapOptions see {@link WebElementOption} and {@link PositionOffsetOption}.
     * @return this TouchAction, for chaining.
     */
    public <S extends PositionOffsetOption> T tap(S tapOptions) {
        ActionParameter action = new ActionParameter("tap", tapOptions);
        parameterBuilder.add(action);
        return (T) this;
    }

    /**
     * Tap the center of an element.
     *
     * @param el element to tap.
     * @return this TouchAction, for chaining.
     * @deprecated use {@link #tap(PositionOffsetOption)} instead.
     */
    @Deprecated
    public T tap(WebElement el) {
        return tap(elementOption(el));
    }

    /**
     * Tap an absolute position on the screen.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @return this TouchAction, for chaining.
     * @deprecated use {@link #tap(PositionOffsetOption)} instead.
     */
    @Deprecated
    public T tap(int x, int y) {
        return (T) tap(positionOffsetOption(x, y));
    }

    /**
     * Tap an element, offset from upper left corner.
     *
     * @param el element to tap.
     * @param x  x offset.
     * @param y  y offset.
     * @return this TouchAction, for chaining.
     * @deprecated use {@link #tap(PositionOffsetOption)} instead.
     */
    @Deprecated
    public T tap(WebElement el, int x, int y) {
        return  tap(elementOption(el, x, y));
    }

    /**
     * A wait action, used as a NOP in multi-chaining.
     *
     * @return this TouchAction, for chaining.
     */
    public T waitAction() {
        ActionParameter action = new ActionParameter("wait");
        parameterBuilder.add(action);
        //noinspection unchecked
        return (T) this;
    }

    /**
     * Waits for specified amount of time to pass before continue to next touch action.
     *
     * @param waitOptions see {@link WaitOptions}.
     * @return this TouchAction, for chaining.
     */
    public T waitAction(WaitOptions waitOptions) {
        ActionParameter action = new ActionParameter("wait", waitOptions);
        parameterBuilder.add(action);
        //noinspection unchecked
        return (T) this;
    }

    /**
     * Waits for specified amount of time to pass before continue to next touch action.
     *
     * @param duration of the wait action. Minimum time reolution unit is one millisecond.
     * @return this TouchAction, for chaining.
     * @deprecated use {@link #waitAction(WaitOptions)} instead.
     */
    @Deprecated
    public T waitAction(Duration duration) {
        ActionParameter action = new ActionParameter("wait",
                new WaitOptions()
                        .withDuration(duration));
        parameterBuilder.add(action);
        //noinspection unchecked
        return (T) this;
    }

    /**
     * Press and hold the at the center of an element until the context menu event has fired.
     *
     * @param longPressOptions see {@link LongPressOptions}.
     * @return this TouchAction, for chaining.
     */
    public T longPress(LongPressOptions longPressOptions) {
        ActionParameter action = new ActionParameter("longPress", longPressOptions);
        parameterBuilder.add(action);
        //noinspection unchecked
        return (T) this;
    }

    /**
     * Press and hold the at the center of an element until the context menu event has fired.
     *
     * @param longPressOptions see {@link WebElementOption} and {@link PositionOffsetOption}.
     * @return this TouchAction, for chaining.
     */
    public <S extends PositionOffsetOption> T longPress(S longPressOptions) {
        ActionParameter action = new ActionParameter("longPress", longPressOptions);
        parameterBuilder.add(action);
        //noinspection unchecked
        return (T) this;
    }

    /**
     * Press and hold the at the center of an element until the contextmenu event has fired.
     *
     * @param el element to long-press.
     * @return this TouchAction, for chaining.
     * @deprecated use {@link #longPress(PositionOffsetOption)} instead
     */
    @Deprecated
    public T longPress(WebElement el) {
        return longPress(elementOption(el));
    }

    /**
     * Press and hold the at the center of an element until the contextmenu event has fired.
     *
     * @param el       element to long-press.
     * @param duration of the long-press. Minimum time resolution unit is one millisecond.
     * @return this TouchAction, for chaining.
     * @deprecated use {@link #longPress(LongPressOptions)} instead
     */
    @Deprecated
    public T longPress(WebElement el, Duration duration) {
        return longPress(longPressOptions()
                .withDuration(duration).withOffset(elementOption(el)));
    }

    /**
     * Press and hold the at an absolute position on the screen
     * until the contextmenu event has fired.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @return this TouchAction, for chaining.
     * @deprecated use {@link #longPress(PositionOffsetOption)} instead
     */
    @Deprecated
    public T longPress(int x, int y) {
        return (T) longPress(positionOffsetOption(x, y));
    }

    /**
     * Press and hold the at an absolute position on the screen until the
     * contextmenu event has fired.
     *
     * @param x        x coordinate.
     * @param y        y coordinate.
     * @param duration of the long-press. Minimum time resolution unit is one millisecond.
     * @return this TouchAction, for chaining.
     * @deprecated use {@link #longPress(LongPressOptions)} instead
     */
    @Deprecated
    public T longPress(int x, int y, Duration duration) {
        return (T) longPress(longPressOptions()
                .withDuration(duration).withOffset(positionOffsetOption(x, y)));
    }

    /**
     * Press and hold the at an elements upper-left corner, offset by the given amount,
     * until the contextmenu event has fired.
     *
     * @param el element to long-press.
     * @param x  x offset.
     * @param y  y offset.
     * @return this TouchAction, for chaining.
     * @deprecated use {@link #longPress(PositionOffsetOption)} instead
     */
    @Deprecated
    public T longPress(WebElement el, int x, int y) {
        return longPress(elementOption(el, x, y));
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
     * @deprecated use {@link #longPress(LongPressOptions)} instead
     */
    @Deprecated
    public TouchAction longPress(WebElement el, int x, int y, Duration duration) {
        return longPress(longPressOptions()
                .withOffset(elementOption(el, x, y)).withDuration(duration));
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
    public T perform() {
        performsTouchActions.performTouchAction(this);
        //noinspection unchecked
        return (T) this;
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
    protected T clearParameters() {
        parameterBuilder = ImmutableList.builder();
        //noinspection unchecked
        return (T) this;
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

        public ActionParameter(String actionName, ActionOptions opts) {
            checkNotNull(opts);
            this.actionName = actionName;
            optionsBuilder = ImmutableMap.builder();
            //noinspection unchecked
            optionsBuilder.putAll(opts.build());
        }

        public ImmutableMap<String, Object> getParameterMap() {
            ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
            builder.put("action", actionName).put("options", optionsBuilder.build());
            return builder.build();
        }
    }
}
