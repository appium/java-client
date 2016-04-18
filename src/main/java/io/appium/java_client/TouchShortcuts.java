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

public interface TouchShortcuts {

    /**
     * Convenience method for "zooming in" on an element on the screen.
     * "zooming in" refers to the action of two appendages pressing the
     * screen and sliding away from each other.
     * NOTE:
     * This convenience method slides touches away from the element,
     * if this would happen to place one of them off the screen, appium will return an
     * outOfBounds error. In this case, revert to using the MultiTouchAction api
     * instead of this method.
     *
     * @param x x coordinate to start zoom on.
     * @param y y coordinate to start zoom on.
     */
    void zoom(int x, int y);

    /**
     * Convenience method for "zooming in" on an element on the screen.
     * "zooming in" refers to the action of two appendages pressing the screen
     * and sliding away from each other.
     * NOTE:
     * This convenience method slides touches away from the element, if this would
     * happen to place one of them off the screen, appium will return an outOfBounds
     * error. In this case, revert to using the MultiTouchAction api
     * instead of this method.
     *
     * @param el The element to pinch.
     */
    void zoom(WebElement el);

    /**
     * Convenience method for tapping a position on the screen.
     *
     * @param fingers  number of fingers/appendages to tap with.
     * @param x        x coordinate.
     * @param y        y coordinate.
     * @param duration how long between pressing down, and lifting fingers/appendages.
     */
    void tap(int fingers, int x, int y, int duration);

    /**
     * Convenience method for tapping the center of an element on the screen.
     *
     * @param fingers  number of fingers/appendages to tap with.
     * @param element  element to tap.
     * @param duration how long between pressing down, and lifting fingers/appendages.
     */
    void tap(int fingers, WebElement element, int duration);

    /**
     * Convenience method for swiping across the screen.
     *
     * @param startx   starting x coordinate.
     * @param starty   starting y coordinate.
     * @param endx     ending x coordinate.
     * @param endy     ending y coordinate.
     * @param duration amount of time in milliseconds for the entire swipe action to take
     */
    void swipe(int startx, int starty, int endx, int endy, int duration);

    /**
     * Convenience method for pinching an element on the screen.
     * "pinching" refers to the action of two appendages pressing the screen
     * and sliding towards each other.
     * NOTE:
     * This convenience method places the initial touches around the element at a distance,
     * if this would happen to place one of them off the screen, appium will return an
     * outOfBounds error. In this case, revert to using the MultiTouchAction api
     * instead of this method.
     *
     * @param x x coordinate to terminate the pinch on.
     * @param y y coordinate to terminate the pinch on.
     */
    void pinch(int x, int y);

    /**
     * Convenience method for pinching an element on the screen.
     * "pinching" refers to the action of two appendages pressing the screen and
     * sliding towards each other.
     * NOTE:
     * This convenience method places the initial touches around the element, if this would
     * happen to place one of them off the screen, appium with return an outOfBounds error.
     * In this case, revert to using the MultiTouchAction api instead of this method.
     *
     * @param el The element to pinch.
     */
    void pinch(WebElement el);

}
