package io.appium.java_client.flutter;


import io.appium.java_client.flutter.commands.DoubleClickOptions;
import io.appium.java_client.flutter.commands.DragAndDropOptions;
import io.appium.java_client.flutter.commands.FlutterCommands;
import io.appium.java_client.flutter.commands.LongPressOptions;
import io.appium.java_client.flutter.commands.ScrollOptions;
import io.appium.java_client.flutter.commands.WaitOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public interface FlutterDriver {

    /**
     * Waits for an element to become visible on the Flutter application.
     *
     * @param option WaitOptions specifying the element and optional timeout.
     */
    default void waitForVisible(WaitOptions option) {
        FlutterCommands.waitForVisible(this.getDriver(), option);
    }

    /**
     * Waits for an element to become invisible on the Flutter application.
     *
     * @param option WaitOptions specifying the element and optional timeout.
     */
    default void waitForInVisible(WaitOptions option) {
        FlutterCommands.waitForInVisible(this.getDriver(), option);
    }

    /**
     * Scrolls to make an element visible on the Flutter application.
     *
     * @param option ScrollOptions specifying the finder and scroll direction.
     */
    default void scrollTillVisible(ScrollOptions option) {
        FlutterCommands.scrollTillVisible(this.getDriver(), option);
    }

    /**
     * Performs a double click on an element in the Flutter application.
     *
     * @param option DoubleClickOptions specifying the element to double-click.
     */
    default void performDoubleClick(DoubleClickOptions option) {
        FlutterCommands.performDoubleClick(this.getDriver(), option);
    }

    /**
     * Performs a long press on an element in the Flutter application.
     *
     * @param option LongPressOptions specifying the element to long-press.
     */
    default void performLongPress(LongPressOptions option) {
        FlutterCommands.performLongPress(this.getDriver(), option);
    }

    /**
     * Performs a drag-and-drop operation between two elements in the Flutter application.
     *
     * @param option DragAndDropOptions specifying the source and target elements for the drag-and-drop operation.
     */
    default void performDragAndDrop(DragAndDropOptions option) {
        FlutterCommands.performDragAndDrop(this.getDriver(), option);
    }

    RemoteWebDriver getDriver();
}
