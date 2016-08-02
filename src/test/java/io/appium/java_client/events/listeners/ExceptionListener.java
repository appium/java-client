package io.appium.java_client.events.listeners;

import io.appium.java_client.events.api.general.ListensToException;
import org.openqa.selenium.WebDriver;

public class ExceptionListener extends TestListener implements ListensToException {
    @Override public void onException(Throwable throwable, WebDriver driver) {
        messages.add("The exception was thrown: " + throwable.getClass());
    }

    @Override protected void add() {
        SingleListeners.listeners.put(ExceptionListener.class, this);
    }
}
