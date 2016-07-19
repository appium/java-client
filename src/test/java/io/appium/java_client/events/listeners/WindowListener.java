package io.appium.java_client.events.listeners;

import io.appium.java_client.events.api.general.WindowEventListener;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

public class WindowListener extends TestListener implements WindowEventListener {

    @Override protected void add() {
        SingleListeners.listeners.put(WindowListener.class, this);
    }

    @Override public void beforeWindowChangeSize(WebDriver driver, WebDriver.Window window,
        Dimension targetSize) {
        messages.add("Attempt to change size of the window. The height is " + targetSize.getHeight()
            + " the width is " + targetSize.getWidth());
    }

    @Override public void afterWindowChangeSize(WebDriver driver, WebDriver.Window window,
        Dimension targetSize) {
        messages.add("Size of the window has been changed. The height is " + targetSize.getHeight()
            + " the width is " + targetSize.getWidth());
    }

    @Override
    public void beforeWindowIsMoved(WebDriver driver, WebDriver.Window window, Point targetPoint) {
        messages.add("Attempt to change position of the window. The X is " + targetPoint.getX()
            + " the Y is " + targetPoint.getY());
    }

    @Override
    public void afterWindowIsMoved(WebDriver driver, WebDriver.Window window, Point targetPoint) {
        messages.add("The position the window has been changed. The X is " + targetPoint.getX()
            + " the Y is " + targetPoint.getY());
    }

    @Override public void beforeWindowIsMaximized(WebDriver driver, WebDriver.Window window) {
        messages.add("Attempt to maximize the window.");
    }

    @Override public void afterWindowIsMaximized(WebDriver driver, WebDriver.Window window) {
        messages.add("The window has been maximized");
    }
}
