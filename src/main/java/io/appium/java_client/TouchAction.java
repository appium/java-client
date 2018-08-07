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
import static com.google.common.collect.ImmutableList.builder;
import static java.util.stream.Collectors.toList;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import io.appium.java_client.touch.ActionOptions;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

import java.util.List;
import java.util.Map;

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
        this.performsTouchActions = checkNotNull(performsTouchActions);
        parameterBuilder = builder();
    }

    /**
     * Press action on the screen.
     *
     * @param pressOptions see {@link PointOption} and {@link ElementOption}.
     * @return this TouchAction, for chaining.
     */
    public T press(PointOption pressOptions) {
        parameterBuilder.add(new ActionParameter("press", pressOptions));
        //noinspection unchecked
        return (T) this;
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
     * @param  moveToOptions see {@link PointOption} and {@link ElementOption}
     *                       Important: some older Appium drivers releases have a bug when moveTo
     *                       coordinates are calculated as relative to the recent pointer position
     *                       in the chain instead of being absolute.
     *                       @see <a href="https://github.com/appium/appium/issues/7486">Appium Issue #7486
     *                       for more details.</a>
     * @return this TouchAction, for chaining.
     */
    public T moveTo(PointOption moveToOptions) {
        ActionParameter action = new ActionParameter("moveTo", moveToOptions);
        parameterBuilder.add(action);
        return (T) this;
    }

    /**
     * Tap on an element.
     *
     * @param tapOptions see {@link TapOptions}.
     * @return this TouchAction, for chaining.
     */
    public T tap(TapOptions tapOptions) {
        ActionParameter action = new ActionParameter("tap", tapOptions);
        parameterBuilder.add(action);
        return (T) this;
    }

    /**
     * Tap on a position.
     *
     * @param tapOptions see {@link PointOption} and {@link ElementOption}
     * @return this TouchAction, for chaining.
     */
    public T tap(PointOption tapOptions) {
        ActionParameter action = new ActionParameter("tap", tapOptions);
        parameterBuilder.add(action);
        return (T) this;
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
     * @param longPressOptions see {@link PointOption} and {@link ElementOption}.
     * @return this TouchAction, for chaining.
     */
    public T longPress(PointOption longPressOptions) {
        ActionParameter action = new ActionParameter("longPress", longPressOptions);
        parameterBuilder.add(action);
        //noinspection unchecked
        return (T) this;
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
    protected Map<String, List<Object>> getParameters() {
        List<ActionParameter> actionList = parameterBuilder.build();
        return ImmutableMap.of("actions", actionList.stream()
                .map(ActionParameter::getParameterMap).collect(toList()));
    }

    /**
     * Clears all the existing action parameters and resets the instance to the initial state.
     *
     * @return this TouchAction, for possible segmented-touches.
     */
    protected T clearParameters() {
        parameterBuilder = builder();
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
