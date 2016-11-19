package io.appium.java_client.android;

import static io.appium.java_client.android.AndroidMobileCommandHelper.isKeyboardShownCommand;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;

public interface IsKeyboardShown extends ExecutesMethod {
    /**
     * Check if the keyboard is displayed.
     *
     * @return true if keyboard is displayed. False otherwise
     */
    default boolean isKeyboardShown() {
        return CommandExecutionHelper.execute(this, isKeyboardShownCommand());
    }
}
