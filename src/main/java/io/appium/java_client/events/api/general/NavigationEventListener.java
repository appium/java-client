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
import org.openqa.selenium.WebDriver;

public interface NavigationEventListener extends Listener {

    /**
     * Called before {@link org.openqa.selenium.WebDriver#get get(String url)}
     * respectively {@link org.openqa.selenium.WebDriver.Navigation#to
     * navigate().to(String url)}.
     *
     * @param url URL
     * @param driver WebDriver
     */
    void beforeNavigateTo(String url, WebDriver driver);

    /**
     * Called after {@link org.openqa.selenium.WebDriver#get get(String url)}
     * respectively {@link org.openqa.selenium.WebDriver.Navigation#to
     * navigate().to(String url)}. Not called, if an exception is thrown.
     *
     * @param url URL
     * @param driver WebDriver
     */
    void afterNavigateTo(String url, WebDriver driver);

    /**
     * Called before {@link org.openqa.selenium.WebDriver.Navigation#back
     * navigate().back()}.
     *
     * @param driver WebDriver
     */
    void beforeNavigateBack(WebDriver driver);

    /**
     * Called after {@link org.openqa.selenium.WebDriver.Navigation
     * navigate().back()}. Not called, if an exception is thrown.
     *
     * @param driver WebDriver
     */
    void afterNavigateBack(WebDriver driver);

    /**
     * Called before {@link org.openqa.selenium.WebDriver.Navigation#forward
     * navigate().forward()}.
     *
     * @param driver WebDriver
     */
    void beforeNavigateForward(WebDriver driver);

    /**
     * Called after {@link org.openqa.selenium.WebDriver.Navigation#forward
     * navigate().forward()}. Not called, if an exception is thrown.
     *
     * @param driver WebDriver
     */
    void afterNavigateForward(WebDriver driver);

    /**
     * Called before {@link org.openqa.selenium.WebDriver.Navigation#refresh navigate().refresh()}.
     *
     * @param driver WebDriver
     */
    void beforeNavigateRefresh(WebDriver driver);

    /**
     * Called after {@link org.openqa.selenium.WebDriver.Navigation#refresh navigate().refresh()}.
     *
     * @param driver WebDriver
     */
    void afterNavigateRefresh(WebDriver driver);
}
