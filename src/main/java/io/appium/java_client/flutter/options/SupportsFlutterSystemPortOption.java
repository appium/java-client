package io.appium.java_client.flutter.options;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toInteger;

public interface SupportsFlutterSystemPortOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String FLUTTER_SYSTEM_PORT_OPTION = "flutterSystemPort";

    /**
     * Set the port where Flutter server starts.
     *
     * @param flutterSystemPort is the port number
     * @return self instance for chaining.
     */
    default T setFlutterSystemPort(Integer flutterSystemPort) {
        return amend(FLUTTER_SYSTEM_PORT_OPTION, flutterSystemPort);
    }

    /**
     * Get the number of the port Flutter server starts on the system.
     *
     * @return Port number
     */
    default Optional<Integer> getFlutterSystemPort() {
        return Optional.ofNullable(toInteger(getCapability(FLUTTER_SYSTEM_PORT_OPTION)));
    }
}
