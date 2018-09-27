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
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD, ElementType.TYPE})
public @interface HowToUseLocators {
    /**
     * The strategy which defines how to use locators which are described by the
     * {@link AndroidFindBy} annotation. These annotations can define the chained searching
     * or the searching by all possible locators.
     *
     * @return the strategy which defines how to use locators which are described by the
     * {@link AndroidFindBy} annotation
     */
    LocatorGroupStrategy androidAutomation() default LocatorGroupStrategy.CHAIN;

    /**
     * The strategy which defines how to use locators which are described by the
     * {@link SelendroidFindBy} annotation. These annotations can define the chained searching
     * or the searching by all possible locators.
     *
     * @return the strategy which defines how to use locators which are described by the
     * {@link SelendroidFindBy} annotation
     * @deprecated Selendroid driver is going to be deprecated.
     *      Use {@link AndroidFindBy} instead.
     *      It is recommended to use UIAutomator1 for Android API below 21
     *      and UIAutomator2 for API &gt;= 21
     */
    @Deprecated
    LocatorGroupStrategy selendroidAutomation() default LocatorGroupStrategy.CHAIN;

    /**
     * The strategy which defines how to use locators which are described by the
     * {@link iOSFindBy} annotation. These annotations can define the chained searching
     * or the searching by all possible locators.
     *
     * @return the strategy which defines how to use locators which are described by the
     * {@link iOSFindBy} annotation
     */
    LocatorGroupStrategy iOSAutomation() default LocatorGroupStrategy.CHAIN;

    /**
     * The strategy which defines how to use locators which are described by the
     * {@link WindowsFindBy} annotation. These annotations can define the chained searching
     * or the searching by all possible locators.
     *
     * @return the strategy which defines how to use locators which are described by the
     * {@link WindowsFindBy} annotation
     */
    LocatorGroupStrategy windowsAutomation() default LocatorGroupStrategy.CHAIN;

    /**
     * The strategy which defines how to use locators which are described by the
     * {@link iOSXCUITFindBy} annotation. These annotations can define the chained searching
     * or the searching by all possible locators.
     *
     * @return the strategy which defines how to use locators which are described by the
     * {@link iOSXCUITFindBy} annotation
     */
    LocatorGroupStrategy iOSXCUITAutomation() default LocatorGroupStrategy.CHAIN;
}
