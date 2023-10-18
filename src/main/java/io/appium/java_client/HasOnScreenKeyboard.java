package io.appium.java_client;

import org.openqa.selenium.UnsupportedCommandException;

import static io.appium.java_client.MobileCommand.isKeyboardShownCommand;
import static java.util.Objects.requireNonNull;

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
            return requireNonNull(CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            return requireNonNull(
                    CommandExecutionHelper.execute(markExtensionAbsence(extName), isKeyboardShownCommand())
            );
        }
    }
}
