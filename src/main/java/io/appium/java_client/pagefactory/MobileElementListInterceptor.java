package io.appium.java_client.pagefactory;

import io.appium.java_client.MobileElement;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static io.appium.java_client.pagefactory.WebDriverUnpackUtility.unpackWebDriverFromSearchContext;

/**
 *
 * Intercepts requests to the list of {@link MobileElement}
 *
 */
class MobileElementListInterceptor<T extends MobileElement> implements MethodInterceptor{

	private final ElementLocator locator;
	private final Class<T> elementClass;
	private final String elementName;

	MobileElementListInterceptor(Class<T> clazz, ElementLocator locator, String name){
		this.locator = locator;
		this.elementClass = clazz;
		this.elementName = name;
	}

	public Object intercept(Object obj, Method method, Object[] args,
							MethodProxy proxy) throws Throwable {
		if(Object.class.getDeclaredMethod("finalize").equals(method)){
			return proxy.invokeSuper(obj, args);  //invokes .finalize of the proxy-object
		}
		if(Object.class.getDeclaredMethod("toString").equals(method)){
			return elementName;
		}

		ArrayList<T> realElements = new ArrayList<>();
		for(WebElement element : locator.findElements()) {
			T instance = elementClass.newInstance();
			if(RemoteWebElement.class.isAssignableFrom(element.getClass())) {
				instance.setId(((RemoteWebElement) element).getId());
				instance.setParent((RemoteWebDriver) unpackWebDriverFromSearchContext(element));
			}
			PageFactory.initElements(new AppiumFieldDecorator(element), instance);
			realElements.add(instance);
		}
		return method.invoke(realElements, args);
	}

}

