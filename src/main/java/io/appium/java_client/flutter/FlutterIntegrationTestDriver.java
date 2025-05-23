package io.appium.java_client.flutter;

import org.openqa.selenium.WebDriver;

/**
 * The {@code FlutterDriver} interface represents a driver that controls interactions with
 * Flutter applications, extending WebDriver and providing additional capabilities for
 * interacting with Flutter-specific elements and behaviors.
 *
 * <p>This interface serves as a common entity for drivers that support Flutter applications
 * on different platforms, such as Android and iOS.</p>
 *
 * @see WebDriver
 * @see SupportsGestureOnFlutterElements
 * @see SupportsScrollingOfFlutterElements
 * @see SupportsWaitingForFlutterElements
 */
public interface FlutterIntegrationTestDriver extends
        WebDriver,
        SupportsGestureOnFlutterElements,
        SupportsScrollingOfFlutterElements,
        SupportsWaitingForFlutterElements,
        SupportsFlutterCameraMocking {
}
