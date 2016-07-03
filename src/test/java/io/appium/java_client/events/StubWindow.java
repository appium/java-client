package io.appium.java_client.events;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

public class StubWindow implements WebDriver.Window {
    @Override public void setSize(Dimension targetSize) {

    }

    @Override public void setPosition(Point targetPosition) {

    }

    @Override public Dimension getSize() {
        return null;
    }

    @Override public Point getPosition() {
        return null;
    }

    @Override public void maximize() {

    }

    @Override public void fullscreen() {

    }
}
