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

import com.google.gson.Gson;
import io.appium.java_client.internal.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.By.Remotable;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.appium.java_client.internal.Strings.isNullOrEmpty;

/**
 * The base class of the locator strategies supported by Appium.
 */
@EqualsAndHashCode(callSuper = true)
public abstract class AppiumBy extends By implements Remotable {

    @Getter
    private final Parameters remoteParameters;
    private final String locatorName;

    /**
     * Creates a new locator.
     *
     * @param selector the name of the locator strategy
     * @param locatorString the locator value; must not be empty
     * @param locatorName the name of the factory method, used in the string representation
     */
    protected AppiumBy(String selector, String locatorString, String locatorName) {
        Preconditions.checkArgument(!isNullOrEmpty(locatorString), "Must supply a not empty locator value.");
        this.remoteParameters = new Parameters(selector, locatorString);
        this.locatorName = locatorName;
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        return context.findElements(this);
    }

    @Override
    public WebElement findElement(SearchContext context) {
        return context.findElement(this);
    }

    @Override
    public String toString() {
        return String.format("%s.%s: %s", AppiumBy.class.getSimpleName(), locatorName, remoteParameters.value());
    }

    /**
     * About Android accessibility
     * https://developer.android.com/intl/ru/training/accessibility/accessible-app.html
     * About iOS accessibility
     * https://developer.apple.com/library/ios/documentation/UIKit/Reference/
     * UIAccessibilityIdentification_Protocol/index.html
     *
     * @param accessibilityId id is a convenient UI automation accessibility Id.
     * @return an instance of {@link AppiumBy.ByAndroidUIAutomator}
     */
    public static By accessibilityId(final String accessibilityId) {
        return new ByAccessibilityId(accessibilityId);
    }

    /**
     * This locator strategy is only available in Espresso Driver mode.
     *
     * @param dataMatcherString is a valid json string detailing hamcrest matcher for Espresso onData().
     *                          See <a href="http://appium.io/docs/en/writing-running-appium/android/espresso-datamatcher-selector/">
     *                          the documentation</a> for more details
     * @return an instance of {@link AppiumBy.ByAndroidDataMatcher}
     */
    public static By androidDataMatcher(final String dataMatcherString) {
        return new ByAndroidDataMatcher(dataMatcherString);
    }

    /**
     * Refer to <a href="https://developer.android.com/training/testing/ui-automator">UI Automator</a> .
     *
     * @param uiautomatorText is Android UIAutomator string
     * @return an instance of {@link ByAndroidUIAutomator}
     */
    public static By androidUIAutomator(final String uiautomatorText) {
        return new ByAndroidUIAutomator(uiautomatorText);
    }

    /**
     * This locator strategy is only available in Espresso Driver mode.
     *
     * @param viewMatcherString is a valid json string detailing hamcrest matcher for Espresso onView().
     *                          See <a href="http://appium.io/docs/en/writing-running-appium/android/espresso-datamatcher-selector/">
     *                          the documentation</a> for more details
     * @return an instance of {@link AppiumBy.ByAndroidViewMatcher}
     */
    public static By androidViewMatcher(final String viewMatcherString) {
        return new ByAndroidViewMatcher(viewMatcherString);
    }

    /**
     * This locator strategy is available in Espresso Driver mode.
     *
     * @param tag is a view tag string
     * @return an instance of {@link ByAndroidViewTag}
     * @since Appium 1.8.2 beta
     */
    public static By androidViewTag(final String tag) {
        return new ByAndroidViewTag(tag);
    }

    /**
     * For IOS it is the full name of the XCUI element and begins with XCUIElementType.
     * For Android it is the full name of the UIAutomator2 class (e.g.: android.widget.TextView)
     *
     * @param selector the class name of the element
     * @return an instance of {@link ByClassName}
     */
    public static By className(final String selector) {
        return new ByClassName(selector);
    }

    /**
     * For IOS the element name.
     * For Android it is the resource identifier.
     *
     * @param selector element id
     * @return an instance of {@link ById}
     */
    public static By id(final String selector) {
        return new ById(selector);
    }

