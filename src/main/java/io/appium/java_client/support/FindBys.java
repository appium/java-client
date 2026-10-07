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

import io.appium.java_client.support.pagefactory.ByChained;
import org.openqa.selenium.By;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * Used to mark a field on a Page Object to indicate that lookup should use a series of
 * {@link FindBy} tags in a chain as described in {@link ByChained}.
 *
 * <p>Eg:
 *
 * <pre class="code">
 * &#64;FindBys({&#64;FindBy(id = "foo"),
 *           &#64;FindBy(className = "bar")})
 * </pre>
 *
 * <p>Adapted from Selenium's {@code org.openqa.selenium.support.FindBys} (Apache License 2.0).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface FindBys {
    /**
     * The locators to chain.
     *
     * @return the {@link FindBy} annotations to chain
     */
    FindBy[] value();

    /**
     * Builds the {@link By} locator from {@link FindBys}.
     */
    class FindByBuilder extends AbstractFindByBuilder<FindBys> {
        @Override
        public By buildIt(FindBys findBys, Field field) {
            assertValidFindBys(findBys);

            FindBy[] findByArray = findBys.value();
            By[] byArray = new By[findByArray.length];
            for (int i = 0; i < findByArray.length; i++) {
                byArray[i] = buildByFromFindBy(findByArray[i]);
            }

            return new ByChained(byArray);
        }
    }
}
