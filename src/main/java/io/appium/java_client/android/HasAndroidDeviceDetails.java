package io.appium.java_client.android;

import io.appium.java_client.CanRememberExtensionPresence;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import org.openqa.selenium.UnsupportedCommandException;

import java.util.Map;

import static io.appium.java_client.android.AndroidMobileCommandHelper.getDisplayDensityCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.getSystemBarsCommand;

public interface HasAndroidDeviceDetails extends ExecutesMethod, CanRememberExtensionPresence {

    /**
     Retrieve the display density of the Android device.

     @return The density value in dpi
     */
    default Long getDisplayDensity() {
        final String extName = "mobile: getDisplayDensity";
        try {
            return CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName);
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            return CommandExecutionHelper.execute(markExtensionAbsence(extName), getDisplayDensityCommand());
        }
    }

    /**
     Retrieve visibility and bounds information of the status and navigation bars.

     @return The map where keys are bar types and values are mappings of bar properties.
     */
    default Map<String, Map<String, Object>> getSystemBars() {
        final String extName = "mobile: getSystemBars";
        try {
            return CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName);
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            return CommandExecutionHelper.execute(markExtensionAbsence(extName), getSystemBarsCommand());
        }
    }

}
