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

import io.appium.java_client.pagefactory.interceptors.InterceptorOfAListOfElements;
import io.appium.java_client.remote.AppiumWebElement;
import io.appium.java_client.support.pagefactory.ElementLocator;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;
import java.util.List;

import static io.appium.java_client.internal.ElementHelpers.unwrapElement;
import static io.appium.java_client.pagefactory.ThrowableUtil.extractReadableException;
import static io.appium.java_client.pagefactory.utils.ProxyFactory.getEnhancedProxy;

/**
 * Intercepts requests to the list of {@link WebElement}.
 */
public class ElementListInterceptor extends InterceptorOfAListOfElements {
    private final boolean adaptToAppiumElement;

    /**
     * Creates a new interceptor.
     *
     * @param locator the locator of the elements
     */
    public ElementListInterceptor(ElementLocator locator) {
        this(locator, false);
    }

    ElementListInterceptor(ElementLocator locator, boolean adaptToAppiumElement) {
        super(locator);
        this.adaptToAppiumElement = adaptToAppiumElement;
    }

    @Override
    protected Object getObject(List<WebElement> elements, Method method, Object[] args) throws Throwable {
        try {
            if (!adaptToAppiumElement) {
                return method.invoke(elements, args);
            }
            String methodName = method.getName();
            if ("size".equals(methodName) || "isEmpty".equals(methodName) || "clear".equals(methodName)) {
                return method.invoke(elements, args);
            }
            if ("get".equals(methodName) && args != null && args.length == 1 && args[0] instanceof Integer) {
                Object item = method.invoke(elements, args);
                return item instanceof WebElement ? adaptIfNeeded((WebElement) item) : item;
            }
            adaptInPlace(elements);
            return method.invoke(elements, args);
        } catch (Throwable t) {
            throw extractReadableException(t);
        }
    }

    private static void adaptInPlace(List<WebElement> elements) {
        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i);
            WebElement adapted = adaptIfNeeded(element);
            if (adapted != element) {
                elements.set(i, adapted);
            }
        }
    }

    @Nullable
    private static WebElement adaptIfNeeded(@Nullable WebElement element) {
        if (element == null || element instanceof AppiumWebElement) {
            return element;
        }
        Object unwrapped = unwrapElement(element);
        if (unwrapped == element) {
            return element;
        }
        return getEnhancedProxy(AppiumWebElement.class, new ElementInterceptor(element));
    }

}
