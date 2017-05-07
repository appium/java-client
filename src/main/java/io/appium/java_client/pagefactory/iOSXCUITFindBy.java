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

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME) @Target({FIELD, TYPE})
@Repeatable(iOSXCUITFindBySet.class)
public @interface iOSXCUITFindBy {

    /**
     * The Class Chain locator is similar to xpath, but it's faster and can only
     * search direct children elements. See the
     * <a href="https://github.com/facebook/WebDriverAgent/wiki/Queries">
     * documentation</a> for more details.
     */
    String iOSClassChain() default "";

    /**
     * The NSPredicate class is used to define logical conditions used to constrain
     * a search either for a fetch or for in-memory filtering.
     */
    String iOSNsPredicate() default "";

    /**
     * It an UI automation accessibility Id which is a convenient to iOS.
     * About iOS accessibility
     * See  <a href="https://goo.gl/a3AivX">UIAccessibilityIdentification</a>
     */
    String accessibility() default "";

    /**
     * It is an id of the target element.
     */
    String id() default "";

    /**
     * It is a name of a type/class of the target element.
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
     * @return priority of the searching
     */
    int priority() default 0;
}
