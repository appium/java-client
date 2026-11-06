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

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.WrapsElement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.unpackWebDriverFromSearchContext;

/**
 * Invocation handler for a {@link SearchingWidgetRoot} proxy.
 * Not part of the public API.
 */
class SearchingWidgetRootHandler implements InvocationHandler {
    private final WebElement resolved;
    private final Supplier<Iterator<WebElement>> furtherRoots;

    SearchingWidgetRootHandler(WebElement resolved, Supplier<Iterator<WebElement>> furtherRoots) {
        this.resolved = resolved;
        this.furtherRoots = furtherRoots;
    }

    static Class<?>[] proxyInterfaces(WebElement resolved) {
        Set<Class<?>> interfaces = new LinkedHashSet<>();
        collect(resolved.getClass(), interfaces);
        interfaces.add(WebElement.class);
        interfaces.add(WrapsDriver.class);
        interfaces.add(WrapsElement.class);
        interfaces.add(SearchingWidgetRoot.class);
        interfaces.removeIf(type -> !Modifier.isPublic(type.getModifiers()));
        return interfaces.toArray(Class<?>[]::new);
    }

    /**
     * Delegates to the real root, except element lookup.
     *
     * @param proxy the searching root
     * @param method the called method
     * @param args the arguments, or null when there are none
     * @return the call result
     * @throws Throwable when the real call fails
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (isLookup(method, args, "findElement")) {
            return findElement((By) args[0]);
        }
        if (isLookup(method, args, "findElements")) {
            return findElements((By) args[0]);
        }
        if ("getWrappedElement".equals(method.getName()) && (args == null || args.length == 0)) {
            return resolved;
        }
        if ("getWrappedDriver".equals(method.getName()) && (args == null || args.length == 0)) {
            return unpackWebDriverFromSearchContext(resolved);
        }
        if ("toString".equals(method.getName()) && (args == null || args.length == 0)) {
            return resolved.toString();
        }
        if ("hashCode".equals(method.getName()) && (args == null || args.length == 0)) {
            return resolved.hashCode();
        }
        if ("equals".equals(method.getName()) && args != null && args.length == 1) {
            return same(proxy, args[0]);
        }
        try {
            return method.invoke(resolved, args);
        } catch (InvocationTargetException thrown) {
            throw thrown.getCause() == null ? thrown : thrown.getCause();
        }
    }

    private WebElement findElement(By by) {
        try {
            return resolved.findElement(by);
        } catch (NoSuchElementException missed) {
            NoSuchElementException firstMiss = missed;
            Iterator<WebElement> laterRoots = furtherRoots.get();
            while (laterRoots.hasNext()) {
                try {
                    return laterRoots.next().findElement(by);
                } catch (NoSuchElementException laterMiss) {
                    firstMiss.addSuppressed(laterMiss);
                }
            }
            throw firstMiss;
        }
    }

    private List<WebElement> findElements(By by) {
        List<WebElement> found = resolved.findElements(by);
        if (!found.isEmpty()) {
            return found;
        }
        Iterator<WebElement> laterRoots = furtherRoots.get();
        while (laterRoots.hasNext()) {
            List<WebElement> later = laterRoots.next().findElements(by);
            if (!later.isEmpty()) {
                return later;
            }
        }
        return found;
    }

    private boolean same(Object proxy, Object other) {
        if (proxy == other) {
            return true;
        }
        if (other instanceof WrapsElement) {
            other = ((WrapsElement) other).getWrappedElement();
        }
        return resolved == other || resolved.equals(other);
    }

    private static boolean isLookup(Method method, Object[] args, String name) {
        return name.equals(method.getName())
                && args != null
                && args.length == 1
                && args[0] instanceof By;
    }

    private static void collect(Class<?> type, Set<Class<?>> interfaces) {
        if (type == null || Object.class.equals(type)) {
            return;
        }
        for (Class<?> iface : type.getInterfaces()) {
            if (interfaces.add(iface)) {
                collect(iface, interfaces);
            }
        }
        collect(type.getSuperclass(), interfaces);
    }
}
