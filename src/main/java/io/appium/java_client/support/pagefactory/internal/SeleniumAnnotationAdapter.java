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

package io.appium.java_client.support.pagefactory.internal;

import io.appium.java_client.support.FindAll;
import io.appium.java_client.support.FindBy;
import io.appium.java_client.support.FindBys;
import org.jspecify.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Reflection and dynamic-proxy shims adapting built-in {@code org.openqa.selenium.support}
 * page-factory annotations to their Appium counterparts.
 */
final class SeleniumAnnotationAdapter {
    private static final String SELENIUM_SUPPORT_PACKAGE = "org.openqa.selenium.support.";
    private static final String SELENIUM_FIND_BY = SELENIUM_SUPPORT_PACKAGE + "FindBy";
    private static final String SELENIUM_FIND_BYS = SELENIUM_SUPPORT_PACKAGE + "FindBys";
    private static final String SELENIUM_FIND_ALL = SELENIUM_SUPPORT_PACKAGE + "FindAll";

    private SeleniumAnnotationAdapter() {
    }

    /**
     * Finds the given Appium annotation on the element, falling back to its Selenium counterpart.
     *
     * @param element the annotated element
     * @param type the Appium annotation type
     * @param <A> the annotation type
     * @return the Appium annotation, an adapted view of the Selenium counterpart, or {@code null}
     */
    @Nullable
    static <A extends Annotation> A findCompatibleAnnotation(AnnotatedElement element, Class<A> type) {
        A annotation = element.getAnnotation(type);
        if (annotation != null) {
            return annotation;
        }
        return Arrays.stream(element.getAnnotations())
                .filter(candidate -> isSeleniumCounterpart(candidate, type))
                .findFirst()
                .map(candidate -> adapt(candidate, type))
                .orElse(null);
    }

    /**
     * Checks whether the given Appium annotation or its Selenium counterpart is present on the element.
     *
     * @param element the annotated element
     * @param type the Appium annotation type
     * @return {@code true} if the Appium annotation or Selenium counterpart is present
     */
    static boolean isCompatibleAnnotationPresent(AnnotatedElement element, Class<? extends Annotation> type) {
        return findCompatibleAnnotation(element, type) != null;
    }

    /**
     * Adapts a built-in Selenium finder annotation to the corresponding Appium annotation unless the
     * matching Appium annotation is already present at the same lookup scope.
     *
     * @param element the annotated element being inspected
     * @param candidate the annotation instance encountered on {@code element}
     * @param declaredOnly whether precedence checks only declared annotations (for fields) or inherited ones
     * @return the adapted Appium annotation, or {@code null} when {@code candidate} is not a Selenium
     *     built-in finder or is superseded by an Appium annotation
     */
    @Nullable
    static Annotation adaptBuiltinSeleniumFinder(
            AnnotatedElement element,
            Annotation candidate,
            boolean declaredOnly
    ) {
        String name = candidate.annotationType().getName();
        if (SELENIUM_FIND_BY.equals(name)) {
            return hasAnnotation(element, FindBy.class, declaredOnly) ? null : adapt(candidate, FindBy.class);
        }
        if (SELENIUM_FIND_BYS.equals(name)) {
            return hasAnnotation(element, FindBys.class, declaredOnly) ? null : adapt(candidate, FindBys.class);
        }
        if (SELENIUM_FIND_ALL.equals(name)) {
            return hasAnnotation(element, FindAll.class, declaredOnly) ? null : adapt(candidate, FindAll.class);
        }
        return null;
    }

    private static boolean hasAnnotation(
            AnnotatedElement element,
            Class<? extends Annotation> type,
            boolean declaredOnly
    ) {
        return (declaredOnly ? element.getDeclaredAnnotation(type) : element.getAnnotation(type)) != null;
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
                    return args != null && proxy == args[0];
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
