package io.appium.java_client.android;

public enum PowerACState {
    ON("on"),
    OFF("off");

    private final String powerACState;

    PowerACState(String powerACState) {
        this.powerACState = powerACState;
    }

    @Override public String toString() {
        return this.powerACState;
    }
}
