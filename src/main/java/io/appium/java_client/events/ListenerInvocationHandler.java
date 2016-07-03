package io.appium.java_client.events;

import io.appium.java_client.events.api.Listener;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class ListenerInvocationHandler implements InvocationHandler {

    private final List<Listener> listeners;

    ListenerInvocationHandler(List<Listener> listeners) {
        this.listeners = listeners;
    }

    @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        for (Listener l: listeners) {
            if (method.getDeclaringClass().isAssignableFrom(l.getClass())) {
                method.invoke(l, args);
            }
        }
        return null;
    }
}
