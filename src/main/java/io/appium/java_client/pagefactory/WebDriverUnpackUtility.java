package io.appium.java_client.pagefactory;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;

final class WebDriverUnpackUtility {

	static WebDriver unpackWebDriverFromSearchContext(SearchContext searchContext){
		WebDriver driver = null;
		if (searchContext instanceof WebDriver){
			driver = (WebDriver) searchContext;
		}		
		//Search context it is not only Webdriver. Webelement is search context too.
		//RemoteWebElement and MobileElement implement WrapsDriver
		if (searchContext instanceof WebElement){
			WebElement element = (WebElement) searchContext; //there can be something that 
			//implements WebElement interface and wraps original
			while (element instanceof WrapsElement){
				element = ((WrapsElement) element).getWrappedElement();
			}
			driver = ((WrapsDriver) element).getWrappedDriver();
		}
		return driver;
	}
}
