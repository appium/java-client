package io.appium.java_client.android;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;

import java.util.Map;

import static java.util.Objects.requireNonNull;

public interface SupportsNetworkStateManagement extends ExecutesMethod {

    /**
     * Toggles Wifi on and off.
     */
    default void toggleWifi() {
        final String extName = "mobile: setConnectivity";
        Map<String, Object> result = requireNonNull(
                CommandExecutionHelper.executeScript(this, "mobile: getConnectivity")
        );
        CommandExecutionHelper.executeScript(this, extName, Map.of(
                "wifi", !((Boolean) result.get("wifi"))
        ));
    }

    /**
     * Toggle Airplane mode and this works on Android versions below
     * 6 and above 10.
     */
    default void toggleAirplaneMode() {
        final String extName = "mobile: setConnectivity";
        Map<String, Object> result = requireNonNull(
                CommandExecutionHelper.executeScript(this, "mobile: getConnectivity")
        );
        CommandExecutionHelper.executeScript(this, extName, Map.of(
                "airplaneMode", !((Boolean) result.get("airplaneMode"))
        ));
    }

    /**
     * Toggle Mobile Data and this works on Emulators and real devices
     * running Android version above 10.
     */
    default void toggleData() {
        final String extName = "mobile: setConnectivity";
        Map<String, Object> result = requireNonNull(
                CommandExecutionHelper.executeScript(this, "mobile: getConnectivity")
        );
        CommandExecutionHelper.executeScript(this, extName, Map.of(
                "data", !((Boolean) result.get("data"))
        ));
    }
}
