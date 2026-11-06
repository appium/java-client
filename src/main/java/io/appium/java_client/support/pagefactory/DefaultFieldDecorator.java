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
import io.appium.java_client.support.PageFactory;
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
import java.util.ArrayList;
import java.util.List;

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
        if (!resolveListElementClasses(field).contains(WebElement.class)) {
            return false;
        }

        return field.getAnnotation(FindBy.class) != null
                || field.getAnnotation(FindBys.class) != null
                || field.getAnnotation(FindAll.class) != null;
    }

    /**
     * Resolves the candidate element classes for a {@link List} field, unwrapping parameterized
     * types, upper- and lower-bounded wildcards ({@code ? extends T} and {@code ? super T}),
     * and type variables ({@code T}).
     *
     * <p>When a type variable is declared on a generic superclass of the page currently being
     * initialized via {@link PageFactory#initElements(FieldDecorator, Object)}, the concrete
     * type argument supplied by the runtime page subclass ({@link PageFactory#getCurrentPageClass()})
     * is resolved. If the generic page class is instantiated directly with erased type parameters
     * or outside {@link PageFactory#initElements(FieldDecorator, Object)}, resolution falls back
     * to the type variable's declared upper bounds.
     *
     * @param field the field to inspect
     * @return the resolved candidate element classes, or an empty list if the field is not a parameterized list
     */
    public static List<Class<?>> resolveListElementClasses(Field field) {
        if (!List.class.isAssignableFrom(field.getType())) {
            return List.of();
        }

        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return List.of();
        }

        Type[] typeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
        if (typeArguments.length == 0) {
            return List.of();
        }

        List<Class<?>> resolved = new ArrayList<>(1);
        collectElementClasses(typeArguments[0], PageFactory.getCurrentPageClass(), resolved);
        return resolved;
    }

    private static void collectElementClasses(
            Type type,
            @Nullable Class<?> pageClass,
            List<Class<?>> resolved
    ) {
        if (type instanceof Class<?>) {
            resolved.add((Class<?>) type);
        } else if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            if (rawType instanceof Class<?>) {
                resolved.add((Class<?>) rawType);
            }
        } else if (type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type;
            Type[] lowerBounds = wildcardType.getLowerBounds();
            Type[] bounds = lowerBounds.length > 0 ? lowerBounds : wildcardType.getUpperBounds();
            for (Type bound : bounds) {
                collectElementClasses(bound, pageClass, resolved);
            }
        } else if (type instanceof TypeVariable<?>) {
            TypeVariable<?> typeVariable = (TypeVariable<?>) type;
            Type resolvedArg = resolveTypeVariable(typeVariable, pageClass);
            if (resolvedArg != null && !(resolvedArg instanceof TypeVariable<?>)) {
                collectElementClasses(resolvedArg, pageClass, resolved);
            } else {
                TypeVariable<?> targetVar = resolvedArg instanceof TypeVariable<?>
                        ? (TypeVariable<?>) resolvedArg
                        : typeVariable;
                for (Type bound : targetVar.getBounds()) {
                    collectElementClasses(bound, pageClass, resolved);
                }
            }
        }
    }

    @Nullable
    private static Type resolveTypeVariable(TypeVariable<?> typeVariable, @Nullable Class<?> pageClass) {
        if (!(typeVariable.getGenericDeclaration() instanceof Class<?>)) {
            return null;
        }
        Class<?> declaringClass = (Class<?>) typeVariable.getGenericDeclaration();
        if (pageClass == null || !declaringClass.isAssignableFrom(pageClass) || declaringClass.equals(pageClass)) {
            return null;
        }

        Class<?> current = pageClass;
        while (current != null && !declaringClass.equals(current)) {
            Type genericSuper = current.getGenericSuperclass();
            if (genericSuper instanceof ParameterizedType) {
                ParameterizedType parameterizedSuper = (ParameterizedType) genericSuper;
                Class<?> rawSuper = (Class<?>) parameterizedSuper.getRawType();
                if (rawSuper.equals(declaringClass)) {
                    TypeVariable<?>[] typeParams = rawSuper.getTypeParameters();
                    Type[] actualArgs = parameterizedSuper.getActualTypeArguments();
                    for (int i = 0; i < typeParams.length; i++) {
                        if (typeParams[i].equals(typeVariable)) {
                            Type arg = actualArgs[i];
                            if (arg instanceof TypeVariable<?>) {
                                Type resolvedFurther = resolveTypeVariable((TypeVariable<?>) arg, pageClass);
                                return resolvedFurther != null ? resolvedFurther : arg;
                            }
                            return arg;
                        }
                    }
                    return null;
                }
            }
            current = current.getSuperclass();
        }
        return null;
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
