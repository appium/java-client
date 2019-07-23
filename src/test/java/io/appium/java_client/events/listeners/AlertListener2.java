package io.appium.java_client.events.listeners;

import io.appium.java_client.events.api.general.AlertEventListener;
import org.openqa.seleniumone.Alert;
import org.openqa.seleniumone.WebDriver;

public class AlertListener2 extends TestListener implements AlertEventListener {
    @Override public void beforeAlertAccept(WebDriver driver, Alert alert) {
        messages.add("Externally defined listener: Attempt to accept alert");
    }

    @Override public void afterAlertAccept(WebDriver driver, Alert alert) {
        messages.add("Externally defined listener: The alert was accepted");
    }

    @Override public void afterAlertDismiss(WebDriver driver, Alert alert) {
        messages.add("Externally defined listener: The alert was dismissed");
    }

    @Override public void beforeAlertDismiss(WebDriver driver, Alert alert) {
        messages.add("Externally defined listener: Attempt to dismiss alert");
    }

    @Override public void beforeAlertSendKeys(WebDriver driver, Alert alert, String keys) {
        messages.add("Externally defined listener: Attempt to send keys to alert");
    }

    @Override public void afterAlertSendKeys(WebDriver driver, Alert alert, String keys) {
        messages.add("Externally defined listener: Keys were sent to alert");
    }

    @Override protected void add() {
        SingleListeners.listeners.put(AlertListener2.class, this);
    }
}
