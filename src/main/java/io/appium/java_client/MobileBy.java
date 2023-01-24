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

import org.openqa.selenium.By;

import java.io.Serializable;

/**
 * Appium locating strategies.
 *
 * @deprecated Use {@link AppiumBy} instead.
 */
@SuppressWarnings("serial")
@Deprecated
public abstract class MobileBy extends AppiumBy {

    protected MobileBy(String selector, String locatorString, String locatorName) {
        super(selector, locatorString, locatorName);
    }

    /**
     * Refer to https://developer.android.com/training/testing/ui-automator
     *
     * @param uiautomatorText is Android UIAutomator string
     * @return an instance of {@link ByAndroidUIAutomator}
     * @deprecated Use {@link AppiumBy#androidUIAutomator(String)} instead.
     */
    @Deprecated
    public static By AndroidUIAutomator(final String uiautomatorText) {
        return new ByAndroidUIAutomator(uiautomatorText);
    }

    /**
     * About Android accessibility
     * https://developer.android.com/intl/ru/training/accessibility/accessible-app.html
     * About iOS accessibility
     * https://developer.apple.com/library/ios/documentation/UIKit/Reference/
     * UIAccessibilityIdentification_Protocol/index.html
     *
     * @param accessibilityId id is a convenient UI automation accessibility Id.
     * @return an instance of {@link ByAndroidUIAutomator}
     * @deprecated Use {@link AppiumBy#accessibilityId(String)} instead.
     */
    @Deprecated
    public static By AccessibilityId(final String accessibilityId) {
        return new ByAccessibilityId(accessibilityId);
    }

    /**
     * This locator strategy is available in XCUITest Driver mode.
     *
     * @param iOSClassChainString is a valid class chain locator string.
     *                            See <a href="https://github.com/facebookarchive/WebDriverAgent/wiki/Class-Chain-Queries-Construction-Rules">
     *                            the documentation</a> for more details
     * @return an instance of {@link ByIosClassChain}
     * @deprecated Use {@link AppiumBy#iOSClassChain(String)} instead.
     */
    @Deprecated
    public static By iOSClassChain(final String iOSClassChainString) {
        return new ByIosClassChain(iOSClassChainString);
    }

    /**
     * This locator strategy is only available in Espresso Driver mode.
     *
     * @param dataMatcherString is a valid json string detailing hamcrest matcher for Espresso onData().
     *                          See <a href="http://appium.io/docs/en/writing-running-appium/android/espresso-datamatcher-selector/">
     *                          the documentation</a> for more details
     * @return an instance of {@link ByAndroidDataMatcher}
     * @deprecated Use {@link AppiumBy#androidDataMatcher(String)} instead.
     */
    @Deprecated
    public static By androidDataMatcher(final String dataMatcherString) {
        return new ByAndroidDataMatcher(dataMatcherString);
    }

    /**
     * This locator strategy is only available in Espresso Driver mode.
     *
     * @param viewMatcherString is a valid json string detailing hamcrest matcher for Espresso onView().
     *                          See <a href="http://appium.io/docs/en/writing-running-appium/android/espresso-datamatcher-selector/">
     *                          the documentation</a> for more details
     * @return an instance of {@link ByAndroidViewMatcher}
     * @deprecated Use {@link AppiumBy#androidViewMatcher(String)} instead.
     */
    @Deprecated
    public static By androidViewMatcher(final String viewMatcherString) {
        return new ByAndroidViewMatcher(viewMatcherString);
    }

    /**
     * This locator strategy is available in XCUITest Driver mode.
     *
     * @param iOSNsPredicateString is an iOS NsPredicate String
     * @return an instance of {@link ByIosNsPredicate}
     * @deprecated Use {@link AppiumBy#iOSNsPredicateString(String)} instead.
     */
    @Deprecated
    public static By iOSNsPredicateString(final String iOSNsPredicateString) {
        return new ByIosNsPredicate(iOSNsPredicateString);
    }

    /**
     * The Windows UIAutomation selector.
     *
     * @param windowsAutomation The element name in the Windows UIAutomation selector
     * @return an instance of {@link MobileBy.ByWindowsAutomation}
     * @deprecated Not supported on the server side.
     */
    @Deprecated
    public static By windowsAutomation(final String windowsAutomation) {
        return new ByWindowsAutomation(windowsAutomation);
    }

    /**
     * This locator strategy is available in Espresso Driver mode.
     *
     * @param tag is an view tag string
     * @return an instance of {@link ByAndroidViewTag}
     * @since Appium 1.8.2 beta
     * @deprecated Use {@link AppiumBy#androidViewTag(String)} instead.
     */
    @Deprecated
    public static By AndroidViewTag(final String tag) {
        return new ByAndroidViewTag(tag);
    }

