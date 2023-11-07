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

package io.appium.java_client.pagefactory.bys.builder;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public class ByChained extends org.openqa.selenium.support.pagefactory.ByChained {

    private final By[] bys;

    private static Function<SearchContext, WebElement> getSearchingFunction(By by) {
        return input -> {
            try {
                if (input == null) {
                    return null;
                }
                return input.findElement(by);
            } catch (NoSuchElementException e) {
                return null;
            }
        };
    }

    /**
     * Finds elements that matches each of the locators in sequence.
     *
     * @param bys is a set of {@link By} which forms the chain of the searching.
     */
    public ByChained(By[] bys) {
        super(requireNonNull(bys));
        if (bys.length == 0) {
            throw new IllegalArgumentException("By array should not be empty");
        }
        this.bys = bys;
    }

    @Override
    public WebElement findElement(SearchContext context) {
        Function<SearchContext, WebElement> searchingFunction = null;
        for (By by: bys) {
            searchingFunction = Optional.ofNullable(searchingFunction)
                    .map(sf -> sf.andThen(getSearchingFunction(by)))
                    .orElseGet(() -> getSearchingFunction(by));
        }
        requireNonNull(searchingFunction);

        try {
            return new FluentWait<>(context).until(searchingFunction);
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Cannot locate an element using " + this);
        }
    }
}
