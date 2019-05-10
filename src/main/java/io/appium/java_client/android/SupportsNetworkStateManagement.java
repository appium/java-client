package io.appium.java_client.android;

import static io.appium.java_client.android.AndroidMobileCommandHelper.toggleAirplaneCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.toggleDataCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.toggleWifiCommand;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;

public interface SupportsNetworkStateManagement extends ExecutesMethod {

    /**
     * Toggles Wifi on and off.
     */
    default void toggleWifi() {
        CommandExecutionHelper.execute(this, toggleWifiCommand());
    }

    /**
     * Toggle Airplane mode and this works on OS 6.0 and lesser
     * and does not work on OS 7.0 and greater
     */
    default void toggleAirplaneMode() {
        CommandExecutionHelper.execute(this, toggleAirplaneCommand());
    }

    /**
     * Toggle Mobile Data and this works on Emulator and rooted device.
     */
    default void toggleData() {
        CommandExecutionHelper.execute(this, toggleDataCommand());
    }
}
