package io.appium.java_client.android;

import io.appium.java_client.CanRememberExtensionPresence;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import io.appium.java_client.MobileCommand;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Map;

public interface CanReplaceElementValue extends ExecutesMethod, CanRememberExtensionPresence {
    /**
     * Sends a text to the given element by replacing its previous content.
     *
     * @param element The destination element.
     * @param value The text to enter. It could also contain Unicode characters.
     *              If the text ends with `\\n` (the backslash must be escaped, so the
     *              char is NOT translated into `0x0A`) then the Enter key press is going to
     *              be emulated after it is entered (the `\\n` substring itself will be cut
     *              off from the typed text).
     */
    default void replaceElementValue(RemoteWebElement element, String value) {
        final String extName = "mobile: replaceElementValue";
        try {
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, Map.of(
                "elementId", element.getId(),
                "text", value
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(
                    markExtensionAbsence(extName),
                    Map.entry(MobileCommand.REPLACE_VALUE, Map.of(
                            "id", element.getId(),
                            "text", value,
                            "value", value
                    ))
            );
        }
    }
}
