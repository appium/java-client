package io.appium.java_client.flutter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Map;

/**
 * This interface extends {@link CanExecuteFlutterScripts} and provides methods
 * to support mocking of camera inputs in Flutter applications.
 */
public interface SupportsFlutterCameraMocking extends CanExecuteFlutterScripts {

    /**
     * Injects a mock image into the Flutter application using the provided file.
     *
     * @param image the image file to be mocked (must be in PNG format)
     * @return an {@code String} representing a unique id of the injected image
     * @throws IOException if an I/O error occurs while reading the image file
     */
    default String injectMockImage(File image) throws IOException {
        String base64EncodedImage = Base64.getEncoder().encodeToString(Files.readAllBytes(image.toPath()));
        return injectMockImage(base64EncodedImage);
    }

    /**
     * Injects a mock image into the Flutter application using the provided Base64-encoded image string.
     *
     * @param base64Image the Base64-encoded string representation of the image (must be in PNG format)
     * @return an {@code String} representing the result of the injection operation
     */
    default String injectMockImage(String base64Image) {
        return (String) executeFlutterCommand("injectImage", Map.of(
                "base64Image", base64Image
        ));
    }

    /**
     * Activates the injected image identified by the specified image ID in the Flutter application.
     *
     * @param imageId the ID of the injected image to activate
     */
    default void activateInjectedImage(String imageId) {
        executeFlutterCommand("activateInjectedImage", Map.of("imageId", imageId));
    }
}
