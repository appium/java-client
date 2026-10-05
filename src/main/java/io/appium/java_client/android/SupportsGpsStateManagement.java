package io.appium.java_client.android;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;

import static java.util.Objects.requireNonNull;

public interface SupportsGpsStateManagement extends ExecutesMethod {

    /**
     * Toggles GPS service state.
     * This method only works reliably since API 31 (Android 12).
     */
    default void toggleLocationServices() {
        final String extName = "mobile: toggleGps";
        CommandExecutionHelper.executeScript(this, extName);
    }

    /**
     * Check GPS service state.
     *
     * @return true if GPS service is enabled.
     */
    default boolean isLocationServicesEnabled() {
        return requireNonNull(
                CommandExecutionHelper.executeScript(this, "mobile: isGpsEnabled")
        );
    }
}
