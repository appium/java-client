package io.appium.java_client.android;

import static io.appium.java_client.android.AndroidMobileCommandHelper.getDisplayDensityCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.getSystemBarsCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.isKeyboardShownCommand;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;

import java.util.Map;

public interface HasDeviceDetails extends ExecutesMethod {
    /*
        Retrieve the display density of the Android device.
     */
    default Long getDisplayDensity() {
        return CommandExecutionHelper.execute(this, getDisplayDensityCommand());
    }

    /*
        Retrieve visibility and bounds information of the status and navigation bars.
     */
    default Map<String, String> getSystemBars() {
        return CommandExecutionHelper.execute(this, getSystemBarsCommand());
    }

    /**
     * Check if the keyboard is displayed.
     *
     * @return true if keyboard is displayed. False otherwise
     */
    default boolean isKeyboardShown() {
        return CommandExecutionHelper.execute(this, isKeyboardShownCommand());
    }
}
