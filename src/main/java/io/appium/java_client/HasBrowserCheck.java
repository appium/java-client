package io.appium.java_client;

import io.appium.java_client.internal.CapabilityHelpers;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CapabilityType;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isBlank;

public interface HasBrowserCheck extends ExecutesMethod, HasCapabilities {
    /**
     * Validates if the driver is currently in a web browser context.
     *
     * @return true or false.
     */
    default boolean isBrowser() {
        String browserName = CapabilityHelpers.getCapability(getCapabilities(),
                CapabilityType.BROWSER_NAME, String.class);
        if (!isBlank(browserName)) {
            try {
                return checkNotNull(
                        CommandExecutionHelper.executeScript(this, "return !!window.navigator;")
                );
            } catch (WebDriverException ign) {
                // ignore
            }
        }
        if (!(this instanceof ContextAware)) {
            return false;
        }
        try {
            return !containsIgnoreCase(((ContextAware) this).getContext(), "NATIVE_APP");
        } catch (WebDriverException e) {
            return false;
        }
    }
}
