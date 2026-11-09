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

import io.appium.java_client.pagefactory.bys.ContentMappedBy;
import io.appium.java_client.pagefactory.bys.ContentType;
import io.appium.java_client.pagefactory.bys.builder.AppiumByBuilder;
import io.appium.java_client.pagefactory.bys.builder.ByChained;
import io.appium.java_client.pagefactory.bys.builder.HowToUseSelectors;
import io.appium.java_client.support.ByIdOrName;
import io.appium.java_client.support.CacheLookup;
import io.appium.java_client.support.FindAll;
import io.appium.java_client.support.FindBy;
import io.appium.java_client.support.FindBys;
import io.appium.java_client.support.PageFactory;
import io.appium.java_client.support.pagefactory.ByAll;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.appium.java_client.pagefactory.AppiumFieldDecorator.extractWidgetClass;
import static java.lang.Integer.signum;
import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;

/** Builds locators of elements from Appium-specific page object annotations. */
public class DefaultElementByBuilder extends AppiumByBuilder {

    private static final String PRIORITY = "priority";
    private static final String VALUE = "value";
    private static final Class<?>[] ANNOTATION_ARGUMENTS = new Class[]{};
    private static final Object[] ANNOTATION_PARAMETERS = new Object[]{};
    private final LocatorGroupStrategy defaultLocatorGroupStrategy;
    @Nullable
    private Class<?> currentPageClass;
    @Nullable
    private Class<? extends Widget> currentWidgetClass;

    /**
     * Creates a new builder.
     *
     * @param platform the name of the current platform
     * @param automation the name of the current automation
     */
    public DefaultElementByBuilder(String platform, String automation) {
        this(platform, automation, LocatorGroupStrategy.CHAIN);
    }

    /**
     * Creates a new builder with a custom default {@link LocatorGroupStrategy}.
     *
     * @param platform the name of the current platform
     * @param automation the name of the current automation
     * @param defaultLocatorGroupStrategy the default strategy used when {@link HowToUseLocators} is not present
     */
    public DefaultElementByBuilder(
            String platform,
            String automation,
            LocatorGroupStrategy defaultLocatorGroupStrategy
    ) {
        super(platform, automation);
        this.defaultLocatorGroupStrategy = requireNonNull(
                defaultLocatorGroupStrategy,
                "Default locator group strategy must not be null"
        );
    }

    private static void checkDisallowedAnnotationPairs(Annotation a1, Annotation a2)
            throws IllegalArgumentException {
        if (a1 != null && a2 != null) {
            throw new IllegalArgumentException(
                    "If you use a '@" + a1.getClass().getSimpleName() + "' annotation, "
                            + "you must not also use a '@" + a2.getClass().getSimpleName()
                            + "' annotation");
        }
    }

    private static By buildMobileBy(LocatorGroupStrategy locatorGroupStrategy, By[] bys) {
        if (bys.length == 0) {
            return null;
        }
        if (LocatorGroupStrategy.ALL_POSSIBLE.equals(locatorGroupStrategy)) {
            return new ByAll(bys);
        }
        return new ByChained(bys);
    }

    @Override
    protected void assertValidAnnotations() {
        AnnotatedElement annotatedElement = annotatedElementContainer.getAnnotated();
        FindBy findBy = SeleniumAnnotationsCompat.find(annotatedElement, FindBy.class);
        FindBys findBys = SeleniumAnnotationsCompat.find(annotatedElement, FindBys.class);
        checkDisallowedAnnotationPairs(findBy, findBys);
        FindAll findAll = SeleniumAnnotationsCompat.find(annotatedElement, FindAll.class);
        checkDisallowedAnnotationPairs(findBy, findAll);
        checkDisallowedAnnotationPairs(findBys, findAll);
    }

    @Override
    protected By buildDefaultBy() {
        AnnotatedElement annotatedElement = annotatedElementContainer.getAnnotated();
        Field field = annotatedElement instanceof Field ? (Field) annotatedElement : null;
        By defaultBy = null;
        FindBy findBy = SeleniumAnnotationsCompat.find(annotatedElement, FindBy.class);
        if (findBy != null) {
            defaultBy = new FindBy.FindByBuilder().buildIt(findBy, field);
        }

        if (defaultBy == null) {
            FindBys findBys = SeleniumAnnotationsCompat.find(annotatedElement, FindBys.class);
            if (findBys != null) {
                defaultBy = new FindBys.FindByBuilder().buildIt(findBys, field);
            }
        }

        if (defaultBy == null) {
            FindAll findAll = SeleniumAnnotationsCompat.find(annotatedElement, FindAll.class);
            if (findAll != null) {
                defaultBy = new FindAll.FindByBuilder().buildIt(findAll, field);
            }
        }
        return defaultBy;
    }

