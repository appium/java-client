package io.appium.java_client.pagefactory;

import io.appium.java_client.remote.MobileCapabilityType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;

class AppiumElementLocator implements ElementLocator {
	
	// This function waits for not empty element list using all defined by
	private static class WaitingFunction implements
			Function<By, List<WebElement>> {
		private final SearchContext searchContext;

		private WaitingFunction(SearchContext searchContext) {
			this.searchContext = searchContext;
		}

		public List<WebElement> apply(By by) {
			List<WebElement> result = new ArrayList<WebElement>();
			try {
				result.addAll(searchContext.findElements(by));
			} catch (StaleElementReferenceException ignored) {}
			if (result.size() > 0) {
				return result;
			} else {
				return null;
			}
		}
	}

	private final SearchContext searchContext;
	private final boolean shouldCache;
	private final By by;
	private WebElement cachedElement;
	private List<WebElement> cachedElementList;
	
	private final TimeOutContainer timeOutContainer;

	/**
	 * Creates a new mobile element locator. It instantiates {@link WebElement}
	 * using @AndroidFindBy (-s), @iOSFindBy (-s) and @FindBy (-s) annotation sets
	 * 
	 * @param searchContext
	 *            The context to use when finding the element
	 * @param field
	 *            The field on the Page Object that will hold the located value
	 */
	AppiumElementLocator(SearchContext searchContext, Field field,
			TimeOutContainer timeOutContainer) {
		this.searchContext = searchContext;
		// All known webdrivers implement HasCapabilities
		Capabilities capabilities = ((HasCapabilities) unpackWebDriverFromSearchContext()).
				getCapabilities();
				
		String platform = String
				.valueOf(capabilities.getCapability(
								MobileCapabilityType.PLATFORM_NAME));
		String automation = String
				.valueOf(capabilities.getCapability(
								MobileCapabilityType.AUTOMATION_NAME));
		
		String browser = (String) capabilities.getCapability(CapabilityType.BROWSER_NAME);		
		String app = (String) capabilities.getCapability(MobileCapabilityType.APP);
		
		boolean isBrowser = ((app == null || "".equals(app.trim())) && 
				(browser != null && !"".equals(browser.trim())));
                
		AppiumAnnotations annotations = new AppiumAnnotations(field, 
				platform, automation, isBrowser);
		this.timeOutContainer = timeOutContainer;
		shouldCache = annotations.isLookupCached();
		by = annotations.buildBy();
	}
	
	private WebDriver unpackWebDriverFromSearchContext(){
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
	
	private void changeImplicitlyWaitTimeOut(long newTimeOut, TimeUnit newTimeUnit){
		unpackWebDriverFromSearchContext().manage().timeouts().implicitlyWait(newTimeOut, newTimeUnit);	
	}
	
	//This method waits for not empty element list using all defined by
	private List<WebElement> waitFor(){
		//When we use complex By strategies (like ChainedBy or ByAll)
		//there are some problems (StaleElementReferenceException, implicitly wait time out
		//for each chain By section, etc)
		try{
			changeImplicitlyWaitTimeOut(0, TimeUnit.SECONDS);
			FluentWait<By> wait = new FluentWait<By>(by);
			wait.withTimeout(timeOutContainer.getTimeValue(), timeOutContainer.getTimeUnitValue());	
			return wait.until(new WaitingFunction(searchContext));
		}
		catch (TimeoutException e){
			return new ArrayList<WebElement>();
		}
		finally{
			changeImplicitlyWaitTimeOut(timeOutContainer.getTimeValue(), 
					timeOutContainer.getTimeUnitValue());
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
		if (result.size() == 0){
			String message = "Can't locate an element by this strategy: " + by.toString();
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
