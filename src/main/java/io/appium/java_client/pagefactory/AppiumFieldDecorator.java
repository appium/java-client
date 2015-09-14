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

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchableElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
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
public class AppiumFieldDecorator implements FieldDecorator {
	
	private static final List<Class<? extends WebElement>> availableElementClasses = 
			new ArrayList<Class<? extends WebElement>>(){
				private static final long serialVersionUID = 1L;
				{
					add(WebElement.class);
					add(RemoteWebElement.class);
					add(MobileElement.class);
                    add(TouchableElement.class);
					add(AndroidElement.class);
					add(IOSElement.class);
				}
		
	};

    private final static Map<Class<? extends SearchContext>, Class<? extends WebElement>> elementRuleMap =
            new HashMap<Class<? extends SearchContext>, Class<? extends WebElement>>(){
                private static final long serialVersionUID = 1L;
                {
                    put(AndroidDriver.class, AndroidElement.class);
                    put(AndroidElement.class, AndroidElement.class);
                    put(IOSDriver.class, IOSElement.class);
                    put(IOSElement.class, IOSElement.class);
                }
            };
	
	private final AppiumElementLocatorFactory factory;
    private final SearchContext context;
	public static long DEFAULT_IMPLICITLY_WAIT_TIMEOUT = 1;

	public static TimeUnit DEFAULT_TIMEUNIT = TimeUnit.SECONDS;

	public AppiumFieldDecorator(SearchContext context, long implicitlyWaitTimeOut, TimeUnit timeUnit) {
        this.context = context;
		factory = new AppiumElementLocatorFactory(this.context, new TimeOutDuration(implicitlyWaitTimeOut, timeUnit));
	}

    public AppiumFieldDecorator(SearchContext context, TimeOutDuration timeOutDuration) {
        this.context = context;
        factory = new AppiumElementLocatorFactory(this.context, timeOutDuration);
    }
	
	public AppiumFieldDecorator(SearchContext context) {
        this.context = context;
		factory = new AppiumElementLocatorFactory(this.context);
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
			return proxyForLocator(locator);
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
		return isAvailableElementClass(listType);		
		//if there is no annotation list is supposed to be found by org.openqa.selenium.support.ByIdOrName
		//DefaultElementLocator has an issue :)
	}

    private Class<?> getTypeForProxy(){
        Class<?> contextClass = context.getClass();
        Iterable<Map.Entry<Class<? extends SearchContext>, Class<? extends WebElement>>> rules = elementRuleMap.entrySet();
        Iterator<Map.Entry<Class<? extends SearchContext>, Class<? extends WebElement>>> iterator = rules.iterator();
        while (iterator.hasNext()){ //it will return MobileElement subclass when here is something
            //that extends AppiumDriver or MobileElement
            Map.Entry<Class<? extends SearchContext>, Class<? extends WebElement>> e = iterator.next();
            if (e.getKey().isAssignableFrom(contextClass))
                return e.getValue();
        } //it is compatible with desktop browser. So at this case it returns RemoteWebElement.class
        return RemoteWebElement.class;
    }

	private Object proxyForLocator(ElementLocator locator) {
		ElementInterceptor elementInterceptor = new ElementInterceptor(locator);
		return ProxyFactory.getEnhancedProxy(getTypeForProxy(),
				elementInterceptor);
	}
	
	@SuppressWarnings("unchecked")
	private List<WebElement> proxyForListLocator(
			ElementLocator locator) {
		ElementListInterceptor elementInterceptor = new ElementListInterceptor(locator);
		return ProxyFactory.getEnhancedProxy(ArrayList.class,
				elementInterceptor);
	}
}
