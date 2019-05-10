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

import static io.appium.java_client.pagefactory.WithTimeout.DurationBuilder.build;
import static java.util.Optional.ofNullable;

import io.appium.java_client.pagefactory.bys.builder.AppiumByBuilder;
import io.appium.java_client.pagefactory.locator.CacheableElementLocatorFactory;
import io.appium.java_client.pagefactory.locator.CacheableLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.time.Duration;
import javax.annotation.Nullable;

public class AppiumElementLocatorFactory implements CacheableElementLocatorFactory {
    private final SearchContext searchContext;
    private final Duration duration;
    private final AppiumByBuilder builder;

    /**
     * Creates a new mobile element locator factory.
     *
     * @param searchContext     The context to use when finding the element
     * @param duration   timeout parameters for the elements to be found
     * @param builder    is handler of Appium-specific page object annotations
     */
    public AppiumElementLocatorFactory(SearchContext searchContext, Duration duration,
                                       AppiumByBuilder builder) {
        this.searchContext = searchContext;
        this.duration = duration;
        this.builder = builder;
    }

    public @Nullable CacheableLocator createLocator(Field field) {
        return this.createLocator((AnnotatedElement) field);
    }

    @Override public @Nullable CacheableLocator createLocator(AnnotatedElement annotatedElement) {
        Duration customDuration;
        if (annotatedElement.isAnnotationPresent(WithTimeout.class)) {
            WithTimeout withTimeout = annotatedElement.getAnnotation(WithTimeout.class);
            customDuration = build(withTimeout);
        } else {
            customDuration = duration;
        }

        builder.setAnnotated(annotatedElement);
        By byResult = builder.buildBy();

        return ofNullable(byResult)
                .map(by -> new AppiumElementLocator(searchContext, by, builder.isLookupCached(), customDuration))
                .orElse(null);
    }


}
