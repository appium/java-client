package io.appium.java_client.android;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;

import java.util.Map;

/** Provides Android device details, such as the display density and system bars. */
public interface HasAndroidDeviceDetails extends ExecutesMethod {

    /**
     Retrieve the display density of the Android device.

     @return The density value in dpi
     */
    default Long getDisplayDensity() {
        final String extName = "mobile: getDisplayDensity";
        return CommandExecutionHelper.executeScript(this, extName);
    }

    /**
     Retrieve visibility and bounds information of the status and navigation bars.

     @return The map where keys are bar types and values are mappings of bar properties.
     */
    default Map<String, Map<String, Object>> getSystemBars() {
        final String extName = "mobile: getSystemBars";
        return CommandExecutionHelper.executeScript(this, extName);
    }

}
