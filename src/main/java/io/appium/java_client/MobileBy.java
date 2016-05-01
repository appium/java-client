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
    /**
     * Read https://developer.apple.com/library/tvos/documentation/DeveloperTools/
     * Conceptual/InstrumentsUserGuide/UIAutomation.html
     *
     * @param uiautomationText is iOS UIAutomation string
     * @return an instance of {@link io.appium.java_client.MobileBy.ByIosUIAutomation}
     */
    public static By IosUIAutomation(final String uiautomationText) {
        if (StringUtils.isBlank(uiautomationText)) {
            throw new IllegalArgumentException("Must supply an iOS UIAutomation string");
        }

        return new ByIosUIAutomation(uiautomationText);
    }

    /**
     * Read http://developer.android.com/intl/ru/tools/testing-support-library/
     * index.html#uia-apis
     * @param uiautomatorText is Android UIAutomator string
     * @return an instance of {@link io.appium.java_client.MobileBy.ByAndroidUIAutomator}
     */
    public static By AndroidUIAutomator(final String uiautomatorText) {
        if (StringUtils.isBlank(uiautomatorText)) {
            throw new IllegalArgumentException("Must supply an Android UIAutomator string");
        }

        return new ByAndroidUIAutomator(uiautomatorText);
    }

    /**
     * About Android accessibility
     * https://developer.android.com/intl/ru/training/accessibility/accessible-app.html
     * About iOS accessibility
     * https://developer.apple.com/library/ios/documentation/UIKit/Reference/
     * UIAccessibilityIdentification_Protocol/index.html
     * @param id id is a convenient UI automation accessibility Id.
     * @return an instance of {@link io.appium.java_client.MobileBy.ByAndroidUIAutomator}
     */
    public static By AccessibilityId(final String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("Must supply a uiautomationText");
        }

        return new ByAccessibilityId(id);
    }

    /**
     * This locator strategy is available in XCUITest Driver mode
     * @param iOSNsPredicateString is an an iOS NsPredicate String
     * @return an instance of {@link io.appium.java_client.MobileBy.ByIosNsPredicate}
     */
    public static By IosNsPredicateString(final String iOSNsPredicateString) {
        if (iOSNsPredicateString == null) {
            throw new IllegalArgumentException("Must supply an iOS NsPredicate String");
        }

        return new ByIosNsPredicate(iOSNsPredicateString);
    }
    
    public static class ByIosUIAutomation extends By implements Serializable {

        private final String automationText;

        public ByIosUIAutomation(String uiautomationText) {
            automationText = uiautomationText;
        }

        @SuppressWarnings("unchecked")
        @Override
        public List<WebElement> findElements(SearchContext context) {
            return (List<WebElement>) ((FindsByIosUIAutomation<?>) context)
                    .findElementsByIosUIAutomation(automationText);
        }

        @Override public WebElement findElement(SearchContext context) {
            return ((FindsByIosUIAutomation<?>) context)
                .findElementByIosUIAutomation(automationText);
        }

        @Override public String toString() {
            return "By.IosUIAutomation: " + automationText;
        }
    }

    public static class ByIosNsPredicate extends By implements Serializable {
        
        private final String automationText;
        
        public ByIosNsPredicate(String uiautomationText) {
            automationText = uiautomationText;
        }
        
        @SuppressWarnings("unchecked")
        @Override public List<WebElement> findElements(SearchContext context) {
            return (List<WebElement>) ((FindsByIosNsPredicate<?>) context)
                    .findElementsByIosNsPredicate(automationText);
        }
        
        @Override public WebElement findElement(SearchContext context) {
            return ((FindsByIosNsPredicate<?>) context)
                    .findElementByIosNsPredicate(automationText);
        }
        
        @Override public String toString() {
            return "By.IosNsPredicate: " + automationText;
        }
    }


    public static class ByAndroidUIAutomator extends By implements Serializable {

        private final String automatorText;

        public ByAndroidUIAutomator(String uiautomatorText) {
            automatorText = uiautomatorText;
        }

        @SuppressWarnings("unchecked")
        @Override
        public List<WebElement> findElements(SearchContext context) {
            return (List<WebElement>) ((FindsByAndroidUIAutomator<?>) context)
                    .findElementsByAndroidUIAutomator(automatorText);
        }

        @Override public WebElement findElement(SearchContext context) {
            return ((FindsByAndroidUIAutomator<?>) context)
                .findElementByAndroidUIAutomator(automatorText);
        }

        @Override public String toString() {
            return "By.AndroidUIAutomator: " + automatorText;
        }
    }


    public static class ByAccessibilityId extends By implements Serializable {

        private final String id;

        public ByAccessibilityId(String id) {
            this.id = id;
        }

        @SuppressWarnings("unchecked")
        @Override
        public List<WebElement> findElements(SearchContext context) {
            return (List<WebElement>) ((FindsByAccessibilityId<?>) context)
                .findElementsByAccessibilityId(id);
        }

        @Override public WebElement findElement(SearchContext context) {
            return ((FindsByAccessibilityId<?>) context).findElementByAccessibilityId(id);
        }

        @Override public String toString() {
            return "By.AccessibilityId: " + id;
        }
    }
}


