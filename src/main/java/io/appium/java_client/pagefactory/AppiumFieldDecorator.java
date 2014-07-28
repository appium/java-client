package io.appium.java_client.pagefactory;

import io.appium.java_client.MobileElement;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

/**
 * Default decorator for use with PageFactory. Will decorate 1) all of the
 * WebElement fields and 2) List<WebElement> fields that have
 * {@literal @AndroidFindBy}, {@literal @AndroidFindBys}, or
 * {@literal @iOSFindBy/@iOSFindBys} annotation with a proxy that locates the
 * elements using the passed in ElementLocatorFactory.
 * 
 * Please pay attention: fields of {@link WebElement}, {@link RemoteWebElement} and
 * {@link MobileElement} are allowed to use with this decorator
 */
public class AppiumFieldDecorator implements FieldDecorator, ResetsImplicitlyWaitTimeOut {
	private final AppiumElementLocatorFactory factory;

	public AppiumFieldDecorator(SearchContext context, long implicitlyWaitTimeOut, TimeUnit timeUnit) {
		factory = new AppiumElementLocatorFactory(context, implicitlyWaitTimeOut, timeUnit);
	}
	
	public AppiumFieldDecorator(SearchContext context) {
		factory = new AppiumElementLocatorFactory(context);
	}

	public Object decorate(ClassLoader ignored, Field field) {
		if (!(WebElement.class.isAssignableFrom(field.getType()) || isDecoratableList(field))) {
			return null;
		}

		ElementLocator locator = factory.createLocator(field);
		if (locator == null) {
			return null;
		}

		if (WebElement.class.isAssignableFrom(field.getType())) {
			return proxyForLocator(field, locator); 
		} else if (List.class.isAssignableFrom(field.getType())) {
			return  proxyForListLocator(locator);
		} else {
			return null;
		}
	}

	private boolean isDecoratableList(Field field) {
		if (!List.class.isAssignableFrom(field.getType())) {
			return false;
		}

		// Type erasure in Java isn't complete. Attempt to discover the generic
		// type of the list.
		Type genericType = field.getGenericType();
		if (!(genericType instanceof ParameterizedType)) {
			return false;
		}

		Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];

		if (!WebElement.class.equals(listType) && RemoteWebElement.class.equals(listType) 
				&& MobileElement.class.equals(listType)) {
			return false;
		}


		if (field.getAnnotation(AndroidFindBy.class) == null
				&& field.getAnnotation(iOSFindBy.class) == null
				&& field.getAnnotation(AndroidFindBys.class) == null
			    && field.getAnnotation(iOSFindBys.class) == null
			    && field.getAnnotation(FindBy.class) == null
			    && field.getAnnotation(FindBys.class) == null
			    && field.getAnnotation(FindAll.class) == null){
			return false;
		}

		return true;
	}

	private Object proxyForLocator(Field field, ElementLocator locator) {
		ElementInterceptor elementInterceptor = new ElementInterceptor(locator);
		return ProxyFactory.getEnhancedProxy(field.getType(),
				elementInterceptor);
	}
	
	@SuppressWarnings("unchecked")
	private List<WebElement> proxyForListLocator(
			ElementLocator locator) {
		ElementListInterceptor elementInterceptor = new ElementListInterceptor(locator);
		return ProxyFactory.getEnhancedProxy(ArrayList.class,
				elementInterceptor);
	}

	@Override
	public void resetImplicitlyWaitTimeOut(long timeOut, TimeUnit timeUnit) {
		factory.resetImplicitlyWaitTimeOut(timeOut, timeUnit);		
	}
}
