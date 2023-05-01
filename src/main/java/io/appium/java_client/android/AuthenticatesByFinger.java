package io.appium.java_client.android;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.CanRememberExtensionPresence;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import org.openqa.selenium.UnsupportedCommandException;

import static io.appium.java_client.android.AndroidMobileCommandHelper.fingerPrintCommand;

public interface AuthenticatesByFinger extends ExecutesMethod, CanRememberExtensionPresence {

    /**
     * Authenticate users by using their finger print scans on supported emulators.
     *
     * @param fingerPrintId finger prints stored in Android Keystore system (from 1 to 10)
     */
    default void fingerPrint(int fingerPrintId) {
        final String extName = "mobile: fingerprint";
        try {
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, ImmutableMap.of(
                    "fingerprintId", fingerPrintId
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(markExtensionAbsence(extName), fingerPrintCommand(fingerPrintId));
        }
    }
}
