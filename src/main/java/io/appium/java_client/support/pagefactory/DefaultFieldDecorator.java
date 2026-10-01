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

package io.appium.java_client.support.pagefactory;

import io.appium.java_client.support.FindAll;
import io.appium.java_client.support.FindBy;
import io.appium.java_client.support.FindBys;
import io.appium.java_client.support.pagefactory.internal.LocatingElementHandler;
import io.appium.java_client.support.pagefactory.internal.LocatingElementListHandler;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.interactions.Locatable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Default decorator for use with PageFactory. Will decorate 1) all the WebElement fields and 2)
 * {@code List<WebElement>} fields that have {@code @FindBy}, {@code @FindBys}, or {@code @FindAll}
 * annotation with a proxy that locates the elements using the passed in ElementLocatorFactory.
 * Adapted from Selenium's {@code org.openqa.selenium.support.pagefactory.DefaultFieldDecorator}
 * (Apache License 2.0).
 */
public class DefaultFieldDecorator implements FieldDecorator {

    protected ElementLocatorFactory factory;

    public DefaultFieldDecorator(ElementLocatorFactory factory) {
        this.factory = factory;
    }

    @Nullable
    @Override
    public Object decorate(ClassLoader loader, Field field) {
        if (!(WebElement.class.isAssignableFrom(field.getType()) || isDecoratableList(field))) {
            return null;
        }

        ElementLocator locator = factory.createLocator(field);
        if (locator == null) {
            return null;
        }

        if (WebElement.class.isAssignableFrom(field.getType())) {
            return proxyForLocator(loader, locator);
        } else if (List.class.isAssignableFrom(field.getType())) {
            return proxyForListLocator(loader, locator);
        } else {
            return null;
        }
    }

    protected boolean isDecoratableList(Field field) {
        if (!List.class.isAssignableFrom(field.getType())) {
            return false;
        }

        // Type erasure in Java isn't complete. Attempt to discover the generic type of the list.
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return false;
        }

        Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];

        if (!WebElement.class.equals(listType)) {
            return false;
        }

        return field.getAnnotation(FindBy.class) != null
                || field.getAnnotation(FindBys.class) != null
                || field.getAnnotation(FindAll.class) != null;
    }

    protected WebElement proxyForLocator(ClassLoader loader, ElementLocator locator) {
        InvocationHandler handler = new LocatingElementHandler(locator);
        return (WebElement) Proxy.newProxyInstance(
                loader, new Class[]{WebElement.class, WrapsElement.class, Locatable.class}, handler);
    }

    @SuppressWarnings("unchecked")
    protected List<WebElement> proxyForListLocator(ClassLoader loader, ElementLocator locator) {
        InvocationHandler handler = new LocatingElementListHandler(locator);
        return (List<WebElement>) Proxy.newProxyInstance(loader, new Class[]{List.class}, handler);
    }
}
