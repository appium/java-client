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

package io.appium.java_client.pagefactory.utils;

import io.appium.java_client.HasBrowserCheck;
import io.appium.java_client.pagefactory.bys.ContentType;
import io.appium.java_client.remote.SupportsContextSwitching;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.WrapsElement;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static io.appium.java_client.HasBrowserCheck.NATIVE_CONTEXT;
import static io.appium.java_client.pagefactory.bys.ContentType.HTML_OR_DEFAULT;
import static io.appium.java_client.pagefactory.bys.ContentType.NATIVE_MOBILE_SPECIFIC;
import static java.util.Locale.ROOT;

/**
 * Utilities to unpack drivers and related objects from a {@link SearchContext}.
 */
public final class WebDriverUnpackUtility {
    private static final String DECORATED_INTERFACE_NAME =
            "org.openqa.selenium.support.decorators.Decorated";

    private WebDriverUnpackUtility() {
    }

    /**
     * This method extracts an instance of the given interface from the given {@link SearchContext}.
     * It is expected that the {@link SearchContext} itself or the object it wraps implements it.
     *
     * @param <T> the type of the object to extract
     * @param searchContext is an instance of {@link SearchContext}. It may be the instance of
     *                {@link WebDriver} or {@link org.openqa.selenium.WebElement} or some other
     *                user's extension/implementation.
     *                Note: if you want to use your own implementation then it should implement
     *                {@link WrapsDriver} or {@link WrapsElement}
     * @param cls interface whose instance is going to be extracted.
     * @return Either an instance of the given interface or Optional.empty().
     */
    public static <T> Optional<T> unpackObjectFromSearchContext(
            @Nullable SearchContext searchContext, Class<T> cls
    ) {
        return unpackObjectFromSearchContext(searchContext, cls, ignored -> true);
    }

    /**
     * Extracts an instance of {@code cls} matching {@code filter} from {@code searchContext}.
     *
     * @param <T> the type of the object to extract
     * @param searchContext the search context to inspect
     * @param cls interface or class to extract
     * @param filter predicate that the extracted instance must satisfy
     * @return a matching instance or {@link Optional#empty()}
     */
    public static <T> Optional<T> unpackObjectFromSearchContext(
            @Nullable SearchContext searchContext,
            Class<T> cls,
            Predicate<? super T> filter
    ) {
        return doUnpackObjectFromSearchContext(
                searchContext, cls, filter, Collections.newSetFromMap(new IdentityHashMap<>())
        );
    }

    /**
     * This method extract an instance of {@link WebDriver} from the given {@link SearchContext}.
     * @param searchContext is an instance of {@link SearchContext}. It may be the instance of
     *                {@link WebDriver} or {@link org.openqa.selenium.WebElement} or some other
     *                user's extension/implementation.
     *                Note: if you want to use your own implementation then it should implement
     *                {@link WrapsDriver} or {@link WrapsElement}
     * @return the instance of {@link WebDriver}.
     *         Note: if the given {@link SearchContext} is not
     *         {@link WebDriver} and it doesn't implement
     *         {@link WrapsDriver} or {@link WrapsElement} then this method returns null.
     *
     */
    @Nullable
    public static WebDriver unpackWebDriverFromSearchContext(@Nullable SearchContext searchContext) {
        return unpackObjectFromSearchContext(searchContext, WebDriver.class).orElse(null);
    }

    /**
     * Unwraps nested {@link WrapsElement} and Selenium decorator wrappers.
     *
     * @param element the object to unwrap
     * @return the innermost unwrapped object, or {@code null}
     */
    @Nullable
    public static Object unwrapElement(@Nullable Object element) {
        Object current = element;
        Set<Object> visited = Collections.newSetFromMap(new IdentityHashMap<>());
        while (current != null && visited.add(current)) {
            if (current instanceof WrapsElement) {
                WebElement wrapped = ((WrapsElement) current).getWrappedElement();
                if (wrapped != null && wrapped != current) {
                    current = wrapped;
                    continue;
                }
            }
            Object decoratedOriginal = extractDecoratedOriginal(current);
            if (decoratedOriginal instanceof WebElement && decoratedOriginal != current) {
                current = decoratedOriginal;
                continue;
            }
            break;
        }
        return current;
    }

