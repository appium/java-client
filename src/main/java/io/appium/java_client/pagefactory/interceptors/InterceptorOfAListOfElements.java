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

package io.appium.java_client.pagefactory.interceptors;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class InterceptorOfAListOfElements implements MethodInterceptor {
    protected final ElementLocator locator;

    public InterceptorOfAListOfElements(ElementLocator locator) {
        this.locator = locator;
    }

    protected abstract Object getObject(List<WebElement> elements, Method method, Object[] args)
        throws InvocationTargetException, IllegalAccessException, InstantiationException, Throwable;

    /**
     * Look at
     * {@link net.sf.cglib.proxy.MethodInterceptor#intercept(Object, Method, Object[], MethodProxy)}
     */
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
        throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return proxy.invokeSuper(obj, args);
        }

        ArrayList<WebElement> realElements = new ArrayList<WebElement>();
        realElements.addAll(locator.findElements());
        return getObject(realElements, method, args);
    }
}
