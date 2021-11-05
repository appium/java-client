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

package io.appium.java_client.pagefactory.bys.builder;

import static io.appium.java_client.remote.AutomationName.IOS_XCUI_TEST;
import static io.appium.java_client.remote.MobilePlatform.ANDROID;
import static io.appium.java_client.remote.MobilePlatform.IOS;
import static io.appium.java_client.remote.MobilePlatform.TVOS;
import static io.appium.java_client.remote.MobilePlatform.WINDOWS;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * It is the basic handler of Appium-specific page object annotations
 * About the Page Object design pattern please read these documents:
 * - https://code.google.com/p/selenium/wiki/PageObjects
 * - https://code.google.com/p/selenium/wiki/PageFactory
 */
public abstract class AppiumByBuilder extends AbstractAnnotations {
    protected static final Class<?>[] DEFAULT_ANNOTATION_METHOD_ARGUMENTS = new Class<?>[]{};

    private static final List<String> METHODS_TO_BE_EXCLUDED_WHEN_ANNOTATION_IS_READ = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;

        {
            Stream.of(Object.class, Annotation.class, Proxy.class)
                    .map(Class::getDeclaredMethods)
                    .map(AppiumByBuilder::getMethodNames)
                    .flatMap(List::stream)
                    .forEach(this::add);
        }
    };
    protected final AnnotatedElementContainer annotatedElementContainer;
    protected final String platform;
    protected final String automation;

    protected AppiumByBuilder(String platform, String automation) {
        this.annotatedElementContainer = new AnnotatedElementContainer();
        this.platform = String.valueOf(platform);
        this.automation = String.valueOf(automation);
    }

    private static List<String> getMethodNames(Method[] methods) {
        return Stream.of(methods).map(Method::getName).collect(Collectors.toList());
    }

    private static Method[] prepareAnnotationMethods(Class<? extends Annotation> annotation) {
        List<String> targetAnnotationMethodNamesList = getMethodNames(annotation.getDeclaredMethods());
        targetAnnotationMethodNamesList.removeAll(METHODS_TO_BE_EXCLUDED_WHEN_ANNOTATION_IS_READ);
        return targetAnnotationMethodNamesList.stream()
                .map((methodName) -> {
                    try {
                        return annotation.getMethod(methodName, DEFAULT_ANNOTATION_METHOD_ARGUMENTS);
                    } catch (NoSuchMethodException | SecurityException e) {
                        throw new RuntimeException(e);
                    }
                }).toArray(Method[]::new);
    }

    private static String getFilledValue(Annotation mobileBy) {
        return Stream.of(prepareAnnotationMethods(mobileBy.getClass()))
                .filter((method) -> String.class == method.getReturnType())
                .filter((method) -> {
                    try {
                        Object strategyParameter = method.invoke(mobileBy);
                        return strategyParameter != null && !String.valueOf(strategyParameter).isEmpty();
                    } catch (IllegalAccessException | IllegalArgumentException
                            | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                })
                .findFirst()
                .map(Method::getName)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("@%s: one of %s should be filled",
                                mobileBy.getClass().getSimpleName(), Strategies.strategiesNames())
                ));
    }

    private static By getMobileBy(Annotation annotation, String valueName) {
        return Stream.of(Strategies.values())
                .filter((strategy) -> strategy.returnValueName().equals(valueName))
                .findFirst()
                .map((strategy) -> strategy.getBy(annotation))
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("@%s: There is an unknown strategy %s",
                                annotation.getClass().getSimpleName(), valueName)
                ));
    }

    private static <T extends By> T getComplexMobileBy(Annotation[] annotations, Class<T> requiredByClass) {
        By[] byArray = Stream.of(annotations)
                .map((annotation) -> getMobileBy(annotation, getFilledValue(annotation)))
                .toArray(By[]::new);
        try {
            Constructor<T> c = requiredByClass.getConstructor(By[].class);
            Object[] values = new Object[]{byArray};
            return c.newInstance(values);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException
                | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    protected static By createBy(Annotation[] annotations, HowToUseSelectors howToUseLocators) {
        if (annotations == null || annotations.length == 0) {
            return null;
        }

        switch (howToUseLocators) {
            case USE_ONE: {
                return getMobileBy(annotations[0], getFilledValue(annotations[0]));
            }
            case BUILD_CHAINED: {
                return getComplexMobileBy(annotations, ByChained.class);
            }
            case USE_ANY: {
                return getComplexMobileBy(annotations, ByAll.class);
            }
            default: {
                return null;
            }
        }
    }

    /**
     * This method should be used for the setting up of
     * AnnotatedElement instances before the building of
     * By-locator strategies.
     *
     * @param annotated is an instance of class which type extends
     *                  {@link java.lang.reflect.AnnotatedElement}
     */
    public void setAnnotated(AnnotatedElement annotated) {
        this.annotatedElementContainer.setAnnotated(annotated);
    }

    protected boolean isAndroid() {
        return ANDROID.equalsIgnoreCase(platform);
    }

    protected boolean isIOS() {
        return IOS.equalsIgnoreCase(platform);
    }

    protected boolean isTvOS() {
        return TVOS.equalsIgnoreCase(platform);
    }

    protected boolean isIOSXcuit() {
        return isIOS() && IOS_XCUI_TEST.equalsIgnoreCase(automation);
    }

    protected boolean isWindows() {
        return WINDOWS.equalsIgnoreCase(platform);
    }

    /**
     * Defines how to transform given object (field, class, etc)
     * into {@link org.openqa.selenium.By} class used by webdriver to locate elements.
     */
    public abstract By buildBy();

    /**
     * Defines whether or not given element
     * should be returned from cache on further calls.
     */
    public abstract boolean isLookupCached();

    protected abstract By buildDefaultBy();

    protected abstract By buildMobileNativeBy();

    protected abstract void assertValidAnnotations();
}