    /**
     * Detects content type by the provided search {@code context}.
     *
     * @param context is an instance of {@link SearchContext}. It may be the instance of
     *                {@link WebDriver} or {@link org.openqa.selenium.WebElement} or some other
     *                user's extension/implementation.
     *                Note: if you want to use your own implementation then it should
     *                implement {@link SupportsContextSwitching} or {@link WrapsDriver} or {@link HasBrowserCheck}
     * @return current content type. It depends on current context. If current context is
     *                NATIVE_APP it will return {@link ContentType#NATIVE_MOBILE_SPECIFIC}.
     *                {@link ContentType#HTML_OR_DEFAULT} will be returned if the current context is WEB_VIEW.
     *                {@link ContentType#HTML_OR_DEFAULT} also will be returned if the given
     *                {@link SearchContext} instance doesn't implement {@link SupportsContextSwitching} and
     *                {@link WrapsDriver}
     */
    public static ContentType getCurrentContentType(SearchContext context) {
        var browserCheckHolder = unpackObjectFromSearchContext(
                context, HasBrowserCheck.class, hbc -> !hbc.isBrowser()
        );
        if (browserCheckHolder.isPresent()) {
            return NATIVE_MOBILE_SPECIFIC;
        }

        var contextAware = unpackObjectFromSearchContext(
                context,
                SupportsContextSwitching.class,
                sc -> {
                    String currentContext = safeGetContext(sc);
                    return currentContext != null && currentContext.toUpperCase(ROOT).contains(NATIVE_CONTEXT);
                }
        );
        if (contextAware.isPresent()) {
            return NATIVE_MOBILE_SPECIFIC;
        }

        return HTML_OR_DEFAULT;
    }

    private static <T> Optional<T> doUnpackObjectFromSearchContext(
            @Nullable SearchContext searchContext,
            Class<T> cls,
            Predicate<? super T> filter,
            Set<SearchContext> visited
    ) {
        if (searchContext == null || !visited.add(searchContext)) {
            return Optional.empty();
        }

        Object decoratedOriginal = extractDecoratedOriginal(searchContext);
        if (cls.isAssignableFrom(searchContext.getClass())
                && (decoratedOriginal == null || cls.isAssignableFrom(decoratedOriginal.getClass()))) {
            T candidate = cls.cast(searchContext);
            if (filter.test(candidate)) {
                return Optional.of(candidate);
            }
        }
        if (searchContext instanceof WrapsDriver) {
            Optional<T> unwrappedDriver = doUnpackObjectFromSearchContext(
                    ((WrapsDriver) searchContext).getWrappedDriver(), cls, filter, visited
            );
            if (unwrappedDriver.isPresent()) {
                return unwrappedDriver;
            }
        }
        if (searchContext instanceof WrapsElement) {
            Optional<T> unwrappedElement = doUnpackObjectFromSearchContext(
                    ((WrapsElement) searchContext).getWrappedElement(), cls, filter, visited
            );
            if (unwrappedElement.isPresent()) {
                return unwrappedElement;
            }
        }

        if (decoratedOriginal instanceof SearchContext) {
            Optional<T> unwrappedOriginal = doUnpackObjectFromSearchContext(
                    (SearchContext) decoratedOriginal, cls, filter, visited
            );
            if (unwrappedOriginal.isPresent()) {
                return unwrappedOriginal;
            }
        }

        SearchContext decoratedDriver = extractDecoratedDriver(searchContext);
        if (decoratedDriver != null) {
            return doUnpackObjectFromSearchContext(decoratedDriver, cls, filter, visited);
        }

        return Optional.empty();
    }

    @Nullable
    private static String safeGetContext(SupportsContextSwitching contextAware) {
        try {
            return contextAware.getContext();
        } catch (WebDriverException e) {
            return null;
        }
    }

    @Nullable
    private static Object extractDecoratedOriginal(Object candidate) {
        Class<?> decoratedInterface = findDecoratedInterface(candidate.getClass());
        if (decoratedInterface == null) {
            return null;
        }
        try {
            Method getOriginal = decoratedInterface.getMethod("getOriginal");
            return getOriginal.invoke(candidate);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    @Nullable
    private static SearchContext extractDecoratedDriver(Object candidate) {
        Class<?> decoratedInterface = findDecoratedInterface(candidate.getClass());
        if (decoratedInterface == null) {
            return null;
        }
        try {
            Method getDecorator = decoratedInterface.getMethod("getDecorator");
            Object decorator = getDecorator.invoke(candidate);
            if (decorator == null) {
                return null;
            }
            Method getDecoratedDriver = decorator.getClass().getMethod("getDecoratedDriver");
            Object decoratedDriver = getDecoratedDriver.invoke(decorator);
            if (decoratedDriver instanceof SearchContext) {
                return (SearchContext) decoratedDriver;
            }
            if (decoratedDriver != null) {
                Object originalDriver = extractDecoratedOriginal(decoratedDriver);
                if (originalDriver instanceof SearchContext) {
                    return (SearchContext) originalDriver;
                }
            }
        } catch (ReflectiveOperationException e) {
            return null;
        }
        return null;
    }

    @Nullable
    private static Class<?> findDecoratedInterface(@Nullable Class<?> type) {
        for (Class<?> current = type; current != null && !Object.class.equals(current);
                current = current.getSuperclass()) {
            for (Class<?> iface : current.getInterfaces()) {
                Class<?> found = findDecoratedOnInterfaceHierarchy(iface);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    @Nullable
    private static Class<?> findDecoratedOnInterfaceHierarchy(Class<?> iface) {
        if (DECORATED_INTERFACE_NAME.equals(iface.getName())) {
            return iface;
        }
        for (Class<?> parent : iface.getInterfaces()) {
            Class<?> found = findDecoratedOnInterfaceHierarchy(parent);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
}
