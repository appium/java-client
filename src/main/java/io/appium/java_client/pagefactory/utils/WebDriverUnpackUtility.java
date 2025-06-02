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
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.WrapsElement;

import java.util.Optional;

import static io.appium.java_client.HasBrowserCheck.NATIVE_CONTEXT;
import static io.appium.java_client.pagefactory.bys.ContentType.HTML_OR_DEFAULT;
import static io.appium.java_client.pagefactory.bys.ContentType.NATIVE_MOBILE_SPECIFIC;

public final class WebDriverUnpackUtility {
    private WebDriverUnpackUtility() {
    }

    /**
     * This method extracts an instance of the given interface from the given {@link SearchContext}.
     * It is expected that the {@link SearchContext} itself or the object it wraps implements it.
     *
     * @param searchContext is an instance of {@link SearchContext}. It may be the instance of
     *                {@link WebDriver} or {@link org.openqa.selenium.WebElement} or some other
     *                user's extension/implementation.
     *                Note: if you want to use your own implementation then it should implement
     *                {@link WrapsDriver} or {@link WrapsElement}
     * @param cls interface whose instance is going to be extracted.
     * @return Either an instance of the given interface or Optional.empty().
     */
    public static <T> Optional<T> unpackObjectFromSearchContext(@Nullable SearchContext searchContext, Class<T> cls) {
        if (searchContext == null) {
            return Optional.empty();
        }

        if (cls.isAssignableFrom(searchContext.getClass())) {
            return Optional.of(cls.cast(searchContext));
        }
        if (searchContext instanceof WrapsDriver) {
            return unpackObjectFromSearchContext(((WrapsDriver) searchContext).getWrappedDriver(), cls);
        }
        // Search context it is not only WebDriver. WebElement is search context too.
        // RemoteWebElement implements WrapsDriver
        if (searchContext instanceof WrapsElement) {
            return unpackObjectFromSearchContext(((WrapsElement) searchContext).getWrappedElement(), cls);
        }

        return Optional.empty();
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
        if (contextAware.map(SupportsContextSwitching::getContext)
                .filter(c -> c.toUpperCase().contains(NATIVE_CONTEXT)).isPresent()) {
            return NATIVE_MOBILE_SPECIFIC;
        }

        return HTML_OR_DEFAULT;
    }
}
