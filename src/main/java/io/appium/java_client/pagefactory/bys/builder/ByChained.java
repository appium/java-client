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

import static com.google.common.base.Preconditions.checkNotNull;

import io.appium.java_client.functions.AppiumFunction;
import org.openqa.seleniumone.By;
import org.openqa.seleniumone.NoSuchElementException;
import org.openqa.seleniumone.SearchContext;
import org.openqa.seleniumone.TimeoutException;
import org.openqa.seleniumone.WebElement;
import org.openqa.seleniumone.support.ui.FluentWait;

import java.util.Optional;

public class ByChained extends org.openqa.seleniumone.support.pagefactory.ByChained {

    private final By[] bys;

    private static AppiumFunction<SearchContext, WebElement> getSearchingFunction(By by) {
        return input -> {
            try {
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
        super(bys);
        checkNotNull(bys);
        if (bys.length == 0) {
            throw new IllegalArgumentException("By array should not be empty");
        }
        this.bys = bys;
    }

    @Override
    public WebElement findElement(SearchContext context) {
        AppiumFunction<SearchContext, WebElement> searchingFunction = null;

        for (By by: bys) {
            searchingFunction = Optional.ofNullable(searchingFunction != null
                    ? searchingFunction.andThen(getSearchingFunction(by)) : null).orElse(getSearchingFunction(by));
        }

        FluentWait<SearchContext> waiting = new FluentWait<>(context);

        try {
            checkNotNull(searchingFunction);
            return waiting.until(searchingFunction);
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Cannot locate an element using " + toString());
        }
    }
}
