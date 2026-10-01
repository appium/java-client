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

package io.appium.java_client.support.pagefactory.internal;

import io.appium.java_client.support.pagefactory.ElementLocator;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Adapted from Selenium's
 * {@code org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler}
 * (Apache License 2.0).
 */
public class LocatingElementHandler implements InvocationHandler {
    private final ElementLocator locator;

    public LocatingElementHandler(ElementLocator locator) {
        this.locator = locator;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
        WebElement element;
        try {
            element = locator.findElement();
        } catch (NoSuchElementException e) {
            if ("toString".equals(method.getName())) {
                return "Proxy element for: " + locator;
            }
            throw e;
        }

        if ("getWrappedElement".equals(method.getName())) {
            return element;
        }

        try {
            return method.invoke(element, objects);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
