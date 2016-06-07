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

import org.openqa.selenium.*;
import org.openqa.selenium.html5.LocationContext;
import org.openqa.selenium.internal.*;
import org.openqa.selenium.remote.Response;

import java.util.List;
import java.util.Map;

public interface MobileDriver<T extends WebElement> extends WebDriver, PerformsTouchActions, ContextAware, Rotatable,
    FindsByAccessibilityId<T>, LocationContext, DeviceActionShortcuts, TouchShortcuts,
    InteractsWithFiles, InteractsWithApps, HasAppStrings, FindsByClassName, FindsByCssSelector, FindsById,
        FindsByLinkText, FindsByName, FindsByTagName, FindsByXPath {

    Response execute(String driverCommand, Map<String, ?> parameters);

    List<T> findElements(By by);

    T findElement(By by);

    T findElementByClassName(String className);

    List<T> findElementsByClassName(String className);

    T findElementByCssSelector(String cssSelector);

    List<T> findElementsByCssSelector(String cssSelector);

    T findElementById(String id);

    List<T> findElementsById(String id);

    T findElementByLinkText(String linkText);

    List<T> findElementsByLinkText(String linkText);

    T findElementByPartialLinkText(String partialLinkText);

    List<T> findElementsByPartialLinkText(String partialLinkText);

    T findElementByName(String name);

    List<T> findElementsByName(String name);

    T findElementByTagName(String tagName);

    List<T> findElementsByTagName(String tagName);

    T findElementByXPath(String xPath);

    List<T> findElementsByXPath(String xPath);
}
