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

import java.util.List;
import java.util.Map;

import static io.appium.java_client.MobileCommand.PERFORM_MULTI_TOUCH;
import static io.appium.java_client.MobileCommand.PERFORM_TOUCH_ACTION;

/**
 * Touch actions are deprecated.
 * Please use W3C Actions instead or the corresponding
 * extension methods for the driver (if available).
 * Check
 * - https://www.youtube.com/watch?v=oAJ7jwMNFVU
 * - https://appiumpro.com/editions/30-ios-specific-touch-action-methods
 * - https://appiumpro.com/editions/29-automating-complex-gestures-with-the-w3c-actions-api
 * for more details.
 */
@Deprecated
@SuppressWarnings({"unchecked", "rawtypes"})
public interface PerformsTouchActions extends ExecutesMethod {
    /**
     * Performs a chain of touch actions, which together can be considered an
     * entire gesture. See the Webriver 3 spec
     * https://dvcs.w3.org/hg/webdriver/raw-file/default/webdriver-spec.html
     * It's more convenient to call the perform() method of the TouchAction
     * object itself.
     * All the existing touch action parameters will be wiped out after this method
     * is called.
     *
     * @param touchAction A TouchAction object, which contains a list of individual
     *                    touch actions to perform
     * @return the same touch action object
     */
    @Deprecated
    default TouchAction performTouchAction(TouchAction touchAction) {
        Map<String, List<Object>> parameters = touchAction.getParameters();
        execute(PERFORM_TOUCH_ACTION, parameters);
        return touchAction.clearParameters();
    }

    /**
     * Performs multiple TouchAction gestures at the same time, to simulate
     * multiple fingers/touch inputs. See the Webriver 3 spec
     * https://dvcs.w3.org/hg/webdriver/raw-file/default/webdriver-spec.html
     * It's more convenient to call the perform() method of the MultiTouchAction
     * object.
     * All the existing multi touch actions will be wiped out after this method
     * is called.
     *
     * @param multiAction the MultiTouchAction object to perform.
     * @return MultiTouchAction instance for chaining.
     */
    @Deprecated
    default MultiTouchAction performMultiTouchAction(MultiTouchAction multiAction) {
        Map<String, List<Object>> parameters = multiAction.getParameters();
        execute(PERFORM_MULTI_TOUCH, parameters);
        return multiAction.clearActions();
    }
}
