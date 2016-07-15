package io.appium.java_client.events.listeners;

import io.appium.java_client.events.api.mobile.ContextEventListener;
import org.openqa.selenium.WebDriver;

public class ContextListener2 extends TestListener implements ContextEventListener {
    @Override public void beforeSwitchingToContext(WebDriver driver, String context) {
        messages.add("Externally defined listener: Attempt to change current context to " + context);
    }

    @Override public void afterSwitchingToContext(WebDriver driver, String context) {
        messages.add("Externally defined listener: The previous context has been changed to " + context);
    }

    @Override protected void add() {
        SingleListeners.listeners.put(ContextListener2.class, this);
    }
}
