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

package io.appium.java_client;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public abstract class MobileBy extends By {

    private static final String ERROR_TEXT = "The class %s of the given context "
        + "doesn't implement %s nor %s. Sorry. It is impossible to find something.";

    private final String locatorString;
    private final MobileSelector selector;

    private static IllegalArgumentException formIllegalArgumentException(Class<?> givenClass,
        Class<?> class1, Class<?> class2) {
        return new IllegalArgumentException(String.format(ERROR_TEXT, givenClass.getCanonicalName(),
            class1.getCanonicalName(), class2.getCanonicalName()));
    }

    protected MobileBy(MobileSelector selector, String locatorString) {
        if (StringUtils.isBlank(locatorString)) {
            throw new IllegalArgumentException("Must supply a not empty locator value.");
        }
        this.locatorString = locatorString;
        this.selector = selector;
    }

    protected String getLocatorString() {
        return locatorString;
    }

    @SuppressWarnings("unchecked")
    @Override public List<WebElement> findElements(SearchContext context) {
        return (List<WebElement>) ((FindsByFluentSelector<?>) context)
            .findElements(selector.toString(), getLocatorString());
    }

    @Override public WebElement findElement(SearchContext context) {
        return ((FindsByFluentSelector<?>) context)
            .findElement(selector.toString(), getLocatorString());
    }

    /**
     * Read https://developer.apple.com/library/tvos/documentation/DeveloperTools/
     * Conceptual/InstrumentsUserGuide/UIAutomation.html
     *
     * @param iOSAutomationText is iOS UIAutomation string
     * @return an instance of {@link io.appium.java_client.MobileBy.ByIosUIAutomation}
     */
    public static By IosUIAutomation(final String iOSAutomationText) {
        return new ByIosUIAutomation(iOSAutomationText);
    }

    /**
     * Read http://developer.android.com/intl/ru/tools/testing-support-library/
     * index.html#uia-apis
     * @param uiautomatorText is Android UIAutomator string
     * @return an instance of {@link io.appium.java_client.MobileBy.ByAndroidUIAutomator}
     */
    public static By AndroidUIAutomator(final String uiautomatorText) {
        return new ByAndroidUIAutomator(uiautomatorText);
    }

    /**
     * About Android accessibility
     * https://developer.android.com/intl/ru/training/accessibility/accessible-app.html
     * About iOS accessibility
     * https://developer.apple.com/library/ios/documentation/UIKit/Reference/
     * UIAccessibilityIdentification_Protocol/index.html
     * @param accessibilityId id is a convenient UI automation accessibility Id.
     * @return an instance of {@link io.appium.java_client.MobileBy.ByAndroidUIAutomator}
     */
    public static By AccessibilityId(final String accessibilityId) {
        return new ByAccessibilityId(accessibilityId);
    }

    /**
    * This locator strategy is available in XCUITest Driver mode
    * @param iOSNsPredicateString is an an iOS NsPredicate String
    * @return an instance of {@link io.appium.java_client.MobileBy.ByIosNsPredicate}
    */
    public static By iOSNsPredicateString(final String iOSNsPredicateString) {
        return new ByIosNsPredicate(iOSNsPredicateString);
    }

    public static By windowsAutomation(final String windowsAutomation) {
        return new ByWindowsAutomation(windowsAutomation);
    }
    
    public static class ByIosUIAutomation extends MobileBy implements Serializable {

        public ByIosUIAutomation(String iOSAutomationText) {
            super(MobileSelector.IOS_UI_AUTOMATION, iOSAutomationText);
        }

        /**
         * @throws WebDriverException when current session doesn't support the given selector or when
         *      value of the selector is not consistent.
         * @throws IllegalArgumentException when it is impossible to find something on the given
         * {@link SearchContext} instance
         */
        @SuppressWarnings("unchecked")
        @Override
        public List<WebElement> findElements(SearchContext context) throws WebDriverException,
            IllegalArgumentException {
            Class<?> contextClass = context.getClass();

            if (FindsByIosUIAutomation.class.isAssignableFrom(contextClass)) {
                return FindsByIosUIAutomation.class.cast(context)
                    .findElementsByIosUIAutomation(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(context.getClass())) {
                return super.findElements(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByIosUIAutomation.class,
                FindsByFluentSelector.class);
        }

        /**
         * @throws WebDriverException when current session doesn't support the given selector or when
         *      value of the selector is not consistent.
         * @throws IllegalArgumentException when it is impossible to find something on the given
         * {@link SearchContext} instance
         */
        @Override public WebElement findElement(SearchContext context) throws WebDriverException,
            IllegalArgumentException {
            Class<?> contextClass = context.getClass();

            if (FindsByIosUIAutomation.class.isAssignableFrom(contextClass)) {
                return ((FindsByIosUIAutomation<?>) context)
                    .findElementByIosUIAutomation(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(context.getClass())) {
                return super.findElement(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByIosUIAutomation.class,
                FindsByFluentSelector.class);
        }

        @Override public String toString() {
            return "By.IosUIAutomation: " + getLocatorString();
        }
    }


    public static class ByAndroidUIAutomator extends MobileBy implements Serializable {


        public ByAndroidUIAutomator(String uiautomatorText) {
            super(MobileSelector.ANDROID_UI_AUTOMATOR, uiautomatorText);
        }

        /**
         * @throws WebDriverException when current session doesn't support the given selector or when
         *      value of the selector is not consistent.
         * @throws IllegalArgumentException when it is impossible to find something on the given
         * {@link SearchContext} instance
         */
        @SuppressWarnings("unchecked")
        @Override
        public List<WebElement> findElements(SearchContext context) throws WebDriverException,
            IllegalArgumentException {
            Class<?> contextClass = context.getClass();

            if (FindsByAndroidUIAutomator.class.isAssignableFrom(contextClass)) {
                return FindsByAndroidUIAutomator.class.cast(context)
                    .findElementsByAndroidUIAutomator(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(context.getClass())) {
                return super.findElements(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByAndroidUIAutomator.class,
                FindsByFluentSelector.class);
        }

        /**
         * @throws WebDriverException when current session doesn't support the given selector or when
         *      value of the selector is not consistent.
         * @throws IllegalArgumentException when it is impossible to find something on the given
         * {@link SearchContext} instance
         */
        @Override public WebElement findElement(SearchContext context) throws WebDriverException,
            IllegalArgumentException {
            Class<?> contextClass = context.getClass();

            if (FindsByAndroidUIAutomator.class.isAssignableFrom(contextClass)) {
                return FindsByAndroidUIAutomator.class.cast(context)
                    .findElementByAndroidUIAutomator(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(context.getClass())) {
                return super.findElement(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByAndroidUIAutomator.class,
                FindsByFluentSelector.class);
        }

        @Override public String toString() {
            return "By.AndroidUIAutomator: " + getLocatorString();
        }
    }


    public static class ByAccessibilityId extends MobileBy implements Serializable {

        public ByAccessibilityId(String accessibilityId) {
            super(MobileSelector.ACCESSIBILITY, accessibilityId);
        }

        /**
         * @throws WebDriverException when current session doesn't support the given selector or when
         *      value of the selector is not consistent.
         * @throws IllegalArgumentException when it is impossible to find something on the given
         * {@link SearchContext} instance
         */
        @SuppressWarnings("unchecked")
        @Override
        public List<WebElement> findElements(SearchContext context) throws WebDriverException,
            IllegalArgumentException {
            Class<?> contextClass = context.getClass();

            if (FindsByAccessibilityId.class.isAssignableFrom(contextClass)) {
                return FindsByAccessibilityId.class.cast(context)
                    .findElementsByAccessibilityId(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(context.getClass())) {
                return super.findElements(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByAccessibilityId.class,
                FindsByFluentSelector.class);
        }

        /**
         * @throws WebDriverException when current session doesn't support the given selector or when
         *      value of the selector is not consistent.
         * @throws IllegalArgumentException when it is impossible to find something on the given
         * {@link SearchContext} instance
         */
        @Override public WebElement findElement(SearchContext context) throws WebDriverException,
            IllegalArgumentException {
            Class<?> contextClass = context.getClass();

            if (FindsByAccessibilityId.class.isAssignableFrom(contextClass)) {
                return FindsByAccessibilityId.class.cast(context)
                    .findElementByAccessibilityId(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(context.getClass())) {
                return super.findElement(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByAccessibilityId.class,
                FindsByFluentSelector.class);
        }

        @Override public String toString() {
            return "By.AccessibilityId: " + getLocatorString();
        }
    }

    public static class ByIosNsPredicate extends MobileBy implements Serializable {

        protected ByIosNsPredicate(String locatorString) {
            super(MobileSelector.IOS_PREDICATE_STRING, locatorString);
        }

        /**
         * @throws WebDriverException when current session doesn't support the given selector or when
         *      value of the selector is not consistent.
         * @throws IllegalArgumentException when it is impossible to find something on the given
         * {@link SearchContext} instance
         */
        @SuppressWarnings("unchecked")
        @Override public List<WebElement> findElements(SearchContext context) {
            Class<?> contextClass = context.getClass();

            if (FindsByIosNSPredicate.class.isAssignableFrom(contextClass)) {
                return FindsByIosNSPredicate.class.cast(context)
                        .findElementsByIosNsPredicate(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(context.getClass())) {
                return super.findElements(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByIosNSPredicate.class,
                    FindsByFluentSelector.class);
        }

        /**
         * @throws WebDriverException when current session doesn't support the given selector or when
         *      value of the selector is not consistent.
         * @throws IllegalArgumentException when it is impossible to find something on the given
         * {@link SearchContext} instance
         */
        @Override public WebElement findElement(SearchContext context) {
            Class<?> contextClass = context.getClass();

            if (FindsByIosNSPredicate.class.isAssignableFrom(contextClass)) {
                return FindsByIosNSPredicate.class.cast(context)
                        .findElementByIosNsPredicate(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(context.getClass())) {
                return super.findElement(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByIosNSPredicate.class,
                    FindsByFluentSelector.class);
        }

        @Override public String toString() {
            return "By.IosNsPredicate: " + getLocatorString();
        }
    }

    public static class ByWindowsAutomation extends MobileBy implements Serializable {

        protected ByWindowsAutomation(String locatorString) {
            super(MobileSelector.WINDOWS_UI_AUTOMATION, locatorString);
        }

        /**
         * @throws WebDriverException when current session doesn't support the given selector or when
         *      value of the selector is not consistent.
         * @throws IllegalArgumentException when it is impossible to find something on the given
         * {@link SearchContext} instance
         */
        @SuppressWarnings("unchecked")
        @Override public List<WebElement> findElements(SearchContext context) {
            Class<?> contextClass = context.getClass();

            if (FindsByWindowsAutomation.class.isAssignableFrom(contextClass)) {
                return FindsByWindowsAutomation.class.cast(context)
                    .findElementsByWindowsUIAutomation(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(context.getClass())) {
                return super.findElements(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByWindowsAutomation.class,
                FindsByFluentSelector.class);
        }

        /**
         * @throws WebDriverException when current session doesn't support the given selector or when
         *      value of the selector is not consistent.
         * @throws IllegalArgumentException when it is impossible to find something on the given
         * {@link SearchContext} instance
         */
        @Override public WebElement findElement(SearchContext context) {
            Class<?> contextClass = context.getClass();

            if (FindsByWindowsAutomation.class.isAssignableFrom(contextClass)) {
                return FindsByWindowsAutomation.class.cast(context)
                    .findElementByWindowsUIAutomation(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(context.getClass())) {
                return super.findElement(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByIosNSPredicate.class,
                FindsByWindowsAutomation.class);
        }
    }
}


