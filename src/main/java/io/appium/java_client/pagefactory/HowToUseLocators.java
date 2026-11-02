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

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines how groups of platform-specific locator annotations are used.
 *
 * <p>Can be placed on a specific field or on a page object / {@link Widget} class or interface
 * (inherited by subclasses). Precedence order is: field-level {@code @HowToUseLocators}
 * &gt; widget class {@code @HowToUseLocators}
 * &gt; page/declaring class {@code @HowToUseLocators} (including superclasses, interfaces, and enclosing classes)
 * &gt; the default {@link LocatorGroupStrategy} configured on {@link AppiumFieldDecorator}.
 *
 * <p>Note that both {@link #androidAutomation()} and {@link #iOSXCUITAutomation()} default to
 * {@link LocatorGroupStrategy#CHAIN}, so when {@code @HowToUseLocators} is present on a field or class,
 * any platform attribute not explicitly specified in that annotation resolves to {@code CHAIN}.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD, ElementType.TYPE})
public @interface HowToUseLocators {
    /**
     * The strategy which defines how to use locators which are described by the
     * {@link AndroidFindBy} annotation. These annotations can define the chained searching
     * or the searching by all possible locators.
     *
     * @return the strategy which defines how to use locators which are described by the
     *     {@link AndroidFindBy} annotation
     */
    LocatorGroupStrategy androidAutomation() default LocatorGroupStrategy.CHAIN;

    /**
     * The strategy which defines how to use locators which are described by the
     * {@link iOSXCUITFindBy} annotation. These annotations can define the chained searching
     * or the searching by all possible locators.
     *
     * @return the strategy which defines how to use locators which are described by the
     *     {@link iOSXCUITFindBy} annotation
     */
    LocatorGroupStrategy iOSXCUITAutomation() default LocatorGroupStrategy.CHAIN;
}
