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

package io.appium.java_client.pagefactory.bys;

import lombok.EqualsAndHashCode;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.appium.java_client.pagefactory.bys.ContentType.HTML_OR_DEFAULT;
import static io.appium.java_client.pagefactory.bys.ContentType.NATIVE_MOBILE_SPECIFIC;
import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.getCurrentContentType;
import static java.util.Objects.requireNonNull;

/**
 * Locator which selects the underlying locator by the active {@link ContentType}.
 * The explicit selection is stored per thread, so two threads can search different
 * content through one shared instance.
 *
 * <p>When a content type is explicitly pinned on the current thread via
 * {@link #useContent(ContentType)}, {@link #findElement(SearchContext)},
 * {@link #findElements(SearchContext)}, and {@link #toString()} use that pinned entry.
 * When no content type has been set on the current thread,
 * {@link #findElement(SearchContext)} and {@link #findElements(SearchContext)}
 * auto-detect the content type from the target {@link SearchContext} via
 * {@link io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility#getCurrentContentType(SearchContext)}
 * (resolving to {@link ContentType#NATIVE_MOBILE_SPECIFIC} for native mobile contexts and
 * {@link ContentType#HTML_OR_DEFAULT} for web/browser contexts or plain {@link SearchContext}
 * implementations that implement neither {@link io.appium.java_client.HasBrowserCheck} nor
 * {@link io.appium.java_client.remote.SupportsContextSwitching}).
 */
@EqualsAndHashCode(callSuper = true)
public class ContentMappedBy extends By {
    private final Map<ContentType, By> map;
    @EqualsAndHashCode.Exclude
    private final ThreadLocal<ContentType> currentContent = new ThreadLocal<>();

    /**
     * Creates a new locator.
     *
     * @param map the locators mapped by the content type
     */
    public ContentMappedBy(Map<ContentType, By> map) {
        this.map = map;
    }

    /**
     * This method sets required content type for the further searching.
     *
     * @param type required content type {@link ContentType}
     * @return self-reference.
     */
    public By useContent(@NonNull ContentType type) {
        currentContent.set(requireNonNull(type));
        return this;
    }

    /**
     * Returns the locator selected for this thread's content type.
     *
     * @return the locator selected by {@link #useContent(ContentType)}
     */
    public By getCurrentLocator() {
        ContentType pinned = currentContent.get();
        return locatorFor(pinned != null ? pinned : NATIVE_MOBILE_SPECIFIC);
    }

    /**
     * Returns the locator for {@code type} without changing this thread's selection.
     *
     * @param type the content type to read
     * @return the locator stored for {@code type}, or null when it is absent
     */
    public By locatorFor(@NonNull ContentType type) {
        return map.get(requireNonNull(type));
    }

    @Override
    public WebElement findElement(SearchContext context) {
        return context.findElement(map.get(resolveContentType(context)));
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        return context.findElements(map.get(resolveContentType(context)));
    }

    @Override
    public String toString() {
        ContentType pinned = currentContent.get();
        if (pinned != null) {
            return String.valueOf(map.get(pinned));
        }
        By defaultBy = map.get(HTML_OR_DEFAULT);
        By nativeBy = map.get(NATIVE_MOBILE_SPECIFIC);
        if (Objects.equals(defaultBy, nativeBy) && nativeBy != null) {
            return nativeBy.toString();
        }
        return String.format(
                "%s(%s=%s, %s=%s)",
                getClass().getSimpleName(),
                HTML_OR_DEFAULT, defaultBy,
                NATIVE_MOBILE_SPECIFIC, nativeBy
        );
    }

    @Nullable
    @EqualsAndHashCode.Include
    private ContentType currentPinnedContent() {
        return currentContent.get();
    }

    private ContentType resolveContentType(SearchContext context) {
        ContentType pinned = currentContent.get();
        return pinned != null ? pinned : getCurrentContentType(context);
    }
}
