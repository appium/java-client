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
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static io.appium.java_client.pagefactory.WidgetConstructorUtil.findConvenientConstructor;
import static io.appium.java_client.remote.MobilePlatform.ANDROID;
import static io.appium.java_client.remote.MobilePlatform.IOS;
import static io.appium.java_client.remote.MobilePlatform.WINDOWS;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class OverrideWidgetReader {
    private static final Class<? extends Widget> EMPTY = Widget.class;
    private static final String HTML = "html";
    private static final String ANDROID_UI_AUTOMATOR = "androidUIAutomator";
    private static final String IOS_XCUIT_AUTOMATION = "iOSXCUITAutomation";
    private static final String WINDOWS_AUTOMATION = "windowsAutomation";

    @SuppressWarnings("unchecked")
    private static Class<? extends Widget> getConvenientClass(Class<? extends Widget> declaredClass,
        AnnotatedElement annotatedElement, String method) {
        Class<? extends Widget> convenientClass;
        OverrideWidget overrideWidget = annotatedElement.getAnnotation(OverrideWidget.class);

        try {
            if (overrideWidget == null || (convenientClass =
                (Class<? extends Widget>) OverrideWidget.class
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

    static Class<? extends Widget> getDefaultOrHTMLWidgetClass(
        Class<? extends Widget> declaredClass, AnnotatedElement annotatedElement) {
        return getConvenientClass(declaredClass, annotatedElement, HTML);
    }

    static Class<? extends Widget> getMobileNativeWidgetClass(Class<? extends Widget> declaredClass,
        AnnotatedElement annotatedElement, String platform) {
        String transformedPlatform = String.valueOf(platform).toUpperCase().trim();

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

    private static Constructor<? extends Widget> getConstructorOfADefaultOrHTMLWidget(
        Class<? extends Widget> declaredClass, AnnotatedElement annotatedElement) {
        Class<? extends Widget> clazz =
            getDefaultOrHTMLWidgetClass(declaredClass, annotatedElement);
        return findConvenientConstructor(clazz);
    }

    private static Constructor<? extends Widget> getConstructorOfAMobileNativeWidgets(
        Class<? extends Widget> declaredClass, AnnotatedElement annotatedElement, String platform) {
        Class<? extends Widget> clazz =
            getMobileNativeWidgetClass(declaredClass, annotatedElement, platform);
        return findConvenientConstructor(clazz);
    }

    protected static Map<ContentType, Constructor<? extends Widget>> read(
        Class<? extends Widget> declaredClass, AnnotatedElement annotatedElement, String platform) {
        Map<ContentType, Constructor<? extends Widget>> result = new HashMap<>();
        result.put(ContentType.HTML_OR_DEFAULT,
            getConstructorOfADefaultOrHTMLWidget(declaredClass, annotatedElement));
        result.put(ContentType.NATIVE_MOBILE_SPECIFIC,
            getConstructorOfAMobileNativeWidgets(declaredClass, annotatedElement, platform));
        return result;
    }
}
