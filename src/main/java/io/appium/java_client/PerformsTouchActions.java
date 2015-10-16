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

public interface PerformsTouchActions {
    /**
     * Performs a chain of touch actions, which together can be considered an
     * entire gesture. See the Webriver 3 spec
     * https://dvcs.w3.org/hg/webdriver/raw-file/default/webdriver-spec.html
     * It's more convenient to call the perform() method of the TouchAction
     * object itself.
     *
     * @param touchAction A TouchAction object, which contains a list of individual
     *                    touch actions to perform
     * @return the same touch action object
     */
    public TouchAction performTouchAction(TouchAction touchAction);

    /**
     * Performs multiple TouchAction gestures at the same time, to simulate
     * multiple fingers/touch inputs. See the Webriver 3 spec
     * https://dvcs.w3.org/hg/webdriver/raw-file/default/webdriver-spec.html
     * It's more convenient to call the perform() method of the MultiTouchAction
     * object.
     *
     * @param multiAction the MultiTouchAction object to perform.
     */
    public void performMultiTouchAction(MultiTouchAction multiAction);
}
