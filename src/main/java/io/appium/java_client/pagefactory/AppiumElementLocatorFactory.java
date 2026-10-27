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
import io.appium.java_client.support.PageFactory;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.SearchContext;

import java.lang.ref.WeakReference;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.List;

import static io.appium.java_client.pagefactory.WithTimeout.DurationBuilder.build;
import static java.util.Optional.ofNullable;

/** Factory of locators for Appium-specific page object annotations. */
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
    AppiumElementLocatorFactory(
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
        Duration customDuration = resolveCustomDuration(annotatedElement);
        builder.setAnnotated(annotatedElement);
        try {
            return ofNullable(builder.buildBy())
                    .map(by -> {
                        var isLookupCached = builder.isLookupCached();
                        return searchContextReference != null
                            ? new AppiumElementLocator(searchContextReference, by, isLookupCached, customDuration)
                            : new AppiumElementLocator(searchContext, by, isLookupCached, customDuration);
                    })
                    .orElse(null);
        } finally {
            // unleak element reference after the locator is built
            builder.setAnnotated(null);
        }
    }

    private Duration resolveCustomDuration(AnnotatedElement annotatedElement) {
        if (annotatedElement.isAnnotationPresent(WithTimeout.class)) {
            return build(annotatedElement.getAnnotation(WithTimeout.class));
        }

        if (annotatedElement instanceof Field) {
            Field field = (Field) annotatedElement;
            Class<?> widgetClass = extractWidgetClass(field);
            if (widgetClass != null) {
                WithTimeout widgetTimeout = findClassTimeout(widgetClass);
                if (widgetTimeout != null) {
                    return build(widgetTimeout);
                }
            }
            Class<?> currentPageClass = PageFactory.getCurrentPageClass();
            if (currentPageClass != null && field.getDeclaringClass().isAssignableFrom(currentPageClass)) {
                WithTimeout pageTimeout = findClassTimeout(currentPageClass);
                if (pageTimeout != null) {
                    return build(pageTimeout);
                }
            }
            WithTimeout declaringTimeout = findClassTimeout(field.getDeclaringClass());
            if (declaringTimeout != null) {
                return build(declaringTimeout);
            }
        } else if (annotatedElement instanceof Class<?>) {
            WithTimeout classTimeout = findClassTimeout((Class<?>) annotatedElement);
            if (classTimeout != null) {
                return build(classTimeout);
            }
        }

        return duration;
    }

    @Nullable
    private static WithTimeout findClassTimeout(@Nullable Class<?> clazz) {
        for (Class<?> current = clazz; current != null; current = current.getEnclosingClass()) {
            WithTimeout annotation = current.getAnnotation(WithTimeout.class);
            if (annotation != null) {
                return annotation;
            }
        }
        return null;
    }

    @Nullable
    private static Class<?> extractWidgetClass(Field field) {
        Class<?> fieldType = field.getType();
        if (Widget.class.isAssignableFrom(fieldType)) {
            return fieldType;
        }
        if (!List.class.isAssignableFrom(fieldType)) {
            return null;
        }
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return null;
        }
        Type[] typeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
        if (typeArguments.length == 0) {
            return null;
        }
        Type listType = typeArguments[0];
        if (listType instanceof ParameterizedType) {
            listType = ((ParameterizedType) listType).getRawType();
        }
        if (listType instanceof Class<?> && Widget.class.isAssignableFrom((Class<?>) listType)) {
            return (Class<?>) listType;
        }
        return null;
    }
}
