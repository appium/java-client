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
import org.openqa.selenium.WebElement;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public abstract class MobileBy extends By {

    private final String locatorString;

    protected MobileBy(String locatorString) {
        if (StringUtils.isBlank(locatorString)) {
            throw new IllegalArgumentException("Must supply a not empty locator value.");
        }
        this.locatorString = locatorString;
    }

    protected String getLocatorString() {
        return locatorString;
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
    
    public static class ByIosUIAutomation extends MobileBy implements Serializable {

        public ByIosUIAutomation(String iOSAutomationText) {
            super(iOSAutomationText);
        }

        @SuppressWarnings("unchecked")
        @Override
        public List<WebElement> findElements(SearchContext context) {
            return (List<WebElement>) ((FindsByIosUIAutomation<?>) context)
                    .findElementsByIosUIAutomation(getLocatorString());
        }

        @Override public WebElement findElement(SearchContext context) {
            return ((FindsByIosUIAutomation<?>) context)
                .findElementByIosUIAutomation(getLocatorString());
        }

        @Override public String toString() {
            return "By.IosUIAutomation: " + getLocatorString();
        }
    }


    public static class ByAndroidUIAutomator extends MobileBy implements Serializable {


        public ByAndroidUIAutomator(String uiautomatorText) {
            super(uiautomatorText);
        }

        @SuppressWarnings("unchecked")
        @Override
        public List<WebElement> findElements(SearchContext context) {
            return (List<WebElement>) ((FindsByAndroidUIAutomator<?>) context)
                    .findElementsByAndroidUIAutomator(getLocatorString());
        }

        @Override public WebElement findElement(SearchContext context) {
            return ((FindsByAndroidUIAutomator<?>) context)
                .findElementByAndroidUIAutomator(getLocatorString());
        }

        @Override public String toString() {
            return "By.AndroidUIAutomator: " + getLocatorString();
        }
    }


    public static class ByAccessibilityId extends MobileBy implements Serializable {

        public ByAccessibilityId(String accessibilityId) {
            super(accessibilityId);
        }

        @SuppressWarnings("unchecked")
        @Override
        public List<WebElement> findElements(SearchContext context) {
            return (List<WebElement>) ((FindsByAccessibilityId<?>) context)
                .findElementsByAccessibilityId(getLocatorString());
        }

        @Override public WebElement findElement(SearchContext context) {
            return ((FindsByAccessibilityId<?>) context)
                    .findElementByAccessibilityId(getLocatorString());
        }

        @Override public String toString() {
            return "By.AccessibilityId: " + getLocatorString();
        }
    }
}


