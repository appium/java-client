package io.appium.java_client.android;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;

import java.util.Map;

public interface AuthenticatesByFinger extends ExecutesMethod {

    /**
     * Authenticate users by using their finger print scans on supported emulators.
     *
     * @param fingerPrintId enrolled virtual fingerprint id passed to
     *                      {@code adb emu finger touch}, not the emulator UI Finger 1-10 labels
     */
    default void fingerPrint(int fingerPrintId) {
        final String extName = "mobile: fingerprint";
        CommandExecutionHelper.executeScript(this, extName, Map.of(
                "fingerprintId", fingerPrintId
        ));
    }
}
