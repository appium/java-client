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

import io.appium.java_client.proxy.MethodCallListener;
import io.appium.java_client.support.pagefactory.ElementLocator;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.Callable;

import static io.appium.java_client.internal.ElementHelpers.sameElement;
import static io.appium.java_client.internal.ElementHelpers.unwrapElement;

/** Base class of interceptors of method calls on a single element. */
public abstract class InterceptorOfASingleElement implements MethodCallListener {
    /** The locator used to find the element. */
    protected final ElementLocator locator;
    private final WeakReference<WebDriver> driverReference;

    /**
     * Creates a new interceptor.
     *
     * @param locator the locator of the element
     * @param driverReference the reference to the driver
     */
    public InterceptorOfASingleElement(
            @Nullable ElementLocator locator,
            WeakReference<WebDriver> driverReference
    ) {
        this.locator = locator;
        this.driverReference = driverReference;
    }

    /**
     * Handles the intercepted method call.
     *
     * @param element the found element
     * @param method the intercepted method
     * @param args the method arguments
     * @return the call result
     * @throws Throwable if the call fails
     */
    protected abstract Object getObject(WebElement element, Method method, Object[] args) throws Throwable;

    @Override
    public Object call(Object obj, Method method, Object[] args, Callable<?> original) throws Throwable {
        if (locator == null) {
            return original.call();
        }

        if ("toString".equals(method.getName()) && args.length == 0) {
            return locator.toString();
        }

        if ("equals".equals(method.getName()) && args.length == 1 && obj == args[0]) {
            return true;
        }

        if (Object.class == method.getDeclaringClass()) {
            return original.call();
        }

        if (WrapsDriver.class.isAssignableFrom(method.getDeclaringClass())
                && "getWrappedDriver".equals(method.getName())) {
            return driverReference.get();
        }

        WebElement realElement = locator.findElement();
        if ("equals".equals(method.getName()) && args.length == 1) {
            return sameElement(realElement, args[0]);
        }
        if ("hashCode".equals(method.getName()) && args.length == 0) {
            return Objects.hashCode(unwrapElement(realElement));
        }
        return getObject(realElement, method, args);
    }
}
