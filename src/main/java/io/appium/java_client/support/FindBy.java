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

package io.appium.java_client.support;

import org.openqa.selenium.By;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * Used to mark a field on a Page Object to indicate an alternative mechanism for locating the
 * element or a list of elements. Used in conjunction with {@link PageFactory} this allows users to
 * quickly and easily create PageObjects.
 *
 * <p>You can either use this annotation by specifying both "how" and "using" or by specifying one
 * of the location strategies (eg: "id") with an appropriate value to use. Both options will
 * delegate down to the matching {@link By} methods in By class.
 *
 * <p>For example, these two annotations point to the same element:
 *
 * <pre class="code">
 * &#64;FindBy(id = "foobar") WebElement foobar;
 * &#64;FindBy(how = How.ID, using = "foobar") WebElement foobar;
 * </pre>
 *
 * <p>Adapted from Selenium's {@code org.openqa.selenium.support.FindBy} (Apache License 2.0).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface FindBy {
    /**
     * The location strategy to use together with {@link #using()}.
     *
     * @return the configured value
     */
    How how() default How.UNSET;

    /**
     * The value of the strategy given in {@link #how()}.
     *
     * @return the configured value
     */
    String using() default "";

    /**
     * The element id.
     *
     * @return the configured value
     */
    String id() default "";

    /**
     * The element name.
     *
     * @return the configured value
     */
    String name() default "";

    /**
     * The element class name.
     *
     * @return the configured value
     */
    String className() default "";

    /**
     * The CSS selector.
     *
     * @return the configured value
     */
    String css() default "";

    /**
     * The element tag name.
     *
     * @return the configured value
     */
    String tagName() default "";

    /**
     * The exact link text.
     *
     * @return the configured value
     */
    String linkText() default "";

    /**
     * A part of the link text.
     *
     * @return the configured value
     */
    String partialLinkText() default "";

    /**
     * The XPath expression.
     *
     * @return the configured value
     */
    String xpath() default "";

    class FindByBuilder extends AbstractFindByBuilder<FindBy> {
        @Override
        public By buildIt(FindBy findBy, Field field) {
            assertValidFindBy(findBy);

            By ans = buildByFromShortFindBy(findBy);
            if (ans == null) {
                ans = buildByFromLongFindBy(findBy);
            }

            return ans;
        }
    }
}
