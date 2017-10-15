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

/**
 * Used to build a complex android automator locator.
 */
public @interface AndroidBy {
    /**
     * It is an is Android UIAutomator string.
     * Read http://developer.android.com/intl/ru/tools/testing-support-library/
     * index.html#uia-apis
     */
    String uiAutomator() default "";

    /**
     * It an UI automation accessibility Id which is a convenient to Android.
     * About Android accessibility
     * https://developer.android.com/intl/ru/training/accessibility/accessible-app.html
     */
    String accessibility() default "";

    /**
     * It is an id of the target element.
     */
    String id() default "";

    /**
     * It is a className of the target element.
     */
    String className() default "";

    /**
     * It is a desired element tag.
     */
    String tagName() default "";

    /**
     * It is a xpath to the target element.
     */
    String xpath() default "";

    /**
     * @return priority of the searching. Higher number means lower priority.
     */
    int priority() default 0;
}
