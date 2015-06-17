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
