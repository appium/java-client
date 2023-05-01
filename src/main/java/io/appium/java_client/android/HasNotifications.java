package io.appium.java_client.android;

import io.appium.java_client.CanRememberExtensionPresence;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import org.openqa.selenium.UnsupportedCommandException;

import static io.appium.java_client.android.AndroidMobileCommandHelper.openNotificationsCommand;

public interface HasNotifications extends ExecutesMethod, CanRememberExtensionPresence {

    /**
     * Opens notification drawer on the device under test.
     */
    default void openNotifications() {
        final String extName = "mobile: openNotifications";
        try {
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName);
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(markExtensionAbsence(extName), openNotificationsCommand());
        }
    }
}
