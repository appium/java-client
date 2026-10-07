package io.appium.java_client.flutter;

import io.appium.java_client.flutter.commands.DoubleClickParameter;
import io.appium.java_client.flutter.commands.DragAndDropParameter;
import io.appium.java_client.flutter.commands.LongPressParameter;

/**
 * The interface for the drivers that can perform gestures on Flutter elements.
 */
public interface SupportsGestureOnFlutterElements extends CanExecuteFlutterScripts {

    /**
     * Performs a double click action on an element.
     *
     * @param parameter The parameters for double-clicking, specifying element details.
     */
    default void performDoubleClick(DoubleClickParameter parameter) {
        executeFlutterCommand("doubleClick", parameter);
    }

    /**
     * Performs a long press action on an element.
     *
     * @param parameter The parameters for long pressing, specifying element details.
     */
    default void performLongPress(LongPressParameter parameter) {
        executeFlutterCommand("longPress", parameter);
    }

    /**
     * Performs a drag-and-drop action between two elements.
     *
     * @param parameter The parameters for drag-and-drop, specifying source and target elements.
     */
    default void performDragAndDrop(DragAndDropParameter parameter) {
        executeFlutterCommand("dragAndDrop", parameter);
    }
}
