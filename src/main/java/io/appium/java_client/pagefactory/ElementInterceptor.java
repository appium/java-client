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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Arrays;

import static io.appium.java_client.internal.ElementHelpers.resolveTargetMethod;
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
        } catch (Throwable t) {
            throw extractReadableException(t);
        }
    }
}
