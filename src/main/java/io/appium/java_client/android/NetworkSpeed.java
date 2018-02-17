package io.appium.java_client.android;

public enum NetworkSpeed {
    GSM("gsm"),
    SCSD("scsd"),
    GPRS("gprs"),
    EDGE("edge"),
    UMTS("umts"),
    HSDPA("hsdpa"),
    LTE("lte"),
    EVDO("evdo"),
    FULL("full");

    private final String networkSpeed;

    NetworkSpeed(String networkSpeed) {
        this.networkSpeed = networkSpeed;
    }

    @Override public String toString() {
        return this.networkSpeed;
    }
}
