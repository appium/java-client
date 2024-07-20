package io.appium.java_client.flutter;

import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.flutter.commands.CommonFlutterCommands;
import io.appium.java_client.flutter.commands.DoubleClickOptions;
import io.appium.java_client.flutter.commands.DragAndDropOptions;
import io.appium.java_client.flutter.commands.LongPressOptions;
import io.appium.java_client.flutter.commands.ScrollOptions;
import io.appium.java_client.flutter.commands.WaitOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;

/**
 * Custom AndroidDriver implementation with additional Flutter-specific capabilities.
 */
public class FlutterAndroidDriver extends AndroidDriver implements FlutterDriver {

    public FlutterAndroidDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, capabilities);
    }

    public FlutterAndroidDriver(URL remoteAddress, Capabilities capabilities) {
        super(remoteAddress, capabilities);
    }

    public FlutterAndroidDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(remoteAddress, httpClientFactory, capabilities);
    }

    public FlutterAndroidDriver(AppiumDriverLocalService service, Capabilities capabilities) {
        super(service, capabilities);
    }

    public FlutterAndroidDriver(
            AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(service, httpClientFactory, capabilities);
    }

    public FlutterAndroidDriver(AppiumServiceBuilder builder, Capabilities capabilities) {
        super(builder, capabilities);
    }

    public FlutterAndroidDriver(
            AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(builder, httpClientFactory, capabilities);
    }

    public FlutterAndroidDriver(HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(httpClientFactory, capabilities);
    }

    public FlutterAndroidDriver(ClientConfig clientConfig, Capabilities capabilities) {
        super(clientConfig, capabilities);
    }

    public FlutterAndroidDriver(AppiumClientConfig appiumClientConfig, Capabilities capabilities) {
        super(appiumClientConfig, capabilities);
    }

    public FlutterAndroidDriver(Capabilities capabilities) {
        super(capabilities);
    }

    public FlutterAndroidDriver(URL remoteSessionAddress, String automationName) {
        super(remoteSessionAddress, automationName);
    }

    /**
     * Waits for an element to become visible on the Flutter application.
     *
     * @param option WaitOptions specifying the element and optional timeout.
     */
    @Override
    public void waitForVisible(WaitOptions option) {
        CommonFlutterCommands.waitForVisible(this, option);
    }

    /**
     * Waits for an element to become invisible on the Flutter application.
     *
     * @param option WaitOptions specifying the element and optional timeout.
     */
    @Override
    public void waitForInVisible(WaitOptions option) {
        CommonFlutterCommands.waitForInVisible(this, option);
    }

    /**
     * Scrolls to make an element visible on the Flutter application.
     *
     * @param option ScrollOptions specifying the finder and scroll direction.
     */
    @Override
    public void scrollTillVisible(ScrollOptions option) {
        CommonFlutterCommands.scrollTillVisible(this, option);
    }

    /**
     * Performs a double click on an element in the Flutter application.
     *
     * @param option DoubleClickOptions specifying the element to double-click.
     */
    @Override
    public void performDoubleClick(DoubleClickOptions option) {
        CommonFlutterCommands.performDoubleClick(this, option);
    }

    /**
     * Performs a long press on an element in the Flutter application.
     *
     * @param option LongPressOptions specifying the element to long-press.
     */
    @Override
    public void performLongPress(LongPressOptions option) {
        CommonFlutterCommands.performLongPress(this, option);
    }

    /**
     * Performs a drag-and-drop operation between two elements in the Flutter application.
     *
     * @param option DragAndDropOptions specifying the source and target elements for the drag-and-drop operation.
     */
    @Override
    public void performDragAndDrop(DragAndDropOptions option) {
        CommonFlutterCommands.performDragAndDrop(this, option);
    }
}