package io.appium.java_client.flutter.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

public interface SupportsFlutterEnableMockCamera<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String FLUTTER_ENABLE_MOCK_CAMERA_OPTION = "flutterEnableMockCamera";

    /**
     * Sets the 'flutterEnableMockCamera' capability to the specified value.
     *
     * @param value the value to set for the 'flutterEnableMockCamera' capability
     * @return an instance of type {@code T} with the updated capability set
     */
    default T setFlutterEnableMockCamera(boolean value) {
        return amend(FLUTTER_ENABLE_MOCK_CAMERA_OPTION, value);
    }

    /**
     * Retrieves the current value of the 'flutterEnableMockCamera' capability, if available.
     *
     * @return an {@code Optional<Boolean>} containing the current value of the capability,
     */
    default Optional<Boolean> doesFlutterEnableMockCamera() {
        return Optional.ofNullable(toSafeBoolean(getCapability(FLUTTER_ENABLE_MOCK_CAMERA_OPTION)));
    }
}