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

import static io.appium.java_client.pagefactory.WidgetConstructorUtil.findConvenientConstructor;
import static io.appium.java_client.remote.MobilePlatform.ANDROID;
import static io.appium.java_client.remote.MobilePlatform.IOS;

import io.appium.java_client.pagefactory.bys.ContentType;
import io.appium.java_client.remote.AutomationName;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

class OverrideWidgetReader {
    private static final Class<? extends Widget> EMPTY = Widget.class;
    private static final String HTML = "html";
    private static final String ANDROID_UI_AUTOMATOR = "androidUIAutomator";
    private static final String IOS_UI_AUTOMATION = "iOSUIAutomation";
    private static final String SELENDROID = "selendroid";

    @SuppressWarnings("unchecked")
    private static Class<? extends Widget> getConvenientClass(Class<? extends Widget> declaredClass,
        AnnotatedElement annotatedElement, String method) {
        Class<? extends Widget> convenientClass;
        OverrideWidget overrideWidget = annotatedElement.getAnnotation(OverrideWidget.class);

        try {
            if (overrideWidget == null || (convenientClass =
                (Class<? extends Widget>) OverrideWidget.class
                    .getDeclaredMethod(method, new Class[] {}).invoke(overrideWidget))
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

    static Class<? extends Widget> getDefaultOrHTMLWidgetClass(
        Class<? extends Widget> declaredClass, AnnotatedElement annotatedElement) {
        return getConvenientClass(declaredClass, annotatedElement, HTML);
    }

    static Class<? extends Widget> getMobileNativeWidgetClass(Class<? extends Widget> declaredClass,
        AnnotatedElement annotatedElement, String platform, String automation) {
        String transformedPlatform = platform.toUpperCase().trim();
        String transformedAutomation = automation.toUpperCase().trim();

        if (ANDROID.toUpperCase().equals(transformedPlatform) && AutomationName.SELENDROID
            .toUpperCase().equals(transformedAutomation)) {
            return getConvenientClass(declaredClass, annotatedElement, SELENDROID);
        }

        if (ANDROID.toUpperCase().equals(transformedPlatform)) {
            return getConvenientClass(declaredClass, annotatedElement, ANDROID_UI_AUTOMATOR);
        }

        if (IOS.toUpperCase().equals(transformedPlatform)) {
            return getConvenientClass(declaredClass, annotatedElement, IOS_UI_AUTOMATION);
        }

        return getDefaultOrHTMLWidgetClass(declaredClass, annotatedElement);
    }

    private static Constructor<? extends Widget> getConstructorOfADefaultOrHTMLWidget(
        Class<? extends Widget> declaredClass, AnnotatedElement annotatedElement) {
        Class<? extends Widget> clazz =
            getDefaultOrHTMLWidgetClass(declaredClass, annotatedElement);
        return findConvenientConstructor(clazz);
    }

    private static Constructor<? extends Widget> getConstructorOfAMobileNativeWidgets(
        Class<? extends Widget> declaredClass, AnnotatedElement annotatedElement, String platform,
        String automation) {
        Class<? extends Widget> clazz =
            getMobileNativeWidgetClass(declaredClass, annotatedElement, platform, automation);
        return findConvenientConstructor(clazz);
    }

    static Map<ContentType, Constructor<? extends Widget>> read(
        Class<? extends Widget> declaredClass, AnnotatedElement annotatedElement, String platform,
        String automation) {
        Map<ContentType, Constructor<? extends Widget>> result = new HashMap<>();
        result.put(ContentType.HTML_OR_DEFAULT,
            getConstructorOfADefaultOrHTMLWidget(declaredClass, annotatedElement));
        result.put(ContentType.NATIVE_MOBILE_SPECIFIC,
            getConstructorOfAMobileNativeWidgets(declaredClass, annotatedElement, platform,
                automation));
        return result;
    }
}
