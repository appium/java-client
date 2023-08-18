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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

import static io.appium.java_client.pagefactory.ThrowableUtil.extractReadableException;

/**
 * Intercepts requests to {@link WebElement}.
 */
public class ElementInterceptor extends InterceptorOfASingleElement {

    public ElementInterceptor(ElementLocator locator, WeakReference<WebDriver> driver) {
        super(locator, driver);
    }

    @Override
    protected Object getObject(WebElement element, Method method, Object[] args)
        throws Throwable {
        try {
            return method.invoke(element, args);
        } catch (Throwable t) {
            throw extractReadableException(t);
        }
    }
}
