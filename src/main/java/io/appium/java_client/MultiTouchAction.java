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

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;

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
public class MultiTouchAction implements PerformsActions<MultiTouchAction> {

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
    public MultiTouchAction perform() {
        List<TouchAction> touchActions = actions.build();
        checkArgument(touchActions.size() > 0,
                "MultiTouch action must have at least one TouchAction added before it can be performed");
        if (touchActions.size() > 1) {
            performsTouchActions.performMultiTouchAction(this);
            return this;
        }  //android doesn't like having multi-touch actions with only a single TouchAction...
        performsTouchActions.performTouchAction(touchActions.get(0));
        return this;
    }

    protected Map<String, List<Object>> getParameters() {
        return Map.of("actions",
                actions.build().stream().map(touchAction ->
                        touchAction.getParameters().get("actions")).collect(toList())
        );
    }

    /**
     * Clears all the existing touch actions and resets the instance to the initial state.
     *
     * @return this MultiTouchAction, for possible segmented-touches.
     */
    protected MultiTouchAction clearActions() {
        actions = ImmutableList.builder();
        return this;
    }
}
