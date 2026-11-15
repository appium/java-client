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

import io.appium.java_client.pagefactory.bys.ContentType;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static io.appium.java_client.pagefactory.WidgetConstructorUtil.findConvenientConstructor;
import static io.appium.java_client.remote.MobilePlatform.ANDROID;
import static io.appium.java_client.remote.MobilePlatform.IOS;
import static io.appium.java_client.remote.MobilePlatform.WINDOWS;
import static java.util.Locale.ROOT;

class OverrideWidgetReader {
    private static final Class<? extends Widget> EMPTY = Widget.class;
    private static final String HTML = "html";
    private static final String ANDROID_UI_AUTOMATOR = "androidUIAutomator";
    private static final String IOS_XCUIT_AUTOMATION = "iOSXCUITAutomation";
    private static final String WINDOWS_AUTOMATION = "windowsAutomation";

    private OverrideWidgetReader() {
    }

    private static Class<?> getConvenientClass(Class<?> declaredClass,
                                               AnnotatedElement annotatedElement, String method) {
        Class<?> convenientClass;
        OverrideWidget overrideWidget = annotatedElement.getAnnotation(OverrideWidget.class);

        try {
            if (overrideWidget == null || (convenientClass =
                    (Class<?>) OverrideWidget.class
                            .getDeclaredMethod(method).invoke(overrideWidget))
                    .equals(EMPTY)) {
                convenientClass = declaredClass;
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        if (!declaredClass.isAssignableFrom(convenientClass)) {
            throw new IllegalArgumentException(
                    new InstantiationException(declaredClass.getName()
                            + " is not assignable from "
                            + convenientClass.getName()));
        }

        return convenientClass;

    }

    static Class<?> getDefaultOrHTMLWidgetClass(Class<?> declaredClass, AnnotatedElement annotatedElement) {
        return getConvenientClass(declaredClass, annotatedElement, HTML);
    }

    static Class<?> getMobileNativeWidgetClass(Class<?> declaredClass,
                                               AnnotatedElement annotatedElement, String platform) {
        String transformedPlatform = String.valueOf(platform).toUpperCase(ROOT).trim();

        if (ANDROID.equalsIgnoreCase(transformedPlatform)) {
            return getConvenientClass(declaredClass, annotatedElement, ANDROID_UI_AUTOMATOR);
        }

        if (IOS.equalsIgnoreCase(transformedPlatform)) {
            return getConvenientClass(declaredClass, annotatedElement, IOS_XCUIT_AUTOMATION);
        }

        if (WINDOWS.equalsIgnoreCase(transformedPlatform)) {
            return getConvenientClass(declaredClass, annotatedElement, WINDOWS_AUTOMATION);
        }

        return getDefaultOrHTMLWidgetClass(declaredClass, annotatedElement);
    }

    /**
     * Interfaces have no widget constructor. A missing platform override stays unresolved
     * until that content type is used, matching a partial {@code @OverrideWidget}.
     */
    @SuppressWarnings("unchecked")
    private static Constructor<? extends Widget> findConstructor(Class<?> clazz) {
        if (clazz == null || clazz.isInterface() || !Widget.class.isAssignableFrom(clazz)) {
            return null;
        }
        return findConvenientConstructor((Class<? extends Widget>) clazz);
    }

    private static Constructor<? extends Widget> getConstructorOfADefaultOrHTMLWidget(
            Class<?> declaredClass, AnnotatedElement annotatedElement) {
        return findConstructor(getDefaultOrHTMLWidgetClass(declaredClass, annotatedElement));
    }

    private static Constructor<? extends Widget> getConstructorOfAMobileNativeWidgets(
            Class<?> declaredClass, AnnotatedElement annotatedElement, String platform) {
        return findConstructor(getMobileNativeWidgetClass(declaredClass, annotatedElement, platform));
    }

    protected static Map<ContentType, Constructor<? extends Widget>> read(
            Class<?> declaredClass, AnnotatedElement annotatedElement, String platform) {
        Map<ContentType, Constructor<? extends Widget>> result = new HashMap<>();
        result.put(ContentType.HTML_OR_DEFAULT,
                getConstructorOfADefaultOrHTMLWidget(declaredClass, annotatedElement));
        result.put(ContentType.NATIVE_MOBILE_SPECIFIC,
                getConstructorOfAMobileNativeWidgets(declaredClass, annotatedElement, platform));
        return result;
    }
}
