package io.appium.java_client.events;

import io.appium.java_client.selenium.WebDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

public class StubWindow implements WebDriver.Window {
    @Override public void setSize(Dimension targetSize) {
        //STUB it does nothing
    }

    @Override public void setPosition(Point targetPosition) {
        //STUB it does nothing
    }

    @Override public Dimension getSize() {
        return null;
    }

    @Override public Point getPosition() {
        return null;
    }

    @Override public void maximize() {
        //STUB it does nothing
    }

    @Override public void fullscreen() {
        //STUB it does nothing
    }
}
