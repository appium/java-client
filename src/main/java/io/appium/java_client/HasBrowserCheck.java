package io.appium.java_client;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.internal.CapabilityHelpers;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CapabilityType;

import java.util.Collections;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.openqa.selenium.remote.DriverCommand.EXECUTE_SCRIPT;

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
                return (boolean) execute(EXECUTE_SCRIPT, ImmutableMap.of(
                        "script", "return !!window.navigator;",
                        "args", Collections.emptyList()
                )).getValue();
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
