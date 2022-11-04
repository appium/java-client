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

import io.appium.java_client.AppiumBy;
import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

enum Strategies {
    BYUIAUTOMATOR("uiAutomator") {
        @Override By getBy(Annotation annotation) {
            String value = getValue(annotation, this);
            if (annotation.annotationType().equals(AndroidFindBy.class)
                    || annotation.annotationType().equals(AndroidBy.class)) {
                return AppiumBy.androidUIAutomator(value);
            }
            return super.getBy(annotation);
        }
    },
    /**
     * This has been deprecated due to misspelling.
     * @deprecated Use {@link Strategies#BYACCESSIBILITY} instead.
     */
    @Deprecated
    BYACCESSABILITY("accessibility") {
        @Override By getBy(Annotation annotation) {
            return AppiumBy.accessibilityId(getValue(annotation, this));
        }
    },
    BYACCESSIBILITY("accessibility") {
        @Override By getBy(Annotation annotation) {
            return AppiumBy.accessibilityId(getValue(annotation, this));
        }
    },
    BYCLASSNAME("className") {
        @Override By getBy(Annotation annotation) {
            return AppiumBy.className(getValue(annotation, this));
        }
    },
    BYID("id") {
        @Override By getBy(Annotation annotation) {
            return AppiumBy.id(getValue(annotation, this));
        }
    },
    BYTAG("tagName") {
        @Override By getBy(Annotation annotation) {
            return By.tagName(getValue(annotation, this));
        }
    },
    BYNAME("name") {
        @Override By getBy(Annotation annotation) {
            return AppiumBy.name(getValue(annotation, this));
        }
    },
    BYXPATH("xpath") {
        @Override By getBy(Annotation annotation) {
            return By
                .xpath(getValue(annotation, this));
        }
    },
    BYLINKTEXT("linkText") {
        @Override By getBy(Annotation annotation) {
            return By
                .linkText(getValue(annotation, this));
        }
    },
    BYPARTIALLINKTEXT("partialLinkText") {
        @Override By getBy(Annotation annotation) {
            return By
                .partialLinkText(getValue(annotation, this));
        }
    },
    /**
     * The Windows UIAutomation strategy.
     * @deprecated Not supported on the server side.
     */
    @Deprecated
    BYWINDOWSAUTOMATION("windowsAutomation") {
        @Override By getBy(Annotation annotation) {
            return MobileBy.windowsAutomation(getValue(annotation, this));
        }
    },
    BY_CLASS_CHAIN("iOSClassChain") {
        @Override By getBy(Annotation annotation) {
            return AppiumBy
                    .iOSClassChain(getValue(annotation, this));
        }
    },
    BY_DATA_MATCHER("androidDataMatcher") {
        @Override By getBy(Annotation annotation) {
            return AppiumBy
                    .androidDataMatcher(getValue(annotation, this));
        }
    },
    BY_VIEW_MATCHER("androidViewMatcher") {
        @Override By getBy(Annotation annotation) {
            return AppiumBy
                .androidViewMatcher(getValue(annotation, this));
        }
    },
    BY_NS_PREDICATE("iOSNsPredicate") {
        @Override By getBy(Annotation annotation) {
            return AppiumBy
                    .iOSNsPredicateString(getValue(annotation, this));
        }
    };

    private final String valueName;

    Strategies(String valueName) {
        this.valueName = valueName;
    }

    static List<String> strategiesNames() {
        return Stream.of(values()).map((s) -> s.valueName).collect(Collectors.toList());
    }

    private static String getValue(Annotation annotation, Strategies strategy) {
        try {
            Method m = annotation.getClass()
                .getMethod(strategy.valueName, AppiumByBuilder.DEFAULT_ANNOTATION_METHOD_ARGUMENTS);
            return m.invoke(annotation).toString();
        } catch (NoSuchMethodException
                | SecurityException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    String returnValueName() {
        return valueName;
    }

    By getBy(Annotation annotation) {
        return null;
    }
}
