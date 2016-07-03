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

package io.appium.java_client.events.api.general;

import io.appium.java_client.events.api.Listener;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

public interface WindowEventListener extends Listener {
    /**
     * This action will be performed each time before
     * {@link org.openqa.selenium.WebDriver.Window#setSize(Dimension)}
     *
     * @param driver WebDriver
     * @param window is the window whose size is going to be changed
     * @param targetSize is the new size
     */
    void beforeWindowChangeSize(WebDriver driver, WebDriver.Window window,
        Dimension targetSize);

    /**
     * This action will be performed each time after
     * {@link WebDriver.Window#setSize(Dimension)}
     *
     * @param driver WebDriver
     * @param window is the window whose size has been changed
     * @param targetSize is the new size
     */
    void afterWindowChangeSize(WebDriver driver, WebDriver.Window window,
        Dimension targetSize);

    /**
     * This action will be performed each time before
     * {@link WebDriver.Window#setPosition(org.openqa.selenium.Point)}
     *
     * @param driver WebDriver
     * @param window is the window whose position is going to be changed
     * @param targetPoint is the new window coordinates
     */
    void beforeWindowIsMoved(WebDriver driver, WebDriver.Window window,
        Point targetPoint);

    /**
     * This action will be performed each time after
     * {@link WebDriver.Window#setPosition(org.openqa.selenium.Point)}
     *
     * @param driver WebDriver
     * @param window is the window whose position has been changed
     * @param targetPoint is the new window coordinates
     */
    void afterWindowIsMoved(WebDriver driver, WebDriver.Window window,
        Point targetPoint);


    /**
     * This action will be performed each time before
     * {@link WebDriver.Window#maximize()}
     *
     * @param driver WebDriver
     * @param window is the window which is going to be maximized
     */
    void beforeWindowIsMaximized(WebDriver driver, WebDriver.Window window);

    /**
     * This action will be performed each time after
     * {@link WebDriver.Window#maximize()}
     *
     * @param driver WebDriver
     * @param window is the window which has been maximized
     */
    void afterWindowIsMaximized(WebDriver driver, WebDriver.Window window);
}
