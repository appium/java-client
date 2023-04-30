package io.appium.java_client.android;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import org.openqa.selenium.UnsupportedCommandException;

import static io.appium.java_client.android.AndroidMobileCommandHelper.openNotificationsCommand;

public interface HasNotifications extends ExecutesMethod {

    /**
     * Opens notification drawer on the device under test.
     */
    default void openNotifications() {
        try {
            CommandExecutionHelper.executeScript(this, "mobile: openNotifications");
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(this, openNotificationsCommand());
        }
    }
}
