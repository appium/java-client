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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation should mark an abstract/certain Widget object class
 * or field that declares some abstract/certain Widget object class.
 * io.appium.java_client.pagefactory.Widget is the Appium-specific extension of the
 * Page Object design pattern.
 * About the Page Object design pattern please read these documents:
 * - https://code.google.com/p/selenium/wiki/PageObjects
 * - https://code.google.com/p/selenium/wiki/PageFactory
 */
@Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD}) public @interface OverrideWidget {
    /**
     * This property is designed for HTML (browser or web view) content.
     * A declared class should not be abstract. Declared class also should be a subclass
     * of an annotated class/class which is declared by an annotated field.
     *
     * @return a class which extends {@link io.appium.java_client.pagefactory.Widget}
     */
    Class<? extends Widget> html() default Widget.class;

    /**
     * This property is designed for Android native content.
     * A declared class should not be abstract. Declared class also should be a subclass
     * of an annotated class/class which is declared by an annotated field.
     *
     * @return a class which extends {@link io.appium.java_client.pagefactory.Widget}
     */
    Class<? extends Widget> androidUIAutomator() default Widget.class;

    /**
     * This property is designed for iOS native content.
     * A declared class should not be abstract. Declared class also should be a subclass
     * of an annotated class/class which is declared by an annotated field.
     *
     * @return a class which extends {@link io.appium.java_client.pagefactory.Widget}
     */
    Class<? extends Widget> iOSUIAutomation() default Widget.class;

    /**
     * This property is designed for Android native content when Selendroid automation is used.
     * A declared class should not be abstract. Declared class also should be a subclass
     * of an annotated class/class which is declared by an annotated field.
     *
     * @return a class which extends {@link io.appium.java_client.pagefactory.Widget}
     */
    Class<? extends Widget> selendroid() default Widget.class;
}
