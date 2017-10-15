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

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * Used to mark a field on a Page Object to indicate an alternative mechanism for locating the
 * element or a list of elements. Used in conjunction with
 * {@link org.openqa.selenium.support.PageFactory}
 * this allows users to quickly and easily create PageObjects.
 * using Selendroid UI selectors like, id, name, class name, tag and xpath
 */
@Retention(RUNTIME) @Target({FIELD, TYPE})
@Repeatable(SelendroidFindBySet.class)
public @interface SelendroidFindBy {
    /**
     * It is an id of the target element.
     */
    String id() default "";

    /**
     * It is used in Selendroid mode instead of accessibility id.
     */
    String name() default "";

    /**
     * It is a className of the target element.
     */
    String className() default "";

    /**
     * It is a desired element tag.
     */
    String tagName() default "";

    /**
     * It is a xpath to the target element.
     */
    String xpath() default "";

    /**
     * It is a text of the desired element.
     */
    String linkText() default "";

    /**
     * It is a part of the text of the desired element.
     */
    String partialLinkText() default "";

    /**
     * @return priority of the searching. Higher number means lower priority.
     */
    int priority() default 0;
}
