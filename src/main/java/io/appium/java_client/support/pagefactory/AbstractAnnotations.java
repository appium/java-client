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

package io.appium.java_client.support.pagefactory;

import org.openqa.selenium.By;

/**
 * Abstract class to work with fields in Page Objects. Provides methods to process
 * {@code @FindBy}, {@code @FindBys} and {@code @FindAll} annotations.
 * Adapted from Selenium's {@code org.openqa.selenium.support.pagefactory.AbstractAnnotations}
 * (Apache License 2.0).
 */
public abstract class AbstractAnnotations {
    /**
     * Defines how to transform given object (field, class, etc.) into {@link By}
     * class used by webdriver to locate elements.
     *
     * @return By object
     */
    public abstract By buildBy();

    /**
     * Defines whether given element should be returned from cache on further calls.
     *
     * @return boolean if lookup cached
     */
    public abstract boolean isLookupCached();
}
