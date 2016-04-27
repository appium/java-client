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

package io.appium.java_client;

import org.openqa.selenium.WebElement;

public interface ScrollsTo<T extends WebElement> {

    /**
     * Scroll to an element which contains the given text.
     *
     * @param text description or text of an element scroll to
     * @return an element that matches
     */
    T scrollTo(String text);

    /**
     * Scroll to the element whose 'predicateString' is predicate of an element..
     * Scrolling happens within this element.
     *
     * @param predicateString iOS predicate of an element.
     */
    T scrollToUsingPredicates(String predicateString);

    /**
     * Scroll to an element with the given text.
     *
     * @param text description or text of an element scroll to
     * @return an element that matches
     */
    T scrollToExact(String text);

}
