package io.appium.java_client.flutter.options;

import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.time.Duration;
import java.util.Optional;

public interface SupportsFlutterServerLaunchTimeoutOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String FLUTTER_SERVER_LAUNCH_TIMEOUT_OPTION = "flutterServerLaunchTimeout";

    /**
     * Timeout to wait for FlutterServer to be pingable,
     * e.g. finishes building. Defaults to 60000ms.
     *
     * @param timeout Timeout to wait until FlutterServer is listening.
     * @return self instance for chaining.
     */
    default T setFlutterServerLaunchTimeout(Duration timeout) {
        return amend(FLUTTER_SERVER_LAUNCH_TIMEOUT_OPTION, timeout.toMillis());
    }

    /**
     * Get the maximum timeout to wait until FlutterServer is listening.
     *
     * @return Timeout value.
     */
    default Optional<Duration> getFlutterServerLaunchTimeout() {
        return Optional.ofNullable(
                CapabilityHelpers.toDuration(getCapability(FLUTTER_SERVER_LAUNCH_TIMEOUT_OPTION))
        );
    }
}
