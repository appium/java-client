package io.appium.java_client.flutter;

import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.flutter.commands.CommonFlutterCommands;
import io.appium.java_client.flutter.commands.DoubleClickOptions;
import io.appium.java_client.flutter.commands.DragAndDropOptions;
import io.appium.java_client.flutter.commands.LongPressOptions;
import io.appium.java_client.flutter.commands.ScrollOptions;
import io.appium.java_client.flutter.commands.WaitOptions;
import io.appium.java_client.ios.IOSDriver;
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
public class FlutterIOSDriver extends IOSDriver implements FlutterDriver {

    public FlutterIOSDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, capabilities);
    }

    public FlutterIOSDriver(URL remoteAddress, Capabilities capabilities) {
        super(remoteAddress, capabilities);
    }

    public FlutterIOSDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(remoteAddress, httpClientFactory, capabilities);
    }

    public FlutterIOSDriver(AppiumDriverLocalService service, Capabilities capabilities) {
        super(service, capabilities);
    }

    public FlutterIOSDriver(
            AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(service, httpClientFactory, capabilities);
    }

    public FlutterIOSDriver(AppiumServiceBuilder builder, Capabilities capabilities) {
        super(builder, capabilities);
    }

    public FlutterIOSDriver(
            AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(builder, httpClientFactory, capabilities);
    }

    public FlutterIOSDriver(HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(httpClientFactory, capabilities);
    }

    public FlutterIOSDriver(ClientConfig clientConfig, Capabilities capabilities) {
        super(clientConfig, capabilities);
    }

    public FlutterIOSDriver(AppiumClientConfig appiumClientConfig, Capabilities capabilities) {
        super(appiumClientConfig, capabilities);
    }

    public FlutterIOSDriver(URL remoteSessionAddress) {
        super(remoteSessionAddress);
    }

    public FlutterIOSDriver(Capabilities capabilities) {
        super(capabilities);
    }

    /**
     * Waits for an element to become visible on the Flutter application.
     *
     * @param option WaitOptions specifying the element and optional timeout.
     */
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