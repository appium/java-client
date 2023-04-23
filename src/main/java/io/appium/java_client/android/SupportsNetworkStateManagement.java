package io.appium.java_client.android;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import org.openqa.selenium.UnsupportedCommandException;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.appium.java_client.android.AndroidMobileCommandHelper.toggleAirplaneCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.toggleDataCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.toggleWifiCommand;

public interface SupportsNetworkStateManagement extends ExecutesMethod {

    /**
     * Toggles Wifi on and off.
     */
    default void toggleWifi() {
        try {
            Map<String, Object> result = checkNotNull(
                    CommandExecutionHelper.executeScript(this, "mobile: getConnectivity")
            );
            CommandExecutionHelper.executeScript(this, "mobile: setConnectivity", ImmutableMap.of(
                    "wifi", !((Boolean) result.get("wifi"))
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(this, toggleWifiCommand());
        }
    }

    /**
     * Toggle Airplane mode and this works on Android versions below
     * 6 and above 10.
     */
    default void toggleAirplaneMode() {
        try {
            Map<String, Object> result = checkNotNull(
                    CommandExecutionHelper.executeScript(this, "mobile: getConnectivity")
            );
            CommandExecutionHelper.executeScript(this, "mobile: setConnectivity", ImmutableMap.of(
                    "airplaneMode", !((Boolean) result.get("airplaneMode"))
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(this, toggleAirplaneCommand());
        }
    }

    /**
     * Toggle Mobile Data and this works on Emulators and real devices
     * running Android version above 10.
     */
    default void toggleData() {
        try {
            Map<String, Object> result = checkNotNull(
                    CommandExecutionHelper.executeScript(this, "mobile: getConnectivity")
            );
            CommandExecutionHelper.executeScript(this, "mobile: setConnectivity", ImmutableMap.of(
                    "data", !((Boolean) result.get("data"))
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(this, toggleDataCommand());
        }
    }
}
