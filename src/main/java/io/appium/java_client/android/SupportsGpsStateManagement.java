package io.appium.java_client.android;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import org.openqa.selenium.UnsupportedCommandException;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.appium.java_client.android.AndroidMobileCommandHelper.toggleLocationServicesCommand;

public interface SupportsGpsStateManagement extends ExecutesMethod {

    /**
     * Toggles GPS service state.
     * This method only works reliably since API 31 (Android 12).
     */
    default void toggleLocationServices() {
        try {
            CommandExecutionHelper.executeScript(this, "mobile: toggleGps");
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(this, toggleLocationServicesCommand());
        }
    }

    /**
     * Check GPS service state.
     *
     * @return true if GPS service is enabled.
     */
    default boolean isLocationServicesEnabled() {
        return checkNotNull(
                CommandExecutionHelper.executeScript(this, "mobile: isGpsEnabled")
        );
    }
}
