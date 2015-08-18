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

import java.lang.reflect.Method;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import static io.appium.java_client.pagefactory.WebDriverUnpackUtility.unpackWebDriverFromSearchContext;

/**
 * Intercepts requests to {@link MobileElement}
 *
 */
class ElementInterceptor<T extends MobileElement> implements MethodInterceptor {
    private final ElementLocator locator;
	private final Class<T> elementClass;
	private final String elementName;

	private WebElement element;
	
	ElementInterceptor(Class<T> clazz, ElementLocator locator, String name) {
		this.elementClass = clazz;
		this.locator = locator;
		this.elementName = name;
	}
	
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
        if(Object.class.getDeclaredMethod("finalize").equals(method)){
            return proxy.invokeSuper(obj, args);  //invokes .finalize of the proxy-object
        }
        if("toString".equals(method.getName())){
            return elementName;
        }
		try {
			if("getWrappedDriver".equals(method.getName())) {
				element.getSize();
			}
			Object result = method.invoke(element, args);
			if("isDisplayed".equals(method.getName()) && result.equals(false)) {
				element.getSize();
			}
			return result;
		} catch (Exception e) {
			Object objToInvokeOn;
			element = locator.findElement();
			if (AppiumElementUtils.isDecoratableElement(method.getDeclaringClass())) {
				objToInvokeOn = element;
			} else {
				T instance = elementClass.newInstance();
				if (RemoteWebElement.class.isAssignableFrom(element.getClass())) {
					instance.setId(((RemoteWebElement) element).getId());
					instance.setParent((RemoteWebDriver) unpackWebDriverFromSearchContext(element));
				}
				PageFactory.initElements(new AppiumFieldDecorator(element), instance);
				objToInvokeOn = instance;
			}
			return method.invoke(objToInvokeOn, args);
		}
	}

}
