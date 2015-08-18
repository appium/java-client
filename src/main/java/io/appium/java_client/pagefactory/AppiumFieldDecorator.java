/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
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

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import static io.appium.java_client.pagefactory.AppiumElementUtils.getGenericParameterClass;

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
		ElementLocator locator;
		String name = getName(field);
		if (AppiumElementUtils.isDecoratableElement(field)) {
			locator = factory.createLocator(field);
			return proxyForLocator(getTypeForProxy(), locator, name);
		}
		if (AppiumElementUtils.isDecoratableList(field)) {
			locator = factory.createLocator(field);
			return proxyForListLocator(locator, name);
		}
		if(AppiumElementUtils.isDecoratableMobileElement(field)) {
			locator = factory.createLocator(field.getType());
			for (Class annotation : Annotations.FIND_BY) {
				if (field.isAnnotationPresent(annotation)) {
					locator = factory.createLocator(field);
					break;
				}
			}
			MobileElement element = (MobileElement) proxyForLocator(field.getType(), locator, name);
			PageFactory.initElements(new AppiumFieldDecorator(element), element);
			return element;
		}
		if(AppiumElementUtils.isDecoratableMobileElementsList(field)) {
			Class<? extends MobileElement> clazz = AppiumElementUtils.getGenericParameterClass(field);
			locator = factory.createLocator(clazz);
			return proxyForListLocator(clazz, locator, name);
		}
		return null;
	}

	private String getName(Field field) {
		if(field.isAnnotationPresent(Name.class)) {
			return field.getAnnotation(Name.class).value();
		}
		if(field.getType().isAnnotationPresent(Name.class)) {
			return field.getType().getAnnotation(Name.class).value();
		}
		if(AppiumElementUtils.isDecoratableList(field)) {
			Class<?> clazz = getGenericParameterClass(field);
			if(clazz.isAnnotationPresent(Name.class)) {
				return clazz.getAnnotation(Name.class).value();
			}
		}
		return field.getName();
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
        return RemoteWebElement.class; // будет всегда возвращать у дочерних элементов, потому-что левый класс не привести к AndroidElement
    }

	@SuppressWarnings("unchecked")
	private Object proxyForLocator(Class<?> clazz, ElementLocator locator, String name) {
		ElementInterceptor elementInterceptor = new ElementInterceptor(clazz, locator, name);
		return ProxyFactory.getEnhancedProxy(clazz, elementInterceptor);
	}
	
	@SuppressWarnings("unchecked")
	private List<? extends WebElement> proxyForListLocator(ElementLocator locator, String name) {
		ElementListInterceptor elementInterceptor = new ElementListInterceptor(locator, name);
		return ProxyFactory.getEnhancedProxy(ArrayList.class, elementInterceptor);
	}

	@SuppressWarnings("unchecked")
	private <T extends MobileElement> List<T> proxyForListLocator(Class<T> clazz, ElementLocator locator, String name) {
		MobileElementListInterceptor elementInterceptor = new MobileElementListInterceptor<>(clazz, locator, name);
		return ProxyFactory.getEnhancedProxy(ArrayList.class, elementInterceptor);
	}
}
