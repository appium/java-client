package io.appium.java_client.events.listeners;

import io.appium.java_client.events.api.Listener;

import java.util.HashMap;
import java.util.Map;

public class SingleListeners {

    public static final Map<Class<? extends Listener>, TestListener> listeners = new HashMap<>();

}
