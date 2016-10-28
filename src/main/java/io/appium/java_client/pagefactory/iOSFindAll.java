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

/**
 * Used to mark a field on a Page/Screen Object to indicate that lookup should use a series
 * of {@link io.appium.java_client.pagefactory.iOSFindBy} tags
 * It will then search for all elements that match any criteria. Note that elements
 * are not guaranteed to be in document order.
 * It is deprecated. Set of {@link io.appium.java_client.pagefactory.iOSFindBy}
 * can be defined without this annotation. To define the correct way how to use
 * the defined set please take a look at {@link HowToUseLocators}. The article.
 * https://docs.oracle.com/javase/tutorial/java/annotations/repeating.html.
 */
@Deprecated
@Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD, ElementType.TYPE})
public @interface iOSFindAll {
    /**
     * It is a set of {@link io.appium.java_client.pagefactory.iOSFindBy} strategies which may be
     * used to find the target element.
     */
    iOSFindBy[] value();
}
