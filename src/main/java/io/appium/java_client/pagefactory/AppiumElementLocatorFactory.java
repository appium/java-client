package io.appium.java_client.pagefactory;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

class AppiumElementLocatorFactory implements ElementLocatorFactory {
    private final SearchContext searchContext;
	private final TimeOutDuration timeOutDuration;

	public AppiumElementLocatorFactory(SearchContext searchContext,
			TimeOutDuration timeOutDuration) {
		this.searchContext = searchContext;
		this.timeOutDuration = timeOutDuration;
	}
	
	public AppiumElementLocatorFactory(SearchContext searchContext) {
		this(searchContext, new TimeOutDuration(AppiumFieldDecorator.DEFAULT_IMPLICITLY_WAIT_TIMEOUT,
				AppiumFieldDecorator.DEFAULT_TIMEUNIT));
	}	

	public ElementLocator createLocator(Field field) {
		return new AppiumElementLocator(searchContext, field, timeOutDuration);
	}
}
