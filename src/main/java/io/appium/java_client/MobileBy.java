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

import lombok.AccessLevel;
import lombok.Getter;
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

    @Getter(AccessLevel.PROTECTED) private final String locatorString;
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
     * This locator strategy is available in XCUITest Driver mode.
     * @param iOSClassChainString is a valid class chain locator string.
     *                            See <a href="https://github.com/facebook/WebDriverAgent/wiki/Queries">
     *                            the documentation</a> for more details
     * @return an instance of {@link io.appium.java_client.MobileBy.ByIosClassChain}
     */
    public static By iOSClassChain(final String iOSClassChainString) {
        return new ByIosClassChain(iOSClassChainString);
    }

    /**
     * This locator strategy is only available in Espresso Driver mode.
     * @param dataMatcherString is a valid class chain locator string.
     *                            See <a href="https://github.com/appium/appium-espresso-driver/pull/386">
     *                            the documentation</a> for more details
     * @return an instance of {@link io.appium.java_client.MobileBy.ByAndroidDataMatcher}
     */
    public static By androidDataMatcher(final String dataMatcherString) {
        return new ByAndroidDataMatcher(dataMatcherString);
    }

    /**
     * This locator strategy is only available in Espresso Driver mode.
     * @param viewMatcherString is a valid class chain locator string.
     *                            See <a href="https://github.com/appium/appium-espresso-driver/pull/516">
     *                            the documentation</a> for more details
     * @return an instance of {@link io.appium.java_client.MobileBy.ByAndroidViewMatcher}
     */
    public static By androidViewMatcher(final String viewMatcherString) {
        return new ByAndroidViewMatcher(viewMatcherString);
    }

    /**
    * This locator strategy is available in XCUITest Driver mode.
    * @param iOSNsPredicateString is an an iOS NsPredicate String
    * @return an instance of {@link io.appium.java_client.MobileBy.ByIosNsPredicate}
    */
    public static By iOSNsPredicateString(final String iOSNsPredicateString) {
        return new ByIosNsPredicate(iOSNsPredicateString);
    }

    public static By windowsAutomation(final String windowsAutomation) {
        return new ByWindowsAutomation(windowsAutomation);
    }

    /**
     * This locator strategy is available in Espresso Driver mode.
     * @since Appium 1.8.2 beta
     * @param tag is an view tag string
     * @return an instance of {@link ByAndroidViewTag}
     */
    public static By AndroidViewTag(final String tag) {
        return new ByAndroidViewTag(tag);
    }

    /**
     * This locator strategy is available only if OpenCV libraries and
     * NodeJS bindings are installed on the server machine.
     *
     * @see <a href="https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/image-comparison.md">
     * The documentation on Image Comparison Features</a>
     * @see <a href="https://github.com/appium/appium-base-driver/blob/master/lib/basedriver/device-settings.js">
     * The settings available for lookup fine-tuning</a>
     * @since Appium 1.8.2
     * @param b64Template base64-encoded template image string. Supported image formats are the same
     *                    as for OpenCV library.
     * @return an instance of {@link ByImage}
     */
    public static By image(final String b64Template) {
        return new ByImage(b64Template);
    }

    /**
     * This type of locator requires the use of the 'customFindModules' capability and a
     * separately-installed element finding plugin.
     *
     * @param selector selector to pass to the custom element finding plugin
     * @return an instance of {@link ByCustom}
     * @since Appium 1.9.2
     */
    public static By custom(final String selector) {
        return new ByCustom(selector);
    }


    public static class ByAndroidUIAutomator extends MobileBy implements Serializable {


        public ByAndroidUIAutomator(String uiautomatorText) {
            super(MobileSelector.ANDROID_UI_AUTOMATOR, uiautomatorText);
        }

        /**
         * {@inheritDoc}
         *
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

            if (FindsByFluentSelector.class.isAssignableFrom(contextClass)) {
                return super.findElements(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByAndroidUIAutomator.class,
                FindsByFluentSelector.class);
        }

        /**
         * {@inheritDoc}
         *
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

            if (FindsByFluentSelector.class.isAssignableFrom(contextClass)) {
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
         * {@inheritDoc}
         *
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

            if (FindsByFluentSelector.class.isAssignableFrom(contextClass)) {
                return super.findElements(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByAccessibilityId.class,
                FindsByFluentSelector.class);
        }

        /**
         * {@inheritDoc}
         *
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

            if (FindsByFluentSelector.class.isAssignableFrom(contextClass)) {
                return super.findElement(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByAccessibilityId.class,
                FindsByFluentSelector.class);
        }

        @Override public String toString() {
            return "By.AccessibilityId: " + getLocatorString();
        }
    }

    public static class ByIosClassChain extends MobileBy implements Serializable {

        protected ByIosClassChain(String locatorString) {
            super(MobileSelector.IOS_CLASS_CHAIN, locatorString);
        }

        /**
         * {@inheritDoc}
         *
         * @throws WebDriverException when current session doesn't support the given selector or when
         *      value of the selector is not consistent.
         * @throws IllegalArgumentException when it is impossible to find something on the given
         * {@link SearchContext} instance
         */
        @SuppressWarnings("unchecked")
        @Override public List<WebElement> findElements(SearchContext context) {
            Class<?> contextClass = context.getClass();

            if (FindsByIosClassChain.class.isAssignableFrom(contextClass)) {
                return FindsByIosClassChain.class.cast(context)
                        .findElementsByIosClassChain(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(contextClass)) {
                return super.findElements(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByIosClassChain.class,
                    FindsByFluentSelector.class);
        }

        /**
         * {@inheritDoc}
         *
         * @throws WebDriverException when current session doesn't support the given selector or when
         *      value of the selector is not consistent.
         * @throws IllegalArgumentException when it is impossible to find something on the given
         * {@link SearchContext} instance
         */
        @Override public WebElement findElement(SearchContext context) {
            Class<?> contextClass = context.getClass();

            if (FindsByIosClassChain.class.isAssignableFrom(contextClass)) {
                return FindsByIosClassChain.class.cast(context)
                        .findElementByIosClassChain(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(contextClass)) {
                return super.findElement(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByIosClassChain.class,
                    FindsByFluentSelector.class);
        }

        @Override public String toString() {
            return "By.IosClassChain: " + getLocatorString();
        }
    }

    public static class ByAndroidDataMatcher extends MobileBy implements Serializable {

        protected ByAndroidDataMatcher(String locatorString) {
            super(MobileSelector.ANDROID_DATA_MATCHER, locatorString);
        }

        /**
         * {@inheritDoc}
         *
         * @throws WebDriverException when current session doesn't support the given selector or when
         *      value of the selector is not consistent.
         * @throws IllegalArgumentException when it is impossible to find something on the given
         * {@link SearchContext} instance
         */
        @SuppressWarnings("unchecked")
        @Override public List<WebElement> findElements(SearchContext context) {
            Class<?> contextClass = context.getClass();

            if (FindsByAndroidDataMatcher.class.isAssignableFrom(contextClass)) {
                return FindsByAndroidDataMatcher.class.cast(context)
                        .findElementsByAndroidDataMatcher(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(contextClass)) {
                return super.findElements(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByAndroidDataMatcher.class,
                    FindsByFluentSelector.class);
        }

        /**
         * {@inheritDoc}
         *
         * @throws WebDriverException when current session doesn't support the given selector or when
         *      value of the selector is not consistent.
         * @throws IllegalArgumentException when it is impossible to find something on the given
         * {@link SearchContext} instance
         */
        @Override public WebElement findElement(SearchContext context) {
            Class<?> contextClass = context.getClass();

            if (FindsByAndroidDataMatcher.class.isAssignableFrom(contextClass)) {
                return FindsByAndroidDataMatcher.class.cast(context)
                        .findElementByAndroidDataMatcher(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(contextClass)) {
                return super.findElement(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByAndroidDataMatcher.class,
                    FindsByFluentSelector.class);
        }

        @Override public String toString() {
            return "By.FindsByAndroidDataMatcher: " + getLocatorString();
        }
    }

    public static class ByAndroidViewMatcher extends MobileBy implements Serializable {

        protected ByAndroidViewMatcher(String locatorString) {
            super(MobileSelector.ANDROID_VIEW_MATCHER, locatorString);
        }

        /**
         * {@inheritDoc}
         *
         * @throws WebDriverException when current session doesn't support the given selector or when
         *      value of the selector is not consistent.
         * @throws IllegalArgumentException when it is impossible to find something on the given
         * {@link SearchContext} instance
         */
        @SuppressWarnings("unchecked")
        @Override public List<WebElement> findElements(SearchContext context) {
            Class<?> contextClass = context.getClass();

            if (FindsByAndroidViewMatcher.class.isAssignableFrom(contextClass)) {
                return FindsByAndroidViewMatcher.class.cast(context)
                    .findElementsByAndroidViewMatcher(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(contextClass)) {
                return super.findElements(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByAndroidViewMatcher.class,
                FindsByFluentSelector.class);
        }

        /**
         * {@inheritDoc}
         *
         * @throws WebDriverException when current session doesn't support the given selector or when
         *      value of the selector is not consistent.
         * @throws IllegalArgumentException when it is impossible to find something on the given
         * {@link SearchContext} instance
         */
        @Override public WebElement findElement(SearchContext context) {
            Class<?> contextClass = context.getClass();

            if (FindsByAndroidViewMatcher.class.isAssignableFrom(contextClass)) {
                return FindsByAndroidViewMatcher.class.cast(context)
                    .findElementByAndroidViewMatcher(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(contextClass)) {
                return super.findElement(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByAndroidViewMatcher.class,
                FindsByFluentSelector.class);
        }

        @Override public String toString() {
            return "By.FindsByAndroidViewMatcher: " + getLocatorString();
        }
    }

    public static class ByIosNsPredicate extends MobileBy implements Serializable {

        protected ByIosNsPredicate(String locatorString) {
            super(MobileSelector.IOS_PREDICATE_STRING, locatorString);
        }

        /**
         * {@inheritDoc}
         *
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

            if (FindsByFluentSelector.class.isAssignableFrom(contextClass)) {
                return super.findElements(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByIosNSPredicate.class,
                    FindsByFluentSelector.class);
        }

        /**
         * {@inheritDoc}
         *
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

            if (FindsByFluentSelector.class.isAssignableFrom(contextClass)) {
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
         * {@inheritDoc}
         *
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

            if (FindsByFluentSelector.class.isAssignableFrom(contextClass)) {
                return super.findElements(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByWindowsAutomation.class,
                FindsByFluentSelector.class);
        }

        /**
         * {@inheritDoc}
         *
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

            if (FindsByFluentSelector.class.isAssignableFrom(contextClass)) {
                return super.findElement(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByIosNSPredicate.class,
                FindsByWindowsAutomation.class);
        }
    }

    public static class ByImage extends MobileBy implements Serializable {

        protected ByImage(String b64Template) {
            super(MobileSelector.IMAGE, b64Template);
        }

        /**
         * {@inheritDoc}
         *
         * @throws WebDriverException when current session doesn't support the given selector or when
         *      value of the selector is not consistent.
         * @throws IllegalArgumentException when it is impossible to find something on the given
         * {@link SearchContext} instance
         */
        @SuppressWarnings("unchecked")
        @Override public List<WebElement> findElements(SearchContext context) {
            Class<?> contextClass = context.getClass();

            if (FindsByImage.class.isAssignableFrom(contextClass)) {
                return FindsByImage.class.cast(context).findElementsByImage(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(contextClass)) {
                return super.findElements(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByImage.class, FindsByFluentSelector.class);
        }

        /**
         * {@inheritDoc}
         *
         * @throws WebDriverException when current session doesn't support the given selector or when
         *      value of the selector is not consistent.
         * @throws IllegalArgumentException when it is impossible to find something on the given
         * {@link SearchContext} instance
         */
        @Override public WebElement findElement(SearchContext context) {
            Class<?> contextClass = context.getClass();

            if (FindsByImage.class.isAssignableFrom(contextClass)) {
                return FindsByImage.class.cast(context).findElementByImage(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(contextClass)) {
                return super.findElement(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByImage.class, FindsByFluentSelector.class);
        }

        @Override public String toString() {
            return "By.Image: " + getLocatorString();
        }
    }

    public static class ByCustom extends MobileBy implements Serializable {

        protected ByCustom(String selector) {
            super(MobileSelector.CUSTOM, selector);
        }

        /**
         * {@inheritDoc}
         *
         * @throws WebDriverException when current session doesn't support the given selector or when
         *      value of the selector is not consistent.
         * @throws IllegalArgumentException when it is impossible to find something on the given
         * {@link SearchContext} instance
         */
        @SuppressWarnings("unchecked")
        @Override public List<WebElement> findElements(SearchContext context) {
            Class<?> contextClass = context.getClass();

            if (FindsByCustom.class.isAssignableFrom(contextClass)) {
                return FindsByCustom.class.cast(context).findElementsByCustom(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(contextClass)) {
                return super.findElements(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByCustom.class, FindsByFluentSelector.class);
        }

        /**
         * {@inheritDoc}
         *
         * @throws WebDriverException when current session doesn't support the given selector or when
         *      value of the selector is not consistent.
         * @throws IllegalArgumentException when it is impossible to find something on the given
         * {@link SearchContext} instance
         */
        @Override public WebElement findElement(SearchContext context) {
            Class<?> contextClass = context.getClass();

            if (FindsByCustom.class.isAssignableFrom(contextClass)) {
                return FindsByCustom.class.cast(context).findElementByCustom(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(contextClass)) {
                return super.findElement(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByCustom.class, FindsByFluentSelector.class);
        }

        @Override public String toString() {
            return "By.Custom: " + getLocatorString();
        }
    }

    public static class ByAndroidViewTag extends MobileBy implements Serializable {

        public ByAndroidViewTag(String tag) {
            super(MobileSelector.ANDROID_VIEWTAG, tag);
        }

        /**
         * {@inheritDoc}
         *
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

            if (FindsByAndroidViewTag.class.isAssignableFrom(contextClass)) {
                return FindsByAndroidViewTag.class.cast(context)
                        .findElementsByAndroidViewTag(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(contextClass)) {
                return super.findElements(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByAndroidViewTag.class,
                    FindsByFluentSelector.class);
        }

        /**
         * {@inheritDoc}
         *
         * @throws WebDriverException when current session doesn't support the given selector or when
         *      value of the selector is not consistent.
         * @throws IllegalArgumentException when it is impossible to find something on the given
         * {@link SearchContext} instance
         */
        @Override public WebElement findElement(SearchContext context) throws WebDriverException,
                IllegalArgumentException {
            Class<?> contextClass = context.getClass();

            if (FindsByAndroidViewTag.class.isAssignableFrom(contextClass)) {
                return FindsByAndroidViewTag.class.cast(context)
                        .findElementByAndroidViewTag(getLocatorString());
            }

            if (FindsByFluentSelector.class.isAssignableFrom(contextClass)) {
                return super.findElement(context);
            }

            throw formIllegalArgumentException(contextClass, FindsByAndroidViewTag.class,
                    FindsByFluentSelector.class);
        }

        @Override public String toString() {
            return "By.AndroidViewTag: " + getLocatorString();
        }
    }
}


