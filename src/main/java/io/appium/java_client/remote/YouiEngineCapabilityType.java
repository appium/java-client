package io.appium.java_client.remote;

import org.openqa.selenium.remote.CapabilityType;

/**
 * The list of youiengine-specific capabilities.
 *
 * @deprecated This interface will be removed. Use Options instead.
 */
@Deprecated
public interface YouiEngineCapabilityType extends CapabilityType {
    /**
     * IP address of the app to execute commands against.
     */
    String APP_ADDRESS = "youiEngineAppAddress";
}
