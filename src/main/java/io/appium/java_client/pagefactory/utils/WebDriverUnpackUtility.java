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
import io.appium.java_client.NoSuchContextException;
import io.appium.java_client.pagefactory.bys.ContentType;
import io.appium.java_client.proxy.HasMethodCallListeners;
import io.appium.java_client.remote.SupportsContextSwitching;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.WrapsElement;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static io.appium.java_client.HasBrowserCheck.NATIVE_CONTEXT;
import static io.appium.java_client.internal.ElementHelpers.extractDecoratedDriver;
import static io.appium.java_client.internal.ElementHelpers.extractDecoratedOriginal;
import static io.appium.java_client.internal.ElementHelpers.unwrapElement;
import static io.appium.java_client.pagefactory.bys.ContentType.HTML_OR_DEFAULT;
import static io.appium.java_client.pagefactory.bys.ContentType.NATIVE_MOBILE_SPECIFIC;
import static java.util.Locale.ROOT;

/**
 * Utilities to unpack drivers and related objects from a {@link SearchContext}.
 */
public final class WebDriverUnpackUtility {
    private static final int MAX_UNALLOCATED_DEPTH = 4;

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
        return doUnpackFromSearchContext(searchContext, cls, 0, null);
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

    /** Checks whether two references resolve to the same unwrapped element.
     * @param first the first element or wrapper
     * @param second the second element or wrapper
     * @return true if both references resolve to equal underlying elements */
    public static boolean sameElement(@Nullable Object first, @Nullable Object second) {
        if (first == second) {
            return first != null;
        }
        if (first == null || second == null) {
            return false;
        }
        Object unwrappedFirst = unwrapElement(first);
        Object unwrappedSecond = unwrapElement(second);
        if (unwrappedFirst == unwrappedSecond) {
            return unwrappedFirst != null;
        }
        if (unwrappedFirst instanceof HasMethodCallListeners
                && !(unwrappedSecond instanceof HasMethodCallListeners)) {
            return unwrappedFirst.equals(unwrappedSecond);
        }
        if (!(unwrappedFirst instanceof HasMethodCallListeners)
                && unwrappedSecond instanceof HasMethodCallListeners) {
            return unwrappedSecond.equals(unwrappedFirst);
        }
        return Objects.equals(unwrappedFirst, unwrappedSecond);
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
        var browserCheckHolder = unpackObjectFromSearchContext(context, HasBrowserCheck.class);
        if (browserCheckHolder.filter(hbc -> !hbc.isBrowser()).isPresent()) {
            return NATIVE_MOBILE_SPECIFIC;
        }

        var contextAware = unpackObjectFromSearchContext(context, SupportsContextSwitching.class);
        if (contextAware.map(WebDriverUnpackUtility::safeGetContext)
                .filter(c -> c.toUpperCase(ROOT).contains(NATIVE_CONTEXT)).isPresent()) {
            return NATIVE_MOBILE_SPECIFIC;
        }

        return HTML_OR_DEFAULT;
    }

    private static <T> Optional<T> doUnpackFromSearchContext(
            @Nullable SearchContext searchContext,
            Class<T> cls,
            int depth,
            @Nullable Set<SearchContext> visited
    ) {
        if (searchContext == null) {
            return Optional.empty();
        }
        Set<SearchContext> activeVisited = visited;
        if (depth >= MAX_UNALLOCATED_DEPTH) {
            if (activeVisited == null) {
                activeVisited = Collections.newSetFromMap(new IdentityHashMap<>());
            }
            if (!activeVisited.add(searchContext)) {
                return Optional.empty();
            }
        }

        Object decoratedOriginal = extractDecoratedOriginal(searchContext);
        if (cls.isAssignableFrom(searchContext.getClass())
                && (decoratedOriginal == null || cls.isAssignableFrom(decoratedOriginal.getClass()))) {
            return Optional.of(cls.cast(searchContext));
        }

        int nextDepth = depth + 1;
        if (searchContext instanceof WrapsDriver) {
            WebDriver wrappedDriver = ((WrapsDriver) searchContext).getWrappedDriver();
            if (wrappedDriver != searchContext) {
                Optional<T> unwrappedDriver = doUnpackFromSearchContext(
                        wrappedDriver, cls, nextDepth, activeVisited
                );
                if (unwrappedDriver.isPresent()) {
                    return unwrappedDriver;
                }
            }
        }
        if (searchContext instanceof WrapsElement) {
            var wrappedElement = ((WrapsElement) searchContext).getWrappedElement();
            if (wrappedElement != searchContext) {
                Optional<T> unwrappedElement = doUnpackFromSearchContext(
                        wrappedElement, cls, nextDepth, activeVisited
                );
                if (unwrappedElement.isPresent()) {
                    return unwrappedElement;
                }
            }
        }

        if (decoratedOriginal instanceof SearchContext && decoratedOriginal != searchContext) {
            Optional<T> unwrappedOriginal = doUnpackFromSearchContext(
                    (SearchContext) decoratedOriginal, cls, nextDepth, activeVisited
            );
            if (unwrappedOriginal.isPresent()) {
                return unwrappedOriginal;
            }
        }

        SearchContext decoratedDriver = extractDecoratedDriver(searchContext);
        if (decoratedDriver != null && decoratedDriver != searchContext) {
            return doUnpackFromSearchContext(decoratedDriver, cls, nextDepth, activeVisited);
        }

        return Optional.empty();
    }

    @Nullable
    private static String safeGetContext(SupportsContextSwitching contextAware) {
        try {
            return contextAware.getContext();
        } catch (UnsupportedCommandException | NoSuchContextException e) {
            return null;
        }
    }
}
