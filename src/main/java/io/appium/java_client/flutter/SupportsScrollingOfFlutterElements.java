package io.appium.java_client.flutter;

import io.appium.java_client.flutter.commands.ScrollParameter;
import org.openqa.selenium.WebElement;

public interface SupportsScrollingOfFlutterElements extends CanExecuteFlutterScripts {

    /**
     * Scrolls to make an element visible on the screen.
     *
     * @param parameter The parameters for scrolling, specifying element details.
     * @return The WebElement that was scrolled to.
     */
    default WebElement scrollTillVisible(ScrollParameter parameter) {
        return (WebElement) executeFlutterCommand("scrollTillVisible", parameter);
    }
}
