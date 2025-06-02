package io.appium.java_client;

import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.remote.SupportsContextSwitching;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CapabilityType;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Objects.requireNonNull;

public interface HasBrowserCheck extends ExecutesMethod, HasCapabilities {
    String NATIVE_CONTEXT = "NATIVE_APP";

    /**
     * Validates if the driver is currently in a web browser context.
     *
     * @return true or false.
     */
    default boolean isBrowser() {
        String browserName = CapabilityHelpers.getCapability(getCapabilities(),
                CapabilityType.BROWSER_NAME, String.class);
        if (!isNullOrEmpty(browserName)) {
            try {
                return requireNonNull(
                        CommandExecutionHelper.executeScript(this, "return !!window.navigator;")
                );
            } catch (WebDriverException ign) {
                // ignore
            }
        }
        if (!(this instanceof SupportsContextSwitching)) {
            return false;
        }
        try {
            var context = ((SupportsContextSwitching) this).getContext();
            return context != null && !context.toUpperCase().contains(NATIVE_CONTEXT);
        } catch (WebDriverException e) {
            return false;
        }
    }
}
