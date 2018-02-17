package io.appium.java_client.android;

public enum GsmSignalStrength {
    NONE_OR_UNKNOWN(0),
    POOR(1),
    MODERATE(2),
    GOOD(3),
    GREAT(4);

    private final int gsmSignalStrength;

    GsmSignalStrength(int gsmSignalStrength) {
        this.gsmSignalStrength = gsmSignalStrength;
    }

    public int getValue() { return gsmSignalStrength; }
}
