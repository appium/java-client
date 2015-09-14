/*
 * Copyright 2015 Appium Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
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

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;

final class WebDriverUnpackUtility {

	static WebDriver unpackWebDriverFromSearchContext(
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
}
