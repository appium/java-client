package io.appium.java_client.flutter;


import io.appium.java_client.flutter.commands.DoubleClickOptions;
import io.appium.java_client.flutter.commands.DragAndDropOptions;
import io.appium.java_client.flutter.commands.LongPressOptions;
import io.appium.java_client.flutter.commands.ScrollOptions;
import io.appium.java_client.flutter.commands.WaitOptions;

public interface FlutterDriver {
    void waitForVisible(WaitOptions option);

    void waitForInVisible(WaitOptions option);

    void scrollTillVisible(ScrollOptions option);

    void performDoubleClick(DoubleClickOptions option);

    void performLongPress(LongPressOptions option);

    void performDragAndDrop(DragAndDropOptions option);
}
