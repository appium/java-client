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

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.unpackWebDriverFromSearchContext;

public class Widget implements SearchContext, WrapsDriver, WrapsElement {

    private final WebElement element;

    protected Widget(WebElement element, TimeOutDuration timeOutDuration) {
        this.element = element;
        PageFactory.initElements(new AppiumFieldDecorator(this, timeOutDuration), this);
    }

    protected Widget(WebElement element, long implicitlyWaitTimeOut, TimeUnit timeUnit) {
        this.element = element;
        PageFactory.initElements(new AppiumFieldDecorator(this, implicitlyWaitTimeOut, timeUnit), this);
    }

    protected Widget(WebElement element) {
        this.element = element;
        PageFactory.initElements(new AppiumFieldDecorator(this), this);
    }

    @Override
    public List<WebElement> findElements(By by) {
        return element.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return element.findElement(by);
    }

    @Override
    public WebDriver getWrappedDriver() {
        return unpackWebDriverFromSearchContext(element);
    }

    @Override
    public WebElement getWrappedElement() {
        return element;
    }
}
