package io.appium.java_client;

import static io.appium.java_client.MobileCommand.isKeyboardShownCommand;

public interface HasDeviceDetails extends ExecutesMethod {

    /**
     * Check if the keyboard is displayed.
     *
     * @return true if keyboard is displayed. False otherwise
     */
    default boolean isKeyboardShown() {
        return CommandExecutionHelper.execute(this, isKeyboardShownCommand());
    }
}
