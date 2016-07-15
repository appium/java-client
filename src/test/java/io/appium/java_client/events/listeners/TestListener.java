package io.appium.java_client.events.listeners;

import io.appium.java_client.events.api.Listener;

import java.util.ArrayList;
import java.util.List;

public abstract class TestListener implements Listener {

    public final List<String> messages = new ArrayList<>();

    public TestListener() {
        add();
    }

    protected abstract void add();
}
