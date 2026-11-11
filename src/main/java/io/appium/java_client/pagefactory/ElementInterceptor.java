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

import io.appium.java_client.pagefactory.interceptors.InterceptorOfASingleElement;
import io.appium.java_client.support.pagefactory.ElementLocator;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import static io.appium.java_client.internal.ElementHelpers.unwrapElement;
import static io.appium.java_client.pagefactory.ThrowableUtil.extractReadableException;
import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.unpackWebDriverFromSearchContext;

/**
 * Intercepts requests to {@link WebElement}.
 */
public class ElementInterceptor extends InterceptorOfASingleElement {

    /**
     * Creates a new interceptor.
     *
     * @param locator the locator of the element
     * @param driver the reference to the driver
     */
    public ElementInterceptor(ElementLocator locator, WeakReference<WebDriver> driver) {
        super(locator, driver);
    }

    ElementInterceptor(WebElement cachedElement) {
        super(
                new StaticElementLocator(cachedElement),
                new WeakReference<>(unpackWebDriverFromSearchContext(cachedElement))
        );
    }

    @Override
    protected Object getObject(WebElement element, Method method, Object[] args)
        throws Throwable {
        try {
            return invokeElementMethod(element, method, args);
        } catch (Throwable t) {
            throw extractReadableException(t);
        }
    }

    private static Object invokeElementMethod(
            WebElement element, Method method, Object[] args
    ) throws ReflectiveOperationException {
        Object target = element;
        Method resolved = resolveTargetMethod(target.getClass(), method);
        if (resolved == null) {
            Object unwrapped = unwrapElement(element);
            if (unwrapped != null && unwrapped != element) {
                target = unwrapped;
                resolved = resolveTargetMethod(target.getClass(), method);
            }
        }
        if (resolved == null) {
            throw new NoSuchMethodException(
                    target.getClass().getName() + "." + method.getName()
                            + Arrays.toString(method.getParameterTypes())
            );
        }
        return resolved.invoke(target, args);
    }

    @Nullable
    private static Method resolveTargetMethod(Class<?> targetClass, Method method) {
        if (method.getDeclaringClass().isAssignableFrom(targetClass)
                && Modifier.isPublic(method.getDeclaringClass().getModifiers())) {
            return method;
        }
        Method interfaceMethod = findPublicInterfaceMethod(
                targetClass, method.getName(), method.getParameterTypes()
        );
        if (interfaceMethod != null) {
            return interfaceMethod;
        }
        if (Modifier.isPublic(targetClass.getModifiers())) {
            return findDeclaredPublicMethod(targetClass, method.getName(), method.getParameterTypes());
        }
        return null;
    }

    @Nullable
    private static Method findPublicInterfaceMethod(
            Class<?> targetClass, String name, Class<?>[] parameterTypes
    ) {
        for (Class<?> current = targetClass; current != null && !Object.class.equals(current);
                current = current.getSuperclass()) {
            for (Class<?> iface : current.getInterfaces()) {
                Method found = findOnInterfaceHierarchy(iface, name, parameterTypes);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    @Nullable
    private static Method findOnInterfaceHierarchy(
            Class<?> iface, String name, Class<?>[] parameterTypes
    ) {
        if (Modifier.isPublic(iface.getModifiers())) {
            Method direct = findDeclaredPublicMethod(iface, name, parameterTypes);
            if (direct != null) {
                return direct;
            }
        }
        for (Class<?> parent : iface.getInterfaces()) {
            Method found = findOnInterfaceHierarchy(parent, name, parameterTypes);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    @Nullable
    private static Method findDeclaredPublicMethod(
            Class<?> owner, String name, Class<?>[] parameterTypes
    ) {
        try {
            return owner.getMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private static final class StaticElementLocator implements ElementLocator {
        private final WebElement element;

        private StaticElementLocator(WebElement element) {
            this.element = element;
        }

        @Override
        public WebElement findElement() {
            return element;
        }

        @Override
        public List<WebElement> findElements() {
            return List.of(element);
        }

        @Override
        public String toString() {
            return element.toString();
        }
    }
}
