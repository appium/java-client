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

import io.appium.java_client.support.AbstractFindByBuilder;
import io.appium.java_client.support.FindAll;
import io.appium.java_client.support.FindBy;
import io.appium.java_client.support.FindBys;
import io.appium.java_client.support.PageFactoryFinder;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Shared resolution, Selenium built-in adaptation, validation, and builder invocation helpers
 * for Appium {@link PageFactoryFinder}-annotated locator annotations.
 */
public final class PageFactoryFinderSupport {
    private static final String SELENIUM_SUPPORT_PACKAGE = "org.openqa.selenium.support.";
    private static final String SELENIUM_FIND_BY = SELENIUM_SUPPORT_PACKAGE + "FindBy";
    private static final String SELENIUM_FIND_BYS = SELENIUM_SUPPORT_PACKAGE + "FindBys";
    private static final String SELENIUM_FIND_ALL = SELENIUM_SUPPORT_PACKAGE + "FindAll";

    private PageFactoryFinderSupport() {
    }

    /**
     * Checks whether the field carries at least one Appium {@link PageFactoryFinder} annotation
     * (including built-in Appium {@link FindBy}, {@link FindBys}, and {@link FindAll}).
     *
     * @param field the field to inspect
     * @return {@code true} if an Appium finder annotation is present on the field
     */
    public static boolean hasAppiumLocatorAnnotation(Field field) {
        for (Annotation annotation : field.getAnnotations()) {
            if (annotation.annotationType().isAnnotationPresent(PageFactoryFinder.class)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the given annotation on the element, falling back to its Selenium counterpart.
     *
     * @param element the annotated element
     * @param type the Appium annotation type
     * @param <A> the annotation type
     * @return the Appium annotation, an adapted view of the Selenium counterpart, or {@code null}
     */
    @Nullable
    public static <A extends Annotation> A findCompatibleAnnotation(AnnotatedElement element, Class<A> type) {
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
    public static boolean isCompatibleAnnotationPresent(AnnotatedElement element, Class<? extends Annotation> type) {
        return findCompatibleAnnotation(element, type) != null;
    }

    /**
     * Resolves all active Appium {@link PageFactoryFinder} annotations (and un-superseded Selenium
     * built-in {@code @FindBy}/{@code @FindBys}/{@code @FindAll}) on the given element. For classes,
     * inherited annotations are consulted only when no finder annotation is declared on the class itself.
     *
     * @param element the field or class to inspect
     * @return the resolved finder annotations (with Selenium built-ins adapted to Appium types)
     */
    public static List<Annotation> resolveFinderAnnotations(AnnotatedElement element) {
        List<Annotation> declared = collectFinderAnnotations(element.getDeclaredAnnotations(), element, true);
        if (!declared.isEmpty() || !(element instanceof Class<?>)) {
            return declared;
        }
        return collectFinderAnnotations(element.getAnnotations(), element, false);
    }

    /**
     * Validates that mutually exclusive {@link PageFactoryFinder} annotations are not combined
     * on the given element.
     *
     * @param element the field or class to validate
     * @throws IllegalArgumentException if more than one finder annotation is active on the element
     */
    public static void validateFinderAnnotations(AnnotatedElement element) {
        validateFinderAnnotations(resolveFinderAnnotations(element));
    }

    /**
     * Validates that at most one finder annotation is present in the resolved list.
     *
     * @param finders the resolved finder annotations
     * @throws IllegalArgumentException if more than one finder annotation is present
     */
    public static void validateFinderAnnotations(List<Annotation> finders) {
        if (finders.size() > 1) {
            Annotation first = finders.get(0);
            Annotation second = finders.get(1);
            throw new IllegalArgumentException(
                    "If you use a '@" + first.annotationType().getSimpleName() + "' annotation, "
                            + "you must not also use a '@" + second.annotationType().getSimpleName()
                            + "' annotation"
            );
        }
    }

    /**
     * Resolves, validates, and builds a {@link By} locator from the finder annotation on {@code element},
     * or returns {@code null} when no finder annotation is present or its builder returns {@code null}.
     *
     * @param element the field or class to build a locator for
     * @return the built {@link By}, or {@code null}
     */
    @Nullable
    public static By buildBy(AnnotatedElement element) {
        List<Annotation> finders = resolveFinderAnnotations(element);
        validateFinderAnnotations(finders);
        if (finders.isEmpty()) {
            return null;
        }
        Field field = element instanceof Field ? (Field) element : null;
        return buildFromFinder(finders.get(0), field);
    }

    private static List<Annotation> collectFinderAnnotations(
            Annotation[] candidates,
            AnnotatedElement element,
            boolean declaredOnly
    ) {
        List<Annotation> finders = new ArrayList<>();
        for (Annotation candidate : candidates) {
            if (candidate.annotationType().isAnnotationPresent(PageFactoryFinder.class)) {
                finders.add(candidate);
            } else {
                Annotation adapted = adaptBuiltinSeleniumFinder(element, candidate, declaredOnly);
                if (adapted != null) {
                    finders.add(adapted);
                }
            }
        }
        return finders;
    }

    @Nullable
    private static Annotation adaptBuiltinSeleniumFinder(
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

    @Nullable
    @SuppressWarnings("unchecked")
    private static By buildFromFinder(Annotation annotation, @Nullable Field field) {
        PageFactoryFinder finder = annotation.annotationType().getAnnotation(PageFactoryFinder.class);
        if (finder == null) {
            return null;
        }
        Class<? extends AbstractFindByBuilder<?>> builderClass = finder.value();
        AbstractFindByBuilder<Annotation> builder;
        try {
            Constructor<? extends AbstractFindByBuilder<?>> constructor = builderClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            builder = (AbstractFindByBuilder<Annotation>) constructor.newInstance();
        } catch (InvocationTargetException e) {
            throw rethrowUnchecked(e.getCause());
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(
                    "Cannot instantiate PageFactoryFinder builder " + builderClass.getName(), e
            );
        }
        return builder.buildIt(annotation, field);
    }

    private static RuntimeException rethrowUnchecked(@Nullable Throwable cause) {
        if (cause instanceof RuntimeException) {
            return (RuntimeException) cause;
        }
        if (cause instanceof Error) {
            throw (Error) cause;
        }
        return new IllegalStateException(cause);
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
