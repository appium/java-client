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

import static io.appium.java_client.remote.AutomationName.SELENDROID;
import static io.appium.java_client.remote.MobilePlatform.ANDROID;
import static io.appium.java_client.remote.MobilePlatform.IOS;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.pagefactory.ByChained;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * It is the basic handler of Appium-specific page object annotations
 * About the Page Object design pattern please read these documents:
 * - https://code.google.com/p/selenium/wiki/PageObjects
 * - https://code.google.com/p/selenium/wiki/PageFactory
 */
public abstract class AppiumByBuilder extends AbstractAnnotations {
    static final Class<?>[] DEFAULT_ANNOTATION_METHOD_ARGUMENTS = new Class<?>[] {};

    private static final List<String> METHODS_TO_BE_EXCLUDED_WHEN_ANNOTATION_IS_READ =
        new ArrayList<String>() {
            private static final long serialVersionUID = 1L; {
                List<String> objectClassMethodNames =
                    getMethodNames(Object.class.getDeclaredMethods());
                addAll(objectClassMethodNames);
                List<String> annotationClassMethodNames =
                    getMethodNames(Annotation.class.getDeclaredMethods());
                annotationClassMethodNames.removeAll(objectClassMethodNames);
                addAll(annotationClassMethodNames);
            }
        };
    protected final AnnotatedElementContainer annotatedElementContainer;
    protected final String platform;
    protected final String automation;

    protected AppiumByBuilder(String platform, String automation) {
        this.annotatedElementContainer = new AnnotatedElementContainer();
        this.platform = String.valueOf(platform).toUpperCase().trim();
        this.automation = String.valueOf(automation).toUpperCase().trim();
    }

    private static List<String> getMethodNames(Method[] methods) {
        List<String> names = new ArrayList<>();
        for (Method m : methods) {
            names.add(m.getName());
        }
        return names;
    }

    private static Method[] prepareAnnotationMethods(Class<? extends Annotation> annotation) {
        List<String> targeAnnotationMethodNamesList =
            getMethodNames(annotation.getDeclaredMethods());
        targeAnnotationMethodNamesList.removeAll(METHODS_TO_BE_EXCLUDED_WHEN_ANNOTATION_IS_READ);
        Method[] result = new Method[targeAnnotationMethodNamesList.size()];
        for (String methodName : targeAnnotationMethodNamesList) {
            try {
                result[targeAnnotationMethodNamesList.indexOf(methodName)] =
                    annotation.getMethod(methodName, DEFAULT_ANNOTATION_METHOD_ARGUMENTS);
            } catch (NoSuchMethodException | SecurityException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    private static String getFilledValue(Annotation mobileBy) {
        Method[] values = prepareAnnotationMethods(mobileBy.getClass());
        for (Method value : values) {
            try {
                String strategyParameter = value.invoke(mobileBy, new Object[] {}).toString();
                if (!"".equals(strategyParameter)) {
                    return value.getName();
                }
            } catch (IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        throw new IllegalArgumentException(
            "@" + mobileBy.getClass().getSimpleName() + ": one of " + Strategies.strategiesNames()
                .toString() + " should be filled");
    }

    private static By getMobileBy(Annotation annotation, String valueName) {
        Strategies[] strategies = Strategies.values();
        for (Strategies strategy : strategies) {
            if (strategy.returnValueName().equals(valueName)) {
                return strategy.getBy(annotation);
            }
        }
        throw new IllegalArgumentException(
            "@" + annotation.getClass().getSimpleName() + ": There is an unknown strategy "
                + valueName);
    }

    @SuppressWarnings("unchecked")
    private static <T extends By> T getComplexMobileBy(Annotation[] annotations,
        Class<T> requiredByClass) {
        By[] byArray = new By[annotations.length];
        for (int i = 0; i < annotations.length; i++) {
            byArray[i] = getMobileBy(annotations[i], getFilledValue(annotations[i]));
        }
        try {
            Constructor<?> c = requiredByClass.getConstructor(By[].class);
            Object[] values = new Object[] {byArray};
            return (T) c.newInstance(values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
     * By-locator strategies
     *
     * @param annotated is an instance of class which type extends
     *                  {@link java.lang.reflect.AnnotatedElement}
     */
    public void setAnnotated(AnnotatedElement annotated) {
        this.annotatedElementContainer.setAnnotated(annotated);
    }

    protected boolean isAndroid() {
        return ANDROID.toUpperCase().equals(platform);
    }

    protected boolean isSelendroidAutomation() {
        return isAndroid() && SELENDROID.toUpperCase().equals(automation);
    }

    protected boolean isIOS() {
        return IOS.toUpperCase().equals(platform);
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
