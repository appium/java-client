package io.appium.java_client.flutter;

import io.appium.java_client.flutter.commands.FlutterCommandParameter;
import io.appium.java_client.flutter.commands.ScrollParameter;
import io.appium.java_client.flutter.commands.WaitParameter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Interface representing a Flutter driver that extends {@link RemoteWebDriver}.
 * Provides convenience methods for executing Flutter-specific commands using JavaScriptExecutor.
 */
public interface FlutterDriver {

    /**
     * Waits for an element to become visible on the screen.
     *
     * @param parameter The parameters for waiting, specifying timeout and element details.
     */
    default void waitForVisible(WaitParameter parameter) {
        executeScript("waitForVisible", parameter);
    }

    /**
     * Waits for an element to become invisible on the screen.
     *
     * @param parameter The parameters for waiting, specifying timeout and element details.
     */
    default void waitForInVisible(WaitParameter parameter) {
        executeScript("waitForAbsent", parameter);
    }

    /**
     * Scrolls to make an element visible on the screen.
     *
     * @param parameter The parameters for scrolling, specifying element details.
     * @return The WebElement that was scrolled to.
     */
    default WebElement scrollTillVisible(ScrollParameter parameter) {
        return (WebElement) executeScript("scrollTillVisible", parameter);
    }

    /**
     * Executes a Flutter-specific script using JavascriptExecutor.
     *
     * @param scriptName The name of the Flutter script to execute.
     * @param parameter  The parameters for the Flutter command.
     * @return The result of executing the script.
     */
    private Object executeScript(String scriptName, FlutterCommandParameter parameter) {
        String commandName = String.format("flutter: %s", scriptName);
        return ((JavascriptExecutor) this.getDriver()).executeScript(commandName, parameter.toJson());
    }

    /**
     * Retrieves the underlying RemoteWebDriver instance associated with this FlutterDriver.
     *
     * @return The RemoteWebDriver instance used by this FlutterDriver.
     */
    RemoteWebDriver getDriver();
}
