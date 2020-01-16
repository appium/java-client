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

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSBy;
import org.openqa.selenium.By;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

enum Strategies {
    BYUIAUTOMATOR("uiAutomator") {
        @Override By getBy(Annotation annotation) {
            String value = getValue(annotation, this);
            if (annotation.annotationType().equals(AndroidFindBy.class)
                    || annotation.annotationType().equals(AndroidBy.class)) {
                return MobileBy.AndroidUIAutomator(value);
            }
            return super.getBy(annotation);
        }
    },
    BYACCESSABILITY("accessibility") {
        @Override By getBy(Annotation annotation) {
            return MobileBy.AccessibilityId(getValue(annotation, this));
        }
    },
    BYCLASSNAME("className") {
        @Override By getBy(Annotation annotation) {
            return By.className(getValue(annotation, this));
        }
    },
    BYID("id") {
        @Override By getBy(Annotation annotation) {
            return By.id(getValue(annotation, this));
        }
    },
    BYTAG("tagName") {
        @Override By getBy(Annotation annotation) {
            return By
                .tagName(getValue(annotation, this));
        }
    },
    BYNAME("name") {
        @Override By getBy(Annotation annotation) {
            return By
                .name(getValue(annotation, this));
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
    BYWINDOWSAUTOMATION("windowsAutomation") {
        @Override By getBy(Annotation annotation) {
            return MobileBy
                    .windowsAutomation(getValue(annotation, this));
        }
    },
    BY_CLASS_CHAIN("iOSClassChain") {
        @Override By getBy(Annotation annotation) {
            return MobileBy
                    .iOSClassChain(getValue(annotation, this));
        }
    },
    BY_DATA_MATCHER("androidDataMatcher") {
        @Override By getBy(Annotation annotation) {
            return MobileBy
                    .androidDataMatcher(getValue(annotation, this));
        }
    },
    BY_VIEW_MATCHER("androidViewMatcher") {
        @Override By getBy(Annotation annotation) {
            return MobileBy
                .androidViewMatcher(getValue(annotation, this));
        }
    },
    BY_NS_PREDICATE("iOSNsPredicate") {
        @Override By getBy(Annotation annotation) {
            return MobileBy
                    .iOSNsPredicateString(getValue(annotation, this));
        }
    };

    private final String valueName;

    Strategies(String valueName) {
        this.valueName = valueName;
    }

    static List<String> strategiesNames() {
        Strategies[] strategies = values();
        List<String> result = new ArrayList<>();
        for (Strategies strategy : strategies) {
            result.add(strategy.valueName);
        }
        return result;
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
