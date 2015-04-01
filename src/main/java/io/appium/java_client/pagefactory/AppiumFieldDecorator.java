package io.appium.java_client.pagefactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchableElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSElement;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

/**
 * Default decorator for use with PageFactory. Will decorate 1) all of the
 * WebElement fields and 2) List<WebElement> fields that have
 * {@literal @AndroidFindBy}, {@literal @AndroidFindBys}, or
 * {@literal @iOSFindBy/@iOSFindBys} annotation with a proxy that locates the
 * elements using the passed in ElementLocatorFactory.
 * 
 * Please pay attention: fields of {@link WebElement}, {@link RemoteWebElement},
 * {@link MobileElement}, {@link AndroidElement} and {@link IOSElement} are allowed 
 * to use with this decorator
 */
public class AppiumFieldDecorator implements FieldDecorator, ResetsImplicitlyWaitTimeOut {
	
	private static final List<Class<? extends WebElement>> availableElementClasses = 
			new ArrayList<Class<? extends WebElement>>(){
				private static final long serialVersionUID = 1L;
				{
					add(WebElement.class);
					add(TouchableElement.class);
					add(RemoteWebElement.class);
					add(MobileElement.class);
					add(AndroidElement.class);
					add(IOSElement.class);
				}
		
	};
	
	private final AppiumElementLocatorFactory factory;

	public static long DEFAULT_IMPLICITLY_WAIT_TIMEOUT = 1;

	public static TimeUnit DEFAULT_TIMEUNIT = TimeUnit.SECONDS;

	public AppiumFieldDecorator(SearchContext context, long implicitlyWaitTimeOut, TimeUnit timeUnit) {
		factory = new AppiumElementLocatorFactory(context, implicitlyWaitTimeOut, timeUnit);
	}
	
	public AppiumFieldDecorator(SearchContext context) {
		factory = new AppiumElementLocatorFactory(context);
	}

	public Object decorate(ClassLoader ignored, Field field) {
		if (!(availableElementClasses.contains(field.getType()) || isDecoratableList(field))) {
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

	private static boolean isAvailableElementClass(Type type){	
		boolean result = false;
		for (Class<? extends WebElement> webElementClass: 
			availableElementClasses){
			if (!webElementClass.equals(type)){
				continue;
			}
			result = true;
			break;
		}
		return result;
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
		if (listType instanceof ParameterizedType){
			listType = ((ParameterizedType) listType).getRawType();
		}
		return isAvailableElementClass(listType);		
		//if there is no annotation list is supposed to be found by org.openqa.selenium.support.ByIdOrName
		//DefaultElementLocator has an issue :)
	}

	private Object proxyForLocator(Field field, ElementLocator locator) {
		Class<?> type = field.getType();
		if (type.equals(WebElement.class)){
			type = RemoteWebElement.class;
		}
		if (type.equals(TouchableElement.class)){
			type = MobileElement.class;
		}
		ElementInterceptor elementInterceptor = new ElementInterceptor(locator);
		return ProxyFactory.getEnhancedProxy(type,
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
