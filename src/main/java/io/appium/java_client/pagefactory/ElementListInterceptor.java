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
import java.util.ArrayList;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 *
 * Intercepts requests to the list of {@link MobileElement}
 *
 */
class ElementListInterceptor implements MethodInterceptor{

	private final ElementLocator locator;
	private final String elementName;

	ElementListInterceptor(ElementLocator locator, String name) {
		this.locator = locator;
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

		ArrayList<WebElement> realElements = new ArrayList<WebElement>();
		realElements.addAll(locator.findElements());
		return method.invoke(realElements, args);
	}

}
