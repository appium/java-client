package io.appium.java_client.events.listeners;

import io.appium.java_client.events.api.mobile.RotationEventListener;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;

public class RotationListener2 extends TestListener implements RotationEventListener {

    @Override public void beforeRotation(WebDriver driver, ScreenOrientation orientation) {
        messages.add("Externally defined listener: Attempt to change screen orientation. "
            + "The new screen orientation is "
            + orientation.toString());
    }

    @Override public void afterRotation(WebDriver driver, ScreenOrientation orientation) {
        messages.add("Externally defined listener: The screen orientation has been changed to "
            + orientation.toString());
    }

    @Override protected void add() {
        SingleListeners.listeners.put(RotationListener2.class, this);
    }
}
