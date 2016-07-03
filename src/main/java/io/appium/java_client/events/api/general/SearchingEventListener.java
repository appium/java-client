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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface SearchingEventListener extends Listener {

    /**
     * Called before {@link org.openqa.selenium.WebDriver#findElement WebDriver.findElement(...)},
     * or
     * {@link org.openqa.selenium.WebDriver#findElements WebDriver.findElements(...)}, or
     * {@link org.openqa.selenium.WebElement#findElement WebElement.findElement(...)}, or
     * {@link org.openqa.selenium.WebElement#findElement WebElement.findElements(...)}.
     *
     * @param element will be <code>null</code>, if a find method of <code>WebDriver</code>
     *                is called.
     * @param by locator being used
     * @param driver WebDriver
     */
    void beforeFindBy(By by, WebElement element, WebDriver driver);

    /**
     * Called after {@link org.openqa.selenium.WebDriver#findElement WebDriver.findElement(...)},
     * or
     * {@link org.openqa.selenium.WebDriver#findElements WebDriver.findElements(...)}, or
     * {@link org.openqa.selenium.WebElement#findElement WebElement.findElement(...)}, or
     * {@link org.openqa.selenium.WebElement#findElement WebElement.findElements(...)}.
     *
     * @param element will be <code>null</code>, if a find method of <code>WebDriver</code>
     *                is called.
     * @param by locator being used
     * @param driver WebDriver
     */
    void afterFindBy(By by, WebElement element, WebDriver driver);
}
