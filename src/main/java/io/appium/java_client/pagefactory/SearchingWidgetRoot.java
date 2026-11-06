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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.WrapsElement;

import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.function.Supplier;

/**
 * Widget root that repeats a nested lookup on later alternative roots.
 * Every call other than {@code findElement} and {@code findElements} is forwarded
 * to the first matching element, including {@code getDomAttribute} and {@code getShadowRoot}.
 *
 * <p>The proxy implements the interfaces of that element, not its concrete class.
 * {@code widget.getWrappedElement()} is therefore not an {@code AppiumWebElement} when
 * several root locators are in use, and a cast to that class fails. The real element is
 * {@code ((WrapsElement) widget.getWrappedElement()).getWrappedElement()}. A widget with
 * one locator is not wrapped, so that cast is unchanged.
 *
 * <p>The whole {@link By} is applied to one root before the next. A {@code ByAll}
 * or {@code ByChained} finishes on the first root before any later root is considered.
 * Each of those driver calls uses the session implicit wait. This type does not change
 * that wait. {@code AppiumElementLocator}'s own timeout does not interrupt a call that
 * is already blocked inside the driver.
 */
public interface SearchingWidgetRoot extends WebElement, WrapsDriver, WrapsElement {

    /**
     * Returns a root that searches {@code furtherRoots} after {@code resolved} misses.
     * {@link #getWrappedElement()} is {@code resolved}.
     *
     * @param resolved the first matching root
     * @param furtherRoots later roots, queried again on each lookup
     * @return a proxy around {@code resolved}
     */
    static WebElement wrap(WebElement resolved, Supplier<Iterator<WebElement>> furtherRoots) {
        return (WebElement) Proxy.newProxyInstance(
                SearchingWidgetRoot.class.getClassLoader(),
                SearchingWidgetRootHandler.proxyInterfaces(resolved),
                new SearchingWidgetRootHandler(resolved, furtherRoots)
        );
    }
}
