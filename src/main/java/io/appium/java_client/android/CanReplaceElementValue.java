package io.appium.java_client.android;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import io.appium.java_client.remote.AppiumWebElement;

import java.util.Map;

/** Provides replacing of the whole value of an element. */
public interface CanReplaceElementValue extends ExecutesMethod {
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
    default void replaceElementValue(AppiumWebElement element, String value) {
        final String extName = "mobile: replaceElementValue";
        CommandExecutionHelper.executeScript(this, extName, Map.of(
            "elementId", element.getId(),
            "text", value
        ));
    }
}