    /**
     * For IOS the element name.
     * For Android it is the resource identifier.
     *
     * @param selector element id
     * @return an instance of {@link ByName}
     */
    public static By name(final String selector) {
        return new ByName(selector);
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
     * Node.js bindings are installed on the server machine.
     *
     * @param b64Template base64-encoded template image string. Supported image formats are the same
     *                    as for OpenCV library.
     * @return an instance of {@link ByImage}
     * @see <a href="https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/image-comparison.md">
     *     The documentation on Image Comparison Features</a>
     * @see <a href="https://github.com/appium/appium-base-driver/blob/master/lib/basedriver/device-settings.js">
     *     The settings available for lookup fine-tuning</a>
     * @since Appium 1.8.2
     */
    public static By image(final String b64Template) {
        return new ByImage(b64Template);
    }

    /**
     * This locator strategy is available in XCUITest Driver mode.
     *
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
     *
     * @param iOSNsPredicateString is an iOS NsPredicate String
     * @return an instance of {@link AppiumBy.ByIosNsPredicate}
     */
    public static By iOSNsPredicateString(final String iOSNsPredicateString) {
        return new ByIosNsPredicate(iOSNsPredicateString);
    }

    /**
     * This locator strategy is available in FlutterIntegration Driver mode.
     *
     * @param selector is the value defined to the key attribute of the flutter element
     * @return an instance of {@link AppiumBy.ByFlutterKey}
     */
    public static FlutterBy flutterKey(final String selector) {
        return new ByFlutterKey(selector);
    }

    /**
     * This locator strategy is available in FlutterIntegration Driver mode.
     *
     * @param selector is the Type of widget mounted in the app tree
     * @return an instance of {@link AppiumBy.ByFlutterType}
     */
    public static FlutterBy flutterType(final String selector) {
        return new ByFlutterType(selector);
    }

    /**
     * This locator strategy is available in FlutterIntegration Driver mode.
     *
     * @param selector is the text that is present on the widget
     * @return an instance of {@link AppiumBy.ByFlutterText}
     */
    public static FlutterBy flutterText(final String selector) {
        return new ByFlutterText(selector);
    }

    /**
     * This locator strategy is available in FlutterIntegration Driver mode.
     *
     * @param selector is the text that is partially present on the widget
     * @return an instance of {@link AppiumBy.ByFlutterTextContaining}
     */
    public static FlutterBy flutterTextContaining(final String selector) {
        return new ByFlutterTextContaining(selector);
    }

    /**
     * This locator strategy is available in FlutterIntegration Driver mode.
     *
     * @param semanticsLabel represents the value assigned to the label attribute of semantics element
     * @return an instance of {@link AppiumBy.ByFlutterSemanticsLabel}
     */
    public static FlutterBy flutterSemanticsLabel(final String semanticsLabel) {
        return new ByFlutterSemanticsLabel(semanticsLabel);
    }

    /**
     * This locator strategy is available in FlutterIntegration Driver mode since version 1.4.0.
     *
     * @param of represents the parent widget locator
     * @param matching represents the descendant widget locator to match
     * @param matchRoot determines whether to include the root widget in the search
     * @param skipOffstage determines whether to skip offstage widgets
     * @return an instance of {@link AppiumBy.ByFlutterDescendant}
     */
    public static FlutterBy flutterDescendant(
            final FlutterBy of,
            final FlutterBy matching,
            boolean matchRoot,
            boolean skipOffstage) {
        return new ByFlutterDescendant(of, matching, matchRoot, skipOffstage);
    }

    /**
     * This locator strategy is available in FlutterIntegration Driver mode since version 1.4.0.
     *
     * @param of represents the parent widget locator
     * @param matching represents the descendant widget locator to match
     * @return an instance of {@link AppiumBy.ByFlutterDescendant}
     */
    public static FlutterBy flutterDescendant(final FlutterBy of, final FlutterBy matching) {
        return flutterDescendant(of, matching, false, true);
    }

    /**
     * This locator strategy is available in FlutterIntegration Driver mode since version 1.4.0.
     *
     * @param of represents the child widget locator
     * @param matching represents the ancestor widget locator to match
     * @param matchRoot determines whether to include the root widget in the search
     * @return an instance of {@link AppiumBy.ByFlutterAncestor}
     */
    public static FlutterBy flutterAncestor(final FlutterBy of, final FlutterBy matching, boolean matchRoot) {
        return new ByFlutterAncestor(of, matching, matchRoot);
    }

    /**
     * This locator strategy is available in FlutterIntegration Driver mode since version 1.4.0.
     *
     * @param of represents the child widget locator
     * @param matching represents the ancestor widget locator to match
     * @return an instance of {@link AppiumBy.ByFlutterAncestor}
     */
    public static FlutterBy flutterAncestor(final FlutterBy of, final FlutterBy matching) {
        return flutterAncestor(of, matching, false);
    }

    /**
     * Locates elements by their accessibility id.
     */
    public static class ByAccessibilityId extends AppiumBy implements Serializable {
        /**
         * Creates a new instance for the given accessibility id.
         *
         * @param accessibilityId the accessibility id
         */
        public ByAccessibilityId(String accessibilityId) {
            super("accessibility id", accessibilityId, "accessibilityId");
        }
    }

    /**
     * Locates elements by an Espresso data matcher (Espresso driver only).
     */
    public static class ByAndroidDataMatcher extends AppiumBy implements Serializable {
        /**
         * Creates a new instance for the given locator.
         *
         * @param locatorString the locator string
         */
        protected ByAndroidDataMatcher(String locatorString) {
            super("-android datamatcher", locatorString, "androidDataMatcher");
        }
    }

    /**
     * Locates elements by an Android UIAutomator expression.
     */
    public static class ByAndroidUIAutomator extends AppiumBy implements Serializable {
        /**
         * Creates a new instance for the given locator.
         *
         * @param uiautomatorText the UIAutomator expression
         */
        public ByAndroidUIAutomator(String uiautomatorText) {
            super("-android uiautomator", uiautomatorText, "androidUIAutomator");
        }
    }

    /**
     * Locates elements by an Espresso view matcher (Espresso driver only).
     */
    public static class ByAndroidViewMatcher extends AppiumBy implements Serializable {
        /**
         * Creates a new instance for the given locator.
         *
         * @param locatorString the locator string
         */
        protected ByAndroidViewMatcher(String locatorString) {
            super("-android viewmatcher", locatorString, "androidViewMatcher");
        }
    }

    /**
     * Locates elements by an Android view tag (Espresso driver only).
     */
    public static class ByAndroidViewTag extends AppiumBy implements Serializable {
        /**
         * Creates a new instance for the given tag.
         *
         * @param tag the view tag
         */
        public ByAndroidViewTag(String tag) {
            super("-android viewtag", tag, "androidViewTag");
        }
    }

    /**
     * Locates elements by the id (the name on iOS, the resource id on Android).
     */
    public static class ById extends AppiumBy implements Serializable {
        /**
         * Creates a new instance for the given id.
         *
         * @param selector the element id
         */
        protected ById(String selector) {
            super("id", selector, "id");
        }
    }

    /**
     * Locates elements by the name.
     */
    public static class ByName extends AppiumBy implements Serializable {
        /**
         * Creates a new instance for the given name.
         *
         * @param selector the element name
         */
        protected ByName(String selector) {
            super("name", selector, "name");
        }
    }

    /**
     * Locates elements by the class name.
     */
    public static class ByClassName extends AppiumBy implements Serializable {
        /**
         * Creates a new instance for the given class name.
         *
         * @param selector the class name
         */
        protected ByClassName(String selector) {
            super("class name", selector, "className");
        }
    }

    /**
     * Locates elements using a custom element finding plugin.
     */
    public static class ByCustom extends AppiumBy implements Serializable {
        /**
         * Creates a new instance for the given selector.
         *
         * @param selector the selector to pass to the plugin
         */
        protected ByCustom(String selector) {
            super("-custom", selector, "custom");
        }
    }

    /**
     * Locates elements by an image template.
     */
    public static class ByImage extends AppiumBy implements Serializable {
        /**
         * Creates a new instance for the given template.
         *
         * @param b64Template the base64-encoded template image
         */
        protected ByImage(String b64Template) {
            super("-image", b64Template, "image");
        }
    }

    /**
     * Locates elements by an iOS class chain (XCUITest driver only).
     */
    public static class ByIosClassChain extends AppiumBy implements Serializable {
        /**
         * Creates a new instance for the given locator.
         *
         * @param locatorString the locator string
         */
        protected ByIosClassChain(String locatorString) {
            super("-ios class chain", locatorString, "iOSClassChain");
        }
    }

    /**
     * Locates elements by an iOS NSPredicate string (XCUITest driver only).
     */
    public static class ByIosNsPredicate extends AppiumBy implements Serializable {
        /**
         * Creates a new instance for the given locator.
         *
         * @param locatorString the locator string
         */
        protected ByIosNsPredicate(String locatorString) {
            super("-ios predicate string", locatorString, "iOSNsPredicate");
        }
    }

    /**
     * The base class of the locator strategies of the Flutter integration driver.
     */
    public abstract static class FlutterBy extends AppiumBy {
        /**
         * Creates a new Flutter locator.
         *
         * @param selector the name of the locator strategy
         * @param locatorString the locator value
         * @param locatorName the name of the factory method, used in the string representation
         */
        protected FlutterBy(String selector, String locatorString, String locatorName) {
            super(selector, locatorString, locatorName);
        }
    }

    /**
     * The base class of the Flutter locators that combine two other Flutter locators.
     */
    public abstract static class FlutterByHierarchy extends FlutterBy {
        private static final Gson GSON = new Gson();

        /**
         * Creates a new hierarchical Flutter locator.
         *
         * @param selector the name of the locator strategy
         * @param of the base widget locator
         * @param matching the related widget locator to match
         * @param properties the additional locator parameters
         * @param locatorName the name of the factory method, used in the string representation
         */
        protected FlutterByHierarchy(
                String selector,
                FlutterBy of,
                FlutterBy matching,
                Map<String, Object> properties,
                String locatorName) {
            super(selector, formatLocator(of, matching, properties), locatorName);
        }

        static Map<String, Object> parseFlutterLocator(FlutterBy by) {
            Parameters params = by.getRemoteParameters();
            return Map.of("using", params.using(), "value", params.value());
        }

        static String formatLocator(FlutterBy of, FlutterBy matching, Map<String, Object> properties) {
            Map<String, Object> locator = new HashMap<>();
            locator.put("of", parseFlutterLocator(of));
            locator.put("matching", parseFlutterLocator(matching));
            locator.put("parameters", properties);
            return GSON.toJson(locator);
        }
    }

    /**
     * Locates Flutter widgets by their type.
     */
    public static class ByFlutterType extends FlutterBy implements Serializable {
        /**
         * Creates a new instance for the given locator.
         *
         * @param locatorString the locator string
         */
        protected ByFlutterType(String locatorString) {
            super("-flutter type", locatorString, "flutterType");
        }
    }

    /**
     * Locates Flutter widgets by the value of their key.
     */
    public static class ByFlutterKey extends FlutterBy implements Serializable {
        /**
         * Creates a new instance for the given locator.
         *
         * @param locatorString the locator string
         */
        protected ByFlutterKey(String locatorString) {
            super("-flutter key", locatorString, "flutterKey");
        }
    }

    /**
     * Locates Flutter widgets by their semantics label.
     */
    public static class ByFlutterSemanticsLabel extends FlutterBy implements Serializable {
        /**
         * Creates a new instance for the given locator.
         *
         * @param locatorString the locator string
         */
        protected ByFlutterSemanticsLabel(String locatorString) {
            super("-flutter semantics label", locatorString, "flutterSemanticsLabel");
        }
    }

    /**
     * Locates Flutter widgets by their text.
     */
    public static class ByFlutterText extends FlutterBy implements Serializable {
        /**
         * Creates a new instance for the given locator.
         *
         * @param locatorString the locator string
         */
        protected ByFlutterText(String locatorString) {
            super("-flutter text", locatorString, "flutterText");
        }
    }

    /**
     * Locates Flutter widgets by a part of their text.
     */
    public static class ByFlutterTextContaining extends FlutterBy implements Serializable {
        /**
         * Creates a new instance for the given locator.
         *
         * @param locatorString the locator string
         */
        protected ByFlutterTextContaining(String locatorString) {
            super("-flutter text containing", locatorString, "flutterTextContaining");
        }
    }

    /**
     * Locates Flutter widgets that are descendants of another widget.
     */
    public static class ByFlutterDescendant extends FlutterByHierarchy implements Serializable {
        /**
         * Creates a new instance for the given locators.
         *
         * @param of the parent widget locator
         * @param matching the descendant widget locator to match
         * @param matchRoot whether to include the root widget in the search
         * @param skipOffstage whether to skip offstage widgets
         */
        protected ByFlutterDescendant(FlutterBy of, FlutterBy matching, boolean matchRoot, boolean skipOffstage) {
            super(
                    "-flutter descendant",
                    of,
                    matching,
                    Map.of("matchRoot", matchRoot, "skipOffstage", skipOffstage), "flutterDescendant");
        }
    }

    /**
     * Locates Flutter widgets that are ancestors of another widget.
     */
    public static class ByFlutterAncestor extends FlutterByHierarchy implements Serializable {
        /**
         * Creates a new instance for the given locators.
         *
         * @param of the child widget locator
         * @param matching the ancestor widget locator to match
         * @param matchRoot whether to include the root widget in the search
         */
        protected ByFlutterAncestor(FlutterBy of, FlutterBy matching, boolean matchRoot) {
            super(
                    "-flutter ancestor",
                    of,
                    matching,
                    Map.of("matchRoot", matchRoot), "flutterAncestor");
        }
    }
}
