package io.appium.java_client.flutter.commands;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class FlutterCommands {

    private static final String FLUTTER_COMMAND_PREFIX = "flutter";
    private static final String WAIT_FOR_VISIBLE_COMMAND = "waitForVisible";
    private static final String WAIT_FOR_INVISIBLE_COMMAND = "waitForAbsent";
    private static final String SCROLL_TILL_VISIBLE = "scrollTillVisible";
    private static final String DOUBLE_CLICK = "doubleClick";
    private static final String LONG_PRESS = "longPress";
    private static final String DRAG_AND_DROP = "dragAndDrop";

    private FlutterCommands() {
    }

    /**
     * Waits for an element to become visible on the Flutter application.
     *
     * @param driver WebDriver instance to execute the command on.
     * @param option WaitOptions specifying the element and optional timeout.
     */
    public static void waitForVisible(RemoteWebDriver driver, WaitOptions option) {
        executeScript(driver, WAIT_FOR_VISIBLE_COMMAND, option);
    }

    /**
     * Waits for an element to become invisible on the Flutter application.
     *
     * @param driver WebDriver instance to execute the command on.
     * @param option WaitOptions specifying the element and optional timeout.
     */
    public static void waitForInVisible(RemoteWebDriver driver, WaitOptions option) {
        executeScript(driver, WAIT_FOR_INVISIBLE_COMMAND, option);
    }

    /**
     * Scrolls to make an element visible on the Flutter application.
     *
     * @param driver WebDriver instance to execute the command on.
     * @param option ScrollOptions specifying the finder and scroll direction.
     * @return WebElement that was scrolled to and made visible.
     */
    public static WebElement scrollTillVisible(RemoteWebDriver driver, ScrollOptions option) {
        return (WebElement) executeScript(driver, SCROLL_TILL_VISIBLE, option);
    }

    /**
     * Performs a double click on an element in the Flutter application.
     *
     * @param driver WebDriver instance to execute the command on.
     * @param option DoubleClickOptions specifying the element to double-click.
     */
    public static void performDoubleClick(RemoteWebDriver driver, DoubleClickOptions option) {
        executeScript(driver, DOUBLE_CLICK, option);
    }

    /**
     * Performs a long press on an element in the Flutter application.
     *
     * @param driver WebDriver instance to execute the command on.
     * @param option LongPressOptions specifying the element to long-press.
     */
    public static void performLongPress(RemoteWebDriver driver, LongPressOptions option) {
        executeScript(driver, LONG_PRESS, option);
    }

    /**
     * Performs a drag-and-drop operation between two elements in the Flutter application.
     *
     * @param driver WebDriver instance to execute the command on.
     * @param option DragAndDropOptions specifying the source and target elements for the drag-and-drop operation.
     */
    public static void performDragAndDrop(RemoteWebDriver driver, DragAndDropOptions option) {
        executeScript(driver, DRAG_AND_DROP, option);
    }

    /**
     * Executes a Flutter command using JavaScriptExecutor.
     *
     * @param driver     WebDriver instance to execute the command on.
     * @param scriptName Name of the Flutter command script.
     * @param args       FlutterCommandOptions containing the command arguments.
     * @return Object returned by the JavaScript execution.
     */
    public static Object executeScript(RemoteWebDriver driver, String scriptName, FlutterCommandOptions args) {
        String commandName = String.format("%s: %s", FLUTTER_COMMAND_PREFIX, scriptName);
        return ((JavascriptExecutor) driver).executeScript(commandName, args.toJson());
    }
}
