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
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Default decorator for use with PageFactory. Will decorate 1) all the WebElement fields and 2)
 * {@code List<WebElement>} fields that have {@code @FindBy}, {@code @FindBys}, or {@code @FindAll}
 * annotation with a proxy that locates the elements using the passed in ElementLocatorFactory.
 * Adapted from Selenium's {@code org.openqa.selenium.support.pagefactory.DefaultFieldDecorator}
 * (Apache License 2.0).
 */
public class DefaultFieldDecorator implements FieldDecorator {

    /** The factory of element locators. */
    protected ElementLocatorFactory factory;

    /**
     * Creates a new decorator.
     *
     * @param factory the factory of element locators
     */
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

    /**
     * Checks whether the field is a list of {@link WebElement} with a locator annotation.
     *
     * @param field the field to check
     * @return true if the field can be decorated
     */
    protected boolean isDecoratableList(Field field) {
        if (!List.class.isAssignableFrom(field.getType())) {
            return false;
        }

        // Type erasure in Java isn't complete. Attempt to discover the generic type of the list.
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return false;
        }

        Type[] typeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
        if (typeArguments.length == 0 || !isWebElementType(typeArguments[0], new HashSet<>())) {
            return false;
        }

        return field.getAnnotation(FindBy.class) != null
                || field.getAnnotation(FindBys.class) != null
                || field.getAnnotation(FindAll.class) != null;
    }

    private static boolean isWebElementType(Type type, Set<Type> visited) {
        if (!visited.add(type)) {
            return false;
        }
        if (WebElement.class.equals(type)) {
            return true;
        }
        if (type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type;
            if (wildcardType.getLowerBounds().length == 0) {
                for (Type bound : wildcardType.getUpperBounds()) {
                    if (isWebElementType(bound, visited)) {
                        return true;
                    }
                }
            }
        } else if (type instanceof TypeVariable<?>) {
            for (Type bound : ((TypeVariable<?>) type).getBounds()) {
                if (isWebElementType(bound, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Creates a proxy of an element found by the locator.
     *
     * @param loader the class loader of the proxy
     * @param locator the element locator
     * @return the element proxy
     */
    protected WebElement proxyForLocator(ClassLoader loader, ElementLocator locator) {
        InvocationHandler handler = new LocatingElementHandler(locator);
        return (WebElement) Proxy.newProxyInstance(
                loader, new Class[]{WebElement.class, WrapsElement.class, Locatable.class}, handler);
    }

    /**
     * Creates a proxy of a list of elements found by the locator.
     *
     * @param loader the class loader of the proxy
     * @param locator the element locator
     * @return the list proxy
     */
    @SuppressWarnings("unchecked")
    protected List<WebElement> proxyForListLocator(ClassLoader loader, ElementLocator locator) {
        InvocationHandler handler = new LocatingElementListHandler(locator);
        return (List<WebElement>) Proxy.newProxyInstance(loader, new Class[]{List.class}, handler);
    }
}
