package io.appium.java_client.android;

/** Emulated GSM voice registration states. */
public enum GsmVoiceState {
    /** Voice service is on. */
    ON,
    /** Voice service is off. */
    OFF,
    /** Registration is denied. */
    DENIED,
    /** The device is searching for a network. */
    SEARCHING,
    /** The device is roaming. */
    ROAMING,
    /** The device is on its home network. */
    HOME,
    /** The device is not registered. */
    UNREGISTERED
}
