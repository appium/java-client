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
import io.appium.java_client.remote.AppiumWebElement;
import io.appium.java_client.support.pagefactory.ElementLocator;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.Callable;

import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.unwrapElement;

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

        if (method.getName().equals("toString") && args.length == 0) {
            return locator.toString();
        }

        if (Object.class == method.getDeclaringClass()) {
            return original.call();
        }

        if (WrapsDriver.class.isAssignableFrom(method.getDeclaringClass())
                && method.getName().equals("getWrappedDriver")) {
            return driverReference.get();
        }

        WebElement realElement = locator.findElement();
        if ("equals".equals(method.getName()) && args.length == 1) {
            return obj == args[0] || areElementsEqual(realElement, args[0]);
        }
        return getObject(realElement, method, args);
    }

    private static boolean areElementsEqual(@Nullable Object we1, @Nullable Object we2) {
        if (we1 == we2) {
            return true;
        }
        Object unwrapped1 = unwrapElement(we1);
        Object unwrapped2 = unwrapElement(we2);
        if (unwrapped1 == unwrapped2) {
            return true;
        }
        if (!(unwrapped1 instanceof AppiumWebElement) && unwrapped2 instanceof AppiumWebElement) {
            return unwrapped2.equals(unwrapped1);
        }
        if (!(unwrapped1 instanceof AppiumWebElement) || !(unwrapped2 instanceof AppiumWebElement)) {
            return Objects.equals(unwrapped1, unwrapped2);
        }
        return Objects.equals(((AppiumWebElement) unwrapped1).getId(), ((AppiumWebElement) unwrapped2).getId());
    }
}
