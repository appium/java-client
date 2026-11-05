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
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

import static io.appium.java_client.pagefactory.bys.ContentType.NATIVE_MOBILE_SPECIFIC;
import static java.util.Objects.requireNonNull;

/**
 * Locator which selects the underlying locator by content type.
 * The selection is stored per thread, so two threads can search different content
 * through one shared instance.
 */
@EqualsAndHashCode(callSuper = true)
public class ContentMappedBy extends By {
    private final Map<ContentType, By> map;
    @EqualsAndHashCode.Exclude
    private final ThreadLocal<ContentType> currentContent =
            ThreadLocal.withInitial(() -> NATIVE_MOBILE_SPECIFIC);

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
        return locatorFor(currentContent.get());
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
        return context.findElement(getCurrentLocator());
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        return context.findElements(getCurrentLocator());
    }

    @Override
    public String toString() {
        return String.valueOf(getCurrentLocator());
    }
}
