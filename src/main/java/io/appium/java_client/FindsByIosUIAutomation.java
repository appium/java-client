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

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.List;

@Deprecated
public interface FindsByIosUIAutomation<T extends WebElement> extends FindsByFluentSelector<T> {
    /**
     * Method performs the searching for a single element by iOS UIAutomation selector
     * and value of the given selector.
     *
     * @param using an iOS UIAutomation selector
     * @return The first element that matches the given selector
     *
     * @throws WebDriverException This method is not applicable with browser/webview UI.
     * @throws NoSuchElementException when no one element is found
     */
    default T findElementByIosUIAutomation(String using) {
        return findElement(MobileSelector.IOS_UI_AUTOMATION.toString(), using);
    }

    /**
     * Method performs the searching for a list of elements by iOS UIAutomation selector
     * and value of the given selector.
     *
     * @param using an iOS UIAutomation selector
     * @return a list of elements that match the given selector
     *
     * @throws WebDriverException This method is not applicable with browser/webview UI.
     */
    default List<T> findElementsByIosUIAutomation(String using) {
        return findElements(MobileSelector.IOS_UI_AUTOMATION.toString(), using);
    }
}
