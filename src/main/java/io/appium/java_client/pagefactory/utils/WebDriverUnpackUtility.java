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
    private final static String NATIVE_APP_PATTERN = "NATIVE_APP";

    public static WebDriver unpackWebDriverFromSearchContext(
            SearchContext searchContext) {
        WebDriver driver = null;
        if (searchContext instanceof WebDriver)
            return (WebDriver) searchContext;

        // Search context it is not only Webdriver. Webelement is search context
        // too.
        // RemoteWebElement and MobileElement implement WrapsDriver
        if (searchContext instanceof WrapsElement)
            return unpackWebDriverFromSearchContext(((WrapsElement) searchContext)
                    .getWrappedElement());

        if (searchContext instanceof WrapsDriver)
            return unpackWebDriverFromSearchContext(((WrapsDriver) searchContext)
                    .getWrappedDriver());

        return driver;
    }

    public static String getPlatform(WebDriver driver){
        if (driver == null)
            return null;

        Class<?> driverClass = driver.getClass();
        if (AndroidDriver.class.isAssignableFrom(driverClass))
            return MobilePlatform.ANDROID;

        if (IOSDriver.class.isAssignableFrom(driverClass))
            return MobilePlatform.IOS;

        //it is possible that somebody uses RemoteWebDriver or their
        //own WebDriver implementation. At this case capabilities are used
        //to detect platform
        if (HasCapabilities.class.isAssignableFrom(driverClass))
            return String.valueOf(((HasCapabilities) driver).getCapabilities().
                    getCapability(MobileCapabilityType.PLATFORM_NAME));

        return null;
    }

    public static String getAutomation(WebDriver driver){
        if (driver == null)
            return null;

        if (HasCapabilities.class.isAssignableFrom(driver.getClass()))
            return String.valueOf(((HasCapabilities) driver).getCapabilities().
                    getCapability(MobileCapabilityType.AUTOMATION_NAME));

        return null;
    }

    public static ContentType getCurrentContentType(SearchContext context){
        WebDriver driver = WebDriverUnpackUtility.unpackWebDriverFromSearchContext(context);
        if (!ContextAware.class.isAssignableFrom(driver.getClass())){ //it is desktop browser
            return ContentType.HTML_OR_DEFAULT;
        }

        ContextAware contextAware = ContextAware.class.cast(driver);
        String currentContext = contextAware.getContext();
        if (currentContext.contains(NATIVE_APP_PATTERN))
            return ContentType.NATIVE_MOBILE_SPECIFIC;

        return ContentType.HTML_OR_DEFAULT;
    }
}
