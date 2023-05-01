package io.appium.java_client.android;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.CanRememberExtensionPresence;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import org.openqa.selenium.UnsupportedCommandException;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.appium.java_client.android.AndroidMobileCommandHelper.toggleAirplaneCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.toggleDataCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.toggleWifiCommand;

public interface SupportsNetworkStateManagement extends ExecutesMethod, CanRememberExtensionPresence {

    /**
     * Toggles Wifi on and off.
     */
    default void toggleWifi() {
        final String extName = "mobile: setConnectivity";
        try {
            Map<String, Object> result = checkNotNull(
                    CommandExecutionHelper.executeScript(assertExtensionExists(extName), "mobile: getConnectivity")
            );
            CommandExecutionHelper.executeScript(this, extName, ImmutableMap.of(
                    "wifi", !((Boolean) result.get("wifi"))
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(markExtensionAbsence(extName), toggleWifiCommand());
        }
    }

    /**
     * Toggle Airplane mode and this works on Android versions below
     * 6 and above 10.
     */
    default void toggleAirplaneMode() {
        final String extName = "mobile: setConnectivity";
        try {
            Map<String, Object> result = checkNotNull(
                    CommandExecutionHelper.executeScript(assertExtensionExists(extName), "mobile: getConnectivity")
            );
            CommandExecutionHelper.executeScript(this, extName, ImmutableMap.of(
                    "airplaneMode", !((Boolean) result.get("airplaneMode"))
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(markExtensionAbsence(extName), toggleAirplaneCommand());
        }
    }

    /**
     * Toggle Mobile Data and this works on Emulators and real devices
     * running Android version above 10.
     */
    default void toggleData() {
        final String extName = "mobile: setConnectivity";
        try {
            Map<String, Object> result = checkNotNull(
                    CommandExecutionHelper.executeScript(assertExtensionExists(extName), "mobile: getConnectivity")
            );
            CommandExecutionHelper.executeScript(this, extName, ImmutableMap.of(
                    "data", !((Boolean) result.get("data"))
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(markExtensionAbsence(extName), toggleDataCommand());
        }
    }
}
