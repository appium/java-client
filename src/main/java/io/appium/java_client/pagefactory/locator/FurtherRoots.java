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

import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.Iterator;

/**
 * Alternate widget roots captured once for a bind.
 * {@link #iterator()} may be called again for each nested miss and must not
 * repeat driver context checks.
 */
public interface FurtherRoots extends Iterable<WebElement> {
    /**
     * Whether a nested miss should try these roots.
     * This is true when the locator declares alternatives, even if none are on screen yet.
     *
     * @return true when there is another root to try
     */
    boolean isPresent();

    /**
     * Returns no alternate roots.
     *
     * @return an empty snapshot
     */
    static FurtherRoots none() {
        return None.INSTANCE;
    }

    /**
     * Empty snapshot.
     */
    final class None implements FurtherRoots {
        private static final None INSTANCE = new None();

        private None() {
        }

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public Iterator<WebElement> iterator() {
            return Collections.emptyIterator();
        }
    }
}
