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

import io.appium.java_client.pagefactory.bys.ContentMappedBy;
import io.appium.java_client.pagefactory.locator.CacheableLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.lang.ref.WeakReference;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.appium.java_client.pagefactory.ThrowableUtil.extractReadableException;
import static io.appium.java_client.pagefactory.ThrowableUtil.isInvalidSelectorRootCause;
import static io.appium.java_client.pagefactory.ThrowableUtil.isStaleElementReferenceException;
import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.getCurrentContentType;
import static java.lang.String.format;

class AppiumElementLocator implements CacheableLocator {

    private static final String exceptionMessageIfElementNotFound = "Can't locate an element by this strategy: %s";

    private final boolean shouldCache;
    private final By by;
    private final Duration duration;
    private final WeakReference<SearchContext> searchContextReference;
    private final SearchContext searchContext;

    private WebElement cachedElement;
    private List<WebElement> cachedElementList;

    /**
     * Creates a new mobile element locator. It instantiates {@link WebElement}
     * using @AndroidFindBy (-s), @iOSFindBy (-s) and @FindBy (-s) annotation
     * sets
     *
     * @param searchContextReference     The context reference to use when finding the element
     * @param by                         a By locator strategy
     * @param shouldCache                is the flag that signalizes that elements which
     *                                   are found once should be cached
     * @param duration                   timeout parameter for the element to be found
     */
    AppiumElementLocator(
            WeakReference<SearchContext> searchContextReference,
            By by,
            boolean shouldCache,
            Duration duration
    ) {
        this.searchContextReference = searchContextReference;
        this.searchContext = null;
        this.shouldCache = shouldCache;
        this.duration = duration;
        this.by = by;
    }

    /**
     * Creates a new mobile element locator. It instantiates {@link WebElement}
     * using @AndroidFindBy (-s), @iOSFindBy (-s) and @FindBy (-s) annotation
     * sets
     *
     * @param searchContext     The context to use when finding the element
     * @param by                a By locator strategy
     * @param shouldCache       is the flag that signalizes that elements which
     *                          are found once should be cached
     * @param duration          timeout parameter for the element to be found
     */
    public AppiumElementLocator(
            SearchContext searchContext,
            By by,
            boolean shouldCache,
            Duration duration
    ) {
        this.searchContextReference = null;
        this.searchContext = searchContext;
        this.shouldCache = shouldCache;
        this.duration = duration;
        this.by = by;
    }

    private Optional<SearchContext> getSearchContext() {
        return searchContext == null
                ? Optional.ofNullable(searchContextReference).map(WeakReference::get)
                : Optional.of(searchContext);
    }

    /**
     * This methods makes sets some settings of the {@link By} according to
     * the given instance of {@link SearchContext}. If there is some {@link ContentMappedBy}
     * then it is switched to the searching for some html or native mobile element.
     * Otherwise nothing happens there.
     *
     * @param currentBy is some locator strategy
     * @param currentContent is an instance of some subclass of the {@link SearchContext}.
     * @return the corrected {@link By} for the further searching
     */
    private static By getBy(By currentBy, SearchContext currentContent) {
        if (!ContentMappedBy.class.isAssignableFrom(currentBy.getClass())) {
            return currentBy;
        }

        return ((ContentMappedBy) currentBy).useContent(getCurrentContentType(currentContent));
    }

    private <T> T waitFor(Supplier<T> supplier) {
        WaitingFunction<T> function = new WaitingFunction<>();
        try {
            FluentWait<Supplier<T>> wait = new FluentWait<>(supplier)
                    .ignoring(NoSuchElementException.class);
            wait.withTimeout(duration);
            return wait.until(function);
        } catch (TimeoutException e) {
            if (function.foundStaleElementReferenceException != null) {
                throw (StaleElementReferenceException) function.foundStaleElementReferenceException;
            }
            throw e;
        }
    }

    /**
     * Find the element.
     */
    public WebElement findElement() {
        if (cachedElement != null && shouldCache) {
            return cachedElement;
        }

        SearchContext searchContext = getSearchContext()
                .orElseThrow(() -> new IllegalStateException(
                        String.format("The element %s is not locatable anymore "
                                + "because its context has been garbage collected", by)
                ));

        By bySearching = getBy(this.by, searchContext);
        try {
            WebElement result = waitFor(() -> searchContext.findElement(bySearching));
            if (shouldCache) {
                cachedElement = result;
            }
            return result;
        } catch (TimeoutException | StaleElementReferenceException e) {
            throw new NoSuchElementException(format(exceptionMessageIfElementNotFound, bySearching.toString()), e);
        }
    }

    /**
     * Find the element list.
     */
    public List<WebElement> findElements() {
        if (cachedElementList != null && shouldCache) {
            return cachedElementList;
        }

        SearchContext searchContext = getSearchContext()
                .orElseThrow(() -> new IllegalStateException(
                    String.format("Elements %s are not locatable anymore "
                            + "because their context has been garbage collected", by)
                ));

        List<WebElement> result;
        try {
            result = waitFor(() -> {
                List<WebElement> list = searchContext.findElements(getBy(by, searchContext));
                return list.isEmpty() ? null : list;
            });
        } catch (TimeoutException | StaleElementReferenceException e) {
            result = new ArrayList<>();
        }

        if (shouldCache) {
            cachedElementList = result;
        }
        return result;
    }

    @Override public boolean isLookUpCached() {
        return shouldCache;
    }

    @Override public String toString() {
        return format("Located by %s", by);
    }


    // This function waits for not empty element list using all defined by
    private static class WaitingFunction<T> implements Function<Supplier<T>, T> {
        private Throwable foundStaleElementReferenceException;

        public T apply(Supplier<T> supplier) {
            foundStaleElementReferenceException = null;

            try {
                return supplier.get();
            } catch (Throwable e) {
                boolean isRootCauseStaleElementReferenceException = false;
                boolean isRootCauseInvalidSelector = isInvalidSelectorRootCause(e);
                if (!isRootCauseInvalidSelector) {
                    isRootCauseStaleElementReferenceException = isStaleElementReferenceException(e);
                }
                if (isRootCauseStaleElementReferenceException) {
                    foundStaleElementReferenceException = extractReadableException(e);
                }
                if (isRootCauseInvalidSelector || isRootCauseStaleElementReferenceException) {
                    return null;
                }

                Throwable excToThrow = extractReadableException(e);
                if (excToThrow instanceof WebDriverException) {
                    throw (WebDriverException) excToThrow;
                } else {
                    throw new WebDriverException(excToThrow);
                }
            }
        }
    }
}
