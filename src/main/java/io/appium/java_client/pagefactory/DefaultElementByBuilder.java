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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultElementByBuilder extends AppiumByBuilder {

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

    private static By buildMobileBy(LocatorGroupStrategy locatorGroupStrategy, Annotation[] annotations) {
        if (annotations.length == 1) {
            return createBy(new Annotation[] {annotations[0]}, HowToUseSelectors.USE_ONE);
        } else {
            LocatorGroupStrategy strategy = Optional.ofNullable(locatorGroupStrategy)
                    .orElse(LocatorGroupStrategy.CHAIN);
            if (strategy.equals(LocatorGroupStrategy.ALL_POSSIBLE)) {
                return createBy(annotations, HowToUseSelectors.USE_ANY);
            }
            return createBy(annotations, HowToUseSelectors.BUILD_CHAINED);
        }
    }

    @Override protected void assertValidAnnotations() {
        AnnotatedElement annotatedElement = annotatedElementContainer.getAnnotated();
        AndroidFindBy androidBy = annotatedElement.getAnnotation(AndroidFindBy.class);
        AndroidFindBys androidBys = annotatedElement.getAnnotation(AndroidFindBys.class);
        checkDisallowedAnnotationPairs(androidBy, androidBys);
        AndroidFindAll androidFindAll = annotatedElement.getAnnotation(AndroidFindAll.class);
        checkDisallowedAnnotationPairs(androidBy, androidFindAll);
        checkDisallowedAnnotationPairs(androidBys, androidFindAll);

        SelendroidFindBy selendroidBy = annotatedElement.getAnnotation(SelendroidFindBy.class);
        SelendroidFindBys selendroidBys = annotatedElement.getAnnotation(SelendroidFindBys.class);
        checkDisallowedAnnotationPairs(selendroidBy, selendroidBys);
        SelendroidFindAll selendroidFindAll =
            annotatedElement.getAnnotation(SelendroidFindAll.class);
        checkDisallowedAnnotationPairs(selendroidBy, selendroidFindAll);
        checkDisallowedAnnotationPairs(selendroidBys, selendroidFindAll);

        iOSFindBy iOSBy = annotatedElement.getAnnotation(iOSFindBy.class);
        iOSFindBys iOSBys = annotatedElement.getAnnotation(iOSFindBys.class);
        checkDisallowedAnnotationPairs(iOSBy, iOSBys);
        iOSFindAll iOSFindAll = annotatedElement.getAnnotation(iOSFindAll.class);
        checkDisallowedAnnotationPairs(iOSBy, iOSFindAll);
        checkDisallowedAnnotationPairs(iOSBys, iOSFindAll);

        FindBy findBy = annotatedElement.getAnnotation(FindBy.class);
        FindBys findBys = annotatedElement.getAnnotation(FindBys.class);
        checkDisallowedAnnotationPairs(findBy, findBys);
        FindAll findAll = annotatedElement.getAnnotation(FindAll.class);
        checkDisallowedAnnotationPairs(findBy, findAll);
        checkDisallowedAnnotationPairs(findBys, findAll);
    }

    @Override protected By buildDefaultBy() {
        AnnotatedElement annotatedElement = annotatedElementContainer.getAnnotated();
        By defaultBy = null;
        FindBy findBy = annotatedElement.getAnnotation(FindBy.class);
        if (findBy != null) {
            defaultBy = super.buildByFromFindBy(findBy);
        }

        if (defaultBy == null) {
            FindBys findBys = annotatedElement.getAnnotation(FindBys.class);
            if (findBys != null) {
                defaultBy = super.buildByFromFindBys(findBys);
            }
        }

        if (defaultBy == null) {
            FindAll findAll = annotatedElement.getAnnotation(FindAll.class);
            if (findAll != null) {
                defaultBy = super.buildBysFromFindByOneOf(findAll);
            }
        }
        return defaultBy;
    }

    @Override protected By buildMobileNativeBy() {
        AnnotatedElement annotatedElement = annotatedElementContainer.getAnnotated();
        HowToUseLocators howToUseLocators = annotatedElement.getAnnotation(HowToUseLocators.class);

        if (isSelendroidAutomation()) {
            SelendroidFindBy[] selendroidFindByArray =
                annotatedElement.getAnnotationsByType(SelendroidFindBy.class);
            //should be kept for some time
            SelendroidFindBys selendroidFindBys =
                annotatedElement.getAnnotation(SelendroidFindBys.class);
            SelendroidFindAll selendroidFindByAll =
                annotatedElement.getAnnotation(SelendroidFindAll.class);

            if (selendroidFindByArray != null && selendroidFindByArray.length == 1) {
                return createBy(new Annotation[] {selendroidFindByArray[0]}, HowToUseSelectors.USE_ONE);
            }

            if (selendroidFindBys != null) {
                return createBy(selendroidFindBys.value(), HowToUseSelectors.BUILD_CHAINED);
            }

            if (selendroidFindByAll != null) {
                return createBy(selendroidFindByAll.value(), HowToUseSelectors.USE_ANY);
            }
            ///////////////////////////////////////
            //code that supposed to be supported
            if (selendroidFindByArray != null && selendroidFindByArray.length > 0) {
                return buildMobileBy(howToUseLocators != null ? howToUseLocators.selendroidAutomation() : null,
                        selendroidFindByArray);
            }
        }

        if (isAndroid()) {
            AndroidFindBy[] androidFindByArray = annotatedElement.getAnnotationsByType(AndroidFindBy.class);
            //should be kept for some time
            AndroidFindBys androidFindBys = annotatedElement.getAnnotation(AndroidFindBys.class);
            AndroidFindAll androidFindAll = annotatedElement.getAnnotation(AndroidFindAll.class);

            if (androidFindByArray != null && androidFindByArray.length == 1) {
                return createBy(new Annotation[] {androidFindByArray[0]}, HowToUseSelectors.USE_ONE);
            }

            if (androidFindBys != null) {
                return createBy(androidFindBys.value(), HowToUseSelectors.BUILD_CHAINED);
            }

            if (androidFindAll != null) {
                return createBy(androidFindAll.value(), HowToUseSelectors.USE_ANY);
            }
            ///////////////////////////////////////
            //code that supposed to be supported
            if (androidFindByArray != null && androidFindByArray.length > 0) {
                return buildMobileBy(howToUseLocators != null ? howToUseLocators.androidAutomation() : null,
                        androidFindByArray);
            }
        }

        if (isIOSXcuit()) {
            iOSXCUITFindBy[] xCuitFindByArray = annotatedElement.getAnnotationsByType(iOSXCUITFindBy.class);
            if (xCuitFindByArray != null && xCuitFindByArray.length > 0) {
                return buildMobileBy(howToUseLocators != null ? howToUseLocators.iOSXCUITAutomation() : null,
                        xCuitFindByArray);
            }
        }

        if (isIOS()) {
            iOSFindBy[] iOSFindByArray = annotatedElement.getAnnotationsByType(iOSFindBy.class);
            //should be kept for some time
            iOSFindBys iOSFindBys = annotatedElement.getAnnotation(iOSFindBys.class);
            iOSFindAll iOSFindAll = annotatedElement.getAnnotation(iOSFindAll.class);

            if (iOSFindByArray != null && iOSFindByArray.length == 1) {
                return createBy(new Annotation[] {iOSFindByArray[0]}, HowToUseSelectors.USE_ONE);
            }

            if (iOSFindBys != null) {
                return createBy(iOSFindBys.value(), HowToUseSelectors.BUILD_CHAINED);
            }

            if (iOSFindAll != null) {
                return createBy(iOSFindAll.value(), HowToUseSelectors.USE_ANY);
            }
            ///////////////////////////////////////
            //code that supposed to be supported
            if (iOSFindByArray != null && iOSFindByArray.length > 0) {
                return buildMobileBy(howToUseLocators != null ? howToUseLocators.iOSAutomation() : null,
                        iOSFindByArray);
            }
        }

        if (isWindows()) {
            WindowsFindBy[] windowsFindByArray = annotatedElement.getAnnotationsByType(WindowsFindBy.class);
            if (windowsFindByArray != null && windowsFindByArray.length > 0) {
                return buildMobileBy(howToUseLocators != null ? howToUseLocators.windowsAutomation() : null,
                        windowsFindByArray);
            }
        }

        return null;
    }

    @Override public boolean isLookupCached() {
        AnnotatedElement annotatedElement = annotatedElementContainer.getAnnotated();
        return (annotatedElement.getAnnotation(CacheLookup.class) != null);
    }

    private By returnMappedBy(By byDefault, By nativeAppBy) {
        Map<ContentType, By> contentMap = new HashMap<>();
        contentMap.put(ContentType.HTML_OR_DEFAULT, byDefault);
        contentMap.put(ContentType.NATIVE_MOBILE_SPECIFIC, nativeAppBy);
        return new ContentMappedBy(contentMap);
    }

    @Override public By buildBy() {
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
}