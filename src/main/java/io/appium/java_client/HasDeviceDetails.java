package io.appium.java_client;

import static io.appium.java_client.MobileCommand.GET_DISPLAY_DENSITY;
import static io.appium.java_client.MobileCommand.GET_SYSTEM_BARS;

import java.util.Map;

public interface HasDeviceDetails extends ExecutesMethod {
    /*
        Retrieve the display density of the Android device.
     */
    default Long getDisplayDensity() {
        return CommandExecutionHelper.execute(this, GET_DISPLAY_DENSITY);
    }

    /*
        Retrieve visibility and bounds information of the status and navigation bars.
     */
    default Map<String, String> getSystemBars() {
        return CommandExecutionHelper.execute(this, GET_SYSTEM_BARS);
    }
}
