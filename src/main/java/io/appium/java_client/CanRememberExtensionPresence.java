package io.appium.java_client;

import org.openqa.selenium.UnsupportedCommandException;

public interface CanRememberExtensionPresence {
    /**
     * Verifies if the given extension is not present in the list of absent extensions
     * for the given driver instance.
     * This API is designed for private usage.
     *
     * @param extName extension name.
     * @return self instance for chaining.
     * @throws UnsupportedCommandException if the extension is listed in the list of absents.
     */
    ExecutesMethod assertExtensionExists(String extName);

    /**
     * Marks the given extension as absent for the given driver instance.
     * This API is designed for private usage.
     *
     * @param extName extension name.
     * @return self instance for chaining.
     */
    ExecutesMethod markExtensionAbsence(String extName);
}
