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

package io.appium.java_client.mac;

import io.appium.java_client.FindsByFluentSelector;
import io.appium.java_client.MobileSelector;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface FindsByClassChain<T extends WebElement> extends FindsByFluentSelector<T> {

    /**
     * Perform single element lookup by class chain expression.
     * Read https://github.com/appium/appium-mac2-driver#element-location
     * for more details on elements location strategies supported by Mac2 driver.
     *
     * @param using A valid class chain lookup expression.
     * @return The found element
     */
    default T findElementByClassChain(String using) {
        return findElement(MobileSelector.IOS_CLASS_CHAIN.toString(), using);
    }

    /**
     * Perform multiple elements lookup by class chain search expression.
     * Read https://github.com/appium/appium-mac2-driver#element-location
     * for more details on elements location strategies supported by Mac2 driver.
     *
     * @param using A valid class chain lookup expression.
     * @return The array of found elements or an empty one if no matches have been found.
     */
    default List<T> findElementsByClassChain(String using) {
        return findElements(MobileSelector.IOS_CLASS_CHAIN.toString(), using);
    }
}
