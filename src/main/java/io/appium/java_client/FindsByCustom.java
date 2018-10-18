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
import org.openqa.selenium.WebElement;

import java.util.List;

public interface FindsByCustom<T extends WebElement> extends FindsByFluentSelector<T> {
    /**
     * Performs the lookup for a single element by sending a selector to a custom element finding
     * plugin. This type of locator requires the use of the 'customFindModules' capability and a
     * separately-installed element finding plugin.
     *
     * @param selector selector to pass to the custom element finding plugin
     * @return The first element that matches the given selector
     * @see <a href="https://appium.io/docs/en/advanced-concepts/element-finding-plugins/">
     * The documentation on custom element finding plugins and their use</a>
     * @throws NoSuchElementException when no element is found
     * @since Appium 1.9.2
     */
    default T findElementByCustom(String selector) {
        return findElement(MobileSelector.CUSTOM.toString(), selector);
    }

    /**
     * Performs the lookup for a single element by sending a selector to a custom element finding
     * plugin. This type of locator requires the use of the 'customFindModules' capability and a
     * separately-installed element finding plugin.
     *
     * @param selector selector to pass to the custom element finding plugin
     * @return a list of elements that match the given selector or an empty list
     * @see <a href="https://appium.io/docs/en/advanced-concepts/element-finding-plugins/">
     * The documentation on custom element finding plugins and their use</a>
     * @since Appium 1.9.2
     */
    default List<T> findElementsByCustom(String selector) {
        return findElements(MobileSelector.CUSTOM.toString(), selector);
    }
}