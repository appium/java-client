package io.appium.java_client.events;

import org.openqa.seleniumone.Dimension;
import org.openqa.seleniumone.Point;
import org.openqa.seleniumone.WebDriver;

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
