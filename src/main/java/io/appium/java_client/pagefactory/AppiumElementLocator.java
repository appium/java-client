package io.appium.java_client.pagefactory;

import io.appium.java_client.remote.MobileCapabilityType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;
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
	
	private final long implicitlyWaitTimeOut;
	private final TimeUnit timeUnit ;

	/**
	 * Creates a new mobile element locator. It instantiates {@link WebElement}
	 * using @AndroidFindBy (-s), @iOSFindBy (-s) and @FindBy (-s) annotation sets
	 * 
	 * @param searchContext
	 *            The context to use when finding the element
	 * @param field
	 *            The field on the Page Object that will hold the located value
	 */
	public AppiumElementLocator(SearchContext searchContext, Field field,
			long implicitlyWaitTimeOut, TimeUnit timeUnit) {
		this.searchContext = searchContext;
		// All known webdrivers implement HasCapabilities
		String platform = String
				.valueOf(((HasCapabilities) unpackWebDriverFromSearchContext())
						.getCapabilities().getCapability(
								MobileCapabilityType.PLATFORM_NAME));
		AppiumAnnotations annotations = new AppiumAnnotations(field, platform);
		this.implicitlyWaitTimeOut = implicitlyWaitTimeOut;
		this.timeUnit = timeUnit;
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
			wait.withTimeout(implicitlyWaitTimeOut, timeUnit);	
			return wait.until(new WaitingFunction(searchContext));
		}
		catch (TimeoutException e){
			return new ArrayList<WebElement>();
		}
		finally{
			changeImplicitlyWaitTimeOut(implicitlyWaitTimeOut, timeUnit);
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
			String message = "Cann't locate an element by this strategy: " + by.toString();			
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
