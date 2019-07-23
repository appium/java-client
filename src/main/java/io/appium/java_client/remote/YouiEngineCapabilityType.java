package io.appium.java_client.remote;

import org.openqa.seleniumone.remote.CapabilityType;

/**
 * The list of youiengine-specific capabilities.
 */
public interface YouiEngineCapabilityType extends CapabilityType {
    /**
     * IP address of the app to execute commands against.
     */
    String APP_ADDRESS = "youiEngineAppAddress";
}
