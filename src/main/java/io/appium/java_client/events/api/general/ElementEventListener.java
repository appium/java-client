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
import org.openqa.selenium.WebElement;

public interface ElementEventListener extends Listener {
    /**
     * Called before {@link org.openqa.selenium.WebElement#click WebElement.click()}.
     *
     * @param driver WebDriver
     * @param element the WebElement being used for the action
     */
    void beforeClickOn(WebElement element, WebDriver driver);

    /**
     * Called after {@link org.openqa.selenium.WebElement#click WebElement.click()}.
     * Not called, if an exception is thrown.
     *
     * @param driver WebDriver
     * @param element the WebElement being used for the action
     */
    void afterClickOn(WebElement element, WebDriver driver);

    /**
     * Called before {@link org.openqa.selenium.WebElement#clear WebElement.clear()},
     * {@link org.openqa.selenium.WebElement#sendKeys WebElement.sendKeys(...)}.
     *
     * @param driver WebDriver
     * @param element the WebElement being used for the action
     */
    void beforeChangeValueOf(WebElement element, WebDriver driver);

    /**
     * Called after {@link org.openqa.selenium.WebElement#clear WebElement.clear()},
     * {@link org.openqa.selenium.WebElement#sendKeys WebElement.sendKeys(...)} .
     * Not called, if an exception is thrown.
     *
     * @param driver WebDriver
     * @param element the WebElement being used for the action
     */
    void afterChangeValueOf(WebElement element, WebDriver driver);
}
