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

import io.appium.java_client.pagefactory.bys.builder.AppiumByBuilder;
import io.appium.java_client.pagefactory.locator.CacheableElementLocatorFactory;
import io.appium.java_client.pagefactory.locator.CacheableLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

class AppiumElementLocatorFactory implements CacheableElementLocatorFactory {
    private final SearchContext searchContext;
    private final TimeOutDuration timeOutDuration;
    private final WebDriver originalWebDriver;
    private final AppiumByBuilder builder;

    public AppiumElementLocatorFactory(SearchContext searchContext, TimeOutDuration timeOutDuration,
        WebDriver originalWebDriver, AppiumByBuilder builder) {
        this.searchContext = searchContext;
        this.originalWebDriver = originalWebDriver;
        this.timeOutDuration = timeOutDuration;
        this.builder = builder;
    }

    public CacheableLocator createLocator(Field field) {
        return this.createLocator((AnnotatedElement) field);
    }

    @Override public CacheableLocator createLocator(AnnotatedElement annotatedElement) {
        TimeOutDuration customDuration;
        if (annotatedElement.isAnnotationPresent(WithTimeout.class)) {
            WithTimeout withTimeout = annotatedElement.getAnnotation(WithTimeout.class);
            customDuration = new TimeOutDuration(withTimeout.time(), withTimeout.unit());
        } else {
            customDuration = timeOutDuration;
        }

        builder.setAnnotated(annotatedElement);
        By by = builder.buildBy();
        if (by != null) {
            return new AppiumElementLocator(searchContext, by, builder.isLookupCached(),
                    customDuration, timeOutDuration, originalWebDriver);
        }
        return null;
    }


}
