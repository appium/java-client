package io.appium.java_client;

import org.openqa.selenium.UnsupportedCommandException;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.appium.java_client.MobileCommand.isKeyboardShownCommand;

public interface HasOnScreenKeyboard extends ExecutesMethod, CanRememberExtensionPresence {

    /**
     * Check if the on-screen keyboard is displayed.
     * See the documentation for 'mobile: isKeyboardShown' extension for more details.
     *
     * @return true if keyboard is displayed. False otherwise
     */
    default boolean isKeyboardShown() {
        final String extName = "mobile: isKeyboardShown";
        try {
            return checkNotNull(CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            return checkNotNull(
                    CommandExecutionHelper.execute(markExtensionAbsence(extName), isKeyboardShownCommand())
            );
        }
    }
}
