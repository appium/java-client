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

import io.appium.java_client.pagefactory.bys.ContentType;
import io.appium.java_client.support.pagefactory.ElementLocator;
import org.openqa.selenium.WebElement;

/** Element locator whose lookup results may be cached. */
public interface CacheableLocator extends ElementLocator {
    /**
     * Checks whether the lookup result should be cached.
     *
     * @return true if the lookup result is cached
     */
    boolean isLookUpCached();

    /**
     * Captures later roots for nested lookups under {@code resolvedRoot}.
     * The snapshot is taken once, when the widget is bound. Later misses reuse it
     * and do not read the driver context again. Each alternative locator still
     * contributes only its first match, and {@code resolvedRoot} is omitted.
     * The default has no further roots, so a single-locator widget keeps the real element.
     *
     * @param resolvedRoot the root that was already searched
     * @param contentType the content type already determined for this root
     * @return alternate roots, none by default
     */
    default FurtherRoots furtherRoots(WebElement resolvedRoot, ContentType contentType) {
        return FurtherRoots.none();
    }
}
