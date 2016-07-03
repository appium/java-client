package io.appium.java_client.events.listeners;

import io.appium.java_client.events.api.general.JavaScriptEventListener;
import org.openqa.selenium.WebDriver;

class JavaScriptListener extends TestListener implements JavaScriptEventListener {
    @Override public void beforeScript(String script, WebDriver driver) {
        messages.add("Attempt to perform java script");
    }

    @Override public void afterScript(String script, WebDriver driver) {
        messages.add("Java script was performed");
    }

    @Override void add() {
        SingleListeners.listeners.put(JavaScriptListener.class, this);
    }
}
