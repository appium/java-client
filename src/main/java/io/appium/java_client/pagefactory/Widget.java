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

package io.appium.java_client.pagefactory;

import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility
    .unpackWebDriverFromSearchContext;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;

import java.util.List;

/**
 * It is the Appium-specific extension of the Page Object design pattern. It allows user
 * to create objects which typify some element with nested sub-elements. Also it allows to
 * describe and encapsulate logic of interaction/behavior within.
 * About the Page Object design pattern please read these documents:
 * - https://code.google.com/p/selenium/wiki/PageObjects
 * - https://code.google.com/p/selenium/wiki/PageFactory
 */
public abstract class Widget implements SearchContext, WrapsDriver, WrapsElement {

    private final SearchContext element;

    protected Widget(WebElement element) {
        this.element = element;
    }

    @Override public List<WebElement> findElements(By by) {
        return element.findElements(by);
    }

    @Override public WebElement findElement(By by) {
        return element.findElement(by);
    }

    @Override public WebDriver getWrappedDriver() {
        return unpackWebDriverFromSearchContext(element);
    }

    @Override public WebElement getWrappedElement() {
        return (WebElement) element;
    }

    public Widget getSelfReference() {
        return this;
    }
}
