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

import lombok.Getter;
import org.apache.commons.lang3.Validate;
import org.openqa.selenium.By;
import org.openqa.selenium.By.Remotable;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public abstract class AppiumBy extends By implements Remotable {

    @Getter private final Parameters remoteParameters;
    private final String locatorName;

    protected AppiumBy(String selector, String locatorString, String locatorName) {
        Validate.notBlank(locatorString, "Must supply a not empty locator value.");
        this.remoteParameters = new Parameters(selector, locatorString);
        this.locatorName = locatorName;
    }

    @SuppressWarnings("unchecked")
    @Override public List<WebElement> findElements(SearchContext context) {
        return context.findElements(this);
    }

    @Override public WebElement findElement(SearchContext context) {
        return context.findElement(this);
    }

    @Override public String toString() {
        return String.format("AppiumBy.%s: %s", locatorName, remoteParameters.value());
    }

    /**
     * About Android accessibility
     * https://developer.android.com/intl/ru/training/accessibility/accessible-app.html
     * About iOS accessibility
     * https://developer.apple.com/library/ios/documentation/UIKit/Reference/
     * UIAccessibilityIdentification_Protocol/index.html
     * @param accessibilityId id is a convenient UI automation accessibility Id.
     * @return an instance of {@link AppiumBy.ByAndroidUIAutomator}
     */
    public static By accessibilityId(final String accessibilityId) {
        return new ByAccessibilityId(accessibilityId);
    }

    /**
     * This locator strategy is only available in Espresso Driver mode.
     * @param dataMatcherString is a valid json string detailing hamcrest matcher for Espresso onData().
     *                            See <a href="http://appium.io/docs/en/writing-running-appium/android/espresso-datamatcher-selector/">
     *                            the documentation</a> for more details
     * @return an instance of {@link AppiumBy.ByAndroidDataMatcher}
     */
    public static By androidDataMatcher(final String dataMatcherString) {
        return new ByAndroidDataMatcher(dataMatcherString);
    }

    /**
     * Refer to https://developer.android.com/training/testing/ui-automator
     * @param uiautomatorText is Android UIAutomator string
     * @return an instance of {@link AppiumBy.ByAndroidUIAutomator}
     */
    public static By androidUIAutomator(final String uiautomatorText) {
        return new ByAndroidUIAutomator(uiautomatorText);
    }

    /**
     * This locator strategy is only available in Espresso Driver mode.
     * @param viewMatcherString is a valid json string detailing hamcrest matcher for Espresso onView().
     *                            See <a href="http://appium.io/docs/en/writing-running-appium/android/espresso-datamatcher-selector/">
     *                            the documentation</a> for more details
     * @return an instance of {@link AppiumBy.ByAndroidViewMatcher}
     */
    public static By androidViewMatcher(final String viewMatcherString) {
        return new ByAndroidViewMatcher(viewMatcherString);
    }

    /**
     * This locator strategy is available in Espresso Driver mode.
     * @since Appium 1.8.2 beta
     * @param tag is a view tag string
     * @return an instance of {@link ByAndroidViewTag}
     */
    public static By androidViewTag(final String tag) {
        return new ByAndroidViewTag(tag);
    }

    /**
     * For IOS it is the full name of the XCUI element and begins with XCUIElementType.
     * For Android it is the full name of the UIAutomator2 class (e.g.: android.widget.TextView)
     * @param selector the class name of the element
     * @return an instance of {@link ByClassName}
     */
    public static By className(final String selector) {
        return new ByClassName(selector);
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
     * This locator strategy is available in XCUITest Driver mode.
     * @param iOSClassChainString is a valid class chain locator string.
     *                            See <a href="https://github.com/facebookarchive/WebDriverAgent/wiki/Class-Chain-Queries-Construction-Rules">
     *                            the documentation</a> for more details
     * @return an instance of {@link AppiumBy.ByIosClassChain}
     */
    public static By iOSClassChain(final String iOSClassChainString) {
        return new ByIosClassChain(iOSClassChainString);
    }

    /**
     * This locator strategy is available in XCUITest Driver mode.
     * @param iOSNsPredicateString is an iOS NsPredicate String
     * @return an instance of {@link AppiumBy.ByIosNsPredicate}
     */
    public static By iOSNsPredicateString(final String iOSNsPredicateString) {
        return new ByIosNsPredicate(iOSNsPredicateString);
    }

    public static class ByAccessibilityId extends AppiumBy implements Serializable {

        public ByAccessibilityId(String accessibilityId) {
            super("accessibility id", accessibilityId, "accessibilityId");
        }
    }

    public static class ByAndroidDataMatcher extends AppiumBy implements Serializable {

        protected ByAndroidDataMatcher(String locatorString) {
            super("-android datamatcher", locatorString, "androidDataMatcher");
        }
    }

    public static class ByAndroidUIAutomator extends AppiumBy implements Serializable {

        public ByAndroidUIAutomator(String uiautomatorText) {
            super("-android uiautomator", uiautomatorText, "androidUIAutomator");
        }
    }

    public static class ByAndroidViewMatcher extends AppiumBy implements Serializable {

        protected ByAndroidViewMatcher(String locatorString) {
            super("-android viewmatcher", locatorString, "androidViewMatcher");
        }
    }

    public static class ByAndroidViewTag extends AppiumBy implements Serializable {

        public ByAndroidViewTag(String tag) {
            super("-android viewtag", tag, "androidViewTag");
        }
    }

    public static class ByClassName extends AppiumBy implements Serializable {

        protected ByClassName(String selector) {
            super("class name", selector, "className");
        }
    }

    public static class ByCustom extends AppiumBy implements Serializable {

        protected ByCustom(String selector) {
            super("-custom", selector, "custom");
        }
    }

    public static class ByImage extends AppiumBy implements Serializable {

        protected ByImage(String b64Template) {
            super("-image", b64Template, "image");
        }
    }

    public static class ByIosClassChain extends AppiumBy implements Serializable {

        protected ByIosClassChain(String locatorString) {
            super("-ios class chain", locatorString, "iOSClassChain");
        }
    }

    public static class ByIosNsPredicate extends AppiumBy implements Serializable {

        protected ByIosNsPredicate(String locatorString) {
            super("-ios predicate string", locatorString, "iOSNsPredicate");
        }
    }
}


