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

package io.appium.java_client.events;

import io.appium.java_client.events.api.Listener;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

class ListenerInvocationHandler implements InvocationHandler {

    private final List<Listener> listeners;

    ListenerInvocationHandler(List<Listener> listeners) {
        this.listeners = listeners;
    }

    private Method findElementInWebDriverEventListener(Method m) {
        try {
            return WebDriverEventListener.class.getMethod(m.getName(), m.getParameterTypes());
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        for (Listener l: listeners) {
            boolean isInvoked = false;
            if (method.getDeclaringClass().isAssignableFrom(l.getClass())) {
                method.invoke(l, args);
                isInvoked = true;
            }

            if (isInvoked) {
                continue;
            }

            Method webDriverEventListenerMethod = findElementInWebDriverEventListener(method);
            if (webDriverEventListenerMethod != null
                && WebDriverEventListener.class.isAssignableFrom(l.getClass())) {
                webDriverEventListenerMethod.invoke(l, args);
            }
        }
        return null;
    }
}
