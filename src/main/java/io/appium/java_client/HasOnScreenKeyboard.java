package io.appium.java_client;

import static java.util.Objects.requireNonNull;

/**
 * The interface for the drivers that can check whether the on-screen keyboard is displayed.
 */
public interface HasOnScreenKeyboard extends ExecutesMethod {

    /**
     * Check if the on-screen keyboard is displayed.
     * See the documentation for 'mobile: isKeyboardShown' extension for more details.
     *
     * @return true if keyboard is displayed. False otherwise
     */
    default boolean isKeyboardShown() {
        final String extName = "mobile: isKeyboardShown";
        return requireNonNull(CommandExecutionHelper.executeScript(this, extName));
    }
}
