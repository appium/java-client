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

import io.appium.java_client.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

/** Factory of locators whose lookup results may be cached. */
public interface CacheableElementLocatorFactory extends ElementLocatorFactory {

    CacheableLocator createLocator(Field field);

    /**
     * Creates a locator for the given annotated element.
     *
     * @param annotatedElement the element annotated with locator annotations
     * @return the locator or null if the element has no locator annotations
     */
    CacheableLocator createLocator(AnnotatedElement annotatedElement);
}
