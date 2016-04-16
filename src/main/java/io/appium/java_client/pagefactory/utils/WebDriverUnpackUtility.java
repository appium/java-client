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

package io.appium.java_client.pagefactory.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.bys.ContentType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;

public final class WebDriverUnpackUtility {
    private static final String NATIVE_APP_PATTERN = "NATIVE_APP";

    /**
     * This method extract an instance of {@link org.openqa.selenium.WebDriver} from the given
     * {@link org.openqa.selenium.SearchContext}.
     * @param searchContext is an instance of {@link org.openqa.selenium.SearchContext}
     *                It may be the instance of {@link org.openqa.selenium.WebDriver}
     *                or {@link org.openqa.selenium.WebElement} or some other user's
     *                extension/implementation.
     *                Note: if you want to use your own implementation then it should implement
     *                {@link org.openqa.selenium.internal.WrapsDriver} or
     *                      {@link org.openqa.selenium.internal.WrapsElement}
     * @return the instance of {@link org.openqa.selenium.WebDriver}.
     *         Note: if the given {@link org.openqa.selenium.SearchContext} is not
     *         {@link org.openqa.selenium.WebDriver} and it doesn't implement
     *         {@link org.openqa.selenium.internal.WrapsDriver} or
     *         {@link org.openqa.selenium.internal.WrapsElement} then this method returns
     *         null.
     *
     */
    public static WebDriver unpackWebDriverFromSearchContext(SearchContext searchContext) {
        if (searchContext instanceof WebDriver) {
            return (WebDriver) searchContext;
        }

        if (searchContext instanceof WrapsDriver) {
            return unpackWebDriverFromSearchContext(
                    ((WrapsDriver) searchContext).getWrappedDriver());
        }

        // Search context it is not only Webdriver. Webelement is search context
        // too.
        // RemoteWebElement and MobileElement implement WrapsDriver
        if (searchContext instanceof WrapsElement) {
            return unpackWebDriverFromSearchContext(
                    ((WrapsElement) searchContext).getWrappedElement());
        }

        return null;
    }

    /**
     * @param driver is an instance of {@link org.openqa.selenium.WebDriver}.
     * @return it returns current mobile platform name. Take a look at
     *     {@link io.appium.java_client.remote.MobilePlatform}.
     *     Note: the given {@link org.openqa.selenium.WebDriver} should implement
     *     @link org.openqa.selenium.HasCapabilities} also. This method will return null
     *     otherwise.
     */
    public static String getPlatform(WebDriver driver) {
        if (driver == null) {
            return null;
        }

        Class<?> driverClass = driver.getClass();
        if (AndroidDriver.class.isAssignableFrom(driverClass)) {
            return MobilePlatform.ANDROID;
        }

        if (IOSDriver.class.isAssignableFrom(driverClass)) {
            return MobilePlatform.IOS;
        }

        //it is possible that somebody uses RemoteWebDriver or their
        //own WebDriver implementation. At this case capabilities are used
        //to detect platform
        if (HasCapabilities.class.isAssignableFrom(driverClass)) {
            return String.valueOf(((HasCapabilities) driver).getCapabilities()
                .getCapability(MobileCapabilityType.PLATFORM_NAME));
        }

        return null;
    }

    /**
     *
     * @param driver is an instance of {@link org.openqa.selenium.WebDriver}.
     * @return it returns current automation type. Take a look at
     * {@link io.appium.java_client.remote.AutomationName}.
     *     Note: the given {@link org.openqa.selenium.WebDriver} should implement
     * {@link org.openqa.selenium.HasCapabilities} also. This method will return null
     *      otherwise.
     *
     */
    public static String getAutomation(WebDriver driver) {
        if (driver == null) {
            return null;
        }

        if (HasCapabilities.class.isAssignableFrom(driver.getClass())) {
            return String.valueOf(((HasCapabilities) driver).getCapabilities()
                .getCapability(MobileCapabilityType.AUTOMATION_NAME));
        }

        return null;
    }

    /**
     * @param context is an instance of {@link org.openqa.selenium.SearchContext}
     *                It may be the instance of {@link org.openqa.selenium.WebDriver}
     *                or {@link org.openqa.selenium.WebElement} or some other user's
     *                extension/implementation.
     *                Note: if you want to use your own implementation then it should
     *                implement {@link org.openqa.selenium.ContextAware} or
     *                {@link org.openqa.selenium.internal.WrapsDriver}
     * @return current content type. It depends on current context. If current context is
     *     NATIVE_APP it will return
     * {@link io.appium.java_client.pagefactory.bys.ContentType#NATIVE_MOBILE_SPECIFIC}.
     * {@link io.appium.java_client.pagefactory.bys.ContentType#HTML_OR_DEFAULT} will be returned
     *     if the current context is WEB_VIEW.
     * {@link io.appium.java_client.pagefactory.bys.ContentType#HTML_OR_DEFAULT} also will be
     *     returned if the given {@link org.openqa.selenium.SearchContext}
     *     instance doesn't implement
     * {@link org.openqa.selenium.ContextAware} and {@link org.openqa.selenium.internal.WrapsDriver}
     */
    public static ContentType getCurrentContentType(SearchContext context) {
        WebDriver driver = WebDriverUnpackUtility.unpackWebDriverFromSearchContext(context);
        if (!ContextAware.class.isAssignableFrom(driver.getClass())) { //it is desktop browser
            return ContentType.HTML_OR_DEFAULT;
        }

        ContextAware contextAware = ContextAware.class.cast(driver);
        String currentContext = contextAware.getContext();
        if (currentContext.contains(NATIVE_APP_PATTERN)) {
            return ContentType.NATIVE_MOBILE_SPECIFIC;
        }

        return ContentType.HTML_OR_DEFAULT;
    }
}
