package io.appium.java_client.pagefactory;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

class AppiumElementLocatorFactory implements ElementLocatorFactory, ResetsImplicitlyWaitTimeOut {
    private static long DEFAULT_IMPLICITLY_WAIT_TIMEOUT = 1;
    private static TimeUnit DEFAULT_TIMEUNIT = TimeUnit.SECONDS;
	
	private final SearchContext searchContext;
	private final TimeOutContainer timeOutContainer;

	public AppiumElementLocatorFactory(SearchContext searchContext,
			long implicitlyWaitTimeOut, TimeUnit timeUnit) {
		this.searchContext = searchContext;
		this.timeOutContainer = new TimeOutContainer(implicitlyWaitTimeOut, timeUnit);
	}
	
	public AppiumElementLocatorFactory(SearchContext searchContext) {
		this(searchContext, DEFAULT_IMPLICITLY_WAIT_TIMEOUT, DEFAULT_TIMEUNIT);
	}	

	public ElementLocator createLocator(Field field) {
		return new AppiumElementLocator(searchContext, field, timeOutContainer);
	}

	@Override
	public void resetImplicitlyWaitTimeOut(long timeOut, TimeUnit timeUnit) {
		timeOutContainer.resetImplicitlyWaitTimeOut(timeOut, timeUnit);
	}
}
