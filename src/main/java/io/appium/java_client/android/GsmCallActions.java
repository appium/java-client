package io.appium.java_client.android;

public enum GsmCallActions {
    CALL("call"),
    ACCEPT("accept"),
    CANCEL("cancel"),
    HOLD("hold");

    private final String gsmcall;

    GsmCallActions(String gsmcall) {
        this.gsmcall = gsmcall;
    }

    @Override public String toString() {
        return this.gsmcall;
    }
}
