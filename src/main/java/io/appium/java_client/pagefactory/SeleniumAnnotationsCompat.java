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

import io.appium.java_client.support.pagefactory.internal.PageFactoryFinderSupport;
import org.jspecify.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * Lets page objects that still use Selenium's {@code @FindBy}, {@code @FindBys}, {@code @FindAll}
 * and {@code @CacheLookup} keep working when selenium-support is on the classpath.
 * The Selenium annotations are matched by name and read reflectively, so there is no compile-time
 * dependency on Selenium's support package.
 */
final class SeleniumAnnotationsCompat {
    private SeleniumAnnotationsCompat() {
    }

    /**
     * Finds the given annotation on the element, falling back to its Selenium counterpart.
     *
     * @param element the annotated element
     * @param type the Appium annotation type
     * @param <A> the annotation type
     * @return the Appium annotation, or a view of the Selenium one, or null if none is present
     */
    @Nullable
    static <A extends Annotation> A find(AnnotatedElement element, Class<A> type) {
        return PageFactoryFinderSupport.findCompatibleAnnotation(element, type);
    }

    static boolean isPresent(AnnotatedElement element, Class<? extends Annotation> type) {
        return PageFactoryFinderSupport.isCompatibleAnnotationPresent(element, type);
    }
}
