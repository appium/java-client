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

package io.appium.java_client.support.pagefactory;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Mechanism used to locate elements within a document using a series of lookups. This class will
 * find all elements that match any of the locators in sequence, e.g.
 * {@code driver.findElements(new ByAll(by1, by2))} will find all elements that match
 * <var>by1</var> and then all elements that match <var>by2</var>.
 * This means that the list of elements returned may not be in document order.
 * Adapted from Selenium's {@code org.openqa.selenium.support.pagefactory.ByAll}
 * (Apache License 2.0).
 */
public class ByAll extends By implements Serializable {
    private static final long serialVersionUID = 4573668832699497306L;

    /** The locators to search with. */
    private final By[] bys;

    /**
     * Creates a locator that matches elements found by any of the given locators.
     *
     * @param bys the locators to search with
     */
    public ByAll(By... bys) {
        this.bys = bys;
    }

    @Override
    public WebElement findElement(SearchContext context) {
        for (By by : bys) {
            List<WebElement> elements = context.findElements(by);
            if (!elements.isEmpty()) {
                return elements.get(0);
            }
        }
        throw new NoSuchElementException("Cannot locate an element using " + this);
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        List<WebElement> elems = new ArrayList<>();
        for (By by : bys) {
            elems.addAll(context.findElements(by));
        }
        return elems;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("By.all(");
        stringBuilder.append("{");

        boolean first = true;
        for (By by : bys) {
            stringBuilder.append(first ? "" : ",").append(by);
            first = false;
        }
        stringBuilder.append("})");
        return stringBuilder.toString();
    }
}
