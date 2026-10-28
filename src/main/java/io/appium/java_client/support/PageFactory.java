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

package io.appium.java_client.support;

import io.appium.java_client.support.pagefactory.DefaultFieldDecorator;
import io.appium.java_client.support.pagefactory.ElementLocatorFactory;
import io.appium.java_client.support.pagefactory.FieldDecorator;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Factory class to make using Page Objects simpler and easier.
 * Adapted from Selenium's {@code org.openqa.selenium.support.PageFactory} (Apache License 2.0).
 */
public final class PageFactory {

    private static final ThreadLocal<Deque<Class<?>>> PAGE_CLASS_STACK =
            ThreadLocal.withInitial(ArrayDeque::new);

    private PageFactory() {
    }

    /**
     * Returns the runtime class of the page object currently being initialized by
     * {@link #initElements(FieldDecorator, Object)} on the current thread, or {@code null}
     * if no page initialization is active.
     *
     * @return the current page class or {@code null}
     */
    @Nullable
    public static Class<?> getCurrentPageClass() {
        return PAGE_CLASS_STACK.get().peek();
    }

    /**
     * Similar to the other "initElements" methods, but takes an {@link ElementLocatorFactory} which
     * is used for providing the mechanism for finding elements. If the ElementLocatorFactory returns
     * null then the field won't be decorated.
     *
     * @param factory The factory to use
     * @param page The object to decorate the fields of
     */
    public static void initElements(ElementLocatorFactory factory, Object page) {
        initElements(new DefaultFieldDecorator(factory), page);
    }

    /**
     * Takes a {@link FieldDecorator} which is used for decorating each of the fields of the page,
     * including the ones declared in its superclasses.
     *
     * @param decorator the decorator to use
     * @param page The object to decorate the fields of
     */
    public static void initElements(FieldDecorator decorator, Object page) {
        Deque<Class<?>> stack = PAGE_CLASS_STACK.get();
        stack.push(page.getClass());
        try {
            Class<?> proxyIn = page.getClass();
            while (proxyIn != Object.class) {
                proxyFields(decorator, page, proxyIn);
                proxyIn = proxyIn.getSuperclass();
            }
        } finally {
            stack.pop();
            if (stack.isEmpty()) {
                PAGE_CLASS_STACK.remove();
            }
        }
    }

    private static void proxyFields(FieldDecorator decorator, Object page, Class<?> proxyIn) {
        Field[] fields = proxyIn.getDeclaredFields();
        for (Field field : fields) {
            Object value = decorator.decorate(page.getClass().getClassLoader(), field);
            if (value != null) {
                try {
                    field.setAccessible(true);
                    field.set(page, value);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }
}
