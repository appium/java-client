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

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Defines set of chained/possible locators. Each one locator
 * should be defined with {@link WindowsFindBy}
 */
@Target(value = {TYPE, FIELD})
@Retention(value = RUNTIME)
public @interface WindowsFindBySet {
    /**
     * An array ofwhich builds a sequence of the chained searching for elements or a set of possible locators.
     *
     * @return an array of {@link WindowsFindBy} which builds a sequence of
     *     the chained searching for elements or a set of possible locators
     */
    WindowsFindBy[] value();
}
