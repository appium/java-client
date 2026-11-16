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
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Shared discovery, validation, and builder invocation helpers for Appium
 * {@link PageFactoryFinder}-annotated locator annotations.
 */
public final class PageFactoryFinderSupport {
    private static final Comparator<Annotation> FINDER_ORDER = Comparator
            .comparingInt((Annotation annotation) -> builtinPriority(annotation.annotationType()))
            .thenComparing(annotation -> annotation.annotationType().getName());

    private PageFactoryFinderSupport() {
    }

    /**
     * Checks whether the field carries at least one Appium {@link PageFactoryFinder} annotation.
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
     * Checks whether the given Appium annotation or its Selenium counterpart is present on the element.
     *
     * @param element the annotated element
     * @param type the Appium annotation type
     * @return {@code true} if the Appium annotation or Selenium counterpart is present
     */
    public static boolean isCompatibleAnnotationPresent(AnnotatedElement element, Class<? extends Annotation> type) {
        return SeleniumAnnotationAdapter.isCompatibleAnnotationPresent(element, type);
    }

    /**
     * Resolves all active Appium {@link PageFactoryFinder} annotations (and un-superseded Selenium
     * built-in {@code @FindBy}/{@code @FindBys}/{@code @FindAll}) on the given element, ordered
     * deterministically as {@code @FindBy}, {@code @FindBys}, {@code @FindAll}, and then custom
     * finder annotations sorted by annotation type name. For classes, inherited annotations are
     * consulted only when no finder annotation is declared on the class itself.
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
        return buildBy(element, true);
    }

    /**
     * Resolves and builds a {@link By} locator from the active finder annotations on {@code element}
     * in deterministic priority order, continuing to the next finder if a builder returns
     * {@code null} and optionally validating that multiple finder annotations are not combined on
     * {@code element}.
     *
     * @param element the field or class to build a locator for
     * @param validateConflicts whether to throw when multiple finder annotations are active
     * @return the built {@link By}, or {@code null}
     */
    @Nullable
    public static By buildBy(AnnotatedElement element, boolean validateConflicts) {
        List<Annotation> finders = resolveFinderAnnotations(element);
        if (validateConflicts) {
            validateFinderAnnotations(finders);
        }
        Field field = element instanceof Field ? (Field) element : null;
        for (Annotation finder : finders) {
            By by = buildFromFinder(finder, field);
            if (by != null) {
                return by;
            }
        }
        return null;
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
                Annotation adapted = SeleniumAnnotationAdapter.adaptBuiltinSeleniumFinder(
                        element, candidate, declaredOnly
                );
                if (adapted != null) {
                    finders.add(adapted);
                }
            }
        }
        finders.sort(FINDER_ORDER);
        return finders;
    }

    private static int builtinPriority(Class<? extends Annotation> annotationType) {
        if (FindBy.class.equals(annotationType)) {
            return 0;
        }
        if (FindBys.class.equals(annotationType)) {
            return 1;
        }
        if (FindAll.class.equals(annotationType)) {
            return 2;
        }
        return 3;
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
}
