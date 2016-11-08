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

import org.openqa.selenium.WebElement;

public interface CreatesSwipeAction {

    /**
     * Creates combined touch action for the swiping by start/end coordinates.
     *
     * @param startX x-coordinate to swipe from
     * @param startY y-coordinate to swipe from
     * @param endX  x-coordinate to swipe to
     * @param endY y-coordinate to swipe to
     * @param duration in milliseconds
     * @return an instance of combined {@link TouchAction}
     */
    TouchAction swipe(int startX, int startY, int endX, int endY, int duration);

    /**
     * Creates combined touch action for the swiping from given coordinates to the given element.
     *
     * @param startX x-coordinate to swipe from
     * @param startY y-coordinate to swipe from
     * @param element  an element to swipe to
     * @param duration in milliseconds
     * @return an instance of combined {@link TouchAction}
     */
    TouchAction swipe(int startX, int startY, WebElement element, int duration);

    /**
     * Creates combined touch action for the swiping from one element to another.
     *
     * @param element2  an element to swipe to
     * @param element2  an element to swipe to
     * @param duration in milliseconds
     * @return an instance of combined {@link TouchAction}
     */
    TouchAction swipe(WebElement element1, WebElement element2, int duration);


    /**
     * Creates combined touch action for the swiping inside an element.
     *
     * @param element  an element where the swiping is performed
     * @param direction  is the direction to perform the swiping inside the element
     * @param duration in milliseconds
     * @return an instance of combined {@link TouchAction}
     */
    TouchAction swipe(WebElement element, SwipeElementDirection direction, int duration);

    /**
     * Creates combined touch action for the swiping inside an element using some offset from its
     * borders.
     *
     * @param element  an element where the swiping is performed
     * @param direction  is the direction to perform the swiping inside the element
     * @param offsetFromStartBorder is the offset from the border of the element where the
     *                              swiping should be started. If direction is UP then
     *                              this is offset from the bottom of the element.
     *                              If direction is DOWN then this is offset from the top of
     *                              the element. If direction is RIGHT then this is offset from
     *                              the left border of the element. If direction is LEFT then
     *                              this is offset from the right border of the element.
     * @param offsetFromEndBorder   is the offset from the border of the element where
     *                              the swiping should be finished. If direction is UP then
     *                              this is offset from the top of the element.
     *                              If direction is DOWN then this is offset from the bottom
     *                              of the element. If direction is RIGHT then
     *                              this is offset from the right border of the element.
     *                              If direction is LEFT then this is offset from the
     *                              left border of the element.
     * @param duration in milliseconds
     * @return an instance of combined {@link TouchAction}
     * @throws IllegalCoordinatesException when resulted coordinates are out of the
     *                                     element borders or disagree with the given direction.
     */
    TouchAction swipe(WebElement element, SwipeElementDirection direction, int offsetFromStartBorder,
                      int offsetFromEndBorder,
                      int duration) throws IllegalCoordinatesException;
}
