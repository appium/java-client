package io.appium.java_client.android;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import org.openqa.selenium.UnsupportedCommandException;

import java.util.Map;

import static io.appium.java_client.android.AndroidMobileCommandHelper.getDisplayDensityCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.getSystemBarsCommand;

public interface HasAndroidDeviceDetails extends ExecutesMethod {

    /**
     Retrieve the display density of the Android device.

     @return The density value in dpi
     */
    default Long getDisplayDensity() {
        try {
            return CommandExecutionHelper.executeScript(this, "mobile: getDisplayDensity");
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            return CommandExecutionHelper.execute(this, getDisplayDensityCommand());
        }
    }

    /**
     Retrieve visibility and bounds information of the status and navigation bars.

     @return The map where keys are bar types and values are mappings of bar properties.
     */
    default Map<String, Map<String, Object>> getSystemBars() {
        try {
            return CommandExecutionHelper.executeScript(this, "mobile: getSystemBars");
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            return CommandExecutionHelper.execute(this, getSystemBarsCommand());
        }
    }

}
