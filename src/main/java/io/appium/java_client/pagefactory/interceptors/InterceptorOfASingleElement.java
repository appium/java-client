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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public abstract class InterceptorOfASingleElement implements MethodCallListener {
    protected final ElementLocator locator;
    private final WeakReference<WebDriver> driverReference;

    public InterceptorOfASingleElement(
            @Nullable
            ElementLocator locator,
            WeakReference<WebDriver> driverReference
    ) {
        this.locator = locator;
        this.driverReference = driverReference;
    }

    protected abstract Object getObject(WebElement element, Method method, Object[] args) throws Throwable;

    @Override
    public Object call(Object obj, Method method, Object[] args, Callable<?> original) throws Throwable {
        if (locator == null) {
            return original.call();
        }

        if (method.getName().equals("toString") && args.length == 0) {
            return locator.toString();
        }

        if (WrapsDriver.class.isAssignableFrom(method.getDeclaringClass())
                && method.getName().equals("getWrappedDriver")) {
            return driverReference.get();
        }

        Supplier<WebElement> realElementSupplier = locator::findElement;
        if (Object.class == method.getDeclaringClass()) {
            if ("equals".equals(method.getName()) && args.length == 1) {
                return realElementSupplier.get().equals(args[0]);
            }
            if ("hashCode".equals(method.getName()) && args.length == 0) {
                return realElementSupplier.get().hashCode();
            }
            return original.call();
        }
        return getObject(realElementSupplier.get(), method, args);
    }
}
