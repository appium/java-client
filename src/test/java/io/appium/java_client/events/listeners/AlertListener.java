package io.appium.java_client.events.listeners;

import io.appium.java_client.events.api.general.AlertEventListener;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.security.Credentials;

public class AlertListener extends TestListener implements AlertEventListener {
    @Override public void beforeAlertAccept(WebDriver driver, Alert alert) {
        messages.add("Attempt to accept alert");
    }

    @Override public void afterAlertAccept(WebDriver driver, Alert alert) {
        messages.add("The alert was accepted");
    }

    @Override public void afterAlertDismiss(WebDriver driver, Alert alert) {
        messages.add("The alert was dismissed");
    }

    @Override public void beforeAlertDismiss(WebDriver driver, Alert alert) {
        messages.add("Attempt to dismiss alert");
    }

    @Override public void beforeAlertSendKeys(WebDriver driver, Alert alert, String keys) {
        messages.add("Attempt to send keys to alert");
    }

    @Override public void afterAlertSendKeys(WebDriver driver, Alert alert, String keys) {
        messages.add("Keys were sent to alert");
    }

    @Override
    public void beforeAuthentication(WebDriver driver, Alert alert, Credentials credentials) {
        messages.add("Attempt to send credentials " + credentials.toString() + " to alert");
    }

    @Override
    public void afterAuthentication(WebDriver driver, Alert alert, Credentials credentials) {
        messages.add("Credentials " + credentials.toString() + " were sent to alert");
    }

    @Override protected void add() {
        SingleListeners.listeners.put(AlertListener.class, this);
    }
}