    private By[] getBys(Class<? extends Annotation> singleLocator, Class<? extends Annotation> chainedLocator,
                        Class<? extends Annotation> allLocator) {
        AnnotationComparator comparator = new AnnotationComparator();
        AnnotatedElement annotatedElement = annotatedElementContainer.getAnnotated();

        List<Annotation> annotations = new ArrayList<>(asList(annotatedElement.getAnnotationsByType(singleLocator)));
        annotations.addAll(asList(annotatedElement.getAnnotationsByType(chainedLocator)));
        annotations.addAll(asList(annotatedElement.getAnnotationsByType(allLocator)));

        annotations.sort(comparator);
        List<By> result = new ArrayList<>();

        for (Annotation a : annotations) {
            Class<?> annotationClass = a.annotationType();
            if (singleLocator.equals(annotationClass)) {
                result.add(createBy(new Annotation[]{a}, HowToUseSelectors.USE_ONE));
                continue;
            }

            Method value;
            Annotation[] subLocators;
            try {
                value = annotationClass.getMethod(VALUE, ANNOTATION_ARGUMENTS);
                subLocators = (Annotation[]) value.invoke(a, ANNOTATION_PARAMETERS);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new ClassCastException(String.format("The annotation '%s' has no convenient '%s' method which "
                        + "returns array of annotations", annotationClass.getName(), VALUE));
            }

            Arrays.sort(subLocators, comparator);
            if (chainedLocator.equals(annotationClass)) {
                result.add(createBy(subLocators, HowToUseSelectors.BUILD_CHAINED));
                continue;
            }

            if (allLocator.equals(annotationClass)) {
                result.add(createBy(subLocators, HowToUseSelectors.USE_ANY));
            }
        }

        return result.toArray(new By[0]);
    }

    void setDecorationContext(@Nullable Class<?> pageClass, @Nullable Class<? extends Widget> widgetClass) {
        this.currentPageClass = pageClass;
        this.currentWidgetClass = widgetClass;
    }

    @Nullable
    Class<? extends Widget> resolveWidgetClassForField(Field field) {
        if (currentWidgetClass != null) {
            return currentWidgetClass;
        }
        Class<?> pageClass = currentPageClass != null ? currentPageClass : PageFactory.getCurrentPageClass();
        return extractWidgetClass(field, pageClass);
    }

