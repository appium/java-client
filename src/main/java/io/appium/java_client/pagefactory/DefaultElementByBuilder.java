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

class DefaultElementByBuilder extends AppiumByBuilder {

    protected DefaultElementByBuilder(String platform, String automation) {
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
        if (isSelendroidAutomation()) {
            SelendroidFindBy selendroidFindBy =
                annotatedElement.getAnnotation(SelendroidFindBy.class);
            SelendroidFindBys selendroidFindBys =
                annotatedElement.getAnnotation(SelendroidFindBys.class);
            SelendroidFindAll selendroidFindByAll =
                annotatedElement.getAnnotation(SelendroidFindAll.class);

            if (selendroidFindBy != null) {
                return createBy(new Annotation[] {selendroidFindBy}, HowToUseSelectors.USE_ONE);
            }

            if (selendroidFindBys != null) {
                return createBy(selendroidFindBys.value(), HowToUseSelectors.BUILD_CHAINED);
            }

            if (selendroidFindByAll != null) {
                return createBy(selendroidFindByAll.value(), HowToUseSelectors.USE_ANY);
            }
        }

        if (isAndroid()) {
            AndroidFindBy androidFindBy = annotatedElement.getAnnotation(AndroidFindBy.class);
            AndroidFindBys androidFindBys = annotatedElement.getAnnotation(AndroidFindBys.class);
            AndroidFindAll androidFindAll = annotatedElement.getAnnotation(AndroidFindAll.class);

            if (androidFindBy != null) {
                return createBy(new Annotation[] {androidFindBy}, HowToUseSelectors.USE_ONE);
            }

            if (androidFindBys != null) {
                return createBy(androidFindBys.value(), HowToUseSelectors.BUILD_CHAINED);
            }

            if (androidFindAll != null) {
                return createBy(androidFindAll.value(), HowToUseSelectors.USE_ANY);
            }
        }

        if (isIOS()) {
            iOSFindBy iOSFindBy = annotatedElement.getAnnotation(iOSFindBy.class);
            iOSFindBys iOSFindBys = annotatedElement.getAnnotation(iOSFindBys.class);
            iOSFindAll iOSFindAll = annotatedElement.getAnnotation(iOSFindAll.class);

            if (iOSFindBy != null) {
                return createBy(new Annotation[] {iOSFindBy}, HowToUseSelectors.USE_ONE);
            }

            if (iOSFindBys != null) {
                return createBy(iOSFindBys.value(), HowToUseSelectors.BUILD_CHAINED);
            }

            if (iOSFindAll != null) {
                return createBy(iOSFindAll.value(), HowToUseSelectors.USE_ANY);
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
