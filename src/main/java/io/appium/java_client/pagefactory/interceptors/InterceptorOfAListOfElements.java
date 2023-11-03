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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;

public abstract class InterceptorOfAListOfElements implements MethodCallListener {
    private final static WeakHashMap<List<WebElement>, List<WebElement>> FORMER_LISTS = new WeakHashMap<>();
    protected final ElementLocator locator;

    public InterceptorOfAListOfElements(@Nullable ElementLocator locator) {
        this.locator = locator;
    }

    protected abstract Object getObject(
            List<WebElement> elements, Method method, Object[] args
    ) throws Throwable;

    @Override
    public Object call(Object obj, Method method, Object[] args, Callable<?> original) throws Throwable {
        if (locator == null || Object.class == method.getDeclaringClass()) {
            return original.call();
        }

        final List<WebElement> realElements = locator.findElements();
        List<WebElement> resultElements;
        // This is needed to be able to compare two element lists
        // and validate if caching happened
        synchronized (FORMER_LISTS) {
            resultElements = Optional.ofNullable(FORMER_LISTS.get(realElements))
                    .orElseGet(() -> {
                        var result = new ArrayList<>(realElements);
                        FORMER_LISTS.put(realElements, result);
                        return result;
                    });
        }
        return getObject(resultElements, method, args);
    }
}
