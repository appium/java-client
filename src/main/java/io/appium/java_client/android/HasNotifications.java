package io.appium.java_client.android;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;

/** Provides access to the Android notifications drawer. */
public interface HasNotifications extends ExecutesMethod {

    /**
     * Opens notification drawer on the device under test.
     */
    default void openNotifications() {
        final String extName = "mobile: openNotifications";
        CommandExecutionHelper.executeScript(this, extName);
    }
}
