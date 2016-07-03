package io.appium.java_client.events.listeners;

import io.appium.java_client.events.api.Listener;

import java.util.ArrayList;
import java.util.List;

abstract class TestListener implements Listener {

    public final List<String> messages = new ArrayList<>();

    TestListener() {
        add();
    }

    abstract void add();
}
