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

import java.util.List;

public interface FindsByAndroidUIAutomator<T extends WebElement> extends FindsByFluentSelector<T> {

    /**
     * @throws {@link org.openqa.selenium.WebDriverException} This method is not
     *      applicable with browser/webview UI.
     * @throws {@link org.openqa.selenium.NoSuchElementException} when no one element is found
     */
    default T findElementByAndroidUIAutomator(String using) {
        return findElement(MobileSelector.ANDROID_UI_AUTOMATOR.toString(), using);
    }

    /**
     * @throws {@link org.openqa.selenium.WebDriverException} This method is not
     *     applicable with browser/webview UI.
     */
    default List<T> findElementsByAndroidUIAutomator(String using) {
        return findElements(MobileSelector.ANDROID_UI_AUTOMATOR.toString(), using);
    }
}
