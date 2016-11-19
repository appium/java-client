package io.appium.java_client;


import static io.appium.java_client.android.AndroidMobileCommandHelper.getDeviceDensityCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.getSystemBarsCommand;

public interface HasDeviceDetails extends ExecutesMethod {
    /*
        Retrieve the display density of the Android device.
     */
    default String getDisplayDensity() {
        return CommandExecutionHelper.execute(this, getDeviceDensityCommand());
    }

    /*
        Retrieve visibility and bounds information of the status and navigation bars.
     */
    default String getSystemBars() {
        return CommandExecutionHelper.execute(this, getSystemBarsCommand());
    }
}
