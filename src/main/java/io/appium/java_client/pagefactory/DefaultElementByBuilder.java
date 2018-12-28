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

import static java.lang.Integer.signum;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;

import io.appium.java_client.pagefactory.bys.ContentMappedBy;
import io.appium.java_client.pagefactory.bys.ContentType;
import io.appium.java_client.pagefactory.bys.builder.AppiumByBuilder;
import io.appium.java_client.pagefactory.bys.builder.ByAll;
import io.appium.java_client.pagefactory.bys.builder.ByChained;
import io.appium.java_client.pagefactory.bys.builder.HowToUseSelectors;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

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

public class DefaultElementByBuilder extends AppiumByBuilder {

    private static final String PRIORITY = "priority";
    private static final String VALUE = "value";
    private static final Class[] ANNOTATION_ARGUMENTS = new Class[]{};
    private static final Object[] ANNOTATION_PARAMETERS = new Object[]{};

    public DefaultElementByBuilder(String platform, String automation) {
        super(platform, automation);
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
        LocatorGroupStrategy strategy = ofNullable(locatorGroupStrategy)
                .orElse(LocatorGroupStrategy.CHAIN);
        if (strategy.equals(LocatorGroupStrategy.ALL_POSSIBLE)) {
            return new ByAll(bys);
        }
        return new ByChained(bys);
    }

    @Override
    protected void assertValidAnnotations() {
        AnnotatedElement annotatedElement = annotatedElementContainer.getAnnotated();
        FindBy findBy = annotatedElement.getAnnotation(FindBy.class);
        FindBys findBys = annotatedElement.getAnnotation(FindBys.class);
        checkDisallowedAnnotationPairs(findBy, findBys);
        FindAll findAll = annotatedElement.getAnnotation(FindAll.class);
        checkDisallowedAnnotationPairs(findBy, findAll);
        checkDisallowedAnnotationPairs(findBys, findAll);
    }

    @Override
    protected By buildDefaultBy() {
        AnnotatedElement annotatedElement = annotatedElementContainer.getAnnotated();
        By defaultBy = null;
        FindBy findBy = annotatedElement.getAnnotation(FindBy.class);
        if (findBy != null) {
            defaultBy = new FindBy.FindByBuilder().buildIt(findBy, (Field) annotatedElement);
        }

        if (defaultBy == null) {
            FindBys findBys = annotatedElement.getAnnotation(FindBys.class);
            if (findBys != null) {
                defaultBy = new FindBys.FindByBuilder().buildIt(findBys, (Field) annotatedElement);
            }
        }

        if (defaultBy == null) {
            FindAll findAll = annotatedElement.getAnnotation(FindAll.class);
            if (findAll != null) {
                defaultBy = new FindAll.FindByBuilder().buildIt(findAll, (Field) annotatedElement);
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

        return result.toArray(new By[result.size()]);
    }

    @Override
    protected By buildMobileNativeBy() {
        AnnotatedElement annotatedElement = annotatedElementContainer.getAnnotated();
        HowToUseLocators howToUseLocators = annotatedElement.getAnnotation(HowToUseLocators.class);

        Optional<HowToUseLocators> howToUseLocatorsOptional = ofNullable(howToUseLocators);

        if (isAndroid()) {
            return buildMobileBy(howToUseLocatorsOptional.map(HowToUseLocators::androidAutomation).orElse(null),
                    getBys(AndroidFindBy.class, AndroidFindBys.class, AndroidFindAll.class));
        }

        if (isIOSXcuit() || isIOS()) {
            return buildMobileBy(howToUseLocatorsOptional.map(HowToUseLocators::iOSXCUITAutomation).orElse(null),
                    getBys(iOSXCUITFindBy.class, iOSXCUITFindBys.class, iOSXCUITFindAll.class));
        }

        if (isWindows()) {
            return buildMobileBy(howToUseLocatorsOptional.map(HowToUseLocators::windowsAutomation).orElse(null),
                    getBys(WindowsFindBy.class, WindowsFindBys.class, WindowsFindAll.class));
        }

        return null;
    }

    @Override
    public boolean isLookupCached() {
        AnnotatedElement annotatedElement = annotatedElementContainer.getAnnotated();
        return (annotatedElement.getAnnotation(CacheLookup.class) != null);
    }

    private By returnMappedBy(By byDefault, By nativeAppBy) {
        Map<ContentType, By> contentMap = new HashMap<>();
        contentMap.put(ContentType.HTML_OR_DEFAULT, byDefault);
        contentMap.put(ContentType.NATIVE_MOBILE_SPECIFIC, nativeAppBy);
        return new ContentMappedBy(contentMap);
    }

    @Override
    public By buildBy() {
        assertValidAnnotations();

        By defaultBy = buildDefaultBy();
        By mobileNativeBy = buildMobileNativeBy();

        String idOrName = ((Field) annotatedElementContainer.getAnnotated()).getName();

        if (defaultBy == null && mobileNativeBy == null) {
            defaultBy =
                    new ByIdOrName(((Field) annotatedElementContainer.getAnnotated()).getName());
            mobileNativeBy = new By.ById(idOrName);
            return returnMappedBy(defaultBy, mobileNativeBy);
        }

        if (defaultBy == null) {
            defaultBy =
                    new ByIdOrName(((Field) annotatedElementContainer.getAnnotated()).getName());
            return returnMappedBy(defaultBy, mobileNativeBy);
        }

        if (mobileNativeBy == null) {
            mobileNativeBy = defaultBy;
            return returnMappedBy(defaultBy, mobileNativeBy);
        }

        return returnMappedBy(defaultBy, mobileNativeBy);
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