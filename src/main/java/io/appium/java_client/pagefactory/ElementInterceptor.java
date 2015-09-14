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

import java.lang.reflect.Method;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Intercepts requests to {@link MobileElement}
 *
 */
class ElementInterceptor implements MethodInterceptor {
    private final ElementLocator locator;
	
	ElementInterceptor(ElementLocator locator) {
		this.locator = locator;
	}
	
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
        if(Object.class.getDeclaredMethod("finalize").equals(method)){
            return proxy.invokeSuper(obj, args);  //invokes .finalize of the proxy-object
        }

		WebElement realElement = locator.findElement();
		return method.invoke(realElement, args);
	}

}
