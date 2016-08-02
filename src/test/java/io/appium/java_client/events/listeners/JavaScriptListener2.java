package io.appium.java_client.events.listeners;

import io.appium.java_client.events.api.general.JavaScriptEventListener;
import org.openqa.selenium.WebDriver;

public class JavaScriptListener2 extends TestListener implements JavaScriptEventListener {
    @Override public void beforeScript(String script, WebDriver driver) {
        messages.add("Externally defined listener: Attempt to perform java script: " + script);
    }

    @Override public void afterScript(String script, WebDriver driver) {
        messages.add("Externally defined listener: Java script " + script + " was performed");
    }

    @Override protected void add() {
        SingleListeners.listeners.put(JavaScriptListener2.class, this);
    }
}
