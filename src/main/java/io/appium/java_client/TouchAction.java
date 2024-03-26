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

import io.appium.java_client.touch.ActionOptions;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 * Used for Webdriver 3 touch actions
 * See the Webriver 3 spec
 * https://dvcs.w3.org/hg/webdriver/raw-file/default/webdriver-spec.html
 * The flow is to chain individual touch actions into an entire gesture. e.g.
 * TouchAction action = new TouchAction(performsTouchActions);
 * action.press(element).waitAction(300).moveTo(element1).release().perform();
 * Calling perform() sends the action command to the Mobile Driver. Otherwise,
 * more and more actions can be chained.
 *
 * @deprecated Touch actions are deprecated.
 *     Please use W3C Actions instead or the corresponding
 *     extension methods for the driver (if available).
 *     Check
 *     - https://www.youtube.com/watch?v=oAJ7jwMNFVU
 *     - https://appiumpro.com/editions/30-ios-specific-touch-action-methods
 *     - https://appiumpro.com/editions/29-automating-complex-gestures-with-the-w3c-actions-api
 *     for more details.
 */
@Deprecated
public class TouchAction<T extends TouchAction<T>> implements PerformsActions<T> {

    protected List<ActionParameter> parameters;
    private PerformsTouchActions performsTouchActions;

    public TouchAction(PerformsTouchActions performsTouchActions) {
        this.performsTouchActions = requireNonNull(performsTouchActions);
        parameters = new ArrayList<>();
    }

    /**
     * Press action on the screen.
     *
     * @param pressOptions see {@link PointOption} and {@link ElementOption}.
     * @return this TouchAction, for chaining.
     */
    public T press(PointOption pressOptions) {
        parameters.add(new ActionParameter("press", pressOptions));
        //noinspection unchecked
        return (T) this;
    }

    /**
     * Remove the current touching implement from the screen (withdraw your touch).
     *
     * @return this TouchAction, for chaining.
     */
    public T release() {
        parameters.add(new ActionParameter("release"));
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
        parameters.add(new ActionParameter("moveTo", moveToOptions));
        return (T) this;
    }

    /**
     * Tap on an element.
     *
     * @param tapOptions see {@link TapOptions}.
     * @return this TouchAction, for chaining.
     */
    public T tap(TapOptions tapOptions) {
        parameters.add(new ActionParameter("tap", tapOptions));
        return (T) this;
    }

    /**
     * Tap on a position.
     *
     * @param tapOptions see {@link PointOption} and {@link ElementOption}
     * @return this TouchAction, for chaining.
     */
    public T tap(PointOption tapOptions) {
        parameters.add(new ActionParameter("tap", tapOptions));
        return (T) this;
    }

    /**
     * A wait action, used as a NOP in multi-chaining.
     *
     * @return this TouchAction, for chaining.
     */
    public T waitAction() {
        parameters.add(new ActionParameter("wait"));
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
        parameters.add(new ActionParameter("wait", waitOptions));
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
        parameters.add(new ActionParameter("longPress", longPressOptions));
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
        parameters.add(new ActionParameter("longPress", longPressOptions));
        //noinspection unchecked
        return (T) this;
    }

    /**
     * Cancel this action, if it was partially completed by the performsTouchActions.
     */
    public void cancel() {
        parameters.add(new ActionParameter("cancel"));
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
        return Map.of("actions",
                parameters.stream().map(ActionParameter::getParameterMap).collect(toList())
        );
    }

    /**
     * Clears all the existing action parameters and resets the instance to the initial state.
     *
     * @return this TouchAction, for possible segmented-touches.
     */
    protected T clearParameters() {
        parameters = new ArrayList<>();
        //noinspection unchecked
        return (T) this;
    }

    /**
     * Just holds values to eventually return the parameters required for the mjsonwp.
     */
    protected class ActionParameter {
        private final String actionName;
        private final Map<String, Object> options;

        public ActionParameter(String actionName) {
            this.actionName = actionName;
            options = new HashMap<>();
        }

        public ActionParameter(String actionName, ActionOptions opts) {
            this(actionName);
            requireNonNull(opts);
            //noinspection unchecked
            options.putAll(opts.build());
        }

        public Map<String, Object> getParameterMap() {
            return Map.of(
                    "action", actionName,
                    "options", Collections.unmodifiableMap(options)
            );
        }
    }
}
