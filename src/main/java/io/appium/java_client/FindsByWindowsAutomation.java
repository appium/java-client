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

public interface FindsByWindowsAutomation<T extends WebElement>  {

    /**
     * Finds the first of elements that match the Windows UIAutomation selector supplied.
     *
     * @param selector a Windows UIAutomation selector
     * @return The first element that matches the given selector
     */
    T findElementByWindowsUIAutomation(String selector);

    /**
     * Finds a list of elements that match the Windows UIAutomation selector supplied.
     *
     * @param selector a Windows UIAutomation selector
     * @return a list of elements that match the given selector
     */
    List<T> findElementsByWindowsUIAutomation(String selector);
}
