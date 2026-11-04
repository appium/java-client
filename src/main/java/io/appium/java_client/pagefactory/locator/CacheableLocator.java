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

package io.appium.java_client.pagefactory.locator;

import io.appium.java_client.support.pagefactory.ElementLocator;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.Iterator;

/** Element locator whose lookup results may be cached. */
public interface CacheableLocator extends ElementLocator {
    /**
     * Checks whether the lookup result should be cached.
     *
     * @return true if the lookup result is cached
     */
    boolean isLookUpCached();

    /**
     * Later roots to search when a nested lookup misses inside {@code resolvedRoot}.
     * The iterator is lazy, so a root that is never asked for does not cause a remote query.
     *
     * <p>Each alternative locator contributes only its first match, the same element
     * {@link #findElement()} would use for that locator. Further matches of one locator
     * are not separate roots. {@code resolvedRoot} itself is not returned. The default
     * is an empty iterator, so a custom locator opts in by overriding this method.
     * The widget method is not run again for each root.
     *
     * @param resolvedRoot the root that was already searched
     * @return later roots, none by default
     */
    default Iterator<WebElement> furtherRoots(WebElement resolvedRoot) {
        return Collections.emptyIterator();
    }
}
