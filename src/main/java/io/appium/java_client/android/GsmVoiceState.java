package io.appium.java_client.android;

public enum GsmVoiceState {
    ON("on"),
    OFF("off"),
    DENIED("denied"),
    SEARCHING("searching"),
    ROAMING("roaming"),
    HOME("home"),
    UNREGISTERED("unregistered");

    private final String gsmVoiceState;

    GsmVoiceState(String gsmVoiceState) {
        this.gsmVoiceState = gsmVoiceState;
    }

    @Override public String toString() {
        return this.gsmVoiceState;
    }
}