    /**
     * This locator strategy is available only if OpenCV libraries and
     * NodeJS bindings are installed on the server machine.
     *
     * @param b64Template base64-encoded template image string. Supported image formats are the same
     *                    as for OpenCV library.
     * @return an instance of {@link ByImage}
     * @see <a href="https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/image-comparison.md">
     * The documentation on Image Comparison Features</a>
     * @see <a href="https://github.com/appium/appium-base-driver/blob/master/lib/basedriver/device-settings.js">
     * The settings available for lookup fine-tuning</a>
     * @since Appium 1.8.2
     * @deprecated Use {@link AppiumBy#image(String)} instead.
     */
    @Deprecated
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
     * @deprecated Use {@link AppiumBy#custom(String)} instead.
     */
    @Deprecated
    public static By custom(final String selector) {
        return new ByCustom(selector);
    }

    /**
     * Refer to https://developer.android.com/training/testing/ui-automator
     *
     * @deprecated Use {@link AppiumBy.ByAndroidUIAutomator} instead.
     */
    @Deprecated
    public static class ByAndroidUIAutomator extends AppiumBy.ByAndroidUIAutomator {

        public ByAndroidUIAutomator(String uiautomatorText) {
            super(uiautomatorText);
        }

        @Override public String toString() {
            return "By.AndroidUIAutomator: " + getRemoteParameters().value();
        }
    }

    /**
     * About Android accessibility
     * https://developer.android.com/intl/ru/training/accessibility/accessible-app.html
     * About iOS accessibility
     * https://developer.apple.com/library/ios/documentation/UIKit/Reference/
     * UIAccessibilityIdentification_Protocol/index.html
     * @deprecated Use {@link AppiumBy.ByAccessibilityId} instead.
     */
    @Deprecated
    public static class ByAccessibilityId extends AppiumBy.ByAccessibilityId {

        public ByAccessibilityId(String accessibilityId) {
            super(accessibilityId);
        }

        @Override public String toString() {
            return "By.AccessibilityId: " + getRemoteParameters().value();
        }
    }

    /**
     * This locator strategy is available in XCUITest Driver mode.
     * See <a href="https://github.com/facebookarchive/WebDriverAgent/wiki/Class-Chain-Queries-Construction-Rules">
     * the documentation</a> for more details
     * @deprecated Use {@link AppiumBy.ByIosClassChain} instead.
     */
    @Deprecated
    public static class ByIosClassChain extends AppiumBy.ByIosClassChain {

        protected ByIosClassChain(String locatorString) {
            super(locatorString);
        }

        @Override public String toString() {
            return "By.IosClassChain: " + getRemoteParameters().value();
        }
    }

    /**
     * This locator strategy is only available in Espresso Driver mode.
     * See <a href="http://appium.io/docs/en/writing-running-appium/android/espresso-datamatcher-selector/">
     * the documentation</a> for more details
     * @deprecated Use {@link AppiumBy.ByAndroidDataMatcher} instead.
     */
    @Deprecated
    public static class ByAndroidDataMatcher extends AppiumBy.ByAndroidDataMatcher {

        protected ByAndroidDataMatcher(String locatorString) {
            super(locatorString);
        }

        @Override public String toString() {
            return "By.AndroidDataMatcher: " + getRemoteParameters().value();
        }
    }

    /**
     * This locator strategy is only available in Espresso Driver mode.
     * See <a href="http://appium.io/docs/en/writing-running-appium/android/espresso-datamatcher-selector/">
     * the documentation</a> for more details
     * @deprecated Use {@link AppiumBy.ByAndroidViewMatcher} instead.
     */
    @Deprecated
    public static class ByAndroidViewMatcher extends AppiumBy.ByAndroidViewMatcher {

        protected ByAndroidViewMatcher(String locatorString) {
            super(locatorString);
        }

        @Override public String toString() {
            return "By.AndroidViewMatcher: " + getRemoteParameters().value();
        }
    }

    /**
     * This locator strategy is available in XCUITest Driver mode.
     * @deprecated Use {@link AppiumBy.ByIosNsPredicate} instead.
     */
    @Deprecated
    public static class ByIosNsPredicate extends AppiumBy.ByIosNsPredicate {

        protected ByIosNsPredicate(String locatorString) {
            super(locatorString);
        }

        @Override public String toString() {
            return "By.IosNsPredicate: " + getRemoteParameters().value();
        }
    }

    /**
     * The Windows UIAutomation selector.
     * @deprecated Not supported on the server side.
     */
    @Deprecated
    public static class ByWindowsAutomation extends MobileBy implements Serializable {

        protected ByWindowsAutomation(String locatorString) {
            super("-windows uiautomation", locatorString, "windowsAutomation");
        }

        @Override public String toString() {
            return "By.windowsAutomation: " + getRemoteParameters().value();
        }
    }

    /**
     * This locator strategy is available only if OpenCV libraries and
     * NodeJS bindings are installed on the server machine.
     * @deprecated Use {@link AppiumBy.ByImage} instead.
     */
    @Deprecated
    public static class ByImage extends AppiumBy.ByImage {

        protected ByImage(String b64Template) {
            super(b64Template);
        }

        @Override public String toString() {
            return "By.Image: " + getRemoteParameters().value();
        }
    }

    /**
     * This type of locator requires the use of the 'customFindModules' capability and a
     * separately-installed element finding plugin.
     * @deprecated Use {@link AppiumBy.ByCustom} instead.
     */
    @Deprecated
    public static class ByCustom extends AppiumBy.ByCustom {

        protected ByCustom(String selector) {
            super(selector);
        }

        @Override public String toString() {
            return "By.Custom: " + getRemoteParameters().value();
        }
    }

    /**
     * This locator strategy is available in Espresso Driver mode.
     * @deprecated Use {@link AppiumBy.ByAndroidViewTag} instead.
     */
    @Deprecated
    public static class ByAndroidViewTag extends AppiumBy.ByAndroidViewTag {

        public ByAndroidViewTag(String tag) {
            super(tag);
        }

        @Override public String toString() {
            return "By.AndroidViewTag: " + getRemoteParameters().value();
        }
    }
}
