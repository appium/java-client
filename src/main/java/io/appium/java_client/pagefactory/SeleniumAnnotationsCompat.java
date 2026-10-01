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

import org.jspecify.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Lets page objects that still use Selenium's {@code @FindBy}, {@code @FindBys}, {@code @FindAll}
 * and {@code @CacheLookup} keep working when selenium-support is on the classpath.
 * The Selenium annotations are matched by name and read reflectively, so there is no compile-time
 * dependency on Selenium's support package.
 */
final class SeleniumAnnotationsCompat {
    private static final String SELENIUM_SUPPORT_PACKAGE = "org.openqa.selenium.support.";

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
        A annotation = element.getAnnotation(type);
        if (annotation != null) {
            return annotation;
        }
        return Arrays.stream(element.getAnnotations())
                .filter(a -> isSeleniumCounterpart(a, type))
                .findFirst()
                .map(a -> adapt(a, type))
                .orElse(null);
    }

    static boolean isPresent(AnnotatedElement element, Class<? extends Annotation> type) {
        return find(element, type) != null;
    }

    private static boolean isSeleniumCounterpart(Annotation annotation, Class<? extends Annotation> type) {
        return annotation.annotationType().getName().equals(SELENIUM_SUPPORT_PACKAGE + type.getSimpleName());
    }

    private static <A extends Annotation> A adapt(Annotation source, Class<A> target) {
        InvocationHandler handler = (proxy, method, args) -> {
            switch (method.getName()) {
                case "annotationType":
                    return target;
                case "toString":
                    return "Adapted " + source;
                case "hashCode":
                    return source.hashCode();
                case "equals":
                    return proxy == args[0];
                default:
                    return convert(readAttribute(source, method.getName()), method.getReturnType());
            }
        };
        return target.cast(Proxy.newProxyInstance(target.getClassLoader(), new Class<?>[]{target}, handler));
    }

    private static Object readAttribute(Annotation source, String name) throws Throwable {
        try {
            return source.annotationType().getMethod(name).invoke(source);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Object convert(Object value, Class<?> targetType) {
        if (targetType.isEnum()) {
            return Enum.valueOf((Class<? extends Enum>) targetType, ((Enum<?>) value).name());
        }
        if (targetType.isArray() && targetType.getComponentType().isAnnotation()) {
            int length = Array.getLength(value);
            Object result = Array.newInstance(targetType.getComponentType(), length);
            for (int i = 0; i < length; i++) {
                Array.set(result, i, adapt((Annotation) Array.get(value, i),
                        (Class<? extends Annotation>) targetType.getComponentType()));
            }
            return result;
        }
        return value;
    }
}
