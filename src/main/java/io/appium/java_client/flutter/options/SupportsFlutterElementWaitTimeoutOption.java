package io.appium.java_client.flutter.options;

import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.time.Duration;
import java.util.Optional;

public interface SupportsFlutterElementWaitTimeoutOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String FLUTTER_ELEMENT_WAIT_TIMEOUT_OPTION = "flutterElementWaitTimeout";

    /**
     * Sets the Flutter element wait timeout.
     * Defaults to 5 seconds.
     *
     * @param timeout The duration to wait for Flutter elements during findElement method
     * @return self instance for chaining.
     */
    default T setFlutterElementWaitTimeout(Duration timeout) {
        return amend(FLUTTER_ELEMENT_WAIT_TIMEOUT_OPTION, timeout.toMillis());
    }

    /**
     * Retrieves the current Flutter element wait timeout if set.
     *
     * @return An {@link Optional} containing the duration of the Flutter element wait timeout, or empty if not set.
     */
    default Optional<Duration> getFlutterElementWaitTimeout() {
        return Optional.ofNullable(
                CapabilityHelpers.toDuration(getCapability(FLUTTER_ELEMENT_WAIT_TIMEOUT_OPTION))
        );
    }
}
