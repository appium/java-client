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

package io.appium.java_client.ios;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.FindsByIosNsPredicate;
import io.appium.java_client.FindsByIosUIAutomation;
import io.appium.java_client.MobileCommand;
import io.appium.java_client.MobileElement;

import io.appium.java_client.ScrollsTo;
import io.appium.java_client.SwipeElementDirection;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class IOSElement extends MobileElement
    implements FindsByIosUIAutomation<MobileElement>, ScrollsTo<MobileElement>,
        FindsByIosNsPredicate<MobileElement> {
    /**
     * @throws WebDriverException
     * This method is not applicable with browser/webview UI.
     */
    @Override public MobileElement findElementByIosUIAutomation(String using)
        throws WebDriverException {
        return (IOSElement) findElement("-ios uiautomation", using);
    }

    /**
     * @throws WebDriverException
     * This method is not applicable with browser/webview UI.
     */
    @Override public List<MobileElement> findElementsByIosUIAutomation(String using)
        throws WebDriverException {
        List<MobileElement> result = new ArrayList<MobileElement>();
        List<WebElement> found = findElements("-ios uiautomation", using);
        for (WebElement e : found) {
            result.add((IOSElement) e);
        }
        return result;
    }
    
    /**
     * @throws org.openqa.selenium.WebDriverException 
     * This method is not applicable with browser/webview UI.
     */
    @Override public MobileElement findElementByIosNsPredicate(String using)
        throws WebDriverException {
        return (IOSElement) findElement("-ios predicate string", using);
    }

    /**
     * @throws WebDriverException This method is not applicable with browser/webview UI.
     */
    @Override public List<MobileElement> findElementsByIosNsPredicate(String using)
        throws WebDriverException {
        List<MobileElement> result = new ArrayList<MobileElement>();
        List<WebElement> found = findElements("-ios predicate string", using);
        for (WebElement e : found) {
            result.add((IOSElement) e);
        }
        return result;
    }

    /**
     * This method is deprecated because it is not consistent and it is going to be removed.
     * It is workaround actually.
     * Recommended to use instead:
     * {@link io.appium.java_client.AppiumDriver#swipe(int, int, int, int, int)}
     * {@link io.appium.java_client.MobileElement#swipe(SwipeElementDirection, int)}
     * {@link io.appium.java_client.MobileElement#swipe(SwipeElementDirection, int, int, int)}
     * or search for elements using {@link io.appium.java_client.MobileBy.ByIosUIAutomation}
     */
    @Deprecated
    @Override public MobileElement scrollTo(String text) {
        return (IOSElement) findElementByIosUIAutomation(
            ".scrollToElementWithPredicate(\"name CONTAINS '" + text + "'\")");
    }

    /**
     * This method is deprecated because it is not consistent and it is going to be removed.
     * It is workaround actually.
     * Recommended to use instead:
     * {@link io.appium.java_client.AppiumDriver#swipe(int, int, int, int, int)}
     * {@link io.appium.java_client.MobileElement#swipe(SwipeElementDirection, int)}
     * {@link io.appium.java_client.MobileElement#swipe(SwipeElementDirection, int, int, int)}
     * or search for elements using {@link io.appium.java_client.MobileBy.ByIosUIAutomation}
     */
    @Deprecated
    @Override public MobileElement scrollToExact(String text) {
        return (IOSElement) findElementByIosUIAutomation(
            ".scrollToElementWithName(\"" + text + "\")");
    }

    /**
     * This method sets the new value of the attribute "value".
     *
     * @param value is the new value which should be set
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void setValue(String value) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        builder.put("id", id).put("value", value);
        execute(MobileCommand.SET_VALUE, builder.build());
    }
}
