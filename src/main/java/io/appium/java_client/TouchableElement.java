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

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.FindsByClassName;
import org.openqa.selenium.internal.FindsByCssSelector;
import org.openqa.selenium.internal.FindsById;
import org.openqa.selenium.internal.FindsByLinkText;
import org.openqa.selenium.internal.FindsByName;
import org.openqa.selenium.internal.FindsByTagName;
import org.openqa.selenium.internal.FindsByXPath;

import java.util.List;

/**
 * It supposed that mobile elements could be tappable, swipeable, zoomable and so on.
 * This interface extends {@link WebElement} and describes this behavior.
 */
public interface TouchableElement<T extends WebElement> extends WebElement, FindsByClassName,
        FindsByCssSelector, FindsById,
        FindsByLinkText, FindsByName, FindsByTagName, FindsByXPath {

    List<T> findElements(By by);

    T findElement(By by);

    T findElementByClassName(String className);

    List<T> findElementsByClassName(String className);

    T findElementByCssSelector(String cssSelector);

    List<T> findElementsByCssSelector(String cssSelector);

    T findElementById(String id);

    List<T> findElementsById(String id);

    T findElementByLinkText(String linkText);

    List<T> findElementsByLinkText(String linkText);

    T findElementByPartialLinkText(String partialLinkText);

    List<T> findElementsByPartialLinkText(String partialLinkText);

    T findElementByName(String name);

    List<T> findElementsByName(String name);

    T findElementByTagName(String tagName);

    List<T> findElementsByTagName(String tagName);

    T findElementByXPath(String xPath);

    List<T> findElementsByXPath(String xPath);

    /**
     * Convenience method for pinching the given element.
     * "pinching" refers to the action of two appendages pressing the screen
     * and sliding towards each other.
     * NOTE:
     * This convenience method places the initial touches around the element,
     * if this would happen to place one of them off the screen, appium with return an
     * outOfBounds error. In this case, revert to using the MultiTouchAction api
     * instead of this method.
     */
    void pinch();

    /**
     * Convenience method for tapping the center of the given element.
     *
     * @param fingers  number of fingers/appendages to tap with.
     * @param duration how long between pressing down, and lifting fingers/appendages.
     */
    void tap(int fingers, int duration);

    /**
     * Convenience method for "zooming in" on the given element.
     * "zooming in" refers to the action of two appendages pressing the screen and
     * sliding away from each other.
     * NOTE:
     * This convenience method slides touches away from the element, if this would happen to
     * place one of them off the screen, appium will return an outOfBounds error.
     * In this case, revert to using the MultiTouchAction api instead of this method.
     */
    void zoom();

    /**
     * Convenience method for swiping on the given element to the given direction.
     *
     * @param direction UP, DOWN, LEFT, RIGHT.
     * @param duration  amount of time in milliseconds for the entire swipe action to
     *                  take.
     */
    void swipe(SwipeElementDirection direction, int duration);


    /**
     * Convenience method for swiping on the given element to the given direction.
     *
     * @param direction           direction UP, DOWN, LEFT, RIGHT.
     * @param offsetFromStartBorder is the offset from the border of the element where the
     *                            swiping should be started. If direction is UP then
     *                            this is offset from the bottom of the element.
     *                            If direction is DOWN then this is offset from the top of
     *                            the element. If direction is RIGHT then this is offset from
     *                            the left border of the element. If direction is LEFT then
     *                            this is offset from the right border of the element.
     * @param offsetFromEndBorder is the offset from the border of the element where
     *                            the swiping should be finished. If direction is UP then
     *                            this is offset from the top of the element.
     *                            If direction is DOWN then this is offset from the bottom
     *                            of the element. If direction is RIGHT then
     *                            this is offset from the right border of the element.
     *                            If direction is LEFT then this is offset from the
     *                            left border of the element.
     * @param duration            amount of time in milliseconds for the entire swipe action to
     *                            take.
     * @throws IllegalCoordinatesException when resulted coordinates are out of the
     *     element borders or disagree with the given direction.
     */
    void swipe(SwipeElementDirection direction, int offsetFromStartBorder, int offsetFromEndBorder,
        int duration) throws IllegalCoordinatesException;
}