    private Optional<HowToUseLocators> findHowToUseLocators(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return Optional.empty();
        }
        HowToUseLocators direct = annotatedElement.getAnnotation(HowToUseLocators.class);
        if (direct != null) {
            return Optional.of(direct);
        }
        if (annotatedElement instanceof Field) {
            Field field = (Field) annotatedElement;
            Class<?> widgetClass = resolveWidgetClassForField(field);
            if (widgetClass != null) {
                HowToUseLocators widgetAnnotation = findClassHowToUseLocators(widgetClass);
                if (widgetAnnotation != null) {
                    return Optional.of(widgetAnnotation);
                }
            }
            Class<?> pageClass = currentPageClass != null ? currentPageClass : PageFactory.getCurrentPageClass();
            if (pageClass != null && field.getDeclaringClass().isAssignableFrom(pageClass)) {
                HowToUseLocators pageAnnotation = findClassHowToUseLocators(pageClass);
                if (pageAnnotation != null) {
                    return Optional.of(pageAnnotation);
                }
            }
            return Optional.ofNullable(findClassHowToUseLocators(field.getDeclaringClass()));
        }
        if (annotatedElement instanceof Class<?>) {
            return Optional.ofNullable(findClassHowToUseLocators((Class<?>) annotatedElement));
        }
        return Optional.empty();
    }

    @Nullable
    private static HowToUseLocators findClassHowToUseLocators(@Nullable Class<?> clazz) {
        for (Class<?> current = clazz; current != null; current = current.getEnclosingClass()) {
            HowToUseLocators annotation = current.getAnnotation(HowToUseLocators.class);
            if (annotation != null) {
                return annotation;
            }
            HowToUseLocators interfaceAnnotation = findInterfaceHowToUseLocators(current);
            if (interfaceAnnotation != null) {
                return interfaceAnnotation;
            }
        }
        return null;
    }

    @Nullable
    private static HowToUseLocators findInterfaceHowToUseLocators(@Nullable Class<?> clazz) {
        for (Class<?> current = clazz; current != null && !Object.class.equals(current);
                current = current.getSuperclass()) {
            for (Class<?> iface : current.getInterfaces()) {
                HowToUseLocators annotation = findOnInterfaceHierarchy(iface);
                if (annotation != null) {
                    return annotation;
                }
            }
        }
        return null;
    }

    @Nullable
    private static HowToUseLocators findOnInterfaceHierarchy(Class<?> iface) {
        HowToUseLocators direct = iface.getAnnotation(HowToUseLocators.class);
        if (direct != null) {
            return direct;
        }
        for (Class<?> parentIface : iface.getInterfaces()) {
            HowToUseLocators parentAnnotation = findOnInterfaceHierarchy(parentIface);
            if (parentAnnotation != null) {
                return parentAnnotation;
            }
        }
        return null;
    }

    @Override
    protected By buildMobileNativeBy() {
        AnnotatedElement annotatedElement = annotatedElementContainer.getAnnotated();
        Optional<HowToUseLocators> howToUseLocatorsOptional = findHowToUseLocators(annotatedElement);

        if (isAndroid()) {
            return buildMobileBy(
                    howToUseLocatorsOptional
                            .map(HowToUseLocators::androidAutomation)
                            .orElse(defaultLocatorGroupStrategy),
                    getBys(AndroidFindBy.class, AndroidFindBys.class, AndroidFindAll.class)
            );
        }

        if (isIOSXcuit() || isIOS() || isTvOS()) {
            return buildMobileBy(
                    howToUseLocatorsOptional
                            .map(HowToUseLocators::iOSXCUITAutomation)
                            .orElse(defaultLocatorGroupStrategy),
                    getBys(iOSXCUITFindBy.class, iOSXCUITFindBys.class, iOSXCUITFindAll.class)
            );
        }

        return null;
    }

    @Override
    public boolean isLookupCached() {
        AnnotatedElement annotatedElement = annotatedElementContainer.getAnnotated();
        return SeleniumAnnotationsCompat.isPresent(annotatedElement, CacheLookup.class);
    }

    private By returnMappedBy(By byDefault, By nativeAppBy) {
        Map<ContentType, By> contentMap = new HashMap<>();
        contentMap.put(ContentType.HTML_OR_DEFAULT, byDefault);
        contentMap.put(ContentType.NATIVE_MOBILE_SPECIFIC, nativeAppBy);
        return new ContentMappedBy(contentMap);
    }

    By buildMappedBy(@Nullable By defaultBy, @Nullable By mobileNativeBy) {
        AnnotatedElement annotatedElement = annotatedElementContainer.getAnnotated();
        String idOrName = annotatedElement instanceof Field
                ? ((Field) annotatedElement).getName()
                : ((Class<?>) annotatedElement).getSimpleName();

        if (defaultBy == null && mobileNativeBy == null) {
            return returnMappedBy(new ByIdOrName(idOrName), new By.ById(idOrName));
        }

        if (defaultBy == null) {
            return returnMappedBy(new ByIdOrName(idOrName), mobileNativeBy);
        }

        if (mobileNativeBy == null) {
            return returnMappedBy(defaultBy, defaultBy);
        }

        return returnMappedBy(defaultBy, mobileNativeBy);
    }

    @Override
    public By buildBy() {
        assertValidAnnotations();
        return buildMappedBy(buildDefaultBy(), buildMobileNativeBy());
    }

    private static class AnnotationComparator implements Comparator<Annotation> {

        private static Method getPriorityMethod(Class<? extends Annotation> clazz) {
            try {
                return clazz.getMethod(PRIORITY, ANNOTATION_ARGUMENTS);
            } catch (NoSuchMethodException e) {
                throw new ClassCastException(String.format("Class %s has no '%s' method", clazz.getName(), PRIORITY));
            }
        }

        private static int getPriorityValue(Method priorityMethod, Annotation annotation,
                                            Class<? extends Annotation> clazz) {
            try {
                return (int) priorityMethod.invoke(annotation, ANNOTATION_PARAMETERS);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalArgumentException(String
                        .format("It is impossible to get priority. Annotation class: %s", clazz.toString()), e);
            }
        }

        @Override
        public int compare(Annotation o1, Annotation o2) {
            Class<? extends Annotation> c1 = o1.annotationType();
            Class<? extends Annotation> c2 = o2.annotationType();

            Method priority1 = getPriorityMethod(c1);
            Method priority2 = getPriorityMethod(c2);

            int p1 = getPriorityValue(priority1, o1, c1);
            int p2 = getPriorityValue(priority2, o2, c2);

            return signum(p1 - p2);
        }
    }
}