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

package io.appium.java_client.pagefactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.*;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;

class AppiumElementLocator implements ElementLocator {

	// This function waits for not empty element list using all defined by
	private static class WaitingFunction implements
			Function<By, List<WebElement>> {
		private final SearchContext searchContext;
        private final static String INVALID_SELECTOR_PATTERN = "Invalid locator strategy:";

		private WaitingFunction(SearchContext searchContext) {
			this.searchContext = searchContext;
		}

		public List<WebElement> apply(By by) {
			List<WebElement> result = new ArrayList<WebElement>();
			try {
				result.addAll(searchContext.findElements(by));
			} catch (StaleElementReferenceException ignored) {
			}
            catch (RuntimeException e){
                if (!isInvalidSelectorRootCause(e))
                    throw e;
            }
			if (result.size() > 0) {
				return result;
			} else {
				return null;
			}
		}

        private static boolean isInvalidSelectorRootCause(Throwable e){
            if (e == null)
                return false;

            if (String.valueOf(e.getMessage()).contains(INVALID_SELECTOR_PATTERN))
                return true;

            return isInvalidSelectorRootCause(e.getCause());
        }
	}

	private final SearchContext searchContext;
	private final boolean shouldCache;
	private final By by;
	private WebElement cachedElement;
	private List<WebElement> cachedElementList;

	private final TimeOutDuration timeOutDuration;

	/**
	 * Creates a new mobile element locator. It instantiates {@link WebElement}
	 * using @AndroidFindBy (-s), @iOSFindBy (-s) and @FindBy (-s) annotation
	 * sets
	 * 
	 * @param searchContext
	 *            The context to use when finding the element
	 * @param field
	 *            The field on the Page Object that will hold the located value
	 */
	AppiumElementLocator(SearchContext searchContext, Field field,
			TimeOutDuration timeOutDuration) {
		this.searchContext = searchContext;

		String platform = getPlatform();
		String automation = getAutomation();

		AppiumAnnotations annotations = new AppiumAnnotations(field, platform,
				automation);
        if (field.isAnnotationPresent(WithTimeout.class)){
            WithTimeout withTimeout = field.getAnnotation(WithTimeout.class);
            this.timeOutDuration = new TimeOutDuration(withTimeout.time(), withTimeout.unit());
        }
        else
		    this.timeOutDuration = timeOutDuration;
		shouldCache = annotations.isLookupCached();
		by = annotations.buildBy();
	}

    private String getPlatform(){
        WebDriver d = WebDriverUnpackUtility.
                unpackWebDriverFromSearchContext(this.searchContext);
        if (d == null)
            return null;

        Class<?> driverClass = d.getClass();
        if (AndroidDriver.class.isAssignableFrom(driverClass))
            return MobilePlatform.ANDROID;

        if (IOSDriver.class.isAssignableFrom(driverClass))
            return MobilePlatform.IOS;

        //it is possible that somebody uses RemoteWebDriver or their
        //own WebDriver implementation. At this case capabilities are used
        //to detect platform
        if (HasCapabilities.class.isAssignableFrom(driverClass))
            return String.valueOf(((HasCapabilities) d).getCapabilities().
                    getCapability(MobileCapabilityType.PLATFORM_NAME));

        return null;
    }

    private String getAutomation(){
        WebDriver d = WebDriverUnpackUtility.
                unpackWebDriverFromSearchContext(this.searchContext);
        if (d == null)
            return null;

        if (HasCapabilities.class.isAssignableFrom(d.getClass()))
            return String.valueOf(((HasCapabilities) d).getCapabilities().
                    getCapability(MobileCapabilityType.AUTOMATION_NAME));

        return null;
    }

	private void changeImplicitlyWaitTimeOut(long newTimeOut,
			TimeUnit newTimeUnit) {
		WebDriverUnpackUtility.unpackWebDriverFromSearchContext(searchContext)
				.manage().timeouts().implicitlyWait(newTimeOut, newTimeUnit);
	}

	// This method waits for not empty element list using all defined by
	private List<WebElement> waitFor() {
		// When we use complex By strategies (like ChainedBy or ByAll)
		// there are some problems (StaleElementReferenceException, implicitly
		// wait time out
		// for each chain By section, etc)
		try {
			changeImplicitlyWaitTimeOut(0, TimeUnit.SECONDS);
			FluentWait<By> wait = new FluentWait<By>(by);
			wait.withTimeout(timeOutDuration.getTime(),
					timeOutDuration.getTimeUnit());
			return wait.until(new WaitingFunction(searchContext));
		} catch (TimeoutException e) {
			return new ArrayList<WebElement>();
		} finally {
			changeImplicitlyWaitTimeOut(timeOutDuration.getTime(),
					timeOutDuration.getTimeUnit());
		}
	}

	/**
	 * Find the element.
	 */
	public WebElement findElement() {
		if (cachedElement != null && shouldCache) {
			return cachedElement;
		}
		List<WebElement> result = waitFor();
		if (result.size() == 0) {
			String message = "Can't locate an element by this strategy: "
					+ by.toString();
			throw new NoSuchElementException(message);
		}
		if (shouldCache) {
			cachedElement = result.get(0);
		}
		return result.get(0);
	}

	/**
	 * Find the element list.
	 */
	public List<WebElement> findElements() {
		if (cachedElementList != null && shouldCache) {
			return cachedElementList;
		}
		List<WebElement> result = waitFor();
		if (shouldCache) {
			cachedElementList = result;
		}
		return result;
	}
}
