package io.appium.java_client.android;

import static io.appium.java_client.android.AndroidMobileCommandHelper.fingerPrintCommand;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;

public interface AuthenticatesByFinger extends ExecutesMethod {

    /**
     * Authenticate users by using their finger print scans on supported emulators.
     *
     * @param fingerPrintId finger prints stored in Android Keystore system (from 1 to 10)
     */
    default void fingerPrint(int fingerPrintId) {
        CommandExecutionHelper.execute(this, fingerPrintCommand(fingerPrintId));
    }
}
