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

package io.appium.java_client.support;

import org.jspecify.annotations.NullMarked;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Finds elements by their id first and then by their name.
 * Adapted from Selenium's {@code org.openqa.selenium.support.ByIdOrName} (Apache License 2.0).
 */
@NullMarked
public class ByIdOrName extends By implements Serializable {
    private static final long serialVersionUID = 3986638402799576701L;

    private final By idFinder;
    private final By nameFinder;
    private final String idOrName;

    /**
     * Creates a locator that looks for the given value in the id and name attributes.
     *
     * @param idOrName the value to look for
     */
    public ByIdOrName(String idOrName) {
        this.idOrName = idOrName;
        idFinder = By.id(idOrName);
        nameFinder = By.name(idOrName);
    }

    @Override
    public WebElement findElement(SearchContext context) {
        try {
            return idFinder.findElement(context);
        } catch (NoSuchElementException e) {
            return nameFinder.findElement(context);
        }
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        List<WebElement> elements = new ArrayList<>();
        elements.addAll(idFinder.findElements(context));
        elements.addAll(nameFinder.findElements(context));
        return elements;
    }

    @Override
    public String toString() {
        return "by id or name \"" + idOrName + '"';
    }
}
