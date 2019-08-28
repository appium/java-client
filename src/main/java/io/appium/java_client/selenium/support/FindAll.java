// Licensed to the Software Freedom Conservancy (SFC) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The SFC licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package io.appium.java_client.selenium.support;

import io.appium.java_client.selenium.By;
import io.appium.java_client.selenium.support.pagefactory.ByAll;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * Used to mark a field on a Page Object to indicate that lookup should use a series of @FindBy tags
 * It will then search for all elements that match any of the FindBy criteria. Note that elements
 * are not guaranteed to be in document order.
 *
 * <p>It can be used on a types as well, but will not be processed by default.
 *
 * <p>Eg:
 *
 * <pre class="code">
 * &#64;FindAll({&#64;FindBy(how = How.ID, using = "foo"),
 *           &#64;FindBy(className = "bar")})
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@PageFactoryFinder(FindAll.FindByBuilder.class)
public @interface FindAll {
    FindBy[] value();

    class FindByBuilder extends AbstractFindByBuilder {
        @Override
        public By buildIt(Object annotation, Field field) {
            FindAll findBys = (FindAll) annotation;
            assertValidFindAll(findBys);

            FindBy[] findByArray = findBys.value();
            By[] byArray = new By[findByArray.length];
            for (int i = 0; i < findByArray.length; i++) {
                byArray[i] = buildByFromFindBy(findByArray[i]);
            }

            return new ByAll(byArray);
        }

    }

}
