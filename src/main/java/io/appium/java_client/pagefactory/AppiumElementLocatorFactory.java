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

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.time.Duration;

import static io.appium.java_client.pagefactory.WithTimeout.DurationBuilder.build;
import static java.util.Optional.ofNullable;

public class AppiumElementLocatorFactory implements CacheableElementLocatorFactory {
    private final SearchContext searchContext;
    private final WeakReference<SearchContext> searchContextReference;
    private final Duration duration;
    private final AppiumByBuilder builder;

    /**
     * Creates a new mobile element locator factory.
     *
     * @param searchContext     The context to use when finding the element
     * @param duration          timeout parameters for the elements to be found
     * @param builder           is handler of Appium-specific page object annotations
     */
    public AppiumElementLocatorFactory(
            SearchContext searchContext,
            Duration duration,
            AppiumByBuilder builder
    ) {
        this.searchContext = searchContext;
        this.searchContextReference = null;
        this.duration = duration;
        this.builder = builder;
    }

    /**
     * Creates a new mobile element locator factory.
     *
     * @param searchContextReference     The context reference to use when finding the element
     * @param duration                   timeout parameters for the elements to be found
     * @param builder                    is handler of Appium-specific page object annotations
     */
    public AppiumElementLocatorFactory(
            WeakReference<SearchContext> searchContextReference,
            Duration duration,
            AppiumByBuilder builder
    ) {
        this.searchContextReference = searchContextReference;
        this.searchContext = null;
        this.duration = duration;
        this.builder = builder;
    }

    @Nullable
    @Override
    public CacheableLocator createLocator(Field field) {
        return this.createLocator((AnnotatedElement) field);
    }

    @Nullable
    @Override
    public CacheableLocator createLocator(AnnotatedElement annotatedElement) {
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
                .map(by -> searchContextReference != null
                        ? new AppiumElementLocator(searchContextReference, by, builder.isLookupCached(), customDuration)
                        : new AppiumElementLocator(searchContext, by, builder.isLookupCached(), customDuration)
                )
                .orElse(null);
    }
}
